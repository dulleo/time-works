package com.duskol.timeworks.service;

import com.duskol.timeworks.model.Developer;
import com.duskol.timeworks.model.Project;
import com.duskol.timeworks.model.TimeWork;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {

    Flux<Project> getAllProjects();

    Mono<Project> addDeveloperToProject(String id, Developer developer);

    Mono<Project> chargeProject(String id, String username, TimeWork timeWork);

    Mono<Void> deleteProjectById(String id);

    Mono<Project> getProject(String id);
}
