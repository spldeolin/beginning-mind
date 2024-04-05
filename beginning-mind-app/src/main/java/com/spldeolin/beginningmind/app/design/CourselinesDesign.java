package com.spldeolin.beginningmind.app.design;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.CourselinesEntity;

//@formatter:off
/**
 * <p>courselines
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@SuppressWarnings("all")
public class CourselinesDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private CourselinesDesign() {
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
        public QueryChain courseLine;

        /**
         */
        public QueryChain textDescription;

        /**
         */
        public QueryChain htmlDescription;

        /**
         */
        public QueryChain imageUrl;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<CourselinesEntity> many() {
            throw e;
        }

        public <P> Map<P, CourselinesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CourselinesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CourselinesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain courseLine(String courseLine);

        /**
         */
        NextableUpdateChain textDescription(String textDescription);

        /**
         */
        NextableUpdateChain htmlDescription(String htmlDescription);

        /**
         */
        NextableUpdateChain imageUrl(String imageUrl);
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
        public ByChainPredicate<NEXT, String> courseLine;

        /**
         */
        public ByChainPredicate<NEXT, String> textDescription;

        /**
         */
        public ByChainPredicate<NEXT, String> htmlDescription;

        /**
         */
        public ByChainPredicate<NEXT, String> imageUrl;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> courseLine;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> textDescription;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> htmlDescription;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> imageUrl;

        public List<CourselinesEntity> many() {
            throw e;
        }

        public <P> Map<P, CourselinesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CourselinesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CourselinesEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, String> courseLine;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> textDescription;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> htmlDescription;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> imageUrl;

        public int over() {
            throw e;
        }
    }

    public static class OrderChain {

        /**
         */
        public OrderChainPredicate<NextableOrderChain> courseLine;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> textDescription;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> htmlDescription;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> imageUrl;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<CourselinesEntity> many() {
            throw e;
        }

        public <P> Map<P, CourselinesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CourselinesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CourselinesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<String> courseLine = (Each<String>) new Object();

        Each<String> textDescription = (Each<String>) new Object();

        Each<String> htmlDescription = (Each<String>) new Object();

        Each<String> imageUrl = (Each<String>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<String> courseLine = (MultiEach<String>) new Object();

        MultiEach<String> textDescription = (MultiEach<String>) new Object();

        MultiEach<String> htmlDescription = (MultiEach<String>) new Object();

        MultiEach<String> imageUrl = (MultiEach<String>) new Object();
    }

    public static EntityKey<CourselinesEntity, String> courseLine;

    public static EntityKey<CourselinesEntity, String> textDescription;

    public static EntityKey<CourselinesEntity, String> htmlDescription;

    public static EntityKey<CourselinesEntity, String> imageUrl;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.CourselinesEntity\",\"entityName\":\"CourselinesEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.CourselinesMapper\",\"mapperName\":\"CourselinesMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/CourselinesMapper.xml\"],\"properties\":{\"imageUrl\":{\"columnName\":\"imageUrl\",\"propertyName\":\"imageUrl\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":255,\"notnull\":false,\"defaultV\":null},\"textDescription\":{\"columnName\":\"textDescription\",\"propertyName\":\"textDescription\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":4000,\"notnull\":false,\"defaultV\":null},\"courseLine\":{\"columnName\":\"courseLine\",\"propertyName\":\"courseLine\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"htmlDescription\":{\"columnName\":\"htmlDescription\",\"propertyName\":\"htmlDescription\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":4294967295,\"notnull\":false,\"defaultV\":null}},\"tableName\":\"courselines\",\"notDeletedSql\":null}";
}
//5a8910df4d401e4fb339b1fcc8156696
