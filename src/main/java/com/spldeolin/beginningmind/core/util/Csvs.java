package com.spldeolin.beginningmind.core.util;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.api.exception.BizException;
import lombok.extern.log4j.Log4j2;

/**
 * CSV工具类
 *
 * @author Deolin 2019-01-14
 */
@Log4j2
public class Csvs {

    public static final CsvMapper defaultCsvMapper;

    static {
        defaultCsvMapper = new CsvMapper();

        // 模组（jsr310）
        defaultCsvMapper.registerModule(Jsons.timeModule());

        // csv -> object时，忽略json中不认识的属性名
        defaultCsvMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 转化为CSV
     */
    public static <T> String toCsv(Collection<T> objects, Class<T> clazz) {
        CsvSchema schema = defaultCsvMapper.schemaFor(clazz).withHeader();
        ObjectWriter writer = defaultCsvMapper.writer(schema);

        try {
            return writer.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            log.error("转化CSV失败", e);
            throw new BizException("转化CSV失败");
        }
    }

    /**
     * 将CSV转化为List
     */
    public static <T> List<T> toList(String csv, Class<T> clazz) {
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        ObjectReader reader = defaultCsvMapper.readerFor(clazz).with(schema);

        try {
            return Lists.newArrayList(reader.readValues(csv));
        } catch (IOException e) {
            log.error("转化List失败", e);
            throw new BizException("转化List失败");
        }
    }

}
