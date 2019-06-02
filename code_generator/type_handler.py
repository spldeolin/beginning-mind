# coding=utf-8

mapping = {
    'varchar':'String',
    'char':'String',
    'text':'String',
    'bigint':'Long',
    'date':'LocalDate',
    'time':'LocalTime',
    'datetime':'LocalDateTime',
    'timestamp':'LocalDateTime',
    'int':'Integer',
    'decimal':'BigDecimal',
    'double':'Double',
    'tinyint':'Byte'
}

def to_java_type_name(jdbc_type_name, is_tiny_int_1_unsigned):
    if 'text' in jdbc_type_name:
        jdbc_type_name = 'text'

    if 'tinyint' == jdbc_type_name and is_tiny_int_1_unsigned:
        return 'Boolean'
    else:
        if jdbc_type_name in mapping.keys():
            return mapping[jdbc_type_name]
        else:
            raise Exception('出现了未考虑到的类型 [{}]，需要迭代type_handler.py'.format(jdbc_type_name))

if __name__ == '__main__':
    print(to_java_type_name('tinyint', False))
    print(to_java_type_name('tinyint', True))
    print(to_java_type_name('text', False))
    print(to_java_type_name('aaaaaaaaaa', False))