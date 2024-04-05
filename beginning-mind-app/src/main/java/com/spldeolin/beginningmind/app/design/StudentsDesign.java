package com.spldeolin.beginningmind.app.design;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.StudentsEntity;
import com.spldeolin.beginningmind.app.enums.StudentsStateEnum;

//@formatter:off
/**
 * <p>students
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@SuppressWarnings("all")
public class StudentsDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private StudentsDesign() {
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
        public QueryChain studentNumber;

        /**
         */
        public QueryChain lastName;

        /**
         */
        public QueryChain firstName;

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

        public List<StudentsEntity> many() {
            throw e;
        }

        public <P> Map<P, StudentsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, StudentsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public StudentsEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain studentNumber(Integer studentNumber);

        /**
         */
        NextableUpdateChain lastName(String lastName);

        /**
         */
        NextableUpdateChain firstName(String firstName);

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
        NextableUpdateChain state(StudentsStateEnum state);

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
        public ByChainPredicate<NEXT, Integer> studentNumber;

        /**
         */
        public ByChainPredicate<NEXT, String> lastName;

        /**
         */
        public ByChainPredicate<NEXT, String> firstName;

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
        public ByChainPredicate<NEXT, StudentsStateEnum> state;

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
        public ByChainPredicate<NextableByChainReturn, Integer> studentNumber;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> lastName;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> firstName;

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
        public ByChainPredicate<NextableByChainReturn, StudentsStateEnum> state;

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

        public List<StudentsEntity> many() {
            throw e;
        }

        public <P> Map<P, StudentsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, StudentsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public StudentsEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, Integer> studentNumber;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> lastName;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> firstName;

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
        public ByChainPredicate<NextableByChainVoid, StudentsStateEnum> state;

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
        public OrderChainPredicate<NextableOrderChain> studentNumber;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> lastName;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> firstName;

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

        public List<StudentsEntity> many() {
            throw e;
        }

        public <P> Map<P, StudentsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, StudentsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public StudentsEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<Integer> studentNumber = (Each<Integer>) new Object();

        Each<String> lastName = (Each<String>) new Object();

        Each<String> firstName = (Each<String>) new Object();

        Each<String> phone = (Each<String>) new Object();

        Each<String> addressLine1 = (Each<String>) new Object();

        Each<String> addressLine2 = (Each<String>) new Object();

        Each<String> city = (Each<String>) new Object();

        Each<StudentsStateEnum> state = (Each<StudentsStateEnum>) new Object();

        Each<String> postalCode = (Each<String>) new Object();

        Each<String> country = (Each<String>) new Object();

        Each<Integer> salesRepEmployeeNumber = (Each<Integer>) new Object();

        Each<BigDecimal> creditLimit = (Each<BigDecimal>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<Integer> studentNumber = (MultiEach<Integer>) new Object();

        MultiEach<String> lastName = (MultiEach<String>) new Object();

        MultiEach<String> firstName = (MultiEach<String>) new Object();

        MultiEach<String> phone = (MultiEach<String>) new Object();

        MultiEach<String> addressLine1 = (MultiEach<String>) new Object();

        MultiEach<String> addressLine2 = (MultiEach<String>) new Object();

        MultiEach<String> city = (MultiEach<String>) new Object();

        MultiEach<StudentsStateEnum> state = (MultiEach<StudentsStateEnum>) new Object();

        MultiEach<String> postalCode = (MultiEach<String>) new Object();

        MultiEach<String> country = (MultiEach<String>) new Object();

        MultiEach<Integer> salesRepEmployeeNumber = (MultiEach<Integer>) new Object();

        MultiEach<BigDecimal> creditLimit = (MultiEach<BigDecimal>) new Object();
    }

    public static EntityKey<StudentsEntity, Integer> studentNumber;

    public static EntityKey<StudentsEntity, String> lastName;

    public static EntityKey<StudentsEntity, String> firstName;

    public static EntityKey<StudentsEntity, String> phone;

    public static EntityKey<StudentsEntity, String> addressLine1;

    public static EntityKey<StudentsEntity, String> addressLine2;

    public static EntityKey<StudentsEntity, String> city;

    public static EntityKey<StudentsEntity, StudentsStateEnum> state;

    public static EntityKey<StudentsEntity, String> postalCode;

    public static EntityKey<StudentsEntity, String> country;

    public static EntityKey<StudentsEntity, Integer> salesRepEmployeeNumber;

    public static EntityKey<StudentsEntity, BigDecimal> creditLimit;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.StudentsEntity\",\"entityName\":\"StudentsEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.StudentsMapper\",\"mapperName\":\"StudentsMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/StudentsMapper.xml\"],\"properties\":{\"lastName\":{\"columnName\":\"LastName\",\"propertyName\":\"lastName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"firstName\":{\"columnName\":\"FirstName\",\"propertyName\":\"firstName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"country\":{\"columnName\":\"country\",\"propertyName\":\"country\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"phone\":{\"columnName\":\"phone\",\"propertyName\":\"phone\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"city\":{\"columnName\":\"city\",\"propertyName\":\"city\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"studentNumber\":{\"columnName\":\"studentNumber\",\"propertyName\":\"studentNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"postalCode\":{\"columnName\":\"postalCode\",\"propertyName\":\"postalCode\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":15,\"notnull\":false,\"defaultV\":null},\"addressLine1\":{\"columnName\":\"addressLine1\",\"propertyName\":\"addressLine1\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"creditLimit\":{\"columnName\":\"creditLimit\",\"propertyName\":\"creditLimit\",\"javaType\":{\"simpleName\":\"BigDecimal\",\"qualifier\":\"java.math.BigDecimal\"},\"description\":\"\",\"length\":null,\"notnull\":false,\"defaultV\":null},\"addressLine2\":{\"columnName\":\"addressLine2\",\"propertyName\":\"addressLine2\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":false,\"defaultV\":null},\"salesRepEmployeeNumber\":{\"columnName\":\"salesRepEmployeeNumber\",\"propertyName\":\"salesRepEmployeeNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":false,\"defaultV\":null},\"state\":{\"columnName\":\"state\",\"propertyName\":\"state\",\"javaType\":{\"simpleName\":\"StudentsStateEnum\",\"qualifier\":\"com.spldeolin.beginningmind.app.enums.StudentsStateEnum\"},\"description\":\"\",\"length\":50,\"notnull\":false,\"defaultV\":null}},\"tableName\":\"students\",\"notDeletedSql\":null}";
}
//0aa78086d16280a29fe4d8ec31a2abed
