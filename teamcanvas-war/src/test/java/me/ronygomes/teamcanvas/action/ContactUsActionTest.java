package me.ronygomes.teamcanvas.action;

import me.ronygomes.teamcanvas.service.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ContactUsActionTest {

    @Mock
    private MailSender mailSender;

    @InjectMocks
    private ContactUsAction contactUsAction;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        contactUsAction.setMessage("Hello");
    }

    @Test
    void testSendMailSuccess() {
        Mockito.when(mailSender.sendMail(Mockito.anyString())).thenReturn(true);

        contactUsAction.sendMail();
        Assertions.assertEquals("Message has been send!", contactUsAction.getInfoMessage());
    }

    @Test
    void testSendMailFailed() {
        Mockito.when(mailSender.sendMail(Mockito.anyString())).thenReturn(false);

        contactUsAction.sendMail();
        Mockito.verify(mailSender).sendMail("Hello");
        Assertions.assertNull(contactUsAction.getInfoMessage());
    }
}
