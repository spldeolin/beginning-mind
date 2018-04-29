package com.spldeolin.beginningmind.restful.dto;

import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 通过切面（AnalyzeAspect）解析出的控制器信息
 *
 * @author Deolin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ControllerInfo {

    /**
     * 控制器对象
     */
    Object controllerTarget;

    /**
     * 请求方法
     */
    Method method;

    /**
     * 请求方法的参数名
     */
    String[] parameterNames;

    /**
     * 请求方法的参数值
     */
    Object[] parameterValues;

    /**
     * 标识
     */
    String insignia;

}
