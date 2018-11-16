package ${packageReference}.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packageReference}.model.${modelName};

/**
<#if modelCnsName??  && modelCnsName?trim != "">
 * ${modelCnsName}
 *
</#if>
 * @author ${author}
 */
public interface ${modelName}Mapper extends BaseMapper<${modelName}> {

}