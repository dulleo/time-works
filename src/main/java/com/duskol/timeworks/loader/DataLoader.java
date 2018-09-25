package com.duskol.timeworks.loader;

import com.duskol.timeworks.model.Developer;
import com.duskol.timeworks.model.Project;
import com.duskol.timeworks.model.TimeWork;
import com.duskol.timeworks.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    private ProjectRepository projectRepository;

    public DataLoader(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) {

        projectRepository.deleteAll().then(Mono.just("Project1"))
                .map(title->new Project("1", title,
                        Arrays.asList(new Developer("developer1", Arrays.asList(new TimeWork( new Date(),"01:11:11"), new TimeWork(new Date(), "02:22:22"))),
                                new Developer("developer2", Arrays.asList(new TimeWork(new Date(), "03:33:33"), new TimeWork(new Date(), "04:44:44"))))))
                .flatMap(projectRepository::save)
                .subscribe(null, null, this::run);
    }

    private void run() {
        projectRepository.findAll().subscribe(System.out::println);
    }
}
