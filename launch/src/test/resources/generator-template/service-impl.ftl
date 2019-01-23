package ${basePackageReference}.${moduleName}.service.impl;

import org.springframework.stereotype.Service;
import ${basePackageReference}.core.api.CommonServiceImpl;
import ${basePackageReference}.${moduleName}.entity.${entityName}Entity;
import ${basePackageReference}.${moduleName}.service.${entityName}Service;

/**
<#if entityCnsName??  && entityCnsName?trim != "">
 * ${entityCnsName}
 *
</#if>
 * @author ${author}
 */
@Service
public class ${entityName}ServiceImpl extends CommonServiceImpl<${entityName}Entity> implements ${entityName}Service {

}