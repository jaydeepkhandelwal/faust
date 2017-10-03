package com.mt.faust.resource;

import com.codahale.metrics.annotation.Timed;
import com.mt.faust.domain.APIResponse;
import com.mt.faust.model.Record;
import com.mt.faust.service.CRUDService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by jaydeep.k on 03/10/17.
 */
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@Api(value = "CRUD Resource")
@Slf4j
public class CRUDResource {

    private static final CRUDResource INSTANCE = new CRUDResource();
    private static CRUDService crudService;
    public static CRUDResource getInstance() {
        crudService = CRUDService.getInstance();
        return INSTANCE;
    }

    @Path("/upsert")
    @PUT
    @ApiOperation(value = "upsert", response = APIResponse.class)
    @Timed
    public APIResponse upsert(@NotNull final List<Record> recordList) {
        log.info("Entering CRUDResource:upsert");
        APIResponse apiResponse = crudService.upsert(recordList);
        log.debug("Returning CRUDResource:upsert with response = {}", apiResponse);
        return apiResponse;
    }

    @Path("/read")
    @GET
    @ApiOperation(value = "read", response = Record.class)
    @Timed
    public List<Record> read(@NotNull  @QueryParam("id") List<Long> ids) {
        log.info("Entering CRUDResource:read");
        List<Record> records = crudService.read(ids);
        log.debug("Returning CRUDResource:read with response = {}", records);
        return records;
    }
    @Path("/delete")
    @DELETE
    @ApiOperation(value = "read", response = APIResponse.class)
    @Timed
    public APIResponse delete(@NotNull  @QueryParam("id") List<Long> ids) {
        log.info("Entering CRUDResource:read");
        APIResponse apiResponse = crudService.delete(ids);
        log.debug("Returning CRUDResource:delete with response = {}", apiResponse);
        return apiResponse;
    }




}
