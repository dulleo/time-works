package com.duskol.timeworks.service;

import com.duskol.timeworks.dto.DeveloperDto;
import com.duskol.timeworks.dto.ProjectDto;
import com.duskol.timeworks.dto.ReportByProjctAndDeveloperDto;
import com.duskol.timeworks.exception.ResourceNotFoundException;
import com.duskol.timeworks.model.Developer;
import com.duskol.timeworks.model.Project;
import com.duskol.timeworks.model.TimeWork;
import com.duskol.timeworks.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Mono<ReportByProjctAndDeveloperDto> getReportByProjectAndDeveloper(String projectId, String username) {

        Mono<Project> fallback = Mono
                .error(new ResourceNotFoundException("CHARGE PROJECT: Project with id=" + projectId + " and developer username: " + username + " is not found."));

        return projectRepository.findByIdAndDevelopersUsername(projectId, username)
                .switchIfEmpty(fallback)
                .map(existingProject-> {

                    ReportByProjctAndDeveloperDto dto = new ReportByProjctAndDeveloperDto();
                    dto.setProjectDto(new ProjectDto(existingProject.getId(), existingProject.getTitle()));
                    dto.setDeveloperDto(getDeveloper(existingProject.getDevelopers(), username));
                    return dto;
                });
    }

    /**
     *
     * @param developers
     * @return
     */
    private DeveloperDto getDeveloper(List<Developer> developers, String username) {

       Developer developer =  developers.stream().filter(d->username.equalsIgnoreCase(d.getUsername())).findFirst().get();

        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setUsername(developer.getUsername());
        developerDto.setTotalHoursWorked(getTotalTimeWorks(developer.getTimeWorks()));
        return developerDto;
    }

    /**
     *
     * @param timeWorks
     * @return
     */
    private String getTotalTimeWorks(List<TimeWork> timeWorks) {

        if (timeWorks.size() < 2)
            throw new IllegalArgumentException("At least 2 times are required");

        Pattern timePattern = Pattern.compile("([0-9]+):([0-5][0-9]):([0-5][0-9])");

        // Parse times and sum hours, minutes, and seconds
        int hour = 0, minute = 0, second = 0;
        for (TimeWork timeWork : timeWorks) {
            String time = timeWork.getHoursOnProject();
            Matcher m = timePattern.matcher(time);
            if (! m.matches())
                throw new IllegalArgumentException("Invalid time: " + time);
            hour   += Integer.parseInt(m.group(1));
            minute += Integer.parseInt(m.group(2));
            second += Integer.parseInt(m.group(3));
        }

        // Handle overflow
        minute += second / 60;   second %= 60;
        hour   += minute / 60;   minute %= 60;

        // Format and return result
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
