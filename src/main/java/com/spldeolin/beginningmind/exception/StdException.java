package com.spldeolin.beginningmind.exception;

import com.spldeolin.beginningmind.ancestor.StderrAncestor;
import lombok.Getter;

/**
 * 标准异常，请结合具体的标准错误（{@link StderrAncestor}的实现类）使用
 *
 * @author Deolin 2021-08-17
 */
@Getter
public class StdException extends RuntimeException {

    private static final long serialVersionUID = -4104806330438981374L;

    private final StderrAncestor stderr;

    public StdException(StderrAncestor stderr) {
        super(stderr.errorMessage());
        this.stderr = stderr;
    }

    public StdException(StderrAncestor stderr, Throwable cause) {
        super(stderr.errorMessage(), cause);
        this.stderr = stderr;
    }

}