package com.spldeolin.beginningmind.util.tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 二元组
 *
 * @author Deolin 2019-09-09
 */
@AllArgsConstructor
@Getter
public class DoubleTuple<A, B> {

    private final A first;

    private final B second;

}
