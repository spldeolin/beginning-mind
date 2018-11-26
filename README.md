## *beginning-mind*

### 简介

`beginning-mind`是一个Spring Boot Web项目，名为`初心`，它归纳总结了Deolin在过往项目中的各种实践。

这个项目将会充满活力，保持更新。



### 特点

- 前后端完全分离，交互数据一律以JSON形式进出。
- 没有xml配置、properties配置，只有yaml。
- 代码生成器与MybatisPlus，单表操作无需任何编码，更新表字段只需改动一处代码。
- 数据校验、统一异常处理，所有对外API都高度健壮。
- 选用Log4j2作为日志实现，主流的日志门面统一适配到Log4j2实现。
- 控制层切面将所有请求都将保存为log，方便追溯。
- 所有数据均支持逻辑删除。
- 所有依赖类库都是，并将会是最新的版本。



### 项目前身

[new-mind](https://github.com/spldeolin/new-mind) 尝试从Spring Boot 1.5.x升级到Spring Boot 2.x.x，现已合并

[try-boot](https://github.com/spldeolin/try-boot) 初次使用Spring Boot	

[restful-api](https://github.com/spldeolin/restful-api) 自定义切面，自定义注解，统一异常处理等

[best-practice](https://github.com/spldeolin/best-practice) Spring的集成各种第三方框架

[tiny-springmvc](https://github.com/spldeolin/tiny-springmvc) 极小的SpringMVC项目

[cadeau-library](https://github.com/spldeolin/cadeau-libray) 一个spring-boot-starter

这些项目将不再更新。