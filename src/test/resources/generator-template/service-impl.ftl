package ${packageReference}.service.impl;

import org.springframework.stereotype.Service;
import ${packageReference}.api.CommonServiceImpl;
import ${packageReference}.entity.${entityName}Entity;
import ${packageReference}.service.${entityName}Service;

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