package com.spldeolin.beginningmind.util.tuple;

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

    private final A first;

    private final B second;

    private final C third;

}
