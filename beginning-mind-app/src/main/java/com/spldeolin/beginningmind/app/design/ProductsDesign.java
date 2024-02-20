package com.spldeolin.beginningmind.app.design;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.ProductsEntity;

//@formatter:off
/**
 * <p>products
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@SuppressWarnings("all")
public class ProductsDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private ProductsDesign() {
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
        public QueryChain productCode;

        /**
         */
        public QueryChain productName;

        /**
         */
        public QueryChain productLine;

        /**
         */
        public QueryChain productScale;

        /**
         */
        public QueryChain productVendor;

        /**
         */
        public QueryChain productDescription;

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

        public List<ProductsEntity> many() {
            throw e;
        }

        public <P> Map<P, ProductsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, ProductsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public ProductsEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain productCode(String productCode);

        /**
         */
        NextableUpdateChain productName(String productName);

        /**
         */
        NextableUpdateChain productLine(String productLine);

        /**
         */
        NextableUpdateChain productScale(String productScale);

        /**
         */
        NextableUpdateChain productVendor(String productVendor);

        /**
         */
        NextableUpdateChain productDescription(String productDescription);

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
        public ByChainPredicate<NEXT, String> productCode;

        /**
         */
        public ByChainPredicate<NEXT, String> productName;

        /**
         */
        public ByChainPredicate<NEXT, String> productLine;

        /**
         */
        public ByChainPredicate<NEXT, String> productScale;

        /**
         */
        public ByChainPredicate<NEXT, String> productVendor;

        /**
         */
        public ByChainPredicate<NEXT, String> productDescription;

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
        public ByChainPredicate<NextableByChainReturn, String> productCode;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> productName;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> productLine;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> productScale;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> productVendor;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> productDescription;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, BigDecimal> buyPrice;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, BigDecimal> msrp;

        public List<ProductsEntity> many() {
            throw e;
        }

        public <P> Map<P, ProductsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, ProductsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public ProductsEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, String> productCode;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> productName;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> productLine;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> productScale;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> productVendor;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> productDescription;

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
        public OrderChainPredicate<NextableOrderChain> productCode;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> productName;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> productLine;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> productScale;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> productVendor;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> productDescription;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> buyPrice;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> msrp;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<ProductsEntity> many() {
            throw e;
        }

        public <P> Map<P, ProductsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, ProductsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public ProductsEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<String> productCode = (Each<String>) new Object();

        Each<String> productName = (Each<String>) new Object();

        Each<String> productLine = (Each<String>) new Object();

        Each<String> productScale = (Each<String>) new Object();

        Each<String> productVendor = (Each<String>) new Object();

        Each<String> productDescription = (Each<String>) new Object();

        Each<BigDecimal> buyPrice = (Each<BigDecimal>) new Object();

        Each<BigDecimal> msrp = (Each<BigDecimal>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<String> productCode = (MultiEach<String>) new Object();

        MultiEach<String> productName = (MultiEach<String>) new Object();

        MultiEach<String> productLine = (MultiEach<String>) new Object();

        MultiEach<String> productScale = (MultiEach<String>) new Object();

        MultiEach<String> productVendor = (MultiEach<String>) new Object();

        MultiEach<String> productDescription = (MultiEach<String>) new Object();

        MultiEach<BigDecimal> buyPrice = (MultiEach<BigDecimal>) new Object();

        MultiEach<BigDecimal> msrp = (MultiEach<BigDecimal>) new Object();
    }

    public static EntityKey<ProductsEntity, String> productCode;

    public static EntityKey<ProductsEntity, String> productName;

    public static EntityKey<ProductsEntity, String> productLine;

    public static EntityKey<ProductsEntity, String> productScale;

    public static EntityKey<ProductsEntity, String> productVendor;

    public static EntityKey<ProductsEntity, String> productDescription;

    public static EntityKey<ProductsEntity, BigDecimal> buyPrice;

    public static EntityKey<ProductsEntity, BigDecimal> msrp;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.ProductsEntity\",\"entityName\":\"ProductsEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.ProductsMapper\",\"mapperName\":\"ProductsMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/ProductsMapper.xml\"],\"properties\":{\"productLine\":{\"columnName\":\"productLine\",\"propertyName\":\"productLine\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"buyPrice\":{\"columnName\":\"buyPrice\",\"propertyName\":\"buyPrice\",\"javaType\":{\"simpleName\":\"BigDecimal\",\"qualifier\":\"java.math.BigDecimal\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"productCode\":{\"columnName\":\"productCode\",\"propertyName\":\"productCode\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":15,\"notnull\":true,\"defaultV\":null},\"productScale\":{\"columnName\":\"productScale\",\"propertyName\":\"productScale\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":10,\"notnull\":true,\"defaultV\":null},\"msrp\":{\"columnName\":\"MSRP\",\"propertyName\":\"msrp\",\"javaType\":{\"simpleName\":\"BigDecimal\",\"qualifier\":\"java.math.BigDecimal\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"productName\":{\"columnName\":\"productName\",\"propertyName\":\"productName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":70,\"notnull\":true,\"defaultV\":null},\"productVendor\":{\"columnName\":\"productVendor\",\"propertyName\":\"productVendor\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"productDescription\":{\"columnName\":\"productDescription\",\"propertyName\":\"productDescription\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":65535,\"notnull\":true,\"defaultV\":null}},\"tableName\":\"products\",\"notDeletedSql\":null}";
}
//2e9538d4a72362c308d2a345e3428418
