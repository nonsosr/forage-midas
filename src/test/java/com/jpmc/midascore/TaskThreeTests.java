package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    @Test
    void task_three_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            try {
                kafkaProducer.send(transactionLine, transactionLine);
            } catch (KafkaException e) {
                logger.error("Failed to send transaction: {}", transactionLine, e);
            }
        }

        Thread.sleep(2000); // Give Kafka some time to process

        Optional<UserRecord> optionalWaldorf = userRepository.findByUsername("waldorf");
        if (optionalWaldorf.isPresent()) {
            UserRecord waldorf = optionalWaldorf.get();
            logger.info("Waldorf's balance: {}", waldorf.getBalance());
        } else {
            logger.warn("User 'waldorf' not found.");
        }

        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what waldorf's balance is after all transactions are processed");
        logger.info("kill this test once you find the answer");

        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}
