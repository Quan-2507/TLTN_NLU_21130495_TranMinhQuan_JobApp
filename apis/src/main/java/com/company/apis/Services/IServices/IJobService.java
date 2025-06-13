package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.JobDTO;
import com.company.apis.Models.Request.RequestApplication;
import com.company.apis.Models.Request.RequestJob;

import java.util.List;

public interface IJobService {
    List<JobDTO> getAll(int size, int page);

    JobDTO create(RequestJob requestJob);

    JobDTO put(int id, RequestJob requestJob);

    JobDTO getById(int id);

    String delete(int id);

    List<JobDTO> getJobsNew(int size, int page);

    List<JobDTO> getJobsSave(int size, int page);

    List<JobDTO> getJobsViewed(int size, int page);

    List<JobDTO> getJobsHot(int size, int page);

    List<JobDTO> getJobApplicationByAccount(int size, int page);

    String CreatejobApplication(RequestApplication requestApplication);

}
