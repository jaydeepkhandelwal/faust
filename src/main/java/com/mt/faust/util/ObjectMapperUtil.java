package com.mt.faust.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Created by jaydeep.k on 23/02/16.
 *
 */
public class ObjectMapperUtil {


    private static final ObjectMapper objectMapperDefault = new ObjectMapper();
    private static final ObjectMapper objectMapperConvertStyle = new ObjectMapper();

    static {
        objectMapperConvertStyle.setPropertyNamingStrategy(
                PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return objectMapperDefault;
    }

    public static ObjectMapper getObjectMapperConvertStyle() {
        return objectMapperConvertStyle;
    }

}
