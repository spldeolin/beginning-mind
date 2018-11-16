package com.spldeolin.beginningmind.core.service;

/**
 * Twitter的“雪花”算法
 * <p>
 * 用于获取唯一业务流水号
 * <p>
 * Long uid = new SnowFlake(0, 0).nextId();
 *
 * @author Deolin 2018/07/04
 */
public interface SnowFlakeService {


    void initDatacenterAndMachine();

    /**
     * 产生下一个ID
     */
    long nextId();

}


