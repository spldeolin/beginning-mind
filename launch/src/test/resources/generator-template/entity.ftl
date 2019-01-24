package ${basePackageReference}.${moduleName}.entity;

import java.time.*;
import com.baomidou.mybatisplus.annotation.*;
import ${basePackageReference}.core.common.*;
import lombok.*;

/**
<#if entityCnsName??  && entityCnsName?trim != "">
 * ${entityCnsName}
 *
</#if>
 * @author ${author}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${tableName}")
public class ${entityName}Entity extends CommonEntity {

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