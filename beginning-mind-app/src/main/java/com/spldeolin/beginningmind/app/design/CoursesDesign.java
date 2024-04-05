package com.spldeolin.beginningmind.app.design;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.CoursesEntity;

//@formatter:off
/**
 * <p>courses
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@SuppressWarnings("all")
public class CoursesDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private CoursesDesign() {
    }

    public static QueryChain query(String methodName) {
        throw e;
    }

    public static QueryChain query() {
        throw e;
    }

    public static UpdateChain update(String methodName) {
        throw e;
    }

    public static UpdateChain update() {
        throw e;
    }

    public static DropChain drop(String methodName) {
        throw e;
    }

    public static DropChain drop() {
        throw e;
    }

    public static class QueryChain {

        private QueryChain() {
        }

        /**
         */
        public QueryChain courseCode;

        /**
         */
        public QueryChain courseName;

        /**
         */
        public QueryChain courseLine;

        /**
         */
        public QueryChain courseScale;

        /**
         */
        public QueryChain courseVendor;

        /**
         */
        public QueryChain courseDescription;

        /**
         */
        public QueryChain buyPrice;

        /**
         */
        public QueryChain msrp;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<CoursesEntity> many() {
            throw e;
        }

        public <P> Map<P, CoursesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CoursesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CoursesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain courseCode(String courseCode);

        /**
         */
        NextableUpdateChain courseName(String courseName);

        /**
         */
        NextableUpdateChain courseLine(String courseLine);

        /**
         */
        NextableUpdateChain courseScale(String courseScale);

        /**
         */
        NextableUpdateChain courseVendor(String courseVendor);

        /**
         */
        NextableUpdateChain courseDescription(String courseDescription);

        /**
         */
        NextableUpdateChain buyPrice(BigDecimal buyPrice);

        /**
         */
        NextableUpdateChain msrp(BigDecimal msrp);
    }

    public interface NextableUpdateChain extends UpdateChain {

        int over();

        ByChainReturn<NextableByChainVoid> by();

        ByChainReturn<NextableByChainVoid> byForced();
    }

    public interface DropChain {

        int over();

        ByChainReturn<NextableByChainVoid> by();

        ByChainReturn<NextableByChainVoid> byForced();
    }

    public static class ByChainReturn<NEXT> {

        /**
         */
        public ByChainPredicate<NEXT, String> courseCode;

        /**
         */
        public ByChainPredicate<NEXT, String> courseName;

        /**
         */
        public ByChainPredicate<NEXT, String> courseLine;

        /**
         */
        public ByChainPredicate<NEXT, String> courseScale;

        /**
         */
        public ByChainPredicate<NEXT, String> courseVendor;

        /**
         */
        public ByChainPredicate<NEXT, String> courseDescription;

        /**
         */
        public ByChainPredicate<NEXT, BigDecimal> buyPrice;

        /**
         */
        public ByChainPredicate<NEXT, BigDecimal> msrp;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> courseCode;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> courseName;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> courseLine;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> courseScale;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> courseVendor;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> courseDescription;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, BigDecimal> buyPrice;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, BigDecimal> msrp;

        public List<CoursesEntity> many() {
            throw e;
        }

        public <P> Map<P, CoursesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CoursesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CoursesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }
    }

    public static class NextableByChainVoid {

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> courseCode;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> courseName;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> courseLine;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> courseScale;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> courseVendor;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> courseDescription;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, BigDecimal> buyPrice;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, BigDecimal> msrp;

        public int over() {
            throw e;
        }
    }

    public static class OrderChain {

        /**
         */
        public OrderChainPredicate<NextableOrderChain> courseCode;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> courseName;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> courseLine;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> courseScale;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> courseVendor;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> courseDescription;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> buyPrice;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> msrp;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<CoursesEntity> many() {
            throw e;
        }

        public <P> Map<P, CoursesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CoursesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CoursesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<String> courseCode = (Each<String>) new Object();

        Each<String> courseName = (Each<String>) new Object();

        Each<String> courseLine = (Each<String>) new Object();

        Each<String> courseScale = (Each<String>) new Object();

        Each<String> courseVendor = (Each<String>) new Object();

        Each<String> courseDescription = (Each<String>) new Object();

        Each<BigDecimal> buyPrice = (Each<BigDecimal>) new Object();

        Each<BigDecimal> msrp = (Each<BigDecimal>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<String> courseCode = (MultiEach<String>) new Object();

        MultiEach<String> courseName = (MultiEach<String>) new Object();

        MultiEach<String> courseLine = (MultiEach<String>) new Object();

        MultiEach<String> courseScale = (MultiEach<String>) new Object();

        MultiEach<String> courseVendor = (MultiEach<String>) new Object();

        MultiEach<String> courseDescription = (MultiEach<String>) new Object();

        MultiEach<BigDecimal> buyPrice = (MultiEach<BigDecimal>) new Object();

        MultiEach<BigDecimal> msrp = (MultiEach<BigDecimal>) new Object();
    }

    public static EntityKey<CoursesEntity, String> courseCode;

    public static EntityKey<CoursesEntity, String> courseName;

    public static EntityKey<CoursesEntity, String> courseLine;

    public static EntityKey<CoursesEntity, String> courseScale;

    public static EntityKey<CoursesEntity, String> courseVendor;

    public static EntityKey<CoursesEntity, String> courseDescription;

    public static EntityKey<CoursesEntity, BigDecimal> buyPrice;

    public static EntityKey<CoursesEntity, BigDecimal> msrp;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.CoursesEntity\",\"entityName\":\"CoursesEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.CoursesMapper\",\"mapperName\":\"CoursesMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/CoursesMapper.xml\"],\"properties\":{\"buyPrice\":{\"columnName\":\"buyPrice\",\"propertyName\":\"buyPrice\",\"javaType\":{\"simpleName\":\"BigDecimal\",\"qualifier\":\"java.math.BigDecimal\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"courseName\":{\"columnName\":\"courseName\",\"propertyName\":\"courseName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":70,\"notnull\":true,\"defaultV\":null},\"courseScale\":{\"columnName\":\"courseScale\",\"propertyName\":\"courseScale\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":10,\"notnull\":true,\"defaultV\":null},\"courseVendor\":{\"columnName\":\"courseVendor\",\"propertyName\":\"courseVendor\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"courseCode\":{\"columnName\":\"courseCode\",\"propertyName\":\"courseCode\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":15,\"notnull\":true,\"defaultV\":null},\"msrp\":{\"columnName\":\"MSRP\",\"propertyName\":\"msrp\",\"javaType\":{\"simpleName\":\"BigDecimal\",\"qualifier\":\"java.math.BigDecimal\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"courseLine\":{\"columnName\":\"courseLine\",\"propertyName\":\"courseLine\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"courseDescription\":{\"columnName\":\"courseDescription\",\"propertyName\":\"courseDescription\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":65535,\"notnull\":true,\"defaultV\":null}},\"tableName\":\"courses\",\"notDeletedSql\":null}";
}
//c1c3c58e1642a21acc72cf95d4606d70
