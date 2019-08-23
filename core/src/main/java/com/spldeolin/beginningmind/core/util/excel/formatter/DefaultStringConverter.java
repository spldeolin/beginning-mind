package com.spldeolin.beginningmind.core.util.excel.formatter;

import javax.annotation.Nonnull;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultStringConverter implements Converter<String> {


    @Override
    public String write(@Nonnull String string) {
        return string;
    }

    @Override
    public String read(@Nonnull String string) {
        return string;
    }

}
