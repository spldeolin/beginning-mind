package com.spldeolin.beginningmind.app.design;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.OrdersEntity;

//@formatter:off
/**
 * <p>orders
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@SuppressWarnings("all")
public class OrdersDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private OrdersDesign() {
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
        public QueryChain orderDate;

        /**
         */
        public QueryChain requiredDate;

        /**
         */
        public QueryChain shippedDate;

        /**
         */
        public QueryChain status;

        /**
         */
        public QueryChain comments;

        /**
         */
        public QueryChain studentNumber;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<OrdersEntity> many() {
            throw e;
        }

        public <P> Map<P, OrdersEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OrdersEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OrdersEntity one() {
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
        NextableUpdateChain orderDate(Date orderDate);

        /**
         */
        NextableUpdateChain requiredDate(Date requiredDate);

        /**
         */
        NextableUpdateChain shippedDate(Date shippedDate);

        /**
         */
        NextableUpdateChain status(String status);

        /**
         */
        NextableUpdateChain comments(String comments);

        /**
         */
        NextableUpdateChain studentNumber(Integer studentNumber);
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
        public ByChainPredicate<NEXT, Date> orderDate;

        /**
         */
        public ByChainPredicate<NEXT, Date> requiredDate;

        /**
         */
        public ByChainPredicate<NEXT, Date> shippedDate;

        /**
         */
        public ByChainPredicate<NEXT, String> status;

        /**
         */
        public ByChainPredicate<NEXT, String> comments;

        /**
         */
        public ByChainPredicate<NEXT, Integer> studentNumber;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> orderNumber;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Date> orderDate;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Date> requiredDate;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Date> shippedDate;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> status;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> comments;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> studentNumber;

        public List<OrdersEntity> many() {
            throw e;
        }

        public <P> Map<P, OrdersEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OrdersEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OrdersEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, Date> orderDate;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, Date> requiredDate;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, Date> shippedDate;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> status;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> comments;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, Integer> studentNumber;

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
        public OrderChainPredicate<NextableOrderChain> orderDate;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> requiredDate;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> shippedDate;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> status;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> comments;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> studentNumber;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<OrdersEntity> many() {
            throw e;
        }

        public <P> Map<P, OrdersEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OrdersEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OrdersEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<Integer> orderNumber = (Each<Integer>) new Object();

        Each<Date> orderDate = (Each<Date>) new Object();

        Each<Date> requiredDate = (Each<Date>) new Object();

        Each<Date> shippedDate = (Each<Date>) new Object();

        Each<String> status = (Each<String>) new Object();

        Each<String> comments = (Each<String>) new Object();

        Each<Integer> studentNumber = (Each<Integer>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<Integer> orderNumber = (MultiEach<Integer>) new Object();

        MultiEach<Date> orderDate = (MultiEach<Date>) new Object();

        MultiEach<Date> requiredDate = (MultiEach<Date>) new Object();

        MultiEach<Date> shippedDate = (MultiEach<Date>) new Object();

        MultiEach<String> status = (MultiEach<String>) new Object();

        MultiEach<String> comments = (MultiEach<String>) new Object();

        MultiEach<Integer> studentNumber = (MultiEach<Integer>) new Object();
    }

    public static EntityKey<OrdersEntity, Integer> orderNumber;

    public static EntityKey<OrdersEntity, Date> orderDate;

    public static EntityKey<OrdersEntity, Date> requiredDate;

    public static EntityKey<OrdersEntity, Date> shippedDate;

    public static EntityKey<OrdersEntity, String> status;

    public static EntityKey<OrdersEntity, String> comments;

    public static EntityKey<OrdersEntity, Integer> studentNumber;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.OrdersEntity\",\"entityName\":\"OrdersEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.OrdersMapper\",\"mapperName\":\"OrdersMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/OrdersMapper.xml\"],\"properties\":{\"orderNumber\":{\"columnName\":\"orderNumber\",\"propertyName\":\"orderNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"comments\":{\"columnName\":\"comments\",\"propertyName\":\"comments\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":65535,\"notnull\":false,\"defaultV\":null},\"studentNumber\":{\"columnName\":\"studentNumber\",\"propertyName\":\"studentNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"requiredDate\":{\"columnName\":\"requiredDate\",\"propertyName\":\"requiredDate\",\"javaType\":{\"simpleName\":\"Date\",\"qualifier\":\"java.util.Date\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"orderDate\":{\"columnName\":\"orderDate\",\"propertyName\":\"orderDate\",\"javaType\":{\"simpleName\":\"Date\",\"qualifier\":\"java.util.Date\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"shippedDate\":{\"columnName\":\"shippedDate\",\"propertyName\":\"shippedDate\",\"javaType\":{\"simpleName\":\"Date\",\"qualifier\":\"java.util.Date\"},\"description\":\"\",\"length\":null,\"notnull\":false,\"defaultV\":null},\"status\":{\"columnName\":\"status\",\"propertyName\":\"status\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":15,\"notnull\":true,\"defaultV\":null}},\"tableName\":\"orders\",\"notDeletedSql\":null}";
}
//723e41ec9bd6ac252d67177fb0bf172d
