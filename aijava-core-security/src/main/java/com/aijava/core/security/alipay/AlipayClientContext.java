package com.aijava.core.security.alipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyijian
 * @describe 创建阿里clients策略上下文类
 * @date 2022-03-09 11：12
 */
@Component
public class AlipayClientContext {

        @Autowired
        private List<AliPayClientStrategy> aliPayClientStrategies;

        private static Map<String, AliPayClientStrategy> aliPayClientStrategyMap;

        /**
         * 将动物类的实现类交由spring管理
         * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
         * PostConstruct在构造函数之后执行，init()方法之前执行。
         * PreDestroy()方法在destroy()方法知性之后执行
         *
         * 运行先后顺序：构造方法 > @Autowired > @PostConstruct
         */
        @PostConstruct
        public void init() {
            aliPayClientStrategyMap = aliPayClientStrategies.stream().collect(Collectors.toMap(AliPayClientStrategy::appId, a -> a));
        }

        /**
         * 根据类型获取具体的实现类
         */
        public AliPayClientStrategy getStrategy(String appId) {
            return aliPayClientStrategyMap.get(appId);
        }
    }

