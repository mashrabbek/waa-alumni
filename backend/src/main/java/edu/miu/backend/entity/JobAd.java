package edu.miu.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class JobAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private String benefits;
    @ManyToOne
    private User creator;
    @ManyToMany
    private List<Tag> tags;

    @ElementCollection
    private List<String> files;


}
