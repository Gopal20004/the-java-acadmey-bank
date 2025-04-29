package com.musdon.thejavaacadmeybank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "The Java Academy Bank",
                description = "Backend Rest APIs for TJA Bank",
                version = "v1.0",
                contact = @Contact(
                        name = "Gopal Verma",
                        email = "gopuverma345@gmail.com",
                        url = "https://github.com/Gopal20004/My-First-Bank-Application"
                ),
                license = @License(
                        name = "Tha Java Academy",
                        url = "https://github.com/Gopal20004/My-First-Bank-Application"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "The Java Academy Bank App Documentation",
                url = "https://github.com/Gopal20004/My-First-Bank-Application"
        )
)
public class TheJavaAcademyBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheJavaAcademyBankApplication.class, args);
    }

}
