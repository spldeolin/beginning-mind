package com.spldeolin.beginningmind.service;

import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import com.spldeolin.beginningmind.entity.BizDemoEntity;

/**
 * @author Deolin 2019-03-18
 */
@Validated
public interface BizDemoService {

    Map<Long, BizDemoEntity> all();

    void doSomething(@NotNull @Valid BizDemoEntity bizDemoEntity, @Max(6) Long id);

}