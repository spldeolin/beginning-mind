package com.spldeolin.beginningmind.app.design;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.OrderdetailsEntity;

//@formatter:off
/**
 * <p>orderdetails
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@SuppressWarnings("all")
public class OrderdetailsDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private OrderdetailsDesign() {
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
        public QueryChain orderNumber;

        /**
         */
        public QueryChain courseCode;

        /**
         */
        public QueryChain quantityOrdered;

        /**
         */
        public QueryChain priceEach;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<OrderdetailsEntity> many() {
            throw e;
        }

        public <P> Map<P, OrderdetailsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OrderdetailsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OrderdetailsEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain orderNumber(Integer orderNumber);

        /**
         */
        NextableUpdateChain courseCode(String courseCode);

        /**
         */
        NextableUpdateChain quantityOrdered(Integer quantityOrdered);

        /**
         */
        NextableUpdateChain priceEach(BigDecimal priceEach);
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
        public ByChainPredicate<NEXT, Integer> orderNumber;

        /**
         */
        public ByChainPredicate<NEXT, String> courseCode;

        /**
         */
        public ByChainPredicate<NEXT, Integer> quantityOrdered;

        /**
         */
        public ByChainPredicate<NEXT, BigDecimal> priceEach;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> orderNumber;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> courseCode;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> quantityOrdered;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, BigDecimal> priceEach;

        public List<OrderdetailsEntity> many() {
            throw e;
        }

        public <P> Map<P, OrderdetailsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OrderdetailsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OrderdetailsEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, Integer> orderNumber;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> courseCode;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, Integer> quantityOrdered;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, BigDecimal> priceEach;

        public int over() {
            throw e;
        }
    }

    public static class OrderChain {

        /**
         */
        public OrderChainPredicate<NextableOrderChain> orderNumber;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> courseCode;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> quantityOrdered;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> priceEach;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<OrderdetailsEntity> many() {
            throw e;
        }

        public <P> Map<P, OrderdetailsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OrderdetailsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OrderdetailsEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<Integer> orderNumber = (Each<Integer>) new Object();

        Each<String> courseCode = (Each<String>) new Object();

        Each<Integer> quantityOrdered = (Each<Integer>) new Object();

        Each<BigDecimal> priceEach = (Each<BigDecimal>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<Integer> orderNumber = (MultiEach<Integer>) new Object();

        MultiEach<String> courseCode = (MultiEach<String>) new Object();

        MultiEach<Integer> quantityOrdered = (MultiEach<Integer>) new Object();

        MultiEach<BigDecimal> priceEach = (MultiEach<BigDecimal>) new Object();
    }

    public static EntityKey<OrderdetailsEntity, Integer> orderNumber;

    public static EntityKey<OrderdetailsEntity, String> courseCode;

    public static EntityKey<OrderdetailsEntity, Integer> quantityOrdered;

    public static EntityKey<OrderdetailsEntity, BigDecimal> priceEach;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.OrderdetailsEntity\",\"entityName\":\"OrderdetailsEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.OrderdetailsMapper\",\"mapperName\":\"OrderdetailsMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/OrderdetailsMapper.xml\"],\"properties\":{\"orderNumber\":{\"columnName\":\"orderNumber\",\"propertyName\":\"orderNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"courseCode\":{\"columnName\":\"courseCode\",\"propertyName\":\"courseCode\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":15,\"notnull\":true,\"defaultV\":null},\"quantityOrdered\":{\"columnName\":\"quantityOrdered\",\"propertyName\":\"quantityOrdered\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"priceEach\":{\"columnName\":\"priceEach\",\"propertyName\":\"priceEach\",\"javaType\":{\"simpleName\":\"BigDecimal\",\"qualifier\":\"java.math.BigDecimal\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null}},\"tableName\":\"orderdetails\",\"notDeletedSql\":null}";
}
//c50912235727af54064e5cfedd63edd2
