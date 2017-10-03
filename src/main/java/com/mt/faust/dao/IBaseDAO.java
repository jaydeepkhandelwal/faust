package com.mt.faust.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jaydeep.k on 03/10/17.
 *
 */
public interface IBaseDAO <T,I>{
    boolean set(List<T> obj) throws SQLException;
    List<T> get(List<I> ids) throws SQLException;
    boolean delete(List<I> ids) throws SQLException;
    }
