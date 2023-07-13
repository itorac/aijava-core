# Cache

### 说明
提供了对本地緩存的封装。

# LocalCache

### 说明
提供了进程内缓存的封装。

### 使用方法
1.加入依赖

```xml
  <dependency>
      <groupId>com.cig.core</groupId>
      <artifactId>cig-core-cache</artifactId>
  </dependency>
```


2.使用工厂方法初始化缓存客户端

```java
//注入LocalCache 或者 new LocalCache

private LocalCache cache = new LocalCache();
private Callback callback = new Callback();

//初始化localcache
cache.init("brand", 10, TimeUnit.SECONDS, callback);

//从缓存中获取key，如果缓存中没有，会触发callback，加载数据
cache.get("key")

//业务实现回调函数，加载数据
class Callback implements LocalCacheCallback{

    @Override
    public Object load(String key, Object oldvalue) throws Exception {
        return null;
    }
}

```

