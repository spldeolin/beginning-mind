package com.spldeolin.beginningmind.util.excel.converter;

import javax.annotation.Nonnull;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultStringCellConverter implements CellConverter<String> {


    @Override
    public String writeToCellContent(@Nonnull String string) {
        return string;
    }

    @Override
    public String readFromCellContent(@Nonnull String string) {
        return string;
    }

}
