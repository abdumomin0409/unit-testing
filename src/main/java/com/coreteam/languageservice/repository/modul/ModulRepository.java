package com.coreteam.languageservice.repository.modul;

import com.coreteam.languageservice.dto.modul.ModulCreateDTO;
import com.coreteam.languageservice.dto.modul.ModulUpdateDTO;
import com.coreteam.languageservice.handler.DataBaseException;
import com.coreteam.languageservice.model.modul.ModulDB;
import com.coreteam.languageservice.repository.BaseRepository;
import com.coreteam.languageservice.utils.JsonUtils;
import io.micrometer.common.lang.Nullable;
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
public class ModulRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JsonUtils jsonUtils;

    private final Logger LOGGER = Logger.getLogger(ModulRepository.class.getName());

    public Integer create(ModulCreateDTO dto) {
        LOGGER.info("Create modul : " + dto);
        String sql = "CALL service_procedure.create_modul(?, ?);";
        LOGGER.info("Create modul request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setString(1, dto.getName());
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
            throw new DataBaseException("Invalid input!", -1499005);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean update(ModulUpdateDTO data) {
        LOGGER.info("Update modul : " + data);
        String sql = "CALL service_procedure.update_modul(?, ?);";
        LOGGER.info("Update modul request to db by query  : " + sql);
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
            throw new DataBaseException("Invalid input", -149906);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean delete(Integer id) {
        LOGGER.info("Delete modul : " + id);
        String sql = "CALL service_procedure.delete_modul(?, ?);";
        LOGGER.info("Delete modul request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                call.registerOutParameter(2, Types.BOOLEAN);
                return call;
            }, (CallableStatementCallback<Boolean>) cs -> {
                cs.execute();
                boolean anInt = cs.getBoolean(2);
                LOGGER.info("delete modul returned : " + anInt);
                return anInt;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input", -1499007);
        } finally {
            jsonUtils.closeConnection();
        }
    }


    public ModulDB get(Integer id) {
        String sql = "CALL service_procedure.get_modul(?, ?);";
        LOGGER.info("GET modul request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<ModulDB>) cs -> {
                cs.execute();
                String anInt = cs.getString(2);
                LOGGER.info("get modul returned : " + anInt);
                ModulDB modulDB = null;
                try {
                    Map<String, Object> json = jsonUtils.parseMapFromJson(anInt);
                    Integer modulId = (Integer) json.get("modul_id");
                    String modulName = (String) json.get("name");
                    Integer status = (Integer) json.get("status");
                    modulDB = new ModulDB(modulId, modulName, status);
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1499008);
                }
                return modulDB;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1499009);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<ModulDB> getAll() {
        String sql = "call service_procedure.get_all_modules(?);";
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.registerOutParameter(1, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<ModulDB>>) cs -> {
                cs.execute();
                String type = cs.getString(1);
                List<ModulDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer modulId = jsonObject.getInt("modul_id");
                        String modulName = (String) jsonObject.get("name");
                        Integer status = (Integer) jsonObject.get("status");
                        ModulDB statusDB = new ModulDB(modulId, modulName, status);
                        list.add(statusDB);
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1499010);
                }
                LOGGER.log(Level.CONFIG, "get all types", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1499011);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<ModulDB> getAllActive() {
        String sql = "call service_procedure.get_all_active_modules(?);;";
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.registerOutParameter(1, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<ModulDB>>) cs -> {
                cs.execute();
                String type = cs.getString(1);
                List<ModulDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer modulId = jsonObject.getInt("modul_id");
                        String modulName = (String) jsonObject.get("name");
                        Integer status = (Integer) jsonObject.get("status");
                        ModulDB statusDB = new ModulDB(modulId, modulName, status);
                        list.add(statusDB);
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1499012);
                }
                LOGGER.log(Level.CONFIG, "get all types", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1499013);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<ModulDB> getAllByStatus(Integer statusId) {
        String sql = "call service_procedure.get_all_modules_by_status(?, ?);";
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, statusId);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<ModulDB>>) cs -> {
                cs.execute();
                String type = cs.getString(2);
                List<ModulDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer modulId = jsonObject.getInt("modul_id");
                        String modulName = (String) jsonObject.get("name");
                        Integer status = (Integer) jsonObject.get("status");
                        ModulDB statusDB = new ModulDB(modulId, modulName, status);
                        list.add(statusDB);
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1499014);
                }
                LOGGER.log(Level.CONFIG, "get all types", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", -1499015);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean existsModuleByName(String name) {
        String sql = "CALL utils.exists_module_by_name(?, ?);";
        String message = "Call exists module by name ";
        return getResultExistsName(name, sql, message, -1499016);
    }

    @Nullable
    public Boolean getResultExistsName(String name, String sql, String message, Integer status) {
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setString(1, name);
                call.registerOutParameter(2, Types.BOOLEAN);
                return call;
            }, (CallableStatementCallback<Boolean>) cs -> {
                cs.execute();
                boolean aBoolean = cs.getBoolean(2);
                LOGGER.log(Level.CONFIG, message + aBoolean);
                return aBoolean;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!", status);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public Boolean existsModuleById(Integer id) {
        String sql = "CALL utils.exists_module_by_id(?, ?);";
        String message = "Call exists module by id ";
        return getResultExistsId(id, sql, message, -1498017);
    }

    @Nullable
    public Boolean getResultExistsId(Integer id, String sql, String message, Integer status) {
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                call.registerOutParameter(2, Types.BOOLEAN);
                return call;
            }, (CallableStatementCallback<Boolean>) cs -> {
                cs.execute();
                boolean aBoolean = cs.getBoolean(2);
                LOGGER.log(Level.CONFIG, message + aBoolean);
                return aBoolean;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input!" + e.getMessage(), status);
        } finally {
            jsonUtils.closeConnection();
        }
    }

}
