package br.edu.ifpb.dac.chargeManager.business.service;

import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @org.springframework.beans.factory.annotation.Value("${USER_MAIL}")
    private String defaultRecipientEmail;

    // Hardcoded sender email as requested by the user
    private static final String SENDER_EMAIL = "charge-system@example.com";

    public void sendChargeNotification(Charge charge, String userEmail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(SENDER_EMAIL);

            // Use provided user email if available, otherwise use secret value
            String recipient = (userEmail != null && !userEmail.isEmpty()) ? userEmail : defaultRecipientEmail;
            message.setTo(recipient);

            message.setSubject("Nova Cobrança Criada: " + charge.getName());
            message.setText(String.format(
                    "Olá,\n\nUma nova cobrança foi criada no sistema.\n\n" +
                            "Detalhes:\n" +
                            "Nome: %s\n" +
                            "Valor: R$ %s\n" +
                            "Tipo de Pagamento: %s\n" +
                            "Status: %s\n" +
                            "ID Externo: %s\n\n" +
                            "Atenciosamente,\nEquipe Charge System",
                    charge.getName(),
                    charge.getAmount().toString(),
                    charge.getPaymentType(),
                    charge.getStatus(),
                    charge.getExternalId()));

            log.info("Enviando e-mail de notificação para: {}", recipient);
            mailSender.send(message);
            log.info("E-mail enviado com sucesso!");

        } catch (Exception e) {
            log.error("Erro ao enviar e-mail: {}", e.getMessage());
        }
    }
}
