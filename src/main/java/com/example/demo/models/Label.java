package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "label")
public class Label {
    @Id
    @SequenceGenerator(
        name = "label_sequence",
        sequenceName = "label_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "label_sequence"
    )
    private Long labelId;

    @Column(nullable = false)
    private String name;

    @Column()
    private Double confidence;

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "labels")
    private List<Image> images;

    public Label() {}

    public Label(Long labelId, String name, Double confidence, List<Image> images) {
        this.labelId = labelId;
        this.name = name;
        this.confidence = confidence;
        this.images = new ArrayList<>(images);
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLabelId() {
        return labelId;
    }

    public String getName() {
        return name;
    }

    public List<Image> getImages() {

        return new ArrayList<>(images);
    }

    public void setImages(List<Image> images) {
        this.images = new ArrayList<>(images);
    }
}
