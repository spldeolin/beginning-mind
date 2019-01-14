package ${packageReference}.service;

import ${packageReference}.api.CommonService;
import ${packageReference}.entity.${entityName}Entity;

/**
<#if entityCnsName??  && entityCnsName?trim != "">
 * ${entityCnsName}
 *
</#if>
 * @author ${author}
 */
public interface ${entityName}Service extends CommonService<${entityName}Entity> {

}