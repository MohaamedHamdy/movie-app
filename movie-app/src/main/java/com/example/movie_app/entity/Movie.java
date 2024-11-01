package com.example.movie_app.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private double imdbRating;
    private String imdbRatingCount;
    // cast many to many
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "movies_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
//    @JsonIgnore
    private Set<Actor> actors;

    // list of reviews
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Reviews> reviews;

    @ManyToMany(cascade = CascadeType.PERSIST)
//    @JsonIgnore
    @JsonBackReference
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")

    )
    private Set<Genre> genres;
}
