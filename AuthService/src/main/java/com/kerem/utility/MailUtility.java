package com.kerem.utility;

import org.springframework.stereotype.Service;

@Service
public class MailUtility {

    public String activationEmail(String name, String code) {
        return
                "<p> Hi " + name +
                        ",</p>" +
                        "<p> Thank you for registering. Please enter the code below to activate your account: </p>" +
                        "<blockquote><p> <h1> " + code + " <h1> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>";
    }

    public String passwordChangeEmail(String name, String code) {
        return
                "<p> Hi " + name +
                        ",</p>" +
                        "<p> Here is your password change token. You can change your password with this code: </p>" +
                        "<blockquote><p>" + code + "</p></blockquote>\n Token will expire in 15 minutes. <p>See you soon</p>";
    }

}
