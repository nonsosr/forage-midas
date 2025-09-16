import com.jpmc.midascore.foundation.Transaction;
import service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionListener {

    private final TransactionService transactionService;

    public TransactionListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(
            topics = "${midas.kafka.transaction-topic}",
            groupId = "midas-core-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(Transaction transaction) {
        String senderId = transaction.getSenderId();
        String recipientId = transaction.getRecipientId();
        BigDecimal amount = transaction.getAmount();

        boolean success = transactionService.processTransaction(senderId, recipientId, amount);
        if (success) {
            System.out.println("Transaction processed: " + transaction);
        } else {
            System.out.println("Transaction discarded: " + transaction);
        }
    }
}
