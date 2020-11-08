package com.spldeolin.beginningmind.querydesign;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.spldeolin.beginningmind.entity.Allison1875DemoEntity;
import com.spldeolin.beginningmind.support.QueryPredicate;

/**
 * 为Allison 1875准备的示例表
 * <p>allison1875_demo
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2020-11-06
 */
public class Allison1875DemoQuery {

    private Allison1875DemoQuery() {
    }

    public static Allison1875DemoQuery design(String methodName) {
        throw new UnsupportedOperationException(methodName);
    }

    /**
     * 主键
     * <p>id
     * <p>不能为null
     */
    public QueryPredicate<Allison1875DemoQuery, Long> id;

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    public QueryPredicate<Allison1875DemoQuery, Boolean> deleteFlag;

    /**
     * 插入数据的时间
     * <p>inserted_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<Allison1875DemoQuery, Date> insertedAt;

    /**
     * 最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致
     * <p>updated_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<Allison1875DemoQuery, Date> updatedAt;

    /**
     * 属性A
     * <p>varchar1
     * <p>长度：255
     */
    public QueryPredicate<Allison1875DemoQuery, String> varchar1;

    /**
     * 属性B
     * <p>varchar2
     * <p>长度：2333
     */
    public QueryPredicate<Allison1875DemoQuery, String> varchar2;

    /**
     * 属性C
     * <p>char1
     * <p>长度：9
     */
    public QueryPredicate<Allison1875DemoQuery, String> char1;

    /**
     * 属性D
     * <p>char2
     * <p>长度：233
     */
    public QueryPredicate<Allison1875DemoQuery, String> char2;

    /**
     * 属性E
     * <p>text1
     * <p>长度：65535
     */
    public QueryPredicate<Allison1875DemoQuery, String> text1;

    /**
     * 属性F
     * <p>text2
     * <p>长度：65535
     */
    public QueryPredicate<Allison1875DemoQuery, String> text2;

    /**
     * 属性G
     * <p>tinyint1
     */
    public QueryPredicate<Allison1875DemoQuery, Byte> tinyint1;

    /**
     * 属性H
     * <p>longtext2
     * <p>长度：4294967295
     */
    public QueryPredicate<Allison1875DemoQuery, String> longtext2;

    /**
     * 属性I
     * <p>longtext1
     * <p>长度：4294967295
     */
    public QueryPredicate<Allison1875DemoQuery, String> longtext1;

    /**
     * 属性J
     * <p>tinyint2
     */
    public QueryPredicate<Allison1875DemoQuery, Byte> tinyint2;

    /**
     * 属性K
     * <p>int1
     */
    public QueryPredicate<Allison1875DemoQuery, Integer> int1;

    /**
     * 属性L
     * <p>int2
     */
    public QueryPredicate<Allison1875DemoQuery, Integer> int2;

    /**
     * 属性M
     * <p>boolean1
     */
    public QueryPredicate<Allison1875DemoQuery, Boolean> boolean1;

    /**
     * 属性N
     * <p>boolean2
     */
    public QueryPredicate<Allison1875DemoQuery, Boolean> boolean2;

    /**
     * 属性O
     * <p>datetime1
     */
    public QueryPredicate<Allison1875DemoQuery, Date> datetime1;

    /**
     * 属性P
     * <p>datetime2
     */
    public QueryPredicate<Allison1875DemoQuery, Date> datetime2;

    /**
     * 属性Q
     * <p>decimal1
     */
    public QueryPredicate<Allison1875DemoQuery, BigDecimal> decimal1;

    /**
     * 属性R
     * <p>decimal2
     */
    public QueryPredicate<Allison1875DemoQuery, BigDecimal> decimal2;

    public List<Allison1875DemoEntity> over() {
        throw new UnsupportedOperationException();
    }
    /*{"entityQualifier":"com.spldeolin.beginningmind.entity.Allison1875DemoEntity",
    "entityName":"Allison1875DemoEntity","mapperQualifier":"com.spldeolin.beginningmind.mapper
    .Allison1875DemoMapper","mapperName":"Allison1875DemoMapper",
    "mapperRelativePath":"src/main/resources/mapper/Allison1875DemoMapper.xml","propertyNames":["id","deleteFlag",
    "insertedAt","updatedAt","varchar1","varchar2","char1","char2","text1","text2","tinyint1","longtext2",
    "longtext1","tinyint2","int1","int2","boolean1","boolean2","datetime1","datetime2","decimal1","decimal2"],
    "tableName":"allison1875_demo"}*/
}
