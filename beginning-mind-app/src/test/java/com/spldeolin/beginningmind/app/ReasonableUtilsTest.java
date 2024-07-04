package com.spldeolin.beginningmind.app;

import com.google.common.collect.Lists;
import com.spldeolin.satisficing.app.util.ReasonableUtils;

/**
 * @author Deolin 2023-04-07
 */
public class ReasonableUtilsTest {

    public static void main(String[] args) {
        Lay1Javabean lay1 = new Lay1Javabean();
        lay1.setA(1L);
        lay1.setB("    啊  \n");
        lay1.setC(Lists.newArrayList(1, 2, 3));


        RootJavabean root = new RootJavabean();
        root.setA(2L);
        root.setB("        ");
        root.setC(Lists.newArrayList());
        root.setLay1(lay1);
        root.setLay1List(Lists.newArrayList(null, lay1, lay1.setB("        "), lay1));
        System.out.println(root);

        ReasonableUtils.reasonable(root);
        System.out.println(root);
    }

}