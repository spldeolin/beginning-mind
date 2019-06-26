# coding=utf-8
import time
import pymysql
import common_column_handler
import type_handler
import templates
import os


def to_low_camel(snake_str):
    components = snake_str.split('_')
    # We capitalize the first letter of each component except the first one
    # with the 'title' method and join them together.
    return components[0] + ''.join(x.title() for x in components[1:])


def to_up_camel(snake_str):
    low_camel = to_low_camel(snake_str)
    return low_camel[0].upper() + low_camel[1:]


# meta
author = 'Deolin ' + time.strftime('%Y-%m-%d', time.localtime())
base_package_reference = 'com.spldeolin.beginningmind'
module = 'biz'
project_path = '/Users/deolin/Documents/project-repo/beginning-mind'
delete_flag_column_name = 'is_deleted'
version_column_name = 'version'
database_name = 'beginning_mind'

# MySQL
conn = pymysql.connect(host='127.0.0.1',
                       user='root',
                       password='root_r0oT',
                       database='information_schema',
                       charset='utf8')
cur = conn.cursor(cursor=pymysql.cursors.DictCursor)

while True:
    table_name = input('输入表名')
    if not table_name.strip():
        continue
    print('准备为[{}] 生成代码'.format(table_name))
    table_context = {}

    # 查询TABLE_SCHEMA和COLUMNS表，结果保存到table_context
    cur.execute('SELECT * FROM `TABLES` WHERE TABLE_SCHEMA = %s AND TABLE_NAME = %s', (database_name, table_name))
    result_set = cur.fetchone()
    if not result_set:
        raise Exception('表[{}] 在数据库[{}] 中不存在'.format(table_name, database_name))

    table_context['name'] = result_set['TABLE_NAME']
    table_context['comment'] = result_set['TABLE_COMMENT']

    cur.execute('SELECT * FROM `COLUMNS` WHERE TABLE_SCHEMA = %s AND TABLE_NAME = %s', (database_name, table_name))
    result_set = cur.fetchall()
    if not result_set:
        raise Exception('表[{}] 中没有定义任何字段'.format(table_name))

    table_context['columns'] = []
    for one in result_set:
        if not common_column_handler.is_common_column(one['COLUMN_NAME']):
            table_context['columns'].append(
                {
                    'name': one['COLUMN_NAME'],
                    'comment': one['COLUMN_COMMENT'],
                    'type': one['DATA_TYPE'],
                    'length': one['CHARACTER_MAXIMUM_LENGTH'],
                    'is_tinyint_1_unsigned': "tinyint(1) unsigned" == one['DATA_TYPE'],
                }
            )
    print('解析完毕 {}'.format(table_context))
    entity_name = to_up_camel(table_context['name'])

    # 准备渲染数据
    properties = []
    for column in table_context['columns']:
        properties.append({
            'field_cns_name': column['comment'],
            'column_name': column['name'],
            'field_type': type_handler.to_java_type_name(column['type'], column['is_tinyint_1_unsigned']),
            'field_name': to_low_camel(column['name']),
        })
    render_data = {
        'base_package_reference': base_package_reference,
        'module': module,
        'author': author,
        'entity_cns_name': table_context['comment'],
        'entity_name': entity_name,
        'table_name': table_context['name'],
        'properties': properties
    }
    print(render_data)


    def render_and_write(file_path, template):
        """共用代码"""
        if os.path.exists(file_path):
            print('已经存在，忽略。 {}'.format(file_path))
        else:
            with open(file_path, 'w') as f:
                f.write(template.render(render_data))
                print('已生成。 {}'.format(file_path))

    # 渲染并生成文件
    common_java_path = os.path.join(project_path, 'biz', 'src/main/java', base_package_reference.replace('.', '/'),
                                    'biz')
    render_and_write(os.path.join(common_java_path, 'entity', entity_name + 'Entity.java'), templates.entity_template)
    render_and_write(os.path.join(common_java_path, 'mapper', entity_name + 'Mapper.java'),
                     templates.mapper_java_template)
    render_and_write(os.path.join(common_java_path, 'repository', entity_name + 'Repo.java'), templates.repo_template)
    render_and_write(os.path.join(project_path, 'biz', 'src/main/resources', 'mapper', entity_name + 'Mapper.xml'),
                     templates.mapper_xml_template)
