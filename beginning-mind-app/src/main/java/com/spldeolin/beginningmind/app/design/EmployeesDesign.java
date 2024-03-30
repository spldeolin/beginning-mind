package com.spldeolin.beginningmind.app.design;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.EmployeesEntity;

//@formatter:off
/**
 * <p>employees
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@SuppressWarnings("all")
public class EmployeesDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private EmployeesDesign() {
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
        public QueryChain employeeNumber;

        /**
         */
        public QueryChain lastName;

        /**
         */
        public QueryChain firstName;

        /**
         */
        public QueryChain extension;

        /**
         */
        public QueryChain email;

        /**
         */
        public QueryChain officeCode;

        /**
         */
        public QueryChain reportsTo;

        /**
         */
        public QueryChain jobTitle;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<EmployeesEntity> many() {
            throw e;
        }

        public <P> Map<P, EmployeesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, EmployeesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public EmployeesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface UpdateChain {

        /**
         */
        NextableUpdateChain employeeNumber(Integer employeeNumber);

        /**
         */
        NextableUpdateChain lastName(String lastName);

        /**
         */
        NextableUpdateChain firstName(String firstName);

        /**
         */
        NextableUpdateChain extension(String extension);

        /**
         */
        NextableUpdateChain email(String email);

        /**
         */
        NextableUpdateChain officeCode(String officeCode);

        /**
         */
        NextableUpdateChain reportsTo(Integer reportsTo);

        /**
         */
        NextableUpdateChain jobTitle(String jobTitle);
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
        public ByChainPredicate<NEXT, Integer> employeeNumber;

        /**
         */
        public ByChainPredicate<NEXT, String> lastName;

        /**
         */
        public ByChainPredicate<NEXT, String> firstName;

        /**
         */
        public ByChainPredicate<NEXT, String> extension;

        /**
         */
        public ByChainPredicate<NEXT, String> email;

        /**
         */
        public ByChainPredicate<NEXT, String> officeCode;

        /**
         */
        public ByChainPredicate<NEXT, Integer> reportsTo;

        /**
         */
        public ByChainPredicate<NEXT, String> jobTitle;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> employeeNumber;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> lastName;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> firstName;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> extension;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> email;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> officeCode;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> reportsTo;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> jobTitle;

        public List<EmployeesEntity> many() {
            throw e;
        }

        public <P> Map<P, EmployeesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, EmployeesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public EmployeesEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, Integer> employeeNumber;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> lastName;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> firstName;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> extension;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> email;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> officeCode;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, Integer> reportsTo;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, String> jobTitle;

        public int over() {
            throw e;
        }
    }

    public static class OrderChain {

        /**
         */
        public OrderChainPredicate<NextableOrderChain> employeeNumber;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> lastName;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> firstName;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> extension;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> email;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> officeCode;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> reportsTo;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> jobTitle;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<EmployeesEntity> many() {
            throw e;
        }

        public <P> Map<P, EmployeesEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, EmployeesEntity> many(MultiEach<P> property) {
            throw e;
        }

        public EmployeesEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<Integer> employeeNumber = (Each<Integer>) new Object();

        Each<String> lastName = (Each<String>) new Object();

        Each<String> firstName = (Each<String>) new Object();

        Each<String> extension = (Each<String>) new Object();

        Each<String> email = (Each<String>) new Object();

        Each<String> officeCode = (Each<String>) new Object();

        Each<Integer> reportsTo = (Each<Integer>) new Object();

        Each<String> jobTitle = (Each<String>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<Integer> employeeNumber = (MultiEach<Integer>) new Object();

        MultiEach<String> lastName = (MultiEach<String>) new Object();

        MultiEach<String> firstName = (MultiEach<String>) new Object();

        MultiEach<String> extension = (MultiEach<String>) new Object();

        MultiEach<String> email = (MultiEach<String>) new Object();

        MultiEach<String> officeCode = (MultiEach<String>) new Object();

        MultiEach<Integer> reportsTo = (MultiEach<Integer>) new Object();

        MultiEach<String> jobTitle = (MultiEach<String>) new Object();
    }

    public static EntityKey<EmployeesEntity, Integer> employeeNumber;

    public static EntityKey<EmployeesEntity, String> lastName;

    public static EntityKey<EmployeesEntity, String> firstName;

    public static EntityKey<EmployeesEntity, String> extension;

    public static EntityKey<EmployeesEntity, String> email;

    public static EntityKey<EmployeesEntity, String> officeCode;

    public static EntityKey<EmployeesEntity, Integer> reportsTo;

    public static EntityKey<EmployeesEntity, String> jobTitle;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.EmployeesEntity\",\"entityName\":\"EmployeesEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.EmployeesMapper\",\"mapperName\":\"EmployeesMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/EmployeesMapper.xml\"],\"properties\":{\"lastName\":{\"columnName\":\"lastName\",\"propertyName\":\"lastName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"firstName\":{\"columnName\":\"firstName\",\"propertyName\":\"firstName\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"extension\":{\"columnName\":\"extension\",\"propertyName\":\"extension\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":10,\"notnull\":true,\"defaultV\":null},\"jobTitle\":{\"columnName\":\"jobTitle\",\"propertyName\":\"jobTitle\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"officeCode\":{\"columnName\":\"officeCode\",\"propertyName\":\"officeCode\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":10,\"notnull\":true,\"defaultV\":null},\"reportsTo\":{\"columnName\":\"reportsTo\",\"propertyName\":\"reportsTo\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":false,\"defaultV\":null},\"email\":{\"columnName\":\"email\",\"propertyName\":\"email\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":100,\"notnull\":true,\"defaultV\":null},\"employeeNumber\":{\"columnName\":\"employeeNumber\",\"propertyName\":\"employeeNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null}},\"tableName\":\"employees\",\"notDeletedSql\":null}";
}
//d8ddd9b1f743065ad62d03a794a5be1d
