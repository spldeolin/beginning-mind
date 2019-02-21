## *beginning-mind*

### 简介

`beginning-mind`是一个Spring Boot Web项目，名为`初心`，它归纳总结了Deolin在过往项目中的各种实践。

这个项目将会充满活力，保持更新。



### 特点

- 项目分为多个Maven模块，核心代码、业务代码、入口代码分离
- 移除视图层，使所有请求均以JSON进出。
- 集成Redis，服务无状态化。
- 没有xml配置，只有yaml。
- 代码生成器与MybatisPlus，单表操作无需任何编码，表字段变化后只需改动一处代码。
- 持久层集成了乐观锁、逻辑删除、分页、通用字段等功能。
- 控制层集成了数据校验、统一异常处理，对外API高度健壮。
- 引入若干个log bridge，主流的日志门面统一适配到Log4j2实现。
- 基于ELK的日志收集方案。
- 利用filter解析并保存每个用户请求，方便追溯BUG和分析用户行为。
- Spring Boot版本为2.1.x，类库都使用了最新的版本。



### 项目前身

[new-mind](https://github.com/spldeolin/new-mind) 尝试从Spring Boot 1.5.x升级到Spring Boot 2.x.x，现已合并

[try-boot](https://github.com/spldeolin/try-boot) 初次使用Spring Boot	

[restful-api](https://github.com/spldeolin/restful-api) 自定义切面，自定义注解，统一异常处理等

[best-practice](https://github.com/spldeolin/best-practice) Spring的集成各种第三方框架

[tiny-springmvc](https://github.com/spldeolin/tiny-springmvc) 极小的SpringMVC项目

[cadeau-library](https://github.com/spldeolin/cadeau-libray) 一个spring-boot-starter

这些项目将不再更新。