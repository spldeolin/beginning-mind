package com.spldeolin.beginningmind.core.api.dto;

import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Document;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Deolin 2018/11/13
 */
@Document("logic_delete")
@Data
public class LogicDeleteDocument {

    public LogicDeleteDocument(Object record) {
        this(LocalDateTime.now(), record);
    }

    public LogicDeleteDocument(LocalDateTime deletedAt, Object record) {
        table = record.getClass().getAnnotation(TableName.class).value();
        this.deletedAt = deletedAt;
        this.record = record;
    }

    private String table;

    private LocalDateTime deletedAt;

    private Object record;

}
