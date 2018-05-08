## beginning-mind

### 简介

`beginning-mind`是一个Spring Boot项目，名为`初心`，它归纳总结了Deolin在过往项目中的各种实践。

这个项目将会保持更新。

### 特点

- 纯粹的Restful API，任何请求的进出数据只会是JSON。
- 定制化代码生成器支持，单表操作基本无需任何编码。
- 统一异常处理，可能的错误均会被考虑到，并得到处理。
- 控制层数据校验，确保对外API高度健壮。
- 选用性能最好的Log4j2作为日志实现，并通过适配器将主流的日志门面统一适配到Log4j2实现。
- 日志输出功能，。

### 依赖

[cadeau-support](https://github.com/spldeolin/cadeau-support) 代码生成器

### 项目前身

[try-boot](https://github.com/spldeolin/try-boot) 初次使用SpringBoot	

[restful-api](https://github.com/spldeolin/restful-api) 自定义切面，自定义注解，统一异常处理等

[best-practice](https://github.com/spldeolin/best-practice) Spring的集成各种第三方框架

[tiny-springmvc](https://github.com/spldeolin/tiny-springmvc) 极小的SpringMVC项目

[cadeau-library](https://github.com/spldeolin/cadeau-libray) 一个spring-boot-starter

这些项目将不再更新。