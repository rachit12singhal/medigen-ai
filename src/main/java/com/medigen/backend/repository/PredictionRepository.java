package com.medigen.backend.repository;

import com.medigen.backend.entity.Prediction;
import com.medigen.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    List<Prediction> findByUser(User user);

}