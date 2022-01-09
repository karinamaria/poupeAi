package br.com.poupeAi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EmailConfig {
    @Value("${commons-mail.host}")
    private String hostname;

    @Value("${commons-mail.port}")
    private int port;

    @Value("${commons-mail.username}")
    private String username;

    @Value("${commons-mail.password}")
    private String password;

    @Value("${commons-mail.smtp.starttls.enable}")
    private boolean tlsEnable;

    @Value("${commons-mail.smtp.starttls.required}")
    private boolean tlsRequired;
}
