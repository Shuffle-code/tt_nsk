package site.tt_nsk;

import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Timestamp;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class TtNskApplication {
	public static void main(String[] args) {


		SpringApplication.run(TtNskApplication.class, args);
		System.setProperty("java.awt.headless", "false");
		log.info("Приложение TtNskApplication запустилось: " + new Timestamp(System.currentTimeMillis()) + " на порту: ");

//		GraphicsEnvironment.isHeadless();
	}

}
