package by.bsu.rikz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by USER on 14.12.2016.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MailServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServerApplication.class);
    }
}
