package com.example.movie_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String image;

    // many to many movies
    @ManyToMany(mappedBy = "actors")
    @JsonBackReference
    private Set<Movie> movies;


}
