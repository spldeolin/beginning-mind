package com.spldeolin.beginningmind.support;

import java.lang.reflect.Method;
import java.util.List;
import lombok.Data;

/**
 * 控制器
 *
 * @author Deolin 2018/05/26
 */
@Data
public class ControllerDefinition {

    private Class controller;

    private List<Method> requestMethods;

}
