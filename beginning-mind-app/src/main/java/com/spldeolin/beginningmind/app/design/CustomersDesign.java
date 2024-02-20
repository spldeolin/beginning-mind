package com.spldeolin.beginningmind.app.design;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.CustomersEntity;

//@formatter:off
/**
 * <p>customers
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@SuppressWarnings("all")
public class CustomersDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private CustomersDesign() {
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
        public QueryChain customerNumber;

        /**
         */
        public QueryChain customerName;

        /**
         */
        public QueryChain contactLastName;

        /**
         */
        public QueryChain contactFirstName;

        /**
         */
        public QueryChain phone;

        /**
         */
        public QueryChain addressLine1;

        /**
         */
        public QueryChain addressLine2;

        /**
         */
        public QueryChain city;

        /**
         */
        public QueryChain state;

        /**
         */
        public QueryChain postalCode;

        /**
         */
        public QueryChain country;

        /**
         */
        public QueryChain salesRepEmployeeNumber;

        /**
         */
        public QueryChain creditLimit;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<CustomersEntity> many() {
            throw e;
        }

        public <P> Map<P, CustomersEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CustomersEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CustomersEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain customerNumber(Integer customerNumber);

        /**
         */
        NextableUpdateChain customerName(String customerName);

        /**
         */
        NextableUpdateChain contactLastName(String contactLastName);

        /**
         */
        NextableUpdateChain contactFirstName(String contactFirstName);

        /**
         */
        NextableUpdateChain phone(String phone);

        /**
         */
        NextableUpdateChain addressLine1(String addressLine1);

        /**
         */
        NextableUpdateChain addressLine2(String addressLine2);

        /**
         */
        NextableUpdateChain city(String city);

        /**
         */
        NextableUpdateChain state(String state);

        /**
         */
        NextableUpdateChain postalCode(String postalCode);

        /**
         */
        NextableUpdateChain country(String country);

        /**
         */
        NextableUpdateChain salesRepEmployeeNumber(Integer salesRepEmployeeNumber);

        /**
         */
        NextableUpdateChain creditLimit(BigDecimal creditLimit);
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
        public ByChainPredicate<NEXT, Integer> customerNumber;

        /**
         */
        public ByChainPredicate<NEXT, String> customerName;

        /**
         */
        public ByChainPredicate<NEXT, String> contactLastName;

        /**
         */
        public ByChainPredicate<NEXT, String> contactFirstName;

        /**
         */
        public ByChainPredicate<NEXT, String> phone;

        /**
         */
        public ByChainPredicate<NEXT, String> addressLine1;

        /**
         */
        public ByChainPredicate<NEXT, String> addressLine2;

        /**
         */
        public ByChainPredicate<NEXT, String> city;

        /**
         */
        public ByChainPredicate<NEXT, String> state;

        /**
         */
        public ByChainPredicate<NEXT, String> postalCode;

        /**
         */
        public ByChainPredicate<NEXT, String> country;

        /**
         */
        public ByChainPredicate<NEXT, Integer> salesRepEmployeeNumber;

        /**
         */
        public ByChainPredicate<NEXT, BigDecimal> creditLimit;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> customerNumber;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> customerName;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> contactLastName;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> contactFirstName;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> phone;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> addressLine1;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> addressLine2;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> city;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> state;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> postalCode;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> country;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> salesRepEmployeeNumber;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, BigDecimal> creditLimit;

        public List<CustomersEntity> many() {
            throw e;
        }

        public <P> Map<P, CustomersEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CustomersEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CustomersEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, Integer> customerNumber;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> customerName;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> contactLastName;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> contactFirstName;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> phone;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> addressLine1;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> addressLine2;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> city;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> state;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> postalCode;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> country;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, Integer> salesRepEmployeeNumber;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, BigDecimal> creditLimit;

        public int over() {
            throw e;
        }
    }

    public static class OrderChain {

        /**
         */
        public OrderChainPredicate<NextableOrderChain> customerNumber;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> customerName;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> contactLastName;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> contactFirstName;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> phone;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> addressLine1;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> addressLine2;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> city;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> state;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> postalCode;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> country;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> salesRepEmployeeNumber;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> creditLimit;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<CustomersEntity> many() {
            throw e;
        }

        public <P> Map<P, CustomersEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, CustomersEntity> many(MultiEach<P> property) {
            throw e;
        }

        public CustomersEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<Integer> customerNumber = (Each<Integer>) new Object();

        Each<String> customerName = (Each<String>) new Object();

        Each<String> contactLastName = (Each<String>) new Object();

        Each<String> contactFirstName = (Each<String>) new Object();

        Each<String> phone = (Each<String>) new Object();

        Each<String> addressLine1 = (Each<String>) new Object();

        Each<String> addressLine2 = (Each<String>) new Object();

        Each<String> city = (Each<String>) new Object();

        Each<String> state = (Each<String>) new Object();

        Each<String> postalCode = (Each<String>) new Object();

        Each<String> country = (Each<String>) new Object();

        Each<Integer> salesRepEmployeeNumber = (Each<Integer>) new Object();

        Each<BigDecimal> creditLimit = (Each<BigDecimal>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<Integer> customerNumber = (MultiEach<Integer>) new Object();

        MultiEach<String> customerName = (MultiEach<String>) new Object();

        MultiEach<String> contactLastName = (MultiEach<String>) new Object();

        MultiEach<String> contactFirstName = (MultiEach<String>) new Object();

        MultiEach<String> phone = (MultiEach<String>) new Object();

        MultiEach<String> addressLine1 = (MultiEach<String>) new Object();

        MultiEach<String> addressLine2 = (MultiEach<String>) new Object();

        MultiEach<String> city = (MultiEach<String>) new Object();

        MultiEach<String> state = (MultiEach<String>) new Object();

        MultiEach<String> postalCode = (MultiEach<String>) new Object();

        MultiEach<String> country = (MultiEach<String>) new Object();

        MultiEach<Integer> salesRepEmployeeNumber = (MultiEach<Integer>) new Object();

        MultiEach<BigDecimal> creditLimit = (MultiEach<BigDecimal>) new Object();
    }

    public static EntityKey<CustomersEntity, Integer> customerNumber;

    public static EntityKey<CustomersEntity, String> customerName;

    public static EntityKey<CustomersEntity, String> contactLastName;

    public static EntityKey<CustomersEntity, String> contactFirstName;

    public static EntityKey<CustomersEntity, String> phone;

    public static EntityKey<CustomersEntity, String> addressLine1;

    public static EntityKey<CustomersEntity, String> addressLine2;

    public static EntityKey<CustomersEntity, String> city;

    public static EntityKey<CustomersEntity, String> state;

    public static EntityKey<CustomersEntity, String> postalCode;

    public static EntityKey<CustomersEntity, String> country;

    public static EntityKey<CustomersEntity, Integer> salesRepEmployeeNumber;

    public static EntityKey<CustomersEntity, BigDecimal> creditLimit;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.CustomersEntity\",\"entityName\":\"CustomersEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.CustomersMapper\",\"mapperName\":\"CustomersMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/CustomersMapper.xml\"],\"properties\":{\"country\":{\"columnName\":\"country\",\"propertyName\":\"country\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"city\":{\"columnName\":\"city\",\"propertyName\":\"city\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"contactFirstName\":{\"columnName\":\"contactFirstName\",\"propertyName\":\"contactFirstName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"postalCode\":{\"columnName\":\"postalCode\",\"propertyName\":\"postalCode\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":15,\"notnull\":false,\"defaultV\":null},\"salesRepEmployeeNumber\":{\"columnName\":\"salesRepEmployeeNumber\",\"propertyName\":\"salesRepEmployeeNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":false,\"defaultV\":null},\"customerNumber\":{\"columnName\":\"customerNumber\",\"propertyName\":\"customerNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"customerName\":{\"columnName\":\"customerName\",\"propertyName\":\"customerName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"phone\":{\"columnName\":\"phone\",\"propertyName\":\"phone\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"addressLine1\":{\"columnName\":\"addressLine1\",\"propertyName\":\"addressLine1\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"creditLimit\":{\"columnName\":\"creditLimit\",\"propertyName\":\"creditLimit\",\"javaType\":{\"simpleName\":\"BigDecimal\",\"qualifier\":\"java.math.BigDecimal\"},\"description\":\"\",\"length\":null,\"notnull\":false,\"defaultV\":null},\"contactLastName\":{\"columnName\":\"contactLastName\",\"propertyName\":\"contactLastName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"addressLine2\":{\"columnName\":\"addressLine2\",\"propertyName\":\"addressLine2\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":false,\"defaultV\":null},\"state\":{\"columnName\":\"state\",\"propertyName\":\"state\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":false,\"defaultV\":null}},\"tableName\":\"customers\",\"notDeletedSql\":null}";
}
//612e022bffee8517d6bb10ed5b11471e
