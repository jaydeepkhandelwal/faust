package com.mt.faust.dao;


import com.mt.faust.comman.FaustErrorCodes;
import com.mt.faust.comman.FaustException;
import com.mt.faust.config.DatabaseConfig;
import com.mt.faust.util.DBUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jaydeep.k on 03/10/17.
 */
@Getter
@Slf4j
public class  DAOObjFactory {
    private static DAOObjFactory INSTANCE = null;
    private IBaseDAO eventDAO;

    DAOObjFactory(final DatabaseConfig databaseConfig) {
        DBUtil.init(databaseConfig);
        eventDAO = new EventDAOImpl();
    }

    public synchronized static void init(final DatabaseConfig databaseConfig) {
        if(INSTANCE == null) {
            INSTANCE = new DAOObjFactory(databaseConfig);
        }
    }
    public static DAOObjFactory getINSTANCE() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else {
            log.error("Failed to get DAOObjFactory instance");
            throw new FaustException(FaustErrorCodes.DB_SET_INSTANCE_ERROR);
        }
    }

}
