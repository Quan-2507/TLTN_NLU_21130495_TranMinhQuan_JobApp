package com.company.apis.Controllers.Employer;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.SubcriptionPlanDTO;
import com.company.apis.Models.DTO.SubcriptionPlanForEmployer;
import com.company.apis.Services.SubcriptionPlanService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer/subcription_plan")

public class EmployerSubcriptionPlanController {
    @Autowired
    SubcriptionPlanService _subcriptionPlanService;
    BaseController<SubcriptionPlanForEmployer> _baseController = new BaseController<SubcriptionPlanForEmployer>();
    BaseController<List<SubcriptionPlanDTO>> _baseControllers = new BaseController<List<SubcriptionPlanDTO>>();

    @GetMapping()
    public ResponseEntity<?> getByAccount() {
        //auto
        try {
            return _baseController.success(_subcriptionPlanService.getByAccount(0));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> getById(@PathVariable int id) {
    //     try {
    //         return _baseController.success(_subcriptionPlanService.getById(id));
    //     } catch (HttpException e) {
    //         return _baseController.error(null, e.StatusCode, e.message);
    //     } catch (Exception e) {
    //         return _baseController.error(null, 500, e.getMessage());
    //     }
    // }

}
