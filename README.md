## *beginning-mind*

### 简介

`beginning-mind`是一个Spring Boot Web项目，名为`初心`，它归纳总结了Deolin在过往项目中的各种实践。

这个项目将会充满活力，保持更新。



### 机制

- RequestTrackFilter 收集、记录每个Spring MVC Handler每次收到请求的HTTP信息、到达时间、耗时等信息

- BoundRequestBodyObtainAdvice 获取@RequestBody参数对象，并将其set当前的RequestTrack对象的ControllerAdvice

- ResponseBodyWrapAdvice ResponseBody对象返回值统一包装

- RestExceptionAdvice 统一异常处理

- EnumTypeHandlerEx Mybatis枚举转化处理

- EnumAncestor结合IDE File Templates JSON序列化、反序列化时枚举处理