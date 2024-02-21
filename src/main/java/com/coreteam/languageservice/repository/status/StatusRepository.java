package com.coreteam.languageservice.repository.status;

import com.coreteam.languageservice.handler.DataBaseException;
import com.coreteam.languageservice.model.status.StatusDB;
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
import org.webjars.NotFoundException;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@RequiredArgsConstructor
public class StatusRepository implements BaseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JsonUtils jsonUtils;
    private final ModulRepository modulRepository;
    private final Logger LOGGER = Logger.getLogger(StatusRepository.class.getName());

    public StatusDB get(Integer id) {
        String sql = "CALL service_procedure.get_status(?, ?);";
        LOGGER.info("Get status request to db by query  : " + sql);
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.setInt(1, id);
                call.registerOutParameter(2, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<StatusDB>) cs -> {
                cs.execute();
                String anInt = cs.getString(2);
                LOGGER.info("Get status returned : " + anInt);
                StatusDB statusDB;
                try {
                    Map<String, Object> json = jsonUtils.parseMapFromJson(anInt);
                    Integer statusId = (Integer) json.get("status_id");
                    String statusName = (String) json.get("status_name");
                    statusDB = new StatusDB(statusId, statusName);
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1497002);
                }
                return statusDB;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> ", e.getMessage());
            throw new DataBaseException("Invalid input", -1497003);
        } finally {
            jsonUtils.closeConnection();
        }
    }

    public List<StatusDB> getAll() {
        String sql = "call service_procedure.get_all_status(?);";
        try {
            return jdbcTemplate.execute(con -> {
                CallableStatement call = con.prepareCall(sql);
                call.registerOutParameter(1, Types.VARCHAR);
                return call;
            }, (CallableStatementCallback<List<StatusDB>>) cs -> {
                cs.execute();
                String type = cs.getString(1);
                if (Objects.isNull(type) || type.equals("[]"))
                    throw new NotFoundException("Hechnarsa topilmadi");
                List<StatusDB> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(type);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer statusId = jsonObject.getInt("status_id");
                        String statusName = jsonObject.getString("status_name");
                        list.add(new StatusDB(statusId, statusName));
                    }
                } catch (NullPointerException e) {
                    LOGGER.log(Level.INFO, "Database exception => %s" + e.getMessage());
                    throw new DataBaseException("Invalid input", -1497004);
                }
                LOGGER.log(Level.CONFIG, "get all types", List.of(type));
                return list;
            });
        } catch (DataAccessException e) {
            LOGGER.log(Level.CONFIG, "Database exception --> %s" + e.getMessage());
            throw new DataBaseException("Invalid input", -1497005);
        } finally {
            jsonUtils.closeConnection();
        }
    }


    public Boolean existsStatusById(Integer id) {
        String sql = "CALL utils.exists_status_by_id(?, ?);";
        String message = "Call exists status by id ";
        return modulRepository.getResultExistsId(id, sql, message, -1497006);
    }
}
