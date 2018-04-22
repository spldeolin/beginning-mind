package com.spldeolin.beginningmind.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class SignInInput implements Serializable {

    @NotBlank
    private String username;

    private char[] password;

    private static final long serialVersionUID = 1L;

}