package com.spldeolin.beginningmind.allison1875;

import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.gadget.BakFileCleaner;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2020-10-20
 */
@Slf4j
public class BakFileCleanerBoot {

    public static void main(String[] args) {
        Allison1875.allison1875(BakFileCleanerBoot.class, new BakFileCleaner());
    }

}