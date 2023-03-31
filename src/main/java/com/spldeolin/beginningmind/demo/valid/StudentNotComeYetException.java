package com.spldeolin.beginningmind.demo.valid;

import com.spldeolin.beginningmind.exception.BizException;

/**
 * 一个具体业务的业务异常类
 *
 * @author Deolin 2020-07-06
 */
public class StudentNotComeYetException extends BizException {

    private static final String DEFAULT_MESSAGE = "这是一个具体业务的业务异常示例类，代表「该学生了当前还未达到学校」的含义";

    public StudentNotComeYetException() {
        super(DEFAULT_MESSAGE);
    }

    private static final long serialVersionUID = 3272464545503308188L;

}

