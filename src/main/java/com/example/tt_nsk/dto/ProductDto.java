//package com.example.tt_nsk.dto;
//
//import com.example.tt_nsk.entity.enums.Status;
//import com.sun.istack.NotNull;
//import lombok.*;
//
//import javax.validation.constraints.*;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class ProductDto {
//    private Long id;
//    @NotBlank
//    private String title;
//    @NotNull
//    @DecimalMin(value = "0.0", inclusive = false)
//    @Digits(integer = 6, fraction = 2)
//    private BigDecimal cost;
//    @PastOrPresent
//    private LocalDate manufactureDate;
//    @NotNull
//    private Status status;
//    private String manufacturer;
////    private Set<CategoryDto> categories;
//}
