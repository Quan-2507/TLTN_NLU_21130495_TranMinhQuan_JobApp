package com.company.apis.Controllers.General;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.JobDTO;
import com.company.apis.Services.JobService;
import com.company.apis.Utils.HttpException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/job")
public class JobController {
    @Autowired
    JobService _jobService;
    BaseController<JobDTO> _baseController = new BaseController<JobDTO>();
    BaseController<List<JobDTO>> _baseControllers = new BaseController<List<JobDTO>>();

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_jobService.getAll(size,page));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        try {
            return _baseController.success(_jobService.getById(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/new")
    public ResponseEntity<?> getJobsNew(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_jobService.getJobsNew(size,page));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    } 
   

}
