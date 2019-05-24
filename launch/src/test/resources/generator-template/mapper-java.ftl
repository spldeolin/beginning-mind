package ${packageReference}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packageReference}.entity.${entityName}Entity;

/**
<#if entityCnsName??  && entityCnsName?trim != "">
 * ${entityCnsName}
 *
</#if>
 * @author ${author}
 */
public interface ${entityName}Mapper extends BaseMapper<${entityName}Entity> {

}