
package com.ansapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ansapp.producer.KafkaProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/kafka/broker")
@Slf4j
public class PublishController {

	private final KafkaProducer kafkaProducer;

	@Autowired
	public PublishController(KafkaProducer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

	@PostMapping(value = "/publish/{topic}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void publish(@RequestBody String payload, @PathVariable String topic) {
		kafkaProducer.publish(topic, payload);
		log.info("published data-->" + payload);

	}
}
