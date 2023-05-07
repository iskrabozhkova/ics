package com.example.demo.models;

import jakarta.persistence.*;
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

    public Label(Long labelId, String name) {
        this.labelId = labelId;
        this.name = name;
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

}
