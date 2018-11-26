package com.spldeolin.beginningmind.core.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.generator.dto.ColumnDTO;
import com.spldeolin.beginningmind.core.generator.dto.MapperJavaFtl;
import com.spldeolin.beginningmind.core.generator.dto.MapperXmlFtl;
import com.spldeolin.beginningmind.core.generator.dto.ModelFtl;
import com.spldeolin.beginningmind.core.generator.dto.ServiceFtl;
import com.spldeolin.beginningmind.core.generator.dto.TableColumnDTO;
import com.spldeolin.beginningmind.core.generator.util.StringCaseUtils;
import com.spldeolin.beginningmind.core.util.Times;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/14
 */
@Log4j2
public class Cadeau {

    private static final String AUTHOR = "Deolin" + " " + Times.toString(LocalDate.now(), "yyyy/MM/dd");

    private static final String BASE_PACKAGE_REFERENCE = "com.spldeolin.beginningmind.core";

    private static final String PROJECT_PATH = "C:\\java-development\\projects-repo\\deolin\\beginning-mind";

    private static final String DELETE_FLAG_COLUMN_NAME = "is_deleted";

    private static final String VERSION_COLUMN_NAME = "version";

    private static final String JDBC_IP = "192.168.2.2";

    private static final Integer JDBC_PORT = 3306;

    private static final String JDBC_DATABASE = "beginning_mind";

    private static final String JDBC_USERNAME = "guest";

    private static final String JDBC_PASSWORD = "guest";

    public static void main(String[] args) {
        generateByScanner();
    }

