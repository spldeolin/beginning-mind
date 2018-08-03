package com.spldeolin.beginningmind.core.api.mapper.util;

import static com.spldeolin.beginningmind.core.api.mapper.constant.AuditField.DELETION_FLAG_COLUMN_NAME;
import static com.spldeolin.beginningmind.core.api.mapper.constant.AuditField.INSERTED_AT_COLUMN_NAME;
import static com.spldeolin.beginningmind.core.api.mapper.constant.AuditField.IS_NOT_DELETED;
import static com.spldeolin.beginningmind.core.api.mapper.constant.AuditField.UPDATED_AT_COLUMN_NAME;

import java.util.Set;
import javax.persistence.Version;
import org.apache.commons.lang3.StringUtils;
import com.spldeolin.beginningmind.core.api.mapper.constant.AuditField;
import lombok.experimental.UtilityClass;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.IDynamicTableName;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.util.StringUtil;
import tk.mybatis.mapper.version.VersionException;

@UtilityClass
public class SqlUtils {

    /**
     * 获取表名 - 支持动态表名
     */
    public static String getDynamicTableName(Class<?> entityClass, String tableName) {
        if (IDynamicTableName.class.isAssignableFrom(entityClass)) {
            String sql = "<choose>" +
                    "<when test=\"@tk.mybatis.mapper.util.OGNL@isDynamicParameter(_parameter) and dynamicTableName != null and dynamicTableName != ''\">"
                    +
                    "${dynamicTableName}\n" +
                    "</when>" +
                    "<otherwise>" +
                    tableName +
                    "</otherwise>" +
                    "</choose>";
            //不支持指定列的时候查询全部列
            return sql;
        } else {
            return tableName;
        }
    }

    /**
     * 获取表名 - 支持动态表名，该方法用于多个入参时，通过parameterName指定入参中实体类的<code>@Param</code>的注解值
     */
    public static String getDynamicTableName(Class<?> entityClass, String tableName, String parameterName) {
        if (IDynamicTableName.class.isAssignableFrom(entityClass)) {
            if (StringUtil.isNotEmpty(parameterName)) {
                String sql = "<choose>" +
                        "<when test=\"@tk.mybatis.mapper.util.OGNL@isDynamicParameter(" + parameterName + ") and " +
                        parameterName + ".dynamicTableName != null and " + parameterName +
                        ".dynamicTableName != ''\">" +
                        "${" + parameterName + ".dynamicTableName}" +
                        "</when>" +
                        "<otherwise>" +
                        tableName +
                        "</otherwise>" +
                        "</choose>";
                //不支持指定列的时候查询全部列
                return sql;
            } else {
                return getDynamicTableName(entityClass, tableName);
            }

        } else {
            return tableName;
        }
    }

    /*
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     */
    public static String getBindCache(EntityColumn column) {
        String sql = "<bind name=\"" +
                column.getProperty() + "_cache\" " +
                "value=\"" + column.getProperty() + "\"/>";
        return sql;
    }

    /*
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     */
    public static String getBindValue(EntityColumn column, String value) {
        String sql = "<bind name=\"" +
                column.getProperty() + "_bind\" " +
                "value='" + value + "'/>";
        return sql;
    }

    /*
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     */
    public static String getIfCacheNotNull(EntityColumn column, String contents) {
        String sql = "<if test=\"" + column.getProperty() + "_cache != null\">" +
                contents +
                "</if>";
        return sql;
    }

    /**
     * 如果_cache == null
     */
    public static String getIfCacheIsNull(EntityColumn column, String contents) {
        String sql = "<if test=\"" + column.getProperty() + "_cache == null\">" +
                contents +
                "</if>";
        return sql;
    }

    /**
     * 判断自动!=null的条件结构
     */
    public static String getIfNotNull(EntityColumn column, String contents, boolean empty) {
        return getIfNotNull(null, column, contents, empty);
    }

    /**
     * 判断自动==null的条件结构
     */
    public static String getIfIsNull(EntityColumn column, String contents, boolean empty) {
        return getIfIsNull(null, column, contents, empty);
    }

