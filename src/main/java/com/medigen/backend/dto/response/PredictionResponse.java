package com.medigen.backend.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionResponse {

    private Long id;

    private String symptoms;

    private String predictedDisease;

    private String confidence;

    private String aiSuggestion;

    private String createdAt;
}