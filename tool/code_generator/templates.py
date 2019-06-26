# coding=utf-8

from jinja2 import Template

entity_template = Template('''package {{base_package_reference}}.{{module}}.entity;

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
@TableName("{{table_name}}")
public class {{entity_name}}Entity extends CommonEntity {
{% for property in properties %}{% if property.field_cns_name is defined and property.field_cns_name|length %}
    /**
     * {{property.field_cns_name}}
     */                         {% endif %}
    @TableField("{{property.column_name}}")
    private {{property.field_type}} {{property.field_name}};
{% endfor %}
    private static final long serialVersionUID = 1L;

}''')

mapper_java_template = Template('''package {{base_package_reference}}.{{module}}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import {{base_package_reference}}.{{module}}.entity.{{entity_name}}Entity;

/**
{% if entity_cns_name is defined and entity_cns_name|length %}
 * {{entity_cns_name}}
 *
{% endif %}
 * @author {{author}}
 */
public interface {{entity_name}}Mapper extends BaseMapper<{{entity_name}}Entity> {

}''')

mapper_xml_template = Template('''<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="{{base_package_reference}}.{{module}}.mapper.{{entity_name}}Mapper">

</mapper>''')

repo_template = Template('''package {{base_package_reference}}.{{module}}.repository;

import org.springframework.stereotype.Component;
import {{base_package_reference}}.core.common.*;
import {{base_package_reference}}.{{module}}.entity.{{entity_name}}Entity;

/**
{% if entity_cns_name is defined and entity_cns_name|length %}
 * {{entity_cns_name}}
 *
{% endif %}
 * @author {{author}}
 */
@Component
public class {{entity_name}}Repo extends CommonRepo<{{entity_name}}Entity> {

}
''')

if __name__ == '__main__':
    entity = entity_template.render({
        'base_package_reference': 'com.aaa.demo',
        'entity_cns_name': '交易订单',
        'author': 'Deolin 2019-06-05',
        'table_name': 'trade_order',
        'entity_name': 'TradeOrder',
        'properties': [
            {
                'field_cns_name': '下单时间',
                'column_name': 'ordered_at',
                'field_type': 'LocalDateTime',
                'field_name': 'orderedAt',
            },
            {
                'field_cns_name': '订单总价',
                'column_name': 'total_price',
                'field_type': 'BigDecimal',
                'field_name': 'totalPrice',
            }
        ],
    })
    print(entity)

    mapper_java = mapper_java_template.render({
        'base_package_reference': 'com.aaa.demo',
        'entity_cns_name': '交易订单',
        'author': 'Deolin 2019-06-05',
        'entity_name': 'TradeOrder',
    })
    print(mapper_java)

    mapper_xml = mapper_xml_template.render({
        'base_package_reference': 'com.aaa.demo',
        'entity_name': 'TradeOrder',
    })
    print(mapper_xml)

    repo = repo_template.render({
        'base_package_reference': 'com.aaa.demo',
        'entity_cns_name': '交易订单',
        'author': 'Deolin 2019-06-05',
        'entity_name': 'TradeOrder',
    })
    print(repo)
