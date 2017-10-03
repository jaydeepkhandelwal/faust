package com.mt.faust.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by jaydeep.k on 03/10/17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Record {
    private Long id;
    private String type;
    private Object event;



}
