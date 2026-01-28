package br.edu.ifpb.dac.chargeManager.business.service;

import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private Charge testCharge;

    @BeforeEach
    void setUp() {
        // Set the default recipient email using reflection
        ReflectionTestUtils.setField(emailService, "defaultRecipientEmail", "default@test.com");

        // Create a test charge
        testCharge = Charge.builder()
                .id(1L)
                .userId(1L)
                .name("Test Charge")
                .amount(new BigDecimal("100.50"))
                .paymentType("CREDIT_CARD")
                .status("PENDING")
                .externalId("ext-123")
                .build();
    }

    @Test
    void shouldSendEmailWithUserEmail() {
        // Arrange
        String userEmail = "user@test.com";
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendChargeNotification(testCharge, userEmail);

        // Assert
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertNotNull(sentMessage);
        assertEquals("charge-system@example.com", sentMessage.getFrom());
        assertArrayEquals(new String[] { userEmail }, sentMessage.getTo());
        assertEquals("Nova Cobrança Criada: Test Charge", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("Test Charge"));
        assertTrue(sentMessage.getText().contains("100.50"));
        assertTrue(sentMessage.getText().contains("CREDIT_CARD"));
        assertTrue(sentMessage.getText().contains("PENDING"));
        assertTrue(sentMessage.getText().contains("ext-123"));
    }

    @Test
    void shouldSendEmailToDefaultRecipientWhenUserEmailIsNull() {
        // Arrange
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendChargeNotification(testCharge, null);

        // Assert
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertNotNull(sentMessage);
        assertArrayEquals(new String[] { "default@test.com" }, sentMessage.getTo());
    }

    @Test
    void shouldSendEmailToDefaultRecipientWhenUserEmailIsEmpty() {
        // Arrange
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendChargeNotification(testCharge, "");

        // Assert
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertNotNull(sentMessage);
        assertArrayEquals(new String[] { "default@test.com" }, sentMessage.getTo());
    }

    @Test
    void shouldHandleExceptionGracefully() {
        // Arrange
        String userEmail = "user@test.com";
        doThrow(new RuntimeException("SMTP Server unreachable"))
                .when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> emailService.sendChargeNotification(testCharge, userEmail));

        // Verify that mailSender.send was called despite the exception
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void shouldContainCorrectEmailContentFormat() {
        // Arrange
        String userEmail = "user@test.com";
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendChargeNotification(testCharge, userEmail);

        // Assert
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        String emailText = sentMessage.getText();

        // Verify email structure
        assertNotNull(emailText);
        assertTrue(emailText.contains("Olá"));
        assertTrue(emailText.contains("Uma nova cobrança foi criada no sistema"));
        assertTrue(emailText.contains("Detalhes:"));
        assertTrue(emailText.contains("Nome:"));
        assertTrue(emailText.contains("Valor:"));
        assertTrue(emailText.contains("Tipo de Pagamento:"));
        assertTrue(emailText.contains("Status:"));
        assertTrue(emailText.contains("ID Externo:"));
        assertTrue(emailText.contains("Atenciosamente"));
        assertTrue(emailText.contains("Equipe Charge System"));
    }

    @Test
    void shouldUseCorrectSenderEmail() {
        // Arrange
        String userEmail = "user@test.com";
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendChargeNotification(testCharge, userEmail);

        // Assert
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals("charge-system@example.com", sentMessage.getFrom());
    }
}
