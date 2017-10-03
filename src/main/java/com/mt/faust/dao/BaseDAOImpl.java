package com.mt.faust.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.faust.util.DBUtil;
import com.mt.faust.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaydeep.k on 03/10/17.
 */
@Slf4j
public abstract class BaseDAOImpl<T, I> implements IBaseDAO<T, I> {
    private static final DBUtil dbUtil = DBUtil.getInstance();
    protected static final ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapperConvertStyle();

    /*private PreparedStatement preparedStatement;
    private Connection connection;
    private ResultSet rs;*/
    private void closeConnection(PreparedStatement preparedStatement, Connection connection, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            log.error("Failed to close resultset :", e);
        }
        try {
            if (preparedStatement != null && !preparedStatement.isClosed())
                preparedStatement.close();
        } catch (SQLException e) {
            log.error("Failed to close statement.");
        }
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException se) {
            log.error("Failed to close database connection");
        }
    }

    protected abstract PreparedStatement getGetterPreparedStatement(Connection connection, List<I> ids) throws
        SQLException;

    protected abstract PreparedStatement getSetterPreparedStatement(Connection connection, T obj) throws SQLException, JsonProcessingException;

    protected abstract T setResultSet(ResultSet rs) throws SQLException;

    protected abstract PreparedStatement getDeletePreparedStatement(Connection connection, List<I> ids) throws
        SQLException;

    public boolean set(List<T> objs) throws SQLException {
        log.debug("Entering into set with objs =  {}", objs);

        if (objs == null) {
            log.error("object passed was null");
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer isAdded = 0;
        try {
            connection = dbUtil.getConnection();
            connection.setAutoCommit(false);
            for(T obj : objs) {
                preparedStatement = getSetterPreparedStatement(connection, obj);
                isAdded = preparedStatement.executeUpdate();
            }
            connection.commit();

        } catch (SQLException | JsonProcessingException e) {
            log.error("Error occurred while making transaction");
            isAdded = 0;
        } finally {
            closeConnection(preparedStatement, connection, null);
        }
        log.info("Exiting set with isAdded = {}", isAdded);
        return isAdded > 0;
    }


    public List<T> get(List<I> ids) throws SQLException {
        log.info("Entering into get with ids = {}", ids);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        T obj = null;
        List<T> objs = null;
        try {
            connection = dbUtil.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = getGetterPreparedStatement(connection, ids);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                obj = setResultSet(rs);
                if (objs == null)  {
                    objs = new ArrayList<>();
                }
                objs.add(obj);
            }

        } finally {
            closeConnection(preparedStatement, connection, rs);
        }
        log.info("Exiting get with {} object", obj == null ? "null" : "non null");
        return objs;
    }




    public boolean delete(List<I> ids) throws SQLException {
        log.debug("Entering into delete with obj =  {}", ids);

        if (ids == null) {
            log.error("object passed was null");
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer isDeleted = 0;
        try {

            connection = dbUtil.getConnection();
            preparedStatement = getDeletePreparedStatement(connection, ids);
            isDeleted = preparedStatement.executeUpdate();

        } finally {
            closeConnection(preparedStatement, connection, null);
        }
        log.info("Exiting delete with isDeleted = {}", isDeleted);
        return isDeleted > 0;
    }







}
