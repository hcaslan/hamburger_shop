package com.kerem.Controller;


import com.kerem.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String body) {
        emailService.sendEmail(toEmail, subject, body);
        return "Email gönderildi!";
    }

    @GetMapping("/sendactivationcode")
    public ResponseEntity<Void> sendActivationCode(@RequestParam String toEmail, String activationCode){
        emailService.sendActivationCode(toEmail, activationCode);
        return ResponseEntity.ok().build();
    }
}
