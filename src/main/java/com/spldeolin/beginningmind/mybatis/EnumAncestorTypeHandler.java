package com.spldeolin.beginningmind.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.spldeolin.beginningmind.ancestor.EnumAncestor;

/**
 * EnumAncestor派生类的TypeHandler
 *
 * @author Deolin 2020-11-10
 */
public class EnumAncestorTypeHandler extends BaseTypeHandler<EnumAncestor<String>> {

    private final Class<EnumAncestor<String>> enumType;

    public EnumAncestorTypeHandler(Class<EnumAncestor<String>> enumType) {
        Objects.requireNonNull(enumType, "Type argument cannot be null");
        this.enumType = enumType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnumAncestor<String> parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getCode().toString());
    }

    @Override
    public EnumAncestor<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return rs.wasNull() ? null : of(code);
    }

    @Override
    public EnumAncestor<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return rs.wasNull() ? null : of(code);
    }

    @Override
    public EnumAncestor<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return cs.wasNull() ? null : of(code);
    }

    private EnumAncestor<String> of(String code) {
        for (EnumAncestor<String> enumConstant : enumType.getEnumConstants()) {
            if (enumConstant.getCode().equals(code)) {
                return enumConstant;
            }
        }
        return null;
    }

}