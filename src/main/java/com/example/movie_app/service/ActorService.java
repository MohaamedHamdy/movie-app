package com.example.movie_app.service;

import com.example.movie_app.DTO.ActorDto;
import com.example.movie_app.entity.Actor;
import com.example.movie_app.repository.ActorRepository;
import com.example.movie_app.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public ResponseEntity<SuccessResponse> createActor(ActorDto actorDto) {
        Actor actor = new Actor();
        actor.setName(actorDto.getName());
        actor.setImage(actorDto.getImage());
        actorRepository.save(actor);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Actor created");
        successResponse.setData(actor);
        successResponse.setCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

}
