package com.spldeolin.beginningmind.app.design;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.OfficesEntity;
import com.spldeolin.beginningmind.app.enums.OfficesStateEnum;

//@formatter:off
/**
 * <p>offices
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@SuppressWarnings("all")
public class OfficesDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private OfficesDesign() {
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
        public QueryChain officeCode;

        /**
         */
        public QueryChain city;

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
        public QueryChain state;

        /**
         */
        public QueryChain country;

        /**
         */
        public QueryChain postalCode;

        /**
         */
        public QueryChain territory;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<OfficesEntity> many() {
            throw e;
        }

        public <P> Map<P, OfficesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OfficesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OfficesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain officeCode(String officeCode);

        /**
         */
        NextableUpdateChain city(String city);

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
        NextableUpdateChain state(OfficesStateEnum state);

        /**
         */
        NextableUpdateChain country(String country);

        /**
         */
        NextableUpdateChain postalCode(String postalCode);

        /**
         */
        NextableUpdateChain territory(String territory);
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
        public ByChainPredicate<NEXT, String> officeCode;

        /**
         */
        public ByChainPredicate<NEXT, String> city;

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
        public ByChainPredicate<NEXT, OfficesStateEnum> state;

        /**
         */
        public ByChainPredicate<NEXT, String> country;

        /**
         */
        public ByChainPredicate<NEXT, String> postalCode;

        /**
         */
        public ByChainPredicate<NEXT, String> territory;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> officeCode;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> city;

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
        public ByChainPredicate<NextableByChainReturn, OfficesStateEnum> state;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> country;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> postalCode;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> territory;

        public List<OfficesEntity> many() {
            throw e;
        }

        public <P> Map<P, OfficesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OfficesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OfficesEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, String> officeCode;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> city;

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
        public ByChainPredicate<NextableByChainVoid, OfficesStateEnum> state;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> country;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> postalCode;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> territory;

        public int over() {
            throw e;
        }
    }

    public static class OrderChain {

        /**
         */
        public OrderChainPredicate<NextableOrderChain> officeCode;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> city;

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
        public OrderChainPredicate<NextableOrderChain> state;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> country;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> postalCode;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> territory;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<OfficesEntity> many() {
            throw e;
        }

        public <P> Map<P, OfficesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, OfficesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public OfficesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<String> officeCode = (Each<String>) new Object();

        Each<String> city = (Each<String>) new Object();

        Each<String> phone = (Each<String>) new Object();

        Each<String> addressLine1 = (Each<String>) new Object();

        Each<String> addressLine2 = (Each<String>) new Object();

        Each<OfficesStateEnum> state = (Each<OfficesStateEnum>) new Object();

        Each<String> country = (Each<String>) new Object();

        Each<String> postalCode = (Each<String>) new Object();

        Each<String> territory = (Each<String>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<String> officeCode = (MultiEach<String>) new Object();

        MultiEach<String> city = (MultiEach<String>) new Object();

        MultiEach<String> phone = (MultiEach<String>) new Object();

        MultiEach<String> addressLine1 = (MultiEach<String>) new Object();

        MultiEach<String> addressLine2 = (MultiEach<String>) new Object();

        MultiEach<OfficesStateEnum> state = (MultiEach<OfficesStateEnum>) new Object();

        MultiEach<String> country = (MultiEach<String>) new Object();

        MultiEach<String> postalCode = (MultiEach<String>) new Object();

        MultiEach<String> territory = (MultiEach<String>) new Object();
    }

    public static EntityKey<OfficesEntity, String> officeCode;

    public static EntityKey<OfficesEntity, String> city;

    public static EntityKey<OfficesEntity, String> phone;

    public static EntityKey<OfficesEntity, String> addressLine1;

    public static EntityKey<OfficesEntity, String> addressLine2;

    public static EntityKey<OfficesEntity, OfficesStateEnum> state;

    public static EntityKey<OfficesEntity, String> country;

    public static EntityKey<OfficesEntity, String> postalCode;

    public static EntityKey<OfficesEntity, String> territory;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.OfficesEntity\",\"entityName\":\"OfficesEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.OfficesMapper\",\"mapperName\":\"OfficesMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/OfficesMapper.xml\"],\"properties\":{\"country\":{\"columnName\":\"country\",\"propertyName\":\"country\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"city\":{\"columnName\":\"city\",\"propertyName\":\"city\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"phone\":{\"columnName\":\"phone\",\"propertyName\":\"phone\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"postalCode\":{\"columnName\":\"postalCode\",\"propertyName\":\"postalCode\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":15,\"notnull\":true,\"defaultV\":null},\"officeCode\":{\"columnName\":\"officeCode\",\"propertyName\":\"officeCode\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":10,\"notnull\":true,\"defaultV\":null},\"addressLine1\":{\"columnName\":\"addressLine1\",\"propertyName\":\"addressLine1\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"addressLine2\":{\"columnName\":\"addressLine2\",\"propertyName\":\"addressLine2\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":false,\"defaultV\":null},\"state\":{\"columnName\":\"state\",\"propertyName\":\"state\",\"javaType\":{\"simpleName\":\"OfficesStateEnum\",\"qualifier\":\"com.spldeolin.beginningmind.app.enums.OfficesStateEnum\"},\"description\":\"\",\"length\":50,\"notnull\":false,\"defaultV\":null},\"territory\":{\"columnName\":\"territory\",\"propertyName\":\"territory\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":10,\"notnull\":true,\"defaultV\":null}},\"tableName\":\"offices\",\"notDeletedSql\":null}";
}
//a8f67bbd29994d9fea7e329f98a474fe
