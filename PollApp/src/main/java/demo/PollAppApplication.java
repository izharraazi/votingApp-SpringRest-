package demo;
/*
 * CMPE-273 - SMSVoting Application -March 4 2015
 * Created By Izhar Raazi 
 * Please add Headers while testing - Accept - application/json
 * 									Content-Type - application/json
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class PollAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollAppApplication.class, args);
    }
}
