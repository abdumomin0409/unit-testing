package com.coreteam.languageservice.repository.foreign_lang;

import com.coreteam.languageservice.dto.foreign_lang.ForeignLangCreateDTO;
import com.coreteam.languageservice.dto.foreign_lang.ForeignLangUpdateDTO;
import com.coreteam.languageservice.handler.DataBaseException;
import com.coreteam.languageservice.model.foreign_lang.ForeignLangDB;
import com.coreteam.languageservice.repository.BaseRepository;
import com.coreteam.languageservice.repository.modul.ModulRepository;
import com.coreteam.languageservice.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@RequiredArgsConstructor
public class ForeignLangRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JsonUtils jsonUtils;
    private final ModulRepository modulRepository;

    private final Logger LOGGER = Logger.getLogger(ForeignLangRepository.class.getName());

    public Integer create(ForeignLangCreateDTO dto) {
        String sql = "CALL service_procedure.create_foreign_language(?, ?);";
        String fromObject = jsonUtils.jsonFromObject(dto);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setString(1, fromObject);
                call.registerOutParameter(2, Types.INTEGER);
                return call;
            }, (CallableStatementCallback<Integer>) cs -> {
                cs.execute();
                int anInt = cs.getInt(2);
                LOGGER.info("Create foreign language returned : " + anInt);
                return anInt;
            });
        } catch (DataAccessException exception) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", exception.getMessage());
            throw new DataBaseException("Invalid input!", -1496012);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean update(ForeignLangUpdateDTO data) {
        String sql = "CALL service_procedure.update_foreign_language(?, ?);";
        LOGGER.info("Update foreign language request to db by query  : " + sql);
        String jsonFromObject = jsonUtils.jsonFromObject(data);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setString(1, jsonFromObject);
                call.registerOutParameter(2, Types.BOOLEAN);
                return call;
            }, (CallableStatementCallback<Boolean>) cs -> {
                cs.execute();
                boolean anInt = cs.getBoolean(2);
                LOGGER.info("Update foreign language returned : " + anInt);
                return anInt;
            });
        } catch (DataAccessException exception) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", exception.getMessage());
            throw new DataBaseException("Invalid input!", -1496013);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean delete(Integer id) {
        String sql = "CALL service_procedure.delete_foreign_language(?, ?);";
        LOGGER.info("Delete foreign language request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                call.registerOutParameter(2, Types.BOOLEAN);
                return call;
            }, (CallableStatementCallback<Boolean>) cs -> {
                cs.execute();
                boolean anInt = cs.getBoolean(2);
                LOGGER.info("delete foreign language returned : " + anInt);
                return anInt;
            });
        } catch (DataAccessException exception) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", exception.getMessage());
            throw new DataBaseException("Invalid input!", -1496014);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public ForeignLangDB get(Integer id) {
        String sql = "CALL service_procedure.get_foreign_language(?, ?);";
        LOGGER.info("Language foreign language request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<ForeignLangDB>) cs -> {
                cs.execute();
                String anInt = cs.getString(2);
                ForeignLangDB foreignLangDB = null;
                LOGGER.info("get foreign language returned : " + anInt);
                try {
                    Map<String, Object> json = jsonUtils.parseMapFromJson(anInt);
                    Integer langID = (Integer) json.get("foreign_language_id");
                    Integer modulID = (Integer) json.get("modul_id");
                    String tConst = (String) json.get("t_const");
                    String languageName = (String) json.get("lang");
                    String shortName = (String) json.get("lang_short");
                    String text = (String) json.get("text");
                    String description = String.valueOf(json.get("description"));
                    Integer created_by_user = null;
                    Integer createdByUser = (Integer) json.get("created_by_user");
                    if (!Objects.isNull(createdByUser))
                        created_by_user = createdByUser;
                    LocalDateTime created_datetime = LocalDateTime.parse((String) json.get("created_datetime"));
                    Integer status = (Integer) json.get("status");
                    foreignLangDB = new ForeignLangDB(langID, tConst, modulID, languageName, shortName, text, description, created_datetime, created_by_user, status);
                } catch (NullPointerException e) {
                    LOGGER.info("Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1496015);
                }
                return foreignLangDB;
            });
        } catch (DataAccessException exception) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", exception.getMessage());
            throw new DataBaseException("Invalid input!", -1496016);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public ForeignLangDB getByTConst(String tConstt) {
        String sql = "CALL service_procedure.get_foreign_language_by_tConst(?, ?);";
        LOGGER.info("Language foreign language by t_const request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setString(1, tConstt);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<ForeignLangDB>) cs -> {
                cs.execute();
                String anInt = cs.getString(2);
                LOGGER.info("get foreign language returned : " + anInt);
                ForeignLangDB foreignLangDB = null;
                try {
                    Map<String, Object> json = jsonUtils.parseMapFromJson(anInt);
                    Integer langID = (Integer) json.get("foreign_language_id");
                    Integer modulID = (Integer) json.get("modul_id");
                    String tConst = (String) json.get("t_const");
                    String languageName = (String) json.get("lang");
                    String shortName = (String) json.get("lang_short");
                    String text = (String) json.get("text");
                    String description = String.valueOf(json.get("description"));
                    Integer created_by_user = null;
                    Integer createdByUser = (Integer) json.get("created_by_user");
                    if (!Objects.isNull(createdByUser))
                        created_by_user = createdByUser;
                    LocalDateTime created_datetime = LocalDateTime.parse((String) json.get("created_datetime"));
                    Integer status = (Integer) json.get("status");
                    foreignLangDB = new ForeignLangDB(langID, tConst, modulID, languageName, shortName, text, description, created_datetime, created_by_user, status);
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s", e.getMessage());
                    throw new DataBaseException("Invalid input", -1496017);
                }
                return foreignLangDB;
            });
        } catch (DataAccessException exception) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", exception.getMessage());
            throw new DataBaseException("Invalid input!", -1496018);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<ForeignLangDB> getAll() {
        String sql = "call service_procedure.get_all_foreign_language(?);";
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.registerOutParameter(1, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<ForeignLangDB>>) cs -> {
                cs.execute();
                String type = cs.getString(1);
                LOGGER.log(Level.CONFIG, "get all foreign language", List.of(type));
                List<ForeignLangDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer langID = jsonObject.getInt("foreign_language_id");
                        Integer modulID = jsonObject.getInt("modul_id");
                        String tConst = jsonObject.getString("t_const");
                        String languageName = jsonObject.getString("lang");
                        String description = String.valueOf(jsonObject.get("description"));
                        String shortName = jsonObject.getString("lang_short");
                        String text = jsonObject.getString("text");
                        Integer created_by_user = jsonObject.getInt("created_by_user");
                        LocalDateTime created_datetime = LocalDateTime.parse(jsonObject.getString("created_datetime"));
                        Integer status = jsonObject.getInt("status");
                        list.add(new ForeignLangDB(langID, tConst, modulID, languageName, shortName, text, description, created_datetime, created_by_user, status));
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1496019);
                }
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1496020);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<ForeignLangDB> getAllActive() {
        String sql = "call service_procedure.get_all_active_foreign_language(?);";
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.registerOutParameter(1, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<ForeignLangDB>>) cs -> {
                cs.execute();
                String type = cs.getString(1);
                List<ForeignLangDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer langID = jsonObject.getInt("foreign_language_id");
                        String tConst = jsonObject.getString("t_const");
                        String languageName = jsonObject.getString("lang");
                        String shortName = jsonObject.getString("lang_short");
                        String text = jsonObject.getString("text");
                        String description = String.valueOf(jsonObject.get("description"));
                        Integer created_by_user = jsonObject.getInt("created_by_user");
                        LocalDateTime created_datetime = LocalDateTime.parse(jsonObject.getString("created_datetime"));
                        Integer status = jsonObject.getInt("status");
                        Integer modulId = jsonObject.getInt("modul_id");
                        list.add(new ForeignLangDB(langID, tConst, modulId, languageName, shortName, text, description, created_datetime, created_by_user, status));
                    }
                    LOGGER.log(Level.CONFIG, "get all active foreign language", List.of(type));
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1496021);
                }
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1496022);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<ForeignLangDB> getAllByStatus(Integer statusId) {
        String sql = "call service_procedure.get_all_foreign_language_by_status(?, ?);";
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, statusId);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<ForeignLangDB>>) cs -> {
                cs.execute();
                String type = cs.getString(2);
                List<ForeignLangDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer langID = jsonObject.getInt("foreign_language_id");
                        String tConst = jsonObject.getString("t_const");
                        String languageName = jsonObject.getString("lang");
                        String shortName = jsonObject.getString("lang_short");
                        String text = jsonObject.getString("text");
                        String description = String.valueOf(jsonObject.get("description"));
                        Integer created_by_user = jsonObject.getInt("created_by_user");
                        LocalDateTime created_datetime = LocalDateTime.parse(jsonObject.getString("created_datetime"));
                        Integer status = jsonObject.getInt("status");
                        Integer modulID = jsonObject.getInt("modul_id");
                        list.add(new ForeignLangDB(langID, tConst, modulID, languageName, shortName, text, description, created_datetime, created_by_user, status));
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1496023);
                }
                LOGGER.log(Level.CONFIG, "get all foreign language by status id", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1496024);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<ForeignLangDB> getAllByModul(Integer modulId) {
        String sql = "call service_procedure.get_all_foreign_language_by_modul(?, ?);";
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, modulId);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<ForeignLangDB>>) cs -> {
                cs.execute();
                String type = cs.getString(2);
                List<ForeignLangDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer langID = jsonObject.getInt("foreign_language_id");
                        String tConst = jsonObject.getString("t_const");
                        String languageName = jsonObject.getString("lang");
                        String shortName = jsonObject.getString("lang_short");
                        String text = jsonObject.getString("text");
                        String description = String.valueOf(jsonObject.get("description"));
                        Integer created_by_user = jsonObject.getInt("created_by_user");
                        LocalDateTime created_datetime = LocalDateTime.parse(jsonObject.getString("created_datetime"));
                        Integer status = jsonObject.getInt("status");
                        Integer modulID = jsonObject.getInt("modul_id");
                        list.add(new ForeignLangDB(langID, tConst, modulID, languageName, shortName, text, description, created_datetime, created_by_user, status));
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1496025);
                }
                LOGGER.log(Level.CONFIG, "get all foreign language by status id", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1496026);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean existsForeignLangById(Integer id) {
        String sql = "call utils.exists_foreign_lang_by_id(?, ?);";
        String message = "call exists foreign lang by id ";
        return modulRepository.getResultExistsId(id, sql, message, -1496027);
    }

    public Boolean existsForeignLangByModuleId(Integer id) {
        String sql = "call utils.exists_foreign_lang_by_module_id(?, ?);";
        String message = "call exists foreign lang by module id ";
        return modulRepository.getResultExistsId(id, sql, message, -1496028);
    }

    public Boolean existsForeignLangByTConst(String tConst) {
        String sql = "call utils.exists_foreign_lang_by_t_const(?, ?);";
        String message = "call exists foreign lang by t_const";
        return modulRepository.getResultExistsName(tConst, sql, message, -1496029);
    }

}
