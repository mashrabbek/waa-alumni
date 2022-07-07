package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private User creator;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;

    @ElementCollection
    private List<String> files;


}
