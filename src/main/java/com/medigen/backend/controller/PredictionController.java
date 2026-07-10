package com.medigen.backend.controller;

import com.medigen.backend.dto.request.PredictionRequest;
import com.medigen.backend.dto.response.PredictionResponse;
import com.medigen.backend.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping
    public ResponseEntity<PredictionResponse> predictDisease(
            @Valid @RequestBody PredictionRequest request) {

        return ResponseEntity.ok(
                predictionService.predictDisease(request)
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<PredictionResponse>> getHistory() {

        return ResponseEntity.ok(
                predictionService.getPredictionHistory()
        );
    }
}