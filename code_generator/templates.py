# coding=utf-8

from jinja2 import Template

entity = Template('''package {{base_package_reference}}.{{module_name}}.entity;

import java.time.*;
import com.baomidou.mybatisplus.annotation.*;
import {{base_package_reference}}.core.common.*;
import lombok.*;

/**
{% if entity_cns_name is defined and entity_cns_name|length %}
 * {{entity_cns_name}}
 *
 {% endif %}
 * @author {{author}}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("{{tableName}}")
public class {{entityName}}Entity extends CommonEntity {

<% for property as properties %>
    {% if property.field_cns_name is defined and property.field_cns_name|length %}
    /**
     * {{property.field_cns_name}}
     */
    </#if>
    <%endif%>
    @TableField("{{property.column_name}}")
    private {{property.field_type}} {{property.field_name}};

<% endfor %>
    private static final long serialVersionUID = 1L;

}''')