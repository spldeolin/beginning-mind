package com.spldeolin.beginningmind.core.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.core.model.Organization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 组织架构树节点
 *
 * @author Deolin 2018/08/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class OrganizationNodeDTO implements Serializable, Comparable<OrganizationNodeDTO> {

    private Long id;

    @JsonProperty("label")
    private String name;

    private Integer userQuantity;

    private Long parentId;

    private List<OrganizationNodeDTO> children;

    private static final long serialVersionUID = 1L;

    public static OrganizationNodeDTO fromModel(Organization model) {
        return OrganizationNodeDTO.builder().id(model.getId()).name(model.getName()).parentId(
                model.getParentId()).build();
    }

    @Override
    public int compareTo(OrganizationNodeDTO that) {
        Long thisId = this.getId();
        Long othersId = that.getId();
        if (thisId > othersId) {
            return 1;
        } else if (thisId < othersId) {
            return -1;
        } else {
            return 0;
        }
    }

}