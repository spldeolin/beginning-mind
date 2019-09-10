package com.spldeolin.beginningmind.core.util.tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 一元组
 *
 * @author Deolin 2019-09-09
 */
@AllArgsConstructor
@Getter
public class SingleTuple<A> {

    private final A a;

}
