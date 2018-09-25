package com.duskol.timeworks.controller;

import com.duskol.timeworks.dto.ReportByProjctAndDeveloperDto;
import com.duskol.timeworks.error.ErrorCodes;
import com.duskol.timeworks.error.ErrorMessenger;
import com.duskol.timeworks.exception.ResourceNotFoundException;
import com.duskol.timeworks.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping(value = "/reports/projects")
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}/developers/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ReportByProjctAndDeveloperDto> getReportByProjectAndDeveloper(@PathVariable @NotNull String id, @PathVariable @NotNull String username) {
        log.info("get report for project:" + id + " and developer: " +  username + " called...");
        return reportService.getReportByProjectAndDeveloper(id, username);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessenger developerCanNotBeAdded(Exception e) {
        ErrorCodes errorCode = ErrorCodes.PROJECT_CAN_NOT_BE_FOUND;
        return new ErrorMessenger(errorCode.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessenger internalError(Exception e) {
        ErrorCodes errorCode = ErrorCodes.INTERNAL_ERROR;
        return new ErrorMessenger(errorCode.getCode(), e.getMessage());
    }
}
