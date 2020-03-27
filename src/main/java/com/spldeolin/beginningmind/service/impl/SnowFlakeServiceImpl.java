package com.spldeolin.beginningmind.service.impl;

import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.CoreProperties;
import com.spldeolin.beginningmind.service.SnowFlakeService;
import com.spldeolin.beginningmind.util.TimeUtils;

/**
 * @author Deolin 2018/11/12
 */
@Service
public class SnowFlakeServiceImpl implements SnowFlakeService {

    @Autowired
    private CoreProperties coreProperties;

    /**
     * 起始的时间戳 2018-11-12 13:27:37
     */
    private final static long START_STMP = TimeUtils.toUnixTimestamp(LocalDateTime.of(2018, 11, 12, 13, 27, 37)) * 1000L;

    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数
     */
    private final static long DATACENTER_BIT = 5;

    /**
     * 序列号的最大值
     */
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);

    /**
     * 机器标识最大值
     */
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    /**
     * 数据中心最大值
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 序列号的向左位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    /**
     * 机器标识的向左位移
     */
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    /**
     * 数据中心的向左位移
     */
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;

    private long machineId;

    private long sequence = 0L;

    /**
     * 上一次的时间
     */
    private long lastStmp = -1L;

    @PostConstruct
    @Override
    public void initDatacenterAndMachine() {
        long datacenterId = coreProperties.getSnowFlake().getDatacenterId();
        long machineId = coreProperties.getSnowFlake().getMachineId();

        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }

        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     */
    @Override
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        //时间戳部分
        //数据中心部分
        //机器标识部分
        //序列号部分
        return (currStmp - START_STMP) << TIMESTMP_LEFT | datacenterId << DATACENTER_LEFT | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

}
