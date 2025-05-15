package com.app.onlinestorebackend.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@JsonSerialize
public class GeneralResponse {

    @NotNull
    private HttpStatus httpStatus;
    @NotNull
    private String Message;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;
    @NotNull
    private GeneralResponseBody body;

}
