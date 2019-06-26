# coding=utf-8

def is_common_column(column_name):
    return column_name in ('id', 'inserted_at', 'updated_at', 'is_deleted', 'version')
