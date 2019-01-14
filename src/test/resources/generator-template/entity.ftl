package ${packageReference}.entity;

import java.io.Serializable;
import java.time.*;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

/**
<#if entityCnsName??  && entityCnsName?trim != "">
 * ${entityCnsName}
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
public class ${entityName}Entity implements Serializable {

<#list properties as property>
    <#if property.fieldCnsName??  && property.fieldCnsName?trim != "">
    /**
     * ${property.fieldCnsName}
     */
    </#if>
    <#if property.isVersion>
    @Version
    </#if>
    <#if property.isDeleteFlag>
    @TableLogic
    </#if>
    @TableField("${property.columnName}")
    private ${property.fieldType} ${property.fieldName};

</#list>
    private static final long serialVersionUID = 1L;

}