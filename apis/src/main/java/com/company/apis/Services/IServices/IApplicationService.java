package com.company.apis.Services.IServices;

import java.util.List;

import com.company.apis.Models.DTO.ApplicationDTO;

public interface IApplicationService {
    List<ApplicationDTO> getByJob(int job_id, int size, int page);
}
