package com.example.movie_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String director;

    private int duration;
    private LocalDateTime releaseDate;
    private String posterUrl;
    private String trailerUrl;

    // cast many to many
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "movies_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name= "actor_id")
    )
    private Set<Actor> actors;

    // list of reviews
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Reviews> reviews;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")

    )
    private Set<Genre> genres;
}
