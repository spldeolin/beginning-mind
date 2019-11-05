package com.spldeolin.beginningmind.constant;

import java.util.Arrays;
import com.spldeolin.beginningmind.valid.ValidityInterpretable;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Deolin 2019-10-29
 */
@AllArgsConstructor
@Getter
public enum VipType implements ValidityInterpretable {

    NORMAL(1), SUPER(2);

    private Integer value;

    @Override
    public boolean isValid(Integer value) {
        return Arrays.stream(VipType.values()).anyMatch(one -> one.getValue().equals(value));
    }

}
