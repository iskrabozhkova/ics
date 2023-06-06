package com.example.demo.repository;

import com.example.demo.models.Image;
import com.example.demo.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByUrl(String url);
    List<Image> findByLabelsIn(List<Label> labels);

}
