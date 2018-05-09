package com.spldeolin.beginningmind.dao.bm2;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExMapper {

    List<String> names();

}
