package com.mt.faust.resource;

import com.mt.faust.dao.DAOObjFactory;
import com.mt.faust.dao.EventDAOImpl;
import com.mt.faust.dao.IBaseDAO;
import com.mt.faust.domain.APIResponse;
import com.mt.faust.model.Record;
import com.mt.faust.service.CRUDService;
import com.mt.faust.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by jaydeep.k on 03/10/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({CRUDService.class, DAOObjFactory.class, DBUtil.class, EventDAOImpl.class})
@PowerMockIgnore("javax.management.*")
@Slf4j
public class CRUDResourceTest {

    private static CRUDService crudService;
    private static IBaseDAO eventDAO;

    @org.junit.BeforeClass
    public static void setUp() throws Exception {
        PowerMockito.mockStatic(DBUtil.class);
        DBUtil dbUtil = PowerMockito.mock(DBUtil.class);
        PowerMockito.when(DBUtil.getInstance()).thenReturn(dbUtil);
        PowerMockito.mockStatic(EventDAOImpl.class);
        eventDAO = PowerMockito.mock(EventDAOImpl.class);
        PowerMockito.mockStatic(DAOObjFactory.class);
        DAOObjFactory daoObjFactory = PowerMockito.mock(DAOObjFactory.class);
        PowerMockito.when(DAOObjFactory.getINSTANCE()).thenReturn(daoObjFactory);
        PowerMockito.when(daoObjFactory.getEventDAO()).thenReturn(eventDAO);
        crudService = CRUDService.getInstance();
    }

    @org.junit.Test
    public void testUpsert() throws Exception {
        Record record = new Record();
        List<Record> recordList = new ArrayList<>();
        Map<Object, Object> event = new HashMap<>();
        event.put("rank", 1);
        event.put("comment", "comment1");
        record.setEvent(event);
        record.setType("rank_event");
        recordList.add(record);

        record = new Record();
        event = new HashMap<>();
        event.put("rank", 2);
        event.put("comment", "comment2");
        record.setEvent(event);
        record.setType("rank_event");
        recordList.add(record);
        PowerMockito.when(eventDAO.set(recordList)).thenReturn(true);
        APIResponse actualResponse = crudService.upsert(recordList);
        APIResponse expectedResponse = APIResponse.builder().status(true).build();
        assertEquals(expectedResponse, actualResponse);
    }

    @org.junit.Test
    public void testRead() throws Exception {
        Record record = new Record();
        List<Record> recordList = new ArrayList<>();
        Map<Object, Object> event = new HashMap<>();
        event.put("rank", 1);
        event.put("comment", "comment1");
        record.setEvent(event);
        record.setType("rank_event");
        recordList.add(record);

        record = new Record();
        event = new HashMap<>();
        event.put("rank", 2);
        event.put("comment", "comment2");
        record.setEvent(event);
        record.setType("rank_event");
        recordList.add(record);
        List<Long> ids = Arrays.asList(1L, 2L);
        PowerMockito.when(eventDAO.get(ids)).thenReturn(recordList);
        List<Record> actualRecords = crudService.read(ids);
        assertEquals(recordList, actualRecords);


    }

    @org.junit.Test
    public void testDelete() throws Exception {

        List<Long> ids = Arrays.asList(1L, 2L);
        PowerMockito.when(eventDAO.delete(ids)).thenReturn(true);
        APIResponse actualResponse = crudService.delete(ids);
        APIResponse expectedResponse = APIResponse.builder().status(true).build();
        assertEquals(expectedResponse, actualResponse);
    }
}