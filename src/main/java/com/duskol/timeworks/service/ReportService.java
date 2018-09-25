package com.duskol.timeworks.service;

import com.duskol.timeworks.dto.ReportByProjctAndDeveloperDto;
import reactor.core.publisher.Mono;

public interface ReportService {

    Mono<ReportByProjctAndDeveloperDto> getReportByProjectAndDeveloper(String projectId, String username);
}
