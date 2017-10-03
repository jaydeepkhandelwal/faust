package com.mt.faust.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mt.faust.model.Record;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by jaydeep.k on 03/10/17.
 */


@Slf4j
public class EventDAOImpl extends BaseDAOImpl<Record, Long> {
    private final static String UPSERT_RECORD =
        "INSERT INTO records "
            + "(id, type, event, created_at, updated_at) "
            + "VALUES "
            + "(?,?,?, now(), now()) "
            + "ON DUPLICATE KEY UPDATE "
            + "event = VALUES(event), type = VALUES(type)";

    private static final String READ_RECORD =
        "select * from records where id in ";

    private static final String DELETE_RECORD =
        "delete from records where id in ";


    private static IBaseDAO INSTANCE = new EventDAOImpl();

    public static IBaseDAO getInstance() {
        return INSTANCE;
    }


    protected PreparedStatement getSetterPreparedStatement(Connection connection, Record record) throws SQLException,
        JsonProcessingException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPSERT_RECORD, Statement.RETURN_GENERATED_KEYS);
        try {
            if (record.getId() != null) {
                preparedStatement.setLong(1, record.getId());
            } else {
                preparedStatement.setNull(1, Types.BIGINT);
            }
            preparedStatement.setString(2, record.getType());

            preparedStatement.setObject(3,  objectMapper.writeValueAsString(record.getEvent()));
        } catch (SQLException e ) {
            log.error("Couldn't set values in prepared statement");
            preparedStatement.close();
            throw e;
        } catch (JsonProcessingException e) {
            preparedStatement.close();
            throw e;
        }
        return preparedStatement;
    }


    protected PreparedStatement getDeletePreparedStatement(Connection connection, List<Long> ids) throws SQLException {
        if(null == ids) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for( int i = 0 ; i < ids.size(); i++ ) {
            builder.append("?,");
        }
        String statement = DELETE_RECORD +  '(' + builder.deleteCharAt( builder.length() -1 ).toString() + ')';
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        try {
            int index = 1;
            for(Long id : ids) {
                preparedStatement.setLong(index++, id);
            }
        } catch (SQLException e) {
            log.error("Couldn't set values in prepared statement");
            preparedStatement.close();
            throw e;
        }
        return preparedStatement;
    }


    protected PreparedStatement getGetterPreparedStatement(Connection connection, List<Long> ids) throws SQLException {
        if(null == ids) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for( int i = 0 ; i < ids.size(); i++ ) {
            builder.append("?,");
        }
        String statement = READ_RECORD +  '(' + builder.deleteCharAt( builder.length() -1 ).toString() + ')';
        PreparedStatement preparedStatement = connection.prepareStatement(statement);

        try {
           int index = 1;
           for(Long id : ids) {
               preparedStatement.setLong(index++, id);
           }
        } catch (SQLException e) {
            log.error("Couldn't set values in prepared statement");
            preparedStatement.close();
            throw e;
        }

        return preparedStatement;


    }
    @Override
    protected Record setResultSet(ResultSet rs) throws SQLException {

        Record record = new Record();
        String eventStr = rs.getString("event");
        Object event = null;
        try {
            event = objectMapper.readValue(eventStr, Map.class);
        } catch (IOException e) {
            log.error("io exception, e = {}", e);
            return record;
        }
        record.setEvent(event);
        record.setId(rs.getLong("id"));
        record.setType(rs.getString("type"));
        return record;
    }

}
