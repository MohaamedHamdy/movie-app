package com.example.movie_app.repository;

import com.example.movie_app.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    public Boolean existsByName(String name);
    public Genre findByName(String name);

    @Query("SELECT g FROM Genre g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    public Genre searchByName(String name);


}
