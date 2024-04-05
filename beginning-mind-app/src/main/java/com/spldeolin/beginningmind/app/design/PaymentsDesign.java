package com.spldeolin.beginningmind.app.design;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Multimap;
import com.spldeolin.allison1875.support.ByChainPredicate;
import com.spldeolin.allison1875.support.EntityKey;
import com.spldeolin.allison1875.support.OrderChainPredicate;
import com.spldeolin.beginningmind.app.entity.PaymentsEntity;

//@formatter:off
/**
 * <p>payments
 * <p>
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-04-05
 */
@SuppressWarnings("all")
public class PaymentsDesign {

    private final static UnsupportedOperationException e = new UnsupportedOperationException();

    private PaymentsDesign() {
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
        public QueryChain checkNumber;

        /**
         */
        public QueryChain paymentDate;

        /**
         */
        public QueryChain amount;

        public ByChainReturn<NextableByChainReturn> by() {
            throw e;
        }

        public ByChainReturn<NextableByChainReturn> byForced() {
            throw e;
        }

        public OrderChain order() {
            throw e;
        }

        public List<PaymentsEntity> many() {
            throw e;
        }

        public <P> Map<P, PaymentsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, PaymentsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public PaymentsEntity one() {
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
        NextableUpdateChain checkNumber(String checkNumber);

        /**
         */
        NextableUpdateChain paymentDate(Date paymentDate);

        /**
         */
        NextableUpdateChain amount(BigDecimal amount);
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
        public ByChainPredicate<NEXT, String> checkNumber;

        /**
         */
        public ByChainPredicate<NEXT, Date> paymentDate;

        /**
         */
        public ByChainPredicate<NEXT, BigDecimal> amount;
    }

    public static class NextableByChainReturn {

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Integer> studentNumber;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, String> checkNumber;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, Date> paymentDate;

        /**
         */
        public ByChainPredicate<NextableByChainReturn, BigDecimal> amount;

        public List<PaymentsEntity> many() {
            throw e;
        }

        public <P> Map<P, PaymentsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, PaymentsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public PaymentsEntity one() {
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
        public ByChainPredicate<NextableByChainVoid, String> checkNumber;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, Date> paymentDate;

        /**
         */
        public ByChainPredicate<NextableByChainVoid, BigDecimal> amount;

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
        public OrderChainPredicate<NextableOrderChain> checkNumber;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> paymentDate;

        /**
         */
        public OrderChainPredicate<NextableOrderChain> amount;
    }

    public static class NextableOrderChain extends OrderChain {

        public List<PaymentsEntity> many() {
            throw e;
        }

        public <P> Map<P, PaymentsEntity> many(Each<P> property) {
            throw e;
        }

        public <P> Multimap<P, PaymentsEntity> many(MultiEach<P> property) {
            throw e;
        }

        public PaymentsEntity one() {
            throw e;
        }

        public int count() {
            throw e;
        }
    }

    public interface Each<P> {

        Each<Integer> studentNumber = (Each<Integer>) new Object();

        Each<String> checkNumber = (Each<String>) new Object();

        Each<Date> paymentDate = (Each<Date>) new Object();

        Each<BigDecimal> amount = (Each<BigDecimal>) new Object();
    }

    public interface MultiEach<P> {

        MultiEach<Integer> studentNumber = (MultiEach<Integer>) new Object();

        MultiEach<String> checkNumber = (MultiEach<String>) new Object();

        MultiEach<Date> paymentDate = (MultiEach<Date>) new Object();

        MultiEach<BigDecimal> amount = (MultiEach<BigDecimal>) new Object();
    }

    public static EntityKey<PaymentsEntity, Integer> studentNumber;

    public static EntityKey<PaymentsEntity, String> checkNumber;

    public static EntityKey<PaymentsEntity, Date> paymentDate;

    public static EntityKey<PaymentsEntity, BigDecimal> amount;

    String meta = "{\"entityQualifier\":\"com.spldeolin.beginningmind.app.entity.PaymentsEntity\",\"entityName\":\"PaymentsEntity\",\"mapperQualifier\":\"com.spldeolin.beginningmind.app.mapper.PaymentsMapper\",\"mapperName\":\"PaymentsMapper\",\"mapperRelativePaths\":[\"src/main/resources/mapper/PaymentsMapper.xml\"],\"properties\":{\"checkNumber\":{\"columnName\":\"checkNumber\",\"propertyName\":\"checkNumber\",\"javaType\":{\"simpleName\":\"String\",\"qualifier\":\"java.lang.String\"},\"description\":\"\",\"length\":50,\"notnull\":true,\"defaultV\":null},\"amount\":{\"columnName\":\"amount\",\"propertyName\":\"amount\",\"javaType\":{\"simpleName\":\"BigDecimal\",\"qualifier\":\"java.math.BigDecimal\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"studentNumber\":{\"columnName\":\"studentNumber\",\"propertyName\":\"studentNumber\",\"javaType\":{\"simpleName\":\"Integer\",\"qualifier\":\"java.lang.Integer\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null},\"paymentDate\":{\"columnName\":\"paymentDate\",\"propertyName\":\"paymentDate\",\"javaType\":{\"simpleName\":\"Date\",\"qualifier\":\"java.util.Date\"},\"description\":\"\",\"length\":null,\"notnull\":true,\"defaultV\":null}},\"tableName\":\"payments\",\"notDeletedSql\":null}";
}
//a1b48a2340d2a964f59fb61d8d2ff194
