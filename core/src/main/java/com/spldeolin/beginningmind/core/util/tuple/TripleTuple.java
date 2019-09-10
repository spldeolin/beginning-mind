package com.spldeolin.beginningmind.core.util.tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 三元组
 *
 * @author Deolin 2019-09-09
 */
@AllArgsConstructor
@Getter
public class TripleTuple<A, B, C> {

    private final A a;

    private final B b;

    private final C c;

}
