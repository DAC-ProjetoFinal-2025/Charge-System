package br.edu.ifpb.dac.chargeManager.business.service;

import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import br.edu.ifpb.dac.chargeManager.infra.client.ChargeProxyClient;
import br.edu.ifpb.dac.chargeManager.infra.client.soap.ChargeResponseDto;
import br.edu.ifpb.dac.chargeManager.infra.repository.ChargeRepository;
import br.edu.ifpb.dac.chargeManager.infra.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargeServiceImpl implements ChargeService {

    private final ChargeRepository chargeRepository;
    private final UserRepository userRepository;
    private final ChargeProxyClient chargeProxyClient;

    public ChargeServiceImpl(ChargeRepository chargeRepository,
            UserRepository userRepository,
            ChargeProxyClient chargeProxyClient) {
        this.chargeRepository = chargeRepository;
        this.userRepository = userRepository;
        this.chargeProxyClient = chargeProxyClient;
    }

    @Override
    public Charge createCharge(Charge charge) {
        // Validate user exists
        userRepository.findById(charge.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + charge.getUserId()));

        // Set initial status
        charge.setStatus("PENDING");

        // Save charge to database
        Charge savedCharge = chargeRepository.save(charge);

        try {
            // Call ChargeProxy via SOAP to create charge in Asaas
            ChargeResponseDto response = chargeProxyClient.sendCharge(
                    savedCharge.getAmount(),
                    savedCharge.getPaymentType(),
                    savedCharge.getCustomer(),
                    savedCharge.getDueDate());

            // Update charge with external data
            savedCharge.setExternalId(response.getId());
            savedCharge.setStatus(response.getStatus());

            // Update in database
            chargeRepository.update(savedCharge);

        } catch (Exception e) {
            // Log error but keep charge in PENDING status
            System.err.println("Error calling ChargeProxy: " + e.getMessage());
        }

        return savedCharge;
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
