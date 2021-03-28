package com.spldeolin.beginningmind.allison1875;

import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.gadget.LineCounterConfig;
import com.spldeolin.allison1875.gadget.LineCounterModule;
import com.spldeolin.beginningmind.allison1875.config.ConfigConstant;

/**
 * 行数统计器
 *
 * @author Deolin 2020-11-17
 */
public class LineCounterBoot {

    public static void main(String[] args) {
        LineCounterConfig config = ConfigConstant.lineCountConfig;
        Allison1875.allison1875(LineCounterBoot.class, new LineCounterModule(), ConfigConstant.baseConfig, config);
    }

}