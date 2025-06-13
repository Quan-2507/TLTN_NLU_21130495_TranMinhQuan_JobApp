package com.company.apis.Controllers.Admin;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.JobDTO;
import com.company.apis.Models.Request.RequestJob;
import com.company.apis.Services.JobService;
import com.company.apis.Utils.HttpException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/job")
public class AdminJobController {
    @Autowired
    JobService _jobService;
    BaseController<JobDTO> _baseController = new BaseController<JobDTO>();
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<List<JobDTO>> _baseControllers = new BaseController<List<JobDTO>>();

    // @GetMapping()
    // public ResponseEntity<?> get() {
    // try {
    // return _baseControllers.success(_jobService.getAll());
    // } catch (HttpException e) {
    // return _baseControllers.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseControllers.error(null, 500, e.getMessage());
    // }
    // }

    @PostMapping()
    public ResponseEntity<?> post(@ModelAttribute RequestJob requestJob) {
        try {
            return _baseController.success(_jobService.create(requestJob));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> get(@PathVariable int id) {
    // try {
    // return _baseController.success(_jobService.getById(id));
    // } catch (HttpException e) {
    // return _baseController.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseController.error(null, 500, e.getMessage());
    // }
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            return _baseController_string.success(_jobService.delete(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable int id, @ModelAttribute RequestJob requestJob) {
        try {
            return _baseController.success(_jobService.put(id, requestJob));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    // @GetMapping("/new")
    // public ResponseEntity<?> getJobsNew() {
    // try {
    // return _baseControllers.success(_jobService.getJobsNew());
    // } catch (HttpException e) {
    // return _baseController.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseController.error(null, 500, e.getMessage());
    // }
    // }

    // @GetMapping("/save/{id}")
    // public ResponseEntity<?> getJobsSave(@PathVariable String id) {
    // try {
    // return _baseControllers.success(_jobService.getJobsSave(id));
    // } catch (HttpException e) {
    // return _baseController.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseController.error(null, 500, e.getMessage());
    // }
    // }

    // @PostMapping("/save")
    // public ResponseEntity<?> postJobsSave(@RequestBody RequestIntermediaryJob
    // requestIntermediaryJob) {
    // try {
    // boolean success = _jobService.postJobsSave(requestIntermediaryJob);
    // if (success) {
    // return _baseControllers.success(null);
    // }
    // throw Variable.Fail;
    // } catch (HttpException e) {
    // return _baseController.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseController.error(null, 500, e.getMessage());
    // }
    // }

    // @GetMapping("/viewed/{id}")
    // public ResponseEntity<?> getJobsViewd(@PathVariable String id) {
    // try {
    // return _baseControllers.success(_jobService.getJobsViewed(id));
    // } catch (HttpException e) {
    // return _baseController.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseController.error(null, 500, e.getMessage());
    // }
    // }
    @PutMapping("/on_or_off/{id}")
    public ResponseEntity<?> jobOnOrOff(@PathVariable int id) {
        try {
            return _baseController.success(_jobService.editJobOnOrOff(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

}
