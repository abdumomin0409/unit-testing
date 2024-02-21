package com.coreteam.languageservice.repository.language;

import com.coreteam.languageservice.dto.language.LanguageCreateDTO;
import com.coreteam.languageservice.dto.language.LanguageUpdateDTO;
import com.coreteam.languageservice.handler.DataBaseException;
import com.coreteam.languageservice.model.language.LanguageDB;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@RequiredArgsConstructor
public class LanguageRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JsonUtils jsonUtils;
    private final ModulRepository modulRepository;

    private final Logger LOGGER = Logger.getLogger(LanguageRepository.class.getName());

    public Integer create(LanguageCreateDTO dto) {
        String sql = "CALL service_procedure.create_language(?, ?);";
        LOGGER.info("Create language request to db by query  : " + sql);
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
                LOGGER.info("Create status returned : " + anInt);
                return anInt;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1498007);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean update(LanguageUpdateDTO data) {
        LOGGER.info("Update language : " + data);
        String sql = "CALL service_procedure.update_language(?, ?);";
        LOGGER.info("Update language request to db by query  : " + sql);
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
                LOGGER.info("Update status returned : " + anInt);
                return anInt;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1498008);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean delete(Integer id) {
        LOGGER.info("Delete language : " + id);
        String sql = "CALL service_procedure.delete_language(?, ?);";
        LOGGER.info("Delete language request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                call.registerOutParameter(2, Types.BOOLEAN);
                return call;
            }, (CallableStatementCallback<Boolean>) cs -> {
                cs.execute();
                boolean anInt = cs.getBoolean(2);
                LOGGER.info("delete status returned : " + anInt);
                return anInt;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1498009);
        } finally {
            jsonUtils.closeConnection();
        }
    }


    public LanguageDB get(Integer id) {
        String sql = "CALL service_procedure.get_language(?, ?);";
        LOGGER.info("Language language request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<LanguageDB>) cs -> {
                cs.execute();
                String anInt = cs.getString(2);
                LOGGER.info("get status returned : " + anInt);
                LanguageDB languageDB = null;
                try {
                    Map<String, Object> json = jsonUtils.parseMapFromJson(anInt);
                    Integer languageId = (Integer) json.get("language_id");
                    String languageName = (String) json.get("name");
                    String shortName = (String) json.get("short_name");
                    Integer status = (Integer) json.get("status");
                    languageDB = new LanguageDB(languageId, languageName, shortName, status);
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1498010);
                }
                return languageDB;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1498011);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<LanguageDB> getAll() {
        String sql = "call service_procedure.get_all_language(?);";
        LOGGER.info("Get all status calling");
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.registerOutParameter(1, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<LanguageDB>>) cs -> {
                cs.execute();
                String type = cs.getString(1);
                List<LanguageDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer languageId = jsonObject.getInt("language_id");
                        String languageName = (String) jsonObject.get("name");
                        String shortName = (String) jsonObject.get("short_name");
                        Integer status = (Integer) jsonObject.get("status");
                        LanguageDB statusDB = new LanguageDB(languageId, languageName, shortName, status);
                        list.add(statusDB);
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1498012);
                }
                LOGGER.log(Level.CONFIG, "get all types", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1498013);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<LanguageDB> getAllActive() {
        String sql = "call service_procedure.get_all_active_language(?);";
        LOGGER.log(Level.CONFIG, "Get all active language calling");
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.registerOutParameter(1, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<LanguageDB>>) cs -> {
                cs.execute();
                String type = cs.getString(1);
                List<LanguageDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer languageId = jsonObject.getInt("language_id");
                        String languageName = (String) jsonObject.get("name");
                        String shortName = (String) jsonObject.get("short_name");
                        Integer status = (Integer) jsonObject.get("status");
                        LanguageDB statusDB = new LanguageDB(languageId, languageName, shortName, status);
                        list.add(statusDB);
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1498014);
                }
                LOGGER.log(Level.CONFIG, "get all active language", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1498015);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<LanguageDB> getAllByStatus(Integer statusId) {
        String sql = "call service_procedure.get_all_language_by_status(?, ?);";
        LOGGER.log(Level.CONFIG, "Get all status calling");
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, statusId);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<LanguageDB>>) cs -> {
                cs.execute();
                String type = cs.getString(2);
                List<LanguageDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer languageId = jsonObject.getInt("language_id");
                        String languageName = (String) jsonObject.get("name");
                        String shortName = (String) jsonObject.get("short_name");
                        Integer status = (Integer) jsonObject.get("status");
                        LanguageDB statusDB = new LanguageDB(languageId, languageName, shortName, status);
                        list.add(statusDB);
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1498016);
                }
                LOGGER.log(Level.CONFIG, "get all types", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1498017);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean existsLanguageByName(String name) {
        String sql = "Call utils.exists_language_by_name(?, ?);";
        String message = "Call exists language by name ";
        return modulRepository.getResultExistsName(name, sql, message, -1498018);
    }

    public Boolean existsLanguageByShortName(String shortName) {
        String sql = "Call utils.exists_language_by_short_name(?, ?);";
        String message = "Call exists language by short name ";
        return modulRepository.getResultExistsName(shortName, sql, message, -1498019);
    }

    public Boolean existsLanguageById(Integer id) {
        String sql = "Call utils.exists_language_by_id(?, ?);";
        String message = "Call exists language by id ";
        return modulRepository.getResultExistsId(id, sql, message, -1498020);
    }

}
