package service;

import dto.ChargeDTO;
import repository.ChargeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
    private final ChargeRepository chargeRepository;
    private final PlatformTransactionManager txManager;

    public ChargeDTO processCharge(ChargeDTO chargeDTO) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = txManager.getTransaction(def);

        try {
            log.info("Processing charge in Manager: {}", chargeDTO);

        // Define status padrão
        if (chargeDTO.getStatus() == null) {
            chargeDTO.setStatus("PENDING");
        }

        // Salva no banco
        ChargeDTO saved = chargeRepository.save(chargeDTO);

        txManager.commit(status);
        
        return saved;
        } catch (Exception e) {
            txManager.rollback(status);
            log.error("Error processing charge in Manager: {}", chargeDTO, e);
            throw e;
        }
    }
}