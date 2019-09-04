package br.com.votacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApiErrorConfig {

    @Bean(name = "apiErrorMessageSource")
    public ReloadableResourceBundleMessageSource apiErrorMessageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:api_errors");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setUseCodeAsDefaultMessage(true);
        return messageBundle;
    }
}
