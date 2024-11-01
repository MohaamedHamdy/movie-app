package com.example.movie_app.repository;

import com.example.movie_app.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    @Query("SELECT a FROM Actor a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    public Actor searchByName(@Param("name") String name);
    public Actor findByName(String name);
}
