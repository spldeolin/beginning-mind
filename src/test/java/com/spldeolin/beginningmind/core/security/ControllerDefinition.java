package com.spldeolin.beginningmind.core.security;

import java.lang.reflect.Method;
import java.util.List;
import lombok.Data;

/**
 * @author Deolin 2018/07/10
 */
@Data
public class ControllerDefinition {

    private Class controller;

    private List<Method> requestMethods;

}