    /**
     * 判断自动!=null的条件结构
     */
    public static String getIfNotNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }
        sql.append(column.getProperty()).append(" != null");
        if (empty && column.getJavaType().equals(String.class)) {
            sql.append(" and ");
            if (StringUtil.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }
            sql.append(column.getProperty()).append(" != '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 判断自动==null的条件结构
     */
    public static String getIfIsNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }
        sql.append(column.getProperty()).append(" == null");
        if (empty && column.getJavaType().equals(String.class)) {
            sql.append(" or ");
            if (StringUtil.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }
            sql.append(column.getProperty()).append(" == '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 获取所有查询列，如id,name,code...
     */
    public static String getAllColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
            sql.append(entityColumn.getColumn()).append(",");
        }
        return sql.substring(0, sql.length() - 1);
    }

    /**
     * select xxx,xxx...
     */
    public static String selectAllColumns(Class<?> entityClass) {
        String sql = "SELECT " +
                getAllColumns(entityClass) +
                " ";
        return sql;
    }

    /**
     * select count(x)
     */
    public static String selectCount(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entityClass);
        if (pkColumns.size() == 1) {
            sql.append("COUNT(").append(pkColumns.iterator().next().getColumn()).append(") ");
        } else {
            sql.append("COUNT(*) ");
        }
        return sql.toString();
    }

    /*
     * select case when count(x) > 0 then 1 else 0 end
     */
    public static String selectCountExists(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE WHEN ");
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entityClass);
        if (pkColumns.size() == 1) {
            sql.append("COUNT(").append(pkColumns.iterator().next().getColumn()).append(") ");
        } else {
            sql.append("COUNT(*) ");
        }
        sql.append(" > 0 THEN 1 ELSE 0 END AS result ");
        return sql.toString();
    }

    /**
     * from tableName - 动态表名
     */
    public static String fromTable(Class<?> entityClass, String defaultTableName) {
        String sql = " FROM " +
                getDynamicTableName(entityClass, defaultTableName) +
                " ";
        return sql;
    }

    /**
     * update tableName - 动态表名
     */
    public static String updateTable(Class<?> entityClass, String defaultTableName) {
        return updateTable(entityClass, defaultTableName, null);
    }

    /**
     * update tableName - 动态表名
     *
     * @param defaultTableName 默认表名
     * @param entityName 别名
     */
    public static String updateTable(Class<?> entityClass, String defaultTableName, String entityName) {
        String sql = "UPDATE " +
                getDynamicTableName(entityClass, defaultTableName, entityName) +
                " ";
        return sql;
    }

    /**
     * delete tableName - 动态表名
     */
    public static String deleteFromTable(Class<?> entityClass, String defaultTableName) {
        String sql = "DELETE FROM " +
                getDynamicTableName(entityClass, defaultTableName) +
                " ";
        return sql;
    }

    /**
     * insert into tableName - 动态表名
     */
    public static String insertIntoTable(Class<?> entityClass, String defaultTableName) {
        String sql = "INSERT INTO " +
                getDynamicTableName(entityClass, defaultTableName) +
                " ";
        return sql;
    }

    /**
     * insert table()列
     *
     * @param skipId 是否从列中忽略id类型
     * @param notNull 是否判断!=null
     * @param notEmpty 是否判断String类型!=''
     */
    public static String insertColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            // 忽略以下字段，原因：id自增，inserted_at有初始值，updated_at初始应该为null，deletion_flag初始应该为-1
            if (StringUtils.equalsAny(column.getColumn(), "id", INSERTED_AT_COLUMN_NAME, UPDATED_AT_COLUMN_NAME,
                    DELETION_FLAG_COLUMN_NAME)) {
                continue;
            }
            if (!column.isInsertable()) {
                continue;
            }
            if (skipId && column.isId()) {
                continue;
            }
            if (notNull) {
                sql.append(
                        tk.mybatis.mapper.mapperhelper.SqlHelper.getIfNotNull(column, column.getColumn() + ",",
                                notEmpty));
            } else {
                sql.append(column.getColumn()).append(",");
            }
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * insert-values()列
     *
     * @param skipId 是否从列中忽略id类型
     * @param notNull 是否判断!=null
     * @param notEmpty 是否判断String类型!=''
     */
    public static String insertValuesColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) {
                continue;
            }
            if (skipId && column.isId()) {
                continue;
            }
            if (notNull) {
                sql.append(tk.mybatis.mapper.mapperhelper.SqlHelper.getIfNotNull(column, column.getColumnHolder() + ",",
                        notEmpty));
            } else {
                sql.append(column.getColumnHolder()).append(",");
            }
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * update set列
     *
     * @param entityName 实体映射名
     * @param notNull 是否判断!=null
     * @param notEmpty 是否判断String类型!=''
     */
    public static String updateSetColumns(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //对乐观锁的支持
        EntityColumn versionColumn = null;
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            // 忽略以下字段，原因：inserted_at数据插入之后不应改变，updated_at MySQL会自动更新，deletion_flag不应能被修改
            if (StringUtils.equalsAny(column.getColumn(), INSERTED_AT_COLUMN_NAME, UPDATED_AT_COLUMN_NAME,
                    DELETION_FLAG_COLUMN_NAME)) {
                continue;
            }
            if (column.getEntityField().isAnnotationPresent(Version.class)) {
                if (versionColumn != null) {
                    throw new VersionException(
                            entityClass.getCanonicalName() + " 中包含多个带有 @Version 注解的字段，一个类中只能存在一个带有 @Version 注解的字段!");
                }
                versionColumn = column;
            }
            if (!column.isId() && column.isUpdatable()) {
                if (column == versionColumn) {
                    Version version = versionColumn.getEntityField().getAnnotation(Version.class);
//                    String versionClass = version.nextVersion().getCanonicalName();
                    String versionClass = null;
                    //version = ${@tk.mybatis.mapper.version@nextVersionClass("versionClass", version)}
                    sql.append(column.getColumn())
                            .append(" = ${@tk.mybatis.mapper.version.VersionUtil@nextVersion(\"")
                            .append(versionClass).append("\", ")
                            .append(column.getProperty()).append(")},");
                } else if (notNull) {
                    sql.append(tk.mybatis.mapper.mapperhelper.SqlHelper.getIfNotNull(entityName, column,
                            column.getColumnEqualsHolder(entityName) + ",", notEmpty));
                } else {
                    sql.append(column.getColumnEqualsHolder(entityName)).append(",");
                }
            }
        }
        sql.append("</set>");
        return sql.toString();
    }

    /**
     * where主键条件
     */
    public static String wherePKColumns(Class<?> entityClass) {
        return wherePKColumns(entityClass, false);
    }

    /**
     * where主键条件
     */
    public static String wherePKColumns(Class<?> entityClass, boolean useVersion) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        // delete flag
        sql.append(" AND ").append(AuditField.IS_NOT_DELETED);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            sql.append(" AND ").append(column.getColumnEqualsHolder());
        }
        if (useVersion) {
            sql.append(whereVersion(entityClass));
        }
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * where所有列的条件，会判断是否!=null
     */
    public static String whereAllIfColumns(Class<?> entityClass, boolean empty) {
        return whereAllIfColumns(entityClass, empty, false);
    }

    /**
     * where所有列的条件，会判断是否!=null
     */
    public static String whereAllIfColumns(Class<?> entityClass, boolean empty, boolean useVersion) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (DELETION_FLAG_COLUMN_NAME.equals(column.getColumn())) {
                sql.append(" AND " + IS_NOT_DELETED);
                continue;
            }
            if (!useVersion || !column.getEntityField().isAnnotationPresent(Version.class)) {
                sql.append(getIfNotNull(column, " AND " + column.getColumnEqualsHolder(), empty));
            }
        }
        if (useVersion) {
            sql.append(whereVersion(entityClass));
        }
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 乐观锁字段条件
     */
    public static String whereVersion(Class<?> entityClass) {
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        boolean hasVersion = false;
        String result = "";
        for (EntityColumn column : columnList) {
            if (column.getEntityField().isAnnotationPresent(Version.class)) {
                if (hasVersion) {
                    throw new VersionException(
                            entityClass.getCanonicalName() + " 中包含多个带有 @Version 注解的字段，一个类中只能存在一个带有 @Version 注解的字段!");
                }
                hasVersion = true;
                result = " AND " + column.getColumnEqualsHolder();
            }
        }
        return result;
    }

    /**
     * 获取默认的orderBy，通过注解设置的
     */
    public static String orderByDefault(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        String orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            sql.append(" ORDER BY ");
            sql.append(orderByClause);
        }
        return sql.toString();
    }

    /**
     * example支持查询指定列时
     */
    public static String exampleSelectColumns(Class<?> entityClass) {
        String sql = "<choose>" +
                "<when test=\"@tk.mybatis.mapper.util.OGNL@hasSelectColumns(_parameter)\">" +
                "<foreach collection=\"_parameter.selectColumns\" item=\"selectColumn\" separator=\",\">" +
                "${selectColumn}" +
                "</foreach>" +
                "</when>" +
                "<otherwise>" +
                getAllColumns(entityClass) +
                "</otherwise>" +
                "</choose>";
        //不支持指定列的时候查询全部列
        return sql;
    }

    /**
     * example支持查询指定列时
     */
    public static String exampleCountColumn(Class<?> entityClass) {
        String sql = "<choose>" +
                "<when test=\"@tk.mybatis.mapper.util.OGNL@hasCountColumn(_parameter)\">" +
                "COUNT(${countColumn})" +
                "</when>" +
                "<otherwise>" +
                "COUNT(0)" +
                "</otherwise>" +
                "</choose>" +
                "<if test=\"@tk.mybatis.mapper.util.OGNL@hasNoSelectColumns(_parameter)\">" +
                getAllColumns(entityClass) +
                "</if>";
        //不支持指定列的时候查询全部列
        return sql;
    }

    /**
     * example查询中的orderBy条件，会判断默认orderBy
     */
    public static String exampleOrderBy(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"orderByClause != null\">");
        sql.append("order by ${orderByClause}");
        sql.append("</if>");
        String orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            sql.append("<if test=\"orderByClause == null\">");
            sql.append("ORDER BY ").append(orderByClause);
            sql.append("</if>");
        }
        return sql.toString();
    }

    /**
     * example 支持 for update
     */
    public static String exampleForUpdate() {
        String sql = "<if test=\"@tk.mybatis.mapper.util.OGNL@hasForUpdate(_parameter)\">" +
                "FOR UPDATE" +
                "</if>";
        return sql;
    }

    /**
     * example 支持 for update
     */
    public static String exampleCheck(Class<?> entityClass) {
        String sql =
                "<bind name=\"checkExampleEntityClass\" value=\"@tk.mybatis.mapper.util.OGNL@checkExampleEntityClass(_parameter, '"
                        +
                        entityClass.getCanonicalName() +
                        "')\"/>";
        return sql;
    }

    /**
     * Example查询中的where结构，用于只有一个Example参数时
     */
    public static String exampleWhereClause() {
        return "<if test=\"_parameter != null\">" +
                "<where>\n" +
                IS_NOT_DELETED + "\n" +
                "  <foreach collection=\"oredCriteria\" item=\"criteria\">\n" +
                "    <if test=\"criteria.valid\">\n" +
                "      ${@tk.mybatis.mapper.util.OGNL@andOr(criteria)}" +
                "      <trim prefix=\"(\" prefixOverrides=\"and |or \" suffix=\")\">\n" +
                "        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" +
                "          <choose>\n" +
                "            <when test=\"criterion.noValue\">\n" +
                "              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition}\n" +
                "            </when>\n" +
                "            <when test=\"criterion.singleValue\">\n" +
                "              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition} #{criterion.value}\n"
                +
                "            </when>\n" +
                "            <when test=\"criterion.betweenValue\">\n" +
                "              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n"
                +
                "            </when>\n" +
                "            <when test=\"criterion.listValue\">\n" +
                "              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition}\n" +
                "              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n"
                +
                "                #{listItem}\n" +
                "              </foreach>\n" +
                "            </when>\n" +
                "          </choose>\n" +
                "        </foreach>\n" +
                "      </trim>\n" +
                "    </if>\n" +
                "  </foreach>\n" +
                "</where>" +
                "</if>";
    }

    /**
     * Example-Update中的where结构，用于多个参数时，Example带@Param("example")注解时
     */
    public static String updateByExampleWhereClause() {
        return "<where>\n" +
                "  <foreach collection=\"example.oredCriteria\" item=\"criteria\">\n" +
                "    <if test=\"criteria.valid\">\n" + "      ${@tk.mybatis.mapper.util.OGNL@andOr(criteria)}" +
                "      <trim prefix=\"(\" prefixOverrides=\"and |or \" suffix=\")\">\n" +
                "        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" +
                "          <choose>\n" +
                "            <when test=\"criterion.noValue\">\n" +
                "              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition}\n" +
                "            </when>\n" +
                "            <when test=\"criterion.singleValue\">\n" +
                "              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition} #{criterion.value}\n"
                +
                "            </when>\n" +
                "            <when test=\"criterion.betweenValue\">\n" +
                "              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n"
                +
                "            </when>\n" +
                "            <when test=\"criterion.listValue\">\n" +
                "              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition}\n" +
                "              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n"
                +
                "                #{listItem}\n" +
                "              </foreach>\n" +
                "            </when>\n" +
                "          </choose>\n" +
                "        </foreach>\n" +
                "      </trim>\n" +
                "    </if>\n" +
                "  </foreach>\n" +
                "</where>";
    }

    public static String deletionFlagWhenDelete() {
        return " set " + AuditField.DELETION_FLAG_COLUMN_NAME + " = id ";
    }

}
