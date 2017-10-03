package com.mt.faust.service;

import com.mt.faust.comman.FaustErrorCodes;
import com.mt.faust.comman.FaustException;
import com.mt.faust.dao.DAOObjFactory;
import com.mt.faust.dao.IBaseDAO;
import com.mt.faust.domain.APIResponse;
import com.mt.faust.model.Record;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jaydeep.k on 03/10/17.
 */
@Slf4j
public class CRUDService {
    private static final CRUDService INSTANCE = new CRUDService();
    private static IBaseDAO eventDAO;
    private CRUDService() {

    }
    public static CRUDService getInstance() {
        eventDAO = DAOObjFactory.getINSTANCE().getEventDAO();
        return INSTANCE;
    }

    public APIResponse upsert(List<Record> records) {
        APIResponse apiResponse = APIResponse.builder().status(false).build();

        try {
            apiResponse.setStatus(eventDAO.set(records));
        } catch (SQLException e) {
           log.error("Transaction failed, e = {}", e);
           throw new FaustException(FaustErrorCodes.DB_SET_ERROR);
        }
        return apiResponse;

    }
    public List<Record> read(List<Long> ids) {
        List<Record> records = null;
        try {
             records =  eventDAO.get(ids);
        } catch (SQLException e) {
            log.error("Transaction failed, e = {}", e);
            throw new FaustException(FaustErrorCodes.DB_SET_ERROR);
        }
        if(null == records) {
            throw new FaustException(FaustErrorCodes.RECORD_NO_FOUND);
        }
        return records;

    }
    public APIResponse delete(List<Long> id) {
        APIResponse apiResponse = APIResponse.builder().status(false).build();
        try {
            apiResponse.setStatus(eventDAO.delete(id));
        } catch (SQLException e) {
            log.error("Transaction failed, e = {}", e);
            throw new FaustException(FaustErrorCodes.DB_SET_ERROR);
        }
        return apiResponse;

    }
}
