package com.aijava.core.rabbitmq.producer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.Charsets;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class CigProducerTemplate {

	@Resource
	private AmqpTemplate amqpTemplate;

	@Resource
	private RabbitAdmin rabbitAdmin;

	/**
	 * amq.topic EXCHAGE
	 */
	private final String TOPIC_EXCHAGE = "amq.topic";

	/**
	 * 
	 */
	private final String DIRECT_EXCHANGE = "amq.direct";

	/**
	 * 
	 */
	private final String DELAYED_EXCHANGE = "delayed.exchange";

	/**
	 * 
	 */
	private final String DEAD_EXCHANGE = "dead.exchange";

	/**
	 * 发送主题消息
	 * 
	 * @param routingKey
	 * @param message
	 */
	public void send(String routingKey, Object message) {
		this.send(routingKey, message, null);
	}

	/**
	 * 主题队列
	 * 
	 * @param routingKey
	 * @param message
	 * @param attributes
	 */
	public void send(String routingKey, Object message, Map<String, Object> attributes) {
		this.send(TOPIC_EXCHAGE, routingKey, message, attributes, null);
	}

	/**
	 * 主题队列
	 * 
	 * @param exchange
	 * @param routingKey
	 * @param message
	 */
	public void send(String exchange, String routingKey, Object message) {
		this.send(exchange, routingKey, message, null, null);
	}

	/**
	 * 
	 * @param exchange
	 * @param routingKey
	 * @param message
	 * @param messagePostProcessor
	 */
	public void send(String exchange, String routingKey, Object message, Map<String, Object> attributes,
			String expiration) {
		MessageProperties properties = new MessageProperties();
		properties.setContentType("text");
		if (attributes != null) {
			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				properties.setHeader(entry.getKey(), entry.getValue());
			}
		}
		// 消息的过期时间,当同时指定了 queue 和 message 的 TTL 值，则两者中较小的那个才会起作用。
		if (expiration != null) {
			properties.setExpiration(expiration);
		}
		byte[] body = JSON.toJSONString(message).getBytes(Charsets.toCharset("UTF-8"));
		Message amqpMsg = new Message(body, properties);
		amqpTemplate.send(exchange, routingKey, amqpMsg);
	}

	/**
	 * 延时队列
	 * 
	 * @param queueName
	 * @param params
	 * @param expiration
	 */
	public void send(String routingKey, Object message, int delayInMinutes) {
		String DELAYED_ROUTING_KEY = "cig_delay." + delayInMinutes + "m." + routingKey;
		declareAndGetQueueName(routingKey, DELAYED_ROUTING_KEY, delayInMinutes);
		send(DELAYED_EXCHANGE, DELAYED_ROUTING_KEY, message, msg -> {
			msg.getMessageProperties().setDelay(delayInMinutes * 60 * 1000);
			return msg;
		});
	}

	/**
	 * 
	 * @param routingKey
	 * @param DELAYED_ROUTING_KEY
	 * @param delayInMinutes
	 */
	private void declareAndGetQueueName(String routingKey, String DELAYED_ROUTING_KEY, int delayInMinutes) {
		// 先创建一个队列
		Queue queue = new Queue(routingKey);
		rabbitAdmin.declareQueue(queue);
		// 创建延迟队列交换机
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-delayed-type", "direct");
		CustomExchange customExchange = new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false,
				arguments);
		rabbitAdmin.declareExchange(customExchange);
		// 将队列和交换机绑定
		Binding binding = BindingBuilder.bind(queue).to(customExchange).with(DELAYED_ROUTING_KEY).noargs();
		rabbitAdmin.declareBinding(binding);
	}

	/**
	 * 
	 * @param exchange
	 * @param routingKey
	 * @param message
	 * @param messagePostProcessor
	 */
	public void send(String exchange, String routingKey, Object message, MessagePostProcessor messagePostProcessor) {
		amqpTemplate.convertAndSend(exchange, routingKey, message, messagePostProcessor);
	}

	/**
	 * 死性队列
	 * 
	 * @param routingKey
	 * @param deadQueueName
	 * @param message
	 * @param delayInMinutes
	 */
	public void send(String routingKey, String deadQueueName, Object message, int delayInMinutes) {
		String DLX_ROUTING_KEY = "cig_dlx." + delayInMinutes + "m." + routingKey;
		String DEAD_ROUTING_KEY = "cig_dead." + delayInMinutes + "m." + routingKey;
		/**
		 * ----------------------------------先创建一个ttl队列和死信队列--------------------------------------------
		 */
		Map<String, Object> map = new HashMap<>();
		// 队列设置存活时间，单位ms,必须是整形数据。
		map.put("x-message-ttl", delayInMinutes * 1000 * 60);
		// 设置死信交换机
		map.put("x-dead-letter-exchange", DEAD_EXCHANGE);
		// 设置死信交换器路由键
		map.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
		/* 参数1：队列名称 参数2：持久化 参数3：是否排他 参数4：自动删除队列 参数5：队列参数 */
		Queue queue = new Queue(routingKey, true, false, false, map);
		rabbitAdmin.declareQueue(queue);
		/**
		 * ---------------------------------创建交换机---------------------------------------------
		 */
		DirectExchange directExchange = new DirectExchange(DIRECT_EXCHANGE, true, false);
		rabbitAdmin.declareExchange(directExchange);
		/**
		 * ---------------------------------队列绑定交换机---------------------------------------------
		 */
		Binding binding = BindingBuilder.bind(queue).to(directExchange).with(DLX_ROUTING_KEY);
		rabbitAdmin.declareBinding(binding);
		/**
		 * ---------------------------------在创建一个死信交换机和队列，接收死信队列---------------------------------------------
		 */
		DirectExchange deadExchange = new DirectExchange(DEAD_EXCHANGE, true, false);
		rabbitAdmin.declareExchange(deadExchange);

		Queue deadQueue = new Queue(deadQueueName, true, false, false);
		rabbitAdmin.declareQueue(deadQueue);
		/**
		 * ---------------------------------队列绑定死信交换机---------------------------------------------
		 */
		// 将队列和交换机绑定
		Binding deadbinding = BindingBuilder.bind(deadQueue).to(deadExchange).with(DEAD_ROUTING_KEY);
		rabbitAdmin.declareBinding(deadbinding);
		// 发送消息
		amqpTemplate.convertAndSend(DIRECT_EXCHANGE, DLX_ROUTING_KEY, message);
	}

}
