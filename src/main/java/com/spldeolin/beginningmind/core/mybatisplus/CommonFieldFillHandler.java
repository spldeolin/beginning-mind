package com.spldeolin.beginningmind.core.mybatisplus;

import java.time.LocalDateTime;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.spldeolin.beginningmind.core.service.SnowFlakeService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/11
 */
@Component
@Log4j2
public class CommonFieldFillHandler implements MetaObjectHandler {

    @Autowired
    private SnowFlakeService snowFlakeService;

    @Override
    public void insertFill(MetaObject metaObject) {
        // 使用雪花算法生成ID
        this.setFieldValByName("id", snowFlakeService.nextId(), metaObject);
        // 使用当前时间
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("insertedAt", now, metaObject);
        this.setFieldValByName("updatedAt", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 使用当前时间
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("updatedAt", now, metaObject);
    }

}
