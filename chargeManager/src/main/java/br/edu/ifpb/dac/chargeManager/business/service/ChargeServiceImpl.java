package br.edu.ifpb.dac.chargeManager.business.service;

import br.edu.ifpb.dac.chargeManager.business.event.ChargeStatusChangedEvent;
import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import br.edu.ifpb.dac.chargeManager.infra.client.ChargeProxyClient;
import br.edu.ifpb.dac.chargeManager.infra.client.soap.ChargeResponseDto;
import br.edu.ifpb.dac.chargeManager.infra.repository.ChargeRepository;
import br.edu.ifpb.dac.chargeManager.infra.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargeServiceImpl implements ChargeService {

    private final ChargeRepository chargeRepository;
    private final UserRepository userRepository;
    private final ChargeProxyClient chargeProxyClient;
    private final EmailService emailService;
    private final ApplicationEventPublisher eventPublisher;

    public ChargeServiceImpl(ChargeRepository chargeRepository,
            UserRepository userRepository,
            ChargeProxyClient chargeProxyClient,
            EmailService emailService,
            ApplicationEventPublisher eventPublisher) {
        this.chargeRepository = chargeRepository;
        this.userRepository = userRepository;
        this.chargeProxyClient = chargeProxyClient;
        this.emailService = emailService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Charge createCharge(Charge charge) {
        // Validate user exists
        var user = userRepository.findById(charge.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + charge.getUserId()));

        // Set initial status
        charge.setStatus("REGISTERED");

        // Salva charge no banco de dados
        Charge savedCharge = chargeRepository.save(charge);

        try {
            // Chama ChargeProxy via SOAP para criar charge no Asaas
            ChargeResponseDto response = chargeProxyClient.sendCharge(
                    savedCharge.getAmount(),
                    savedCharge.getPaymentType(),
                    savedCharge.getCustomer(),
                    savedCharge.getDueDate());

            // Atualiza charge com dados externos
            savedCharge.setExternalId(response.getId());
            savedCharge.setStatus(response.getStatus());

            // Atualiza no banco de dados
            chargeRepository.update(savedCharge);

        } catch (Exception e) {
            // Log error but keep charge in REGISTERED status
            System.err.println("Error calling ChargeProxy: " + e.getMessage());
        }

        // Envia notificação de email no final
        emailService.sendChargeNotification(savedCharge, user.getEmail());

        return savedCharge;
    }

    @Override
    public Charge cancelCharge(Long id) {
        Charge charge = getChargeById(id);

        if ("CANCELED".equals(charge.getStatus())) {
            return charge;
        }

        try {
            // Chama ChargeProxy para cancelar no Asaas
            boolean success = chargeProxyClient.cancelCharge(charge.getExternalId());

            if (success) {
                String oldStatus = charge.getStatus();
                charge.setStatus("CANCELED");
                chargeRepository.update(charge);

                // Observer Pattern: Publica evento para cancelamento
                var user = userRepository.findById(charge.getUserId()).orElse(null);
                String userEmail = (user != null) ? user.getEmail() : null;
                eventPublisher
                        .publishEvent(new ChargeStatusChangedEvent(this, charge, oldStatus, "CANCELED", userEmail));
            }

        } catch (Exception e) {
            System.err.println("Error canceling charge: " + e.getMessage());
        }

        return charge;
    }

    @Override
    public Charge getChargeById(Long id) {
        return chargeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charge not found with id: " + id));
    }

    @Override
    public List<Charge> getChargesByUserId(Long userId) {
        return chargeRepository.findByUserId(userId);
    }

    @Override
    public List<Charge> getAllCharges() {
        return chargeRepository.findAll();
    }
}
