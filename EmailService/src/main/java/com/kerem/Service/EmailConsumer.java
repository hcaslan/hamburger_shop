package com.kerem.Service;

import com.kerem.exceptions.ErrorType;
import com.kerem.exceptions.MailServiceException;
import com.kerem.model.EmailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.amqp.rabbit.connection.NodeLocator.LOGGER;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;
    private final JavaMailSender mailSender;

    @Async
    @RabbitListener(queues = "getMail.Queue")
    public void send(EmailModel mailModel) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(mailModel.getText(), true);
            helper.setTo(mailModel.getToEmail());
            helper.setSubject(mailModel.getSubject());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MailServiceException(ErrorType.MAIL_SEND_FAIL);
        }
    }

}


