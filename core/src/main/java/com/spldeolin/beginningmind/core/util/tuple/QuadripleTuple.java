package com.spldeolin.beginningmind.core.util.tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 四元组
 *
 * @author Deolin 2019-09-09
 */
@AllArgsConstructor
@Getter
public class QuadripleTuple<A, B, C, D> {

    private final A first;

    private final B second;

    private final C third;

    private final D fourth;

}
