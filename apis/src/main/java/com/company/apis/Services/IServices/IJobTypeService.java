package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.JobTypeDTO;
import com.company.apis.Models.Request.RequestJobType;

import java.util.List;

public interface IJobTypeService {
    List<JobTypeDTO> getAll();

    JobTypeDTO create(RequestJobType requestJobType);

    JobTypeDTO put(int id, RequestJobType requestJobType);

    String delete(int id);

    JobTypeDTO getById(int id);
}
