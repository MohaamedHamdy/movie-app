package com.example.movie_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String image;

    // many to many movies
    @ManyToMany(mappedBy = "actors", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Set<Movie> movies;


}
