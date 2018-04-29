package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class SignInInput implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private static final long serialVersionUID = 1L;

}