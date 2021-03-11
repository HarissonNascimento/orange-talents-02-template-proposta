package br.com.zup.propostazup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PropostaZupApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropostaZupApplication.class, args);
    }

}
