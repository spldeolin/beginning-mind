### 描述
${decsription}

### URL
`${url}`

### 动词
${httpMethod}

<#if hasParams>
### 参数说明
|名称|JSON类型|必传|描述|
|-|
<#list paramEntries as paramEntry>
|${paramEntry.name}|${paramEntry.jsonType}|${paramEntry.isRequired}|${paramEntry.decsription}|
</#list>
<br>
</#if>

<#if hasBody>
### 请求体示例
```json
${bodyJson}
```
<br>

<#if !isBodyRegular>
### 请求体
解析请求体失败，请联系**开发者**手动补全。

失败的原因可能是
- 请求体类型声明过于宽泛，如`Object`、`Map`等
- 请求体数据结构过于复杂，如出现`private List<List<User>> u;`等
- 请求体类型中存在Lambda表达式
- 文档生成器本身的BUG
</#if>

<#if isBodyRegualr>
### 请求体说明
<#if isBodySimpleType>
${bodyDescription}
</#if>
<#if !isBodySimpleType>
|名称|JSON类型|必传|描述|
|-|
<#list bodyEntries as bodyEntry>
|${bodyField.bodyName}|${bodyField.bodyType}|${bodyField.bodyRequired}|${bodyField.bodyDesc}|
</#list>
<br>
</#if>
</#if>

</#if>

<#if !isReturnRegular>
### 返回值
解析返回值失败，请联系**开发者**手动补全。

失败的原因可能是
- 返回值类型声明过于宽泛，如`Object`、`RequestResult`、`Map`等
- 返回值数据结构过于复杂，如`private List<List<User>> u;`等
- 返回类型中存在Lambda表达式
- 文档生成器本身的BUG
</#if>

<#if isReturnRegualar>
### 返回值示例
<#if !returnShow>
没有返回值
</#if>
<#if returnShow>
```json
${returnJson}
```
<br>

<#if displayReturnInfo>
### 返回值说明
<#if returnDesc ??>
${returnDesc}
</#if>
<#if !isRetrunSimpleType>
|名称|JSON类型|描述|
|-|
<#list returnFields as returnField>
|${returnField.returnName}|${returnField.returnType}|${returnField.returnDesc}|
</#list>
<br>
</#if>
</#if>
</#if>
</#if>

### 开发者
${developer}