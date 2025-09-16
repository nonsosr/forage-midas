package service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionService(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public boolean processTransaction(String senderId, String recipientId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false; // invalid amount
        }
        Optional<UserRecord> senderOpt = userRepository.findByUsername(senderId);
        Optional<UserRecord> recipientOpt = userRepository.findByUsername(recipientId);

        if (senderOpt.isEmpty() || recipientOpt.isEmpty()) {
            return false; // sender or recipient invalid
        }

        UserRecord sender = senderOpt.get();
        UserRecord recipient = recipientOpt.get();

        if (sender.getBalance().compareTo(amount) < 0) {
            return false; // insufficient balance
        }

        // Adjust balances using BigDecimal arithmetic
        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));

        userRepository.save(sender);
        userRepository.save(recipient);

        TransactionRecord record = new TransactionRecord(sender, recipient, amount.doubleValue(), Instant.now());
        transactionRecordRepository.save(record);

        return true;
    }
}
