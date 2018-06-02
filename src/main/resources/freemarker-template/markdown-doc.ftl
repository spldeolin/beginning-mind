### 描述

- ${commonDesc}

### URL

- `${httpUrl}`

### 动词

- ${httpMethod}

### 参数示例

<#if paramShow == false>
（无需参数）
<br>
</#if>
<#if paramShow == true>
```json
${paramJson}
```
<br>
</#if>

<#if paramShow == true>
### 参数说明

|名称|位置|必传|JSON类型|描述|
|-|
<#list paramFields as paramField>
|${paramField.paramName}|${paramField.paramPlace}|${paramField.paramRequired}|${paramField.paramType}|${paramField.paramDesc}|
</#list>
<br>
</#if>

<#if returnShow == true>
### 返回值示例

```json
${returnJson}
```
<br>
<#if returnFieldsShow == true>

### 返回值说明

|名称|JSON类型|描述|
|-|
<#list returnFields as returnField>
|${returnField.returnName}|${returnField.returnType}|${returnField.returnDesc}|
</#list>
<br>
</#if>
</#if>

### 开发者

${commonDeveloper} ${commonDate}