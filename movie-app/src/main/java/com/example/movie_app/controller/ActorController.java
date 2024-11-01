package com.example.movie_app.controller;

import com.example.movie_app.DTO.ActorDto;
import com.example.movie_app.entity.Actor;
import com.example.movie_app.response.SuccessResponse;
import com.example.movie_app.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actor")
public class ActorController {
    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping("/admin")
    public ResponseEntity<SuccessResponse> createActor(@RequestBody ActorDto actorDto) {
        return actorService.createActor(actorDto);
    }
}
