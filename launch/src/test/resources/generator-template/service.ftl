package ${basePackageReference}.${moduleName}.service;

import ${basePackageReference}.core.common.*;
import ${basePackageReference}.${moduleName}.entity.${entityName}Entity;

/**
<#if entityCnsName??  && entityCnsName?trim != "">
 * ${entityCnsName}
 *
</#if>
 * @author ${author}
 */
public interface ${entityName}Service extends CommonService<${entityName}Entity> {

}