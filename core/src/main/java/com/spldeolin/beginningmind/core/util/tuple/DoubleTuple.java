package com.spldeolin.beginningmind.core.util.tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Deolin 2019-09-09
 */
@AllArgsConstructor
@Getter
public class DoubleTuple<A, B> {

    private final A a;

    private final B b;

}
