package com.example.demo.services;

import com.example.demo.models.Label;
import com.example.demo.repository.LabelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> getAllTags() {
        return labelRepository.findAll();
    }

    public List<Label> getTagsByPrefix(String prefix) {
        return labelRepository.findByNameStartingWith(prefix);
    }
}
