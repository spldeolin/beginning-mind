## *beginning-mind*

### 简介

`beginning-mind`是一个Spring Boot Web项目，名为`初心`，它归纳总结了Deolin在过往项目中的各种实践。

这个项目将会充满活力，保持更新。

### 特点

- 前后端完全分离，交互数据一律以JSON形式进出。
- 没有xml配置、properties配置，只有yaml。
- 集成了代码生成器与MybatisPlus，单表操作基本无需任何编码。
- 统一异常处理，可能的错误均会被考虑到，并得到处理。
- 控制层数据校验，确保对外API高度健壮。
- 选用性能最好的Log4j2作为日志实现，并通过适配器将主流的日志门面统一适配到Log4j2实现。
- 每次请求的轨迹都将保存到Mongo，方便追溯。
- 数据库所有数据的删除方式均为逻辑删除。

### 项目前身

[new-mind](https://github.com/spldeolin/new-mind) 尝试从Spring Boot 1.5.x升级到Spring Boot 2.x.x，现已合并

[try-boot](https://github.com/spldeolin/try-boot) 初次使用Spring Boot	

[restful-api](https://github.com/spldeolin/restful-api) 自定义切面，自定义注解，统一异常处理等

[best-practice](https://github.com/spldeolin/best-practice) Spring的集成各种第三方框架

[tiny-springmvc](https://github.com/spldeolin/tiny-springmvc) 极小的SpringMVC项目

[cadeau-library](https://github.com/spldeolin/cadeau-libray) 一个spring-boot-starter

这些项目将不再更新。