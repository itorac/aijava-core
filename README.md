# ajava-core说明

1.简介

```
 aijava各平台和saas系统的核心包
```

2.结构

```
1. 所有的工程命名结构必须统一，采用ajava开头，例如:ajava-core等.
2. 每个工程内部子项目结构的命名,如ajava-core-common.
3. 每个工程的结构统一，以ajava-core为例子:
3.1 ajava-core-alarm
3.2 ajava-core-cache
3.3 ajava-core-common
3.4 ajava-core-config
3.5 ajava-core-redis
3.6 ajava-core-security
3.7 ajava-core-dal
3.8 ajava-core-error
4. 其他
```

3.命名规范

```
1. 

2.

3.

4.

```

4.模块说明

```
1. ajava-core-alarm           事件发布模块
2. ajava-core-cache           本地缓存模块
3. ajava-core-common          工具包模块
4. ajava-core-config          配置文件模块
5. ajava-core-redis           redis操作模块
6. ajava-core-security        各平台的公钥私钥模块
6. ajava-core-dal             数据库操作及分页
7. ajava-core-error           错误描述,项目错误配置
8. 其他模块以后再增加

```

5.用法


```xml

   <dependency>
     <groupId>com.ajava.core</groupId>
     <artifactId>ajava-core-模块</artifactId>
     <version>版本号</version>
    </dependency>
```