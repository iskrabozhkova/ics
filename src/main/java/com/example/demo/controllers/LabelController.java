package com.example.demo.controllers;

import com.example.demo.models.Label;
import com.example.demo.services.LabelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/tags")
@CrossOrigin("http://localhost:4200")
public class LabelController {
    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        // Using Objects.requiteNonNull as a workaround because https://github.com/spotbugs/spotbugs/issues/1797
        this.labelService = Objects.requireNonNull(labelService, "image service must not be null");
    }

    @GetMapping
    public List<Label> getAllTags() {
        return labelService.getAllTags();
    }

    @GetMapping(params = "prefix")
    public List<Label> getTagsByPrefix(@RequestParam("prefix") String prefix) {
        return labelService.getTagsByPrefix(prefix);
    }
}
