package com.medigen.backend.service;

import com.medigen.backend.dto.request.PredictionRequest;
import com.medigen.backend.dto.response.PredictionResponse;

import java.util.List;

public interface PredictionService {

    PredictionResponse predictDisease(PredictionRequest request);

    List<PredictionResponse> getPredictionHistory();
}