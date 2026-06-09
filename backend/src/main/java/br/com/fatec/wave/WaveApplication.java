package br.com.fatec.wave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaveApplication.class, args);
    }

}