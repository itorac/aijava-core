### redis 说明

1.加入依赖

```xml
<dependency>
    <groupId>com.cig.core</groupId>
    <artifactId>cig-core-redis</artifactId>
 </dependency>

```

2.配置文件中加入配置：(当前这个`redis`配置作为测试redis)

```bash
redis.proxy.address=192.168.100.100
redis.proxy.port=6379
#password, can be empty
redis.proxy.auth=yL0o4983aTNkfJxvzxhIRyvYCQTfmDeq

```

3.使用方法


```
 @Resource(name="redisTemplate")
 private ListOperations<String,String> listOperations;


 @Resource(name="redisTemplate")
 private ValueOperations<String,String> valueOperations;

```

3.注意

当前key和value都是使用基本的`string`类型，如果需要存储对象，需要先使用`fastjson`进行序列化和反序列化

