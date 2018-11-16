package ${packageReference}.service;

import ${packageReference}.api.CommonService;
import ${packageReference}.model.${modelName};

/**
<#if modelCnsName??  && modelCnsName?trim != "">
 * ${modelCnsName}
 *
</#if>
 * @author ${author}
 */
public interface ${modelName}Service extends CommonService<${modelName}> {

}