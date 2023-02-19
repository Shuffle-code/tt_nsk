package com.example.tt_nsk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.util.TimeZone;

@SpringBootApplication
public class TtNskApplication {
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(TtNskApplication.class, args);
		System.setProperty("java.awt.headless", "false");

//		GraphicsEnvironment.isHeadless();
	}

}
