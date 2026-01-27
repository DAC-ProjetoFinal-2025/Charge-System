package br.edu.ifpb.dac.chargeManager.business.event;

import br.edu.ifpb.dac.chargeManager.business.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailEventListener {

    private final EmailService emailService;

    @EventListener
    public void handleChargeStatusChanged(ChargeStatusChangedEvent event) {
        log.info("Observer: Detectada mudança de status para cobrança {}: {} -> {}",
                event.getCharge().getId(), event.getOldStatus(), event.getNewStatus());

        emailService.sendChargeNotification(event.getCharge(), event.getUserEmail());
    }
}
