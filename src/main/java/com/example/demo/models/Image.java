package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(
        name = "image",
        uniqueConstraints = {
                @UniqueConstraint(name = "url_unique", columnNames = "url")
        }
)

public class Image {
    @Id
    @SequenceGenerator(
            name = "image_sequence",
            sequenceName = "image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_sequence"
    )
    private Long imageId;
    @Column(nullable = false)
    private String url;
    private LocalDate uploadedAt;
    private String analysis_service;
    @Column(nullable = false)
    private Integer width;
    @Column(nullable = false)
    private Integer height;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "image_label",
            joinColumns = {
                    @JoinColumn(name = "imageId", referencedColumnName = "imageId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "labelId", referencedColumnName = "labelId")

            }
    )
    private List<Label> labels;

    public Image() {
    }

    public Image(Long imageId, String url, LocalDate uploadedAt, String analysis_service, Integer width, Integer height, List<Label> labels) {
        this.imageId = imageId;
        this.url = url;
        this.uploadedAt = uploadedAt;
        this.analysis_service = analysis_service;
        this.width = width;
        this.height = height;
        this.labels = labels;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDate uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getAnalysis_service() {
        return analysis_service;
    }

    public void setAnalysis_service(String analysis_service) {
        this.analysis_service = analysis_service;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Label> getLabels() {
        System.out.println("Heloooo");
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}