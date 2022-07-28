package com.ansapp.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Async
	public void publish(String topicName, String payload) {
		log.info("sending message='{}' to destination='{}'", payload, topicName);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, payload);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.debug("Sent Message to: {} on partition: {}", topicName, result.getRecordMetadata().partition());

			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Unable to send message to: {} ", topicName);

			}

		});
	}
}