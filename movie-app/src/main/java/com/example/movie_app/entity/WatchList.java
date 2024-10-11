//package com.example.movie_app.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import java.util.Set;
//
//@Entity
//@Data
//public class WatchList {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @ManyToMany
//    @JoinTable(
//            name = "user_watchlist",
//            joinColumns = @JoinColumn(name = "watchlist_id"),
//            inverseJoinColumns = @JoinColumn(name = "movie_id")
//    )
//    private Set<Movie> movies;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//}
