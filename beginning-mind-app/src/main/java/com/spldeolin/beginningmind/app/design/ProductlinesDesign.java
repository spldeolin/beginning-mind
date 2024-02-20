package com.spldeolin.beginningmind.app.design;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.ProductlinesEntity;

//@formatter:off
/**
 * <p>productlines
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@SuppressWarnings("all")
public class ProductlinesDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private ProductlinesDesign() {
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
        public QueryChain productLine;

        /**
         */
        public QueryChain textDescription;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<ProductlinesEntity> many() {
            throw e;
        }

        public <P> Map<P, ProductlinesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, ProductlinesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public ProductlinesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain productLine(String productLine);

        /**
         */
        NextableUpdateChain textDescription(String textDescription);
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
        public ByChainPredicate<NEXT, String> productLine;

        /**
         */
        public ByChainPredicate<NEXT, String> textDescription;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> productLine;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> textDescription;

        public List<ProductlinesEntity> many() {
            throw e;
        }

        public <P> Map<P, ProductlinesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, ProductlinesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public ProductlinesEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, String> productLine;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> textDescription;

        public int over() {
            throw e;
        }
    }

    public static class OrderChain {

        /**
         */
        public OrderChainPredicate<NextableOrderChain> productLine;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> textDescription;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<ProductlinesEntity> many() {
            throw e;
        }

        public <P> Map<P, ProductlinesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, ProductlinesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public ProductlinesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<String> productLine = (Each<String>) new Object();

        Each<String> textDescription = (Each<String>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<String> productLine = (MultiEach<String>) new Object();

        MultiEach<String> textDescription = (MultiEach<String>) new Object();
    }

    public static EntityKey<ProductlinesEntity, String> productLine;

    public static EntityKey<ProductlinesEntity, String> textDescription;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.ProductlinesEntity\",\"entityName\":\"ProductlinesEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.ProductlinesMapper\",\"mapperName\":\"ProductlinesMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/ProductlinesMapper.xml\"],\"properties\":{\"productLine\":{\"columnName\":\"productLine\",\"propertyName\":\"productLine\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"textDescription\":{\"columnName\":\"textDescription\",\"propertyName\":\"textDescription\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":4000,\"notnull\":false,\"defaultV\":null}},\"tableName\":\"productlines\",\"notDeletedSql\":null}";
}
//747aa7b809c3907c6ac48d64ed1847a2
