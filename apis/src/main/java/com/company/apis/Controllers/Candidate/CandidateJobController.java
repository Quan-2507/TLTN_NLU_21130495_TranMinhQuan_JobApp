package com.company.apis.Controllers.Candidate;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.JobDTO;
import com.company.apis.Models.Request.RequestApplication;
import com.company.apis.Models.Request.RequestIntermediaryJob;
import com.company.apis.Services.JobService;
import com.company.apis.Utils.HttpException;
import com.company.apis.Utils.Variable;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate/job")
public class CandidateJobController {
    @Autowired
    JobService _jobService;
    BaseController<JobDTO> _baseController = new BaseController<JobDTO>();
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<List<JobDTO>> _baseControllers = new BaseController<List<JobDTO>>();

    @PostMapping("/application")
    public ResponseEntity<?> postJobsApplication(@RequestBody RequestApplication requestApplication) {
        try {
            return _baseController_string.success(_jobService.CreatejobApplication(requestApplication));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/application")
    public ResponseEntity<?> getApplicated(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_jobService.getJobApplicationByAccount(size,page));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/save")
    public ResponseEntity<?> getJobsSave(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_jobService.getJobsSave(size,page));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> postJobsSave(@RequestBody RequestIntermediaryJob requestIntermediaryJob) {
        try {
            boolean success = _jobService.postJobsSave(requestIntermediaryJob);
            if (success) {
                return _baseControllers.success(null);
            }
            throw Variable.FAIL;
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
    // @DeleteMapping("/save")
    // public ResponseEntity<?> deleteJobSave(@RequestBody RequestIntermediaryJob requestIntermediaryJob) {
    //     try {
    //         boolean success = _jobService.deleteJobsSave(requestIntermediaryJob);
    //         if (success) {
    //             return _baseControllers.success(null);
    //         }
    //         throw Variable.FAIL;
    //     } catch (HttpException e) {
    //         return _baseController.error(null, e.StatusCode, e.message);
    //     } catch (Exception e) {
    //         return _baseController.error(null, 500, e.getMessage());
    //     }
    // }

    @GetMapping("/viewed")
    public ResponseEntity<?> getJobsViewd(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_jobService.getJobsViewed(size,page));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PostMapping("/viewed")
    public ResponseEntity<?> postJobsViewed(@RequestBody RequestIntermediaryJob requestIntermediaryJob) {
        try {
            boolean success = _jobService.postJobsViewed(requestIntermediaryJob);
            if (success) {
                return _baseControllers.success(null);
            }
            throw Variable.FAIL;
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
    @GetMapping("/hot")
    public ResponseEntity<?> getJobsHot(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_jobService.getJobsHot(size,page));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
}
