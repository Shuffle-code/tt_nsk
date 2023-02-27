package site.tt_nsk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TtNskApplication {
	public static void main(String[] args) {
		SpringApplication.run(TtNskApplication.class, args);
		System.setProperty("java.awt.headless", "false");

//		GraphicsEnvironment.isHeadless();
	}

}
