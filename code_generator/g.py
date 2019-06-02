# coding=utf-8
import time
import pymysql
import common_column_handler

# meta
author = 'Deolin ' + time.strftime('%Y-%m-%d', time.localtime())
base_package_reference = 'com.spldeolin.beginningmind'
module_name = 'biz'
project_path = '/Users/deolin/Documents/project-repo/beginning-mind'
delete_flag_column_name = 'is_deleted'
version_column_name = 'version'
database_name = 'beginning_mind'

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

    # table info
    cur.execute('SELECT * FROM `TABLES` WHERE TABLE_SCHEMA = %s AND TABLE_NAME = %s', (database_name, table_name))
    result_set = cur.fetchone()
    if not result_set:
        raise Exception('表[{}] 在数据库[{}] 中不存在'.format(table_name, database_name))

    table_context['name'] = result_set['TABLE_NAME']
    table_context['comment'] = result_set['TABLE_COMMENT']

    # column info
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
                    'isTinyint1Unsigned': "tinyint(1) unsigned" == one['DATA_TYPE'],
                }
            )
    print('解析完毕 {}'.format(table_context))