    private static void generateByScanner() {
        for (; ; ) {
            String input = scanner("表名");
            if (StringUtils.isBlank(input)) {
                return;
            }
            try {
                generator(input);
                log.info("生成成功。");
            } catch (Exception e) {
                log.error("生成失败。", e);
            }
        }
    }

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        log.info("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            return ipt;
        }
        throw new RuntimeException("请输入正确的" + tip + "。");
    }

    private static void generator(String tableName) {
        // DataSource
        DataSource dataSource = createDataSource();

        // 表信息
        StringBuilder tableInfoSql = appendTableInfoSql(tableName);
        List<Map<String, Object>> tableInfos = selectAsMapList(dataSource, tableInfoSql.toString());
        Map<String, Object> tableInfo = getFirstTableInfo(tableInfos, tableName);
        TableColumnDTO tableColumnDTO = mapTableName2TableComment(tableInfo);

        // 字段信息
        StringBuilder columnInfoSql = appendColumnInfoSql(tableName);
        List<Map<String, Object>> columnInfos = selectAsMapList(dataSource, columnInfoSql.toString());
        fillColumnInfo(tableColumnDTO, columnInfos);

        String modelName = StringCaseUtils.snakeToUpperCamel(tableName);

        // 生成Model层
        generateModel(modelName, tableColumnDTO);

        // 生成持久层
        generateMapper(modelName, tableColumnDTO);

        // 生成业务层
        generateService(modelName, tableColumnDTO);
    }

    private static DataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(JDBC_USERNAME);
        dataSource.setPassword(JDBC_PASSWORD);
        dataSource.setJdbcUrl("jdbc:mysql://" + JDBC_IP + ":" + JDBC_PORT + "/" + "information_schema");
        return dataSource;
    }

    private static StringBuilder appendTableInfoSql(String tableName) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("SELECT * FROM `TABLES` WHERE TABLE_SCHEMA = '");
        sql.append(JDBC_DATABASE);
        sql.append("' AND TABLE_NAME IN (");
        sql.append("'");
        sql.append(tableName);
        sql.append("',");
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        return sql;
    }

    private static List<Map<String, Object>> selectAsMapList(DataSource dataSource, String sql) {
        QueryRunner qr = new QueryRunner(dataSource);
        MapListHandler mapListHandler = new MapListHandler();
        try {
            return qr.query(sql, mapListHandler);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> getFirstTableInfo(List<Map<String, Object>> tableInfos, String tableName) {
        if (tableInfos.size() < 1) {
            throw new RuntimeException("表不存在 " + tableName);
        }
        return tableInfos.get(0);
    }

    private static TableColumnDTO mapTableName2TableComment(Map<String, Object> tableInfo) {
        return TableColumnDTO.builder()
                .name((String) tableInfo.get("TABLE_NAME"))
                .comment((String) tableInfo.get("TABLE_COMMENT"))
                .columns(Lists.newArrayList()).build();
    }

    private static StringBuilder appendColumnInfoSql(String tableName) {
        StringBuilder sb = new StringBuilder(128);
        sb.append("SELECT * FROM `COLUMNS` WHERE TABLE_SCHEMA = '");
        sb.append(JDBC_DATABASE);
        sb.append("' AND TABLE_NAME IN (");
        sb.append("'");
        sb.append(tableName);
        sb.append("',");
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb;
    }

    private static void fillColumnInfo(TableColumnDTO tableColumnDTO, List<Map<String, Object>> columnInfos) {
        for (Map<String, Object> columnInfo : columnInfos) {
            ColumnDTO column = ColumnDTO.builder()
                    .name((String) columnInfo.get("COLUMN_NAME"))
                    .comment((String) columnInfo.get("COLUMN_COMMENT"))
                    .type((String) columnInfo.get("DATA_TYPE"))
                    .length((BigInteger) columnInfo.get("CHARACTER_MAXIMUM_LENGTH"))
                    .isTinyint1Unsigned("tinyint(1) unsigned".equals(columnInfo.get("COLUMN_TYPE")))
                    .build();
            tableColumnDTO.getColumns().add(column);
        }
    }

    private static String formatFreemarker(String freemarkerFileName, Object ftl) {
        if (!freemarkerFileName.endsWith(".ftl")) {
            freemarkerFileName += ".ftl";
        }

        Version version = new Version("2.3.23");
        Configuration cfg = new Configuration(version);
        String folderPath = System.getProperty("user.dir") + ".src.test.resources.".replace('.', File.separatorChar)
                + "generator-template" + File.separator;
        try (StringWriter out = new StringWriter()) {
            cfg.setDirectoryForTemplateLoading(new File(folderPath));
            Template template = cfg.getTemplate(freemarkerFileName, "utf-8");

            template.process(ftl, out);
            out.flush();
            return out.getBuffer().toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeJavaFileToPackage(String packageName, String fileName, String fileContent) {
        try {
            File file = new File(PROJECT_PATH + (".src.main.java." + BASE_PACKAGE_REFERENCE + "." + packageName
                    + ".").replace('.', File.separatorChar) + fileName + ".java");
            FileUtils.write(file, fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeXmlFileToDirectory(String directoryName, String fileName, String fileContent) {
        try {
            File file = new File(PROJECT_PATH + (".src.main.resources." + directoryName + ".")
                    .replace('.', File.separatorChar) + fileName + ".xml");
            FileUtils.write(file, fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateModel(String modelName, TableColumnDTO tableColumnDTO) {
        // Model Freemarker
        ModelFtl modelFtl = createModelFtl(tableColumnDTO);
        String fileContent = formatFreemarker("model.ftl", modelFtl);
        // 输出文件
        writeJavaFileToPackage("model", modelName, fileContent);
    }

    private static ModelFtl createModelFtl(TableColumnDTO tableColumnDTO) {
        ModelFtl modelFtl = new ModelFtl();
        modelFtl.setPackageReference(BASE_PACKAGE_REFERENCE);
        modelFtl.setModelCnsName(tableColumnDTO.getComment());
        modelFtl.setAuthor(AUTHOR);
        String tableName = tableColumnDTO.getName();
        modelFtl.setTableName(tableName);
        modelFtl.setModelName(StringCaseUtils.snakeToUpperCamel(tableName));

        List<ModelFtl.Property> properties = Lists.newArrayList();
        for (ColumnDTO columnDTO : tableColumnDTO.getColumns()) {
            ModelFtl.Property property = new ModelFtl.Property();
            property.setFieldCnsName(columnDTO.getComment());
            String columnName = columnDTO.getName();
            property.setIsDeleteFlag(DELETE_FLAG_COLUMN_NAME.equals(columnName));
            property.setIsVersion(VERSION_COLUMN_NAME.equals(columnName));
            property.setColumnName(columnName);
            property.setFieldType(TypeHander.toJavaTypeName(columnDTO));
            property.setFieldName(StringCaseUtils.snakeToLowerCamel(columnName));
            properties.add(property);
        }
        modelFtl.setProperties(properties);

        return modelFtl;
    }

    private static void generateMapper(String modelName, TableColumnDTO tableColumnDTO) {
        // Mapper.java Freemarker
        MapperJavaFtl mapperJavaFtl = createMapperJavaFtl(tableColumnDTO);
        String fileContent = formatFreemarker("mapper-java.ftl", mapperJavaFtl);
        // Mapper.java 输出文件
        writeJavaFileToPackage("dao", modelName + "Mapper", fileContent);
        // Mapper.xml Freemarker
        MapperXmlFtl mapperXmlFtl = createMapperXmlFtl(tableColumnDTO);
        fileContent = formatFreemarker("mapper-xml.ftl", mapperXmlFtl);
        // Mapper.xml 输出文件
        writeXmlFileToDirectory("mapper", modelName + "Mapper", fileContent);
    }

    private static MapperJavaFtl createMapperJavaFtl(TableColumnDTO tableColumnDTO) {
        MapperJavaFtl mapperJavaFtl = new MapperJavaFtl();
        mapperJavaFtl.setPackageReference(BASE_PACKAGE_REFERENCE);
        mapperJavaFtl.setModelCnsName(tableColumnDTO.getComment());
        mapperJavaFtl.setAuthor(AUTHOR);
        String tableName = tableColumnDTO.getName();
        mapperJavaFtl.setModelName(StringCaseUtils.snakeToUpperCamel(tableName));
        return mapperJavaFtl;
    }

    private static MapperXmlFtl createMapperXmlFtl(TableColumnDTO tableColumnDTO) {
        MapperXmlFtl mapperXmlFtl = new MapperXmlFtl();
        mapperXmlFtl.setPackageReference(BASE_PACKAGE_REFERENCE);
        mapperXmlFtl.setModelName(StringCaseUtils.snakeToUpperCamel(tableColumnDTO.getName()));
        return mapperXmlFtl;
    }

    private static void generateService(String modelName, TableColumnDTO tableColumnDTO) {
        // Service Freemarker
        ServiceFtl serviceFtl = createServiceFtl(tableColumnDTO);
        String fileContent = formatFreemarker("service.ftl", serviceFtl);
        // Service 生成文件
        writeJavaFileToPackage("service", modelName + "Service", fileContent);
        // ServiceImpl Freemarker
        fileContent = formatFreemarker("service-impl.ftl", serviceFtl);
        // ServiceImpl 生成文件
        writeJavaFileToPackage("service.impl", modelName + "ServiceImpl", fileContent);
    }

    private static ServiceFtl createServiceFtl(TableColumnDTO tableColumnDTO) {
        ServiceFtl serviceFtl = new ServiceFtl();
        serviceFtl.setPackageReference(BASE_PACKAGE_REFERENCE);
        serviceFtl.setModelName(StringCaseUtils.snakeToUpperCamel(tableColumnDTO.getName()));
        serviceFtl.setModelCnsName(tableColumnDTO.getComment());
        serviceFtl.setAuthor(AUTHOR);
        return serviceFtl;
    }

}
