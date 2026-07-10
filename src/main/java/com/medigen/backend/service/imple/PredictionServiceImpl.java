package com.medigen.backend.service.imple;

import com.medigen.backend.dto.request.PredictionRequest;
import com.medigen.backend.dto.response.PredictionResponse;
import com.medigen.backend.entity.Prediction;
import com.medigen.backend.entity.User;
import com.medigen.backend.repository.PredictionRepository;
import com.medigen.backend.repository.UserRepository;
import com.medigen.backend.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private final PredictionRepository predictionRepository;
    private final UserRepository userRepository;

    @Override
    public PredictionResponse predictDisease(PredictionRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Dummy AI Prediction (will be replaced with IBM watsonx.ai)
        Prediction prediction = Prediction.builder()
                .symptoms(request.getSymptoms())
                .predictedDisease("Common Cold")
                .confidence("92%")
                .aiSuggestion("Drink plenty of fluids, take proper rest, and consult a doctor if symptoms worsen.")
                .user(user)
                .build();

        Prediction savedPrediction = predictionRepository.save(prediction);

        return PredictionResponse.builder()
                .id(savedPrediction.getId())
                .symptoms(savedPrediction.getSymptoms())
                .predictedDisease(savedPrediction.getPredictedDisease())
                .confidence(savedPrediction.getConfidence())
                .aiSuggestion(savedPrediction.getAiSuggestion())
                .createdAt(savedPrediction.getCreatedAt().toString())
                .build();
    }

    @Override
    public List<PredictionResponse> getPredictionHistory() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return predictionRepository.findByUser(user)
                .stream()
                .map(prediction -> PredictionResponse.builder()
                        .id(prediction.getId())
                        .symptoms(prediction.getSymptoms())
                        .predictedDisease(prediction.getPredictedDisease())
                        .confidence(prediction.getConfidence())
                        .aiSuggestion(prediction.getAiSuggestion())
                        .createdAt(prediction.getCreatedAt().toString())
                        .build())
                .collect(Collectors.toList());
    }
}