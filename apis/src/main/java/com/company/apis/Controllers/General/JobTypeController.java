package com.company.apis.Controllers.General;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.JobTypeDTO;
import com.company.apis.Services.JobTypeService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/job_type")
public class JobTypeController {
    @Autowired
    JobTypeService _jobTypeService;
    BaseController<JobTypeDTO> _baseController = new BaseController<JobTypeDTO>();
    BaseController<List<JobTypeDTO>> _baseControllers = new BaseController<List<JobTypeDTO>>();

    @GetMapping()
    public ResponseEntity<?> get() {
        try {
            return _baseControllers.success(_jobTypeService.getAll());
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return _baseController.success(_jobTypeService.getById(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
}
