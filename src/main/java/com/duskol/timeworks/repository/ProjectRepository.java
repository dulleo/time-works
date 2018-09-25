package com.duskol.timeworks.repository;

import com.duskol.timeworks.model.Developer;
import com.duskol.timeworks.model.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {

    Mono<Project> findByIdAndDevelopersUsername(String id, String username);

    Mono<Project> findByIdAndDevelopersNotIn(String id, Developer developer);
}
