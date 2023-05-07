package com.example.demo.models;

import jakarta.persistence.*;

import java.util.List;

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
    @ManyToMany()
    @JoinTable(name="image_label",
            joinColumns = {
                    @JoinColumn(name="labelId",referencedColumnName = "labelId")

            },
            inverseJoinColumns =  {
                    @JoinColumn(name="imageId",referencedColumnName = "imageId")

            }
    )
    private List<Image> images;
    public Label(){}
    public Label(Long labelId, String name, List<Image> images) {
        this.labelId = labelId;
        this.name = name;
        this.images = images;
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
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
