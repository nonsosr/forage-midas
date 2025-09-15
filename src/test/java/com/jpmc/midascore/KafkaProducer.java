    package com.jpmc.midascore;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.kafka.core.KafkaTemplate;
    import org.springframework.stereotype.Component;

    @Component
    public class KafkaProducer {

        private final KafkaTemplate<String, String> kafkaTemplate;
        private final String defaultTopic;

        public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate,
                             @Value("${general.kafka-topic}") String defaultTopic) {
            this.kafkaTemplate = kafkaTemplate;
            this.defaultTopic = defaultTopic;
        }

        // Used in TaskTwoTests
        public void send(String message) {
            kafkaTemplate.send(defaultTopic, message);
        }

        // Used in TaskFourTests (topic provided explicitly)
        public void send(String topic, String message) {
            kafkaTemplate.send(topic, message);
        }
    }
