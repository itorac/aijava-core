# 支付宝 支付相关的签名算法
 
## 支付宝
 
支付宝当前使用了两种接口：

 - 开发平台接口（最新），用到的参数：`app id`， `rsa public key`, `rsa private key`, `支付宝 rsa public key`, `AES key`
 - mapi接口（历史），用到的参数： `partner id`, `md5 key`, `rsa public key`, `rsa private key`, `支付宝 rsa public key` 

## 如何使用？

```xml

   <dependency>
     <groupId>com.cig.core</groupId>
     <artifactId>cig-core-security</artifactId>
    </dependency>
```
 
JSON配置:

```json

{
  "alipayOpenapi": [
    {
      "appId": "",
      "aliPubicKey":  "",
      "privateKey":  "",
      "pubicKey": ""
    }
  ],

  "alipayMapi": [
    {
      "partnerId": "partnerId",
      "md5Key":  "md5Key",
      "publicKey":  "publicKey",
      "privateKey":  "privateKey",
      "aliPubicKey": "aliPubicKey"
    }
  ]

}

```


## 线上限制下面的子网才能正常访问：
