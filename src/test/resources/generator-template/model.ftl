package ${packageReference}.model;

import java.io.Serializable;
import java.time.*;
import com.baomidou.mybatisplus.annotation.*;
import ${packageReference}.api.IdGetable;
import lombok.*;
import lombok.experimental.Accessors;

/**
<#if modelCnsName??  && modelCnsName?trim != "">
 * ${modelCnsName}
 *
</#if>
 * @author ${author}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("${tableName}")
public class ${modelName} implements IdGetable, Serializable {

<#list properties as property>
    <#if property.fieldCnsName??  && property.fieldCnsName?trim != "">
    /**
     * ${property.fieldCnsName}
     */
    </#if>
    <#if property.isVersion>
    @Version
    </#if>
    @TableField("${property.columnName}")
    private ${property.fieldType} ${property.fieldName};

</#list>
    private static final long serialVersionUID = 1L;

}