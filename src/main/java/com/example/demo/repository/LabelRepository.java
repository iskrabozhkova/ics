package com.example.demo.repository;

import com.example.demo.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findByName(String labelName);
    List<Label> findAll();

    List<Label> findByNameStartingWith(String prefix);
}
