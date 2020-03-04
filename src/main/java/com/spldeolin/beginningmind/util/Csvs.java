package com.spldeolin.beginningmind.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.util.exception.CsvException;
import lombok.extern.log4j.Log4j2;

/**
 * CSV工具类
 *
 * @author Deolin 2019-01-14
 */
@Log4j2
public class Csvs {

    private static final String utf8 = StandardCharsets.UTF_8.name();

    public static final CsvMapper defaultCsvMapper = new CsvMapper();

    static {
        // 模组（jsr310）
        defaultCsvMapper.registerModule(Jsons.timeModule());

        // csv -> object时，忽略json中不认识的属性名
        defaultCsvMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private Csvs() {
        throw new UnsupportedOperationException("Never instantiate me.");
    }

    /**
     * 读取csv
     */
    public static <T> List<T> readCsv(String csvContent, Class<T> clazz) throws CsvException {
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        ObjectReader reader = defaultCsvMapper.readerFor(clazz).with(schema);

        try {
            return Lists.newArrayList(reader.readValues(csvContent));
        } catch (IOException e) {
            log.error("csvContent={}, clazz={}", csvContent, clazz, e);
            throw new CsvException();
        }
    }

    /**
     * 生成csv
     */
    public static <T> String writeCsv(Collection<T> data, Class<T> clazz) {
        CsvSchema schema = defaultCsvMapper.schemaFor(clazz).withHeader();
        ObjectWriter writer = defaultCsvMapper.writer(schema);

        try {
            return writer.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("data={}, clazz={}", data, clazz, e);
            throw new CsvException();
        }
    }

    /**
     * 生成csv
     */
    public static <T> void writeCsv(Collection<T> data, Class<T> clazz, HttpServletResponse response,
            String fileBaseName) {
        String csvContent = writeCsv(data, clazz);

        response.setContentType("application/CSV;numberformat:@");
        response.setCharacterEncoding(utf8);
        response.setCharacterEncoding(utf8);
        try {
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileBaseName + ".csv", utf8));
            OutputStream os = response.getOutputStream();
            os.write(csvContent.getBytes(utf8));
            os.flush();
        } catch (IOException e) {
            log.error("data={}, clazz={}, fileBaseName={}", data, clazz, fileBaseName, e);
            throw new CsvException();
        }
    }

}
