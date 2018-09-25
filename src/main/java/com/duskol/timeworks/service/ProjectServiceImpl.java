package com.duskol.timeworks.service;

import com.duskol.timeworks.exception.ResourceNotFoundException;
import com.duskol.timeworks.model.Developer;
import com.duskol.timeworks.model.Project;
import com.duskol.timeworks.model.TimeWork;
import com.duskol.timeworks.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Flux<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Mono<Project> addDeveloperToProject(String id, Developer developer) {

        Mono<Project> fallback = Mono
                .error(new ResourceNotFoundException("ADD DEVELOPER TO PROJECT: Project with id=" + id + " does not exists or developer with username:" +  developer.getUsername() + " is already added"));

        return projectRepository.findByIdAndDevelopersNotIn(id, developer)
                .switchIfEmpty(fallback)
                .flatMap(existingProject-> {
                    existingProject.getDevelopers().add(developer);
                    return projectRepository.save(existingProject);
                });
    }

    @Override
    public Mono<Project> chargeProject(String id, String username, TimeWork timeWork) {

        Mono<Project> fallback = Mono
                .error(new ResourceNotFoundException("CHARGE PROJECT: Project with id=" + id + " and developer username: " + username + " is not found."));

        return projectRepository.findByIdAndDevelopersUsername(id, username)
                .switchIfEmpty(fallback)
                .flatMap(existingProject-> {
                    existingProject.getDevelopers().stream().filter(d -> username.equalsIgnoreCase(d.getUsername())).findFirst().get().getTimeWorks().add(timeWork);
                    return projectRepository.save(existingProject);
                });
    }

    @Override
    public Mono<Void> deleteProjectById(String id) {

        Mono<Project> fallback = Mono.error(new ResourceNotFoundException("DELETE PROJECT: Project with id:" + id + " does not exist in the database"));

        return projectRepository.findById(id)
                .switchIfEmpty(fallback)
                .flatMap(existingProject -> projectRepository.delete(existingProject));
    }

    @Override
    public Mono<Project> getProject(String id) {
        Mono<Project> fallback = Mono.error(new ResourceNotFoundException("GET PROJECT: Project with id:" + id + " does not exist in the database"));

        return projectRepository.findById(id)
                .switchIfEmpty(fallback);
    }
}
