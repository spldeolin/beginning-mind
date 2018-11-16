package ${packageReference}.service.impl;

import org.springframework.stereotype.Service;
import ${packageReference}.api.CommonServiceImpl;
import ${packageReference}.model.${modelName};
import ${packageReference}.service.${modelName}Service;

/**
<#if modelCnsName??  && modelCnsName?trim != "">
 * ${modelCnsName}
 *
</#if>
 * @author ${author}
 */
@Service
public class ${modelName}ServiceImpl extends CommonServiceImpl<${modelName}> implements ${modelName}Service {

}