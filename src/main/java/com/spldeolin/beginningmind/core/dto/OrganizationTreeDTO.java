package com.spldeolin.beginningmind.core.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2018/08/28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class OrganizationTreeDTO implements Serializable {

    private List<OrganizationNodeDTO> nodes;

    private static final long serialVersionUID = 1L;

}