package br.edu.ifpb.dac.chargeManager.business.event;

import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChargeStatusChangedEvent extends ApplicationEvent {
    private final Charge charge;
    private final String oldStatus;
    private final String newStatus;
    private final String userEmail;

    public ChargeStatusChangedEvent(Object source, Charge charge, String oldStatus, String newStatus,
            String userEmail) {
        super(source);
        this.charge = charge;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.userEmail = userEmail;
    }
}
