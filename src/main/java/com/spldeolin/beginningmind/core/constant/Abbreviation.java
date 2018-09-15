package com.spldeolin.beginningmind.core.constant;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

/**
 * 常用对象、值的缩写常量
 *
 * @author Deolin
 */
public interface Abbreviation {

    /**
     * 当前操作系统的文件路径分割符
     */
    String sep = File.separator;

    /**
     * 当前操作系统的换行符
     */
    String br = System.lineSeparator();

    /**
     * 4个空格
     */
    String tab = "    ";

    /**
     * Objenesis对象
     */
    Objenesis objs = new ObjenesisStd(true);

    /**
     * UTF-8编码
     */
    Charset utf8 = StandardCharsets.UTF_8;

}
