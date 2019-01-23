package com.spldeolin.beginningmind.launch.test.generator.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @author Deolin 2018/11/14
 */

@Data
@Builder
public class TableColumnDTO implements Serializable {

    private String name;

    private String comment;

    private List<ColumnDTO> columns;

    private static final long serialVersionUID = 1L;

}