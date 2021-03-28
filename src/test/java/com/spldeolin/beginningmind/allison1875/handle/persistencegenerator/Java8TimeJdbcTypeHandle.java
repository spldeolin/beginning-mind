package com.spldeolin.beginningmind.allison1875.handle.persistencegenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.persistencegenerator.handle.DefaultJdbcTypeHandle;

/**
 * @author Deolin 2021-03-23
 */
@Singleton
public class Java8TimeJdbcTypeHandle extends DefaultJdbcTypeHandle {

    @Override
    public Class<?> jdbcType2javaType(String columnType, String dataType) {
        if ("date".equals(dataType)) {
            return LocalDate.class;
        }
        if ("time".equals(dataType)) {
            return LocalTime.class;
        }
        if ("datetime".equals(dataType)) {
            return LocalDateTime.class;
        }
        if ("timestamp".equals(dataType)) {
            return LocalDateTime.class;
        }
        return super.jdbcType2javaType(columnType, dataType);
    }

}