package com.example.tt_nsk.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackages = {"com.example.tt_nsk.controller"})
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info());
        return openAPI;
    }

    @Bean
    Info info() {
        return new Info().title("Администрирование «коммерческих соревнований» по настольному теннису")
                .description("Среди участников турниров большая разница в уровне игры. " +
                        "Большинство игр являются «формальными». " +
                        "Либо быстрая и легкая победа, либо такое же быстрое поражение. " +
                        "Решить данную проблему может система «гандикапа» (форы) - начальное преимущество, " +
                        "намеренно предоставленное одной из сторон в соревновании, чтобы уравнять шансы " +
                        "на победу при разных силах и возможностях соревнующихся.")
                .version("1.0")
                .license(licence())
                .contact(contact());
    }

    @Bean
    License licence() {
        return new License()
                .name("Unlicense")
                .url("https://unlicense.org");
    }

    @Bean
    Contact contact() {
        return new Contact().email("incoming34@yandex.ru").name("Евгений Малюгин");
    }

}