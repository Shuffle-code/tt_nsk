//package com.example.tt_nsk.dto;
//
//import com.example.tt_nsk.entity.enums.Status;
//import com.sun.istack.NotNull;
//import lombok.*;
//import org.springframework.context.annotation.Bean;
//
//import javax.validation.constraints.*;
//import java.math.BigDecimal;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class PlayerDto {
//    private Long id;
//    @NotBlank
//    private String firstname;
//    @NotBlank
//    private String patronymic;
//    @NotBlank
//    private String lastname;
//    @NotNull
//    @DecimalMin(value = "0.0", inclusive = false)
//    @Digits(integer = 6, fraction = 2)
//    private BigDecimal rating;
//    @NotNull
//    @DecimalMin(value = "0.0", inclusive = false)
//    @Digits(integer = 6, fraction = 2)
//    private BigDecimal ratingTtw;
//    @Past
//    private Integer yearOfBirth;
//    @NotNull
//    private Status status;
//}
