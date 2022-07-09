package edu.miu.backend.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@SQLDelete(sql = "UPDATE tag SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    private Boolean deleted = Boolean.FALSE;

}
