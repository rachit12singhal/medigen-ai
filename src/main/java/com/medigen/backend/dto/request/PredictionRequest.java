package com.medigen.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionRequest {

    @NotBlank(message = "Symptoms cannot be empty")
    private String symptoms;

}