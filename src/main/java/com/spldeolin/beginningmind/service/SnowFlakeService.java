package com.spldeolin.beginningmind.service;

/**
 * Twitter的“雪花”算法
 * <p>
 * 用于获取唯一业务流水号
 * <p>
 * snowFlakeService.nextId();
 *
 * @author Deolin 2018/07/04
 */
public interface SnowFlakeService {

    /**
     * 初始化datacenter和服务器码
     */
    void initDatacenterAndMachine();

    /**
     * 产生下一个ID
     */
    long nextId();

}


