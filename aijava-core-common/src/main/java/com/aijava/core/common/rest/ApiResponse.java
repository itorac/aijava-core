package com.aijava.core.common.rest;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.aijava.core.common.utils.MD5;

import lombok.Builder;
import lombok.experimental.Accessors;


/**
 * API 响应格式
 * /**
 * 响应为：
 * <pre>
 * {
 * "code": 200,
 * "message": "\u767b\u5f55\u6210\u529f",
 * “alg": "SALT_MD5"
 * "data": {
 * "uid": "10216497",
 * "profile": "18751986615",
 * "session_key": "fa31d3a5d069c6c98cd8c38c3a5f89e6",
 * "vip": 0
 * },
 * "md5": "fa5b07f95a0bf95c26ac50abf0024eed"
 * }
 * Created by xiegr
 */

@Builder
@Accessors(chain = true)
public class ApiResponse implements Serializable {

   private static final long serialVersionUID = 2984521054427500857L;

   /**
    * 默认编码
    */
   private static final int DEFAULT_CODE = 200;
   
   /**
    * 默认消息
    */
   private static final String DEFAULT_MSG = "SUCCESS";
   
   /**
    * code编码
    */
   private int code;
   
   /**
    * 消息
    */
   private String message;

   /**
    * 返回数据
    */
   private Object data;
   
   /** 如果客户端判断有这个，则校验MD5 */
   private final String alg = "SALT_MD5";
   
   private final String md5 = MD5.md5(UUID.randomUUID().toString());

   public ApiResponse() {
       this(200, DEFAULT_MSG, null);
   }

   public ApiResponse(int code, String message, Object data) {
       this.code = code;
       if (StringUtils.isNotEmpty(message)) {
           this.message = message;
       }
       this.data = data;
   }

   public int getCode() {
       return code;
   }

   public void setCode(int code) {
       this.code = code;
   }

   public String getMessage() {
       return message;
   }

   public void setMessage(String message) {
       this.message = message;
   }

   public Object getData() {
       return data;
   }

   public void setData(Object data) {
       this.data = data;
   }
   
   public String getAlg() {
	   return alg;
   }

   public String getMd5() {
       return md5;
   }

   @Override
   public String toString() {
       return ReflectionToStringBuilder.toString(this);
   }

   /**
    * 构造响应。使用方式：<p/>
    * <pre>
    *   ApiResponse.ApiResponseBuilder builder = new ApiResponse.ApiResponseBuilder();
    *   ApiResponse apiResponse =  builder.code(200).message("coupons total").data(new Total("0")).build();
    * </pre>
    */
   public static class ApiResponseBuilder {

       ApiResponse apiResponse;

       public ApiResponseBuilder() {
           apiResponse = new ApiResponse();
       }

       /**
        * 设置错误码。默认200
        *
        * @param code 错误码
        * @return ApiResponseBuilder
        */
       public ApiResponseBuilder code(int code) {
           apiResponse.code = code;
           return this;
       }

       /**
        * 设置消息。默认[操作成功]
        *
        * @param message 错误消息
        * @return ApiResponseBuilder
        */
       public ApiResponseBuilder message(String message) {
           apiResponse.message = message;
           return this;
       }

       /**
        * 设置响应的具体内容
        *
        * @param data 响应的具体内容
        * @return 内容
        */
       public ApiResponseBuilder data(Object data) {
           apiResponse.data = data;
           return this;
       }

       /**
        * 构造响应
        *
        * @return 响应
        */
       public ApiResponse build() {
           //参数校验，并且设置默认值
           if (this.apiResponse.code <= 0) {
               this.apiResponse.code = DEFAULT_CODE;
           }
           if (StringUtils.isEmpty(apiResponse.message)) {
               this.apiResponse.message = DEFAULT_MSG;
           }

           //构造JSON
           return apiResponse;
       }
   }
}