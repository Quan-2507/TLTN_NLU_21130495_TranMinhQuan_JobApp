package com.company.apis.Controllers.General;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.SubcriptionPlanDTO;
import com.company.apis.Services.SubcriptionPlanService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/subcription_plan")

public class SubcriptionPlanController {
    @Autowired
    SubcriptionPlanService _subcriptionPlanService;
    BaseController<SubcriptionPlanDTO> _baseController = new BaseController<SubcriptionPlanDTO>();
    BaseController<List<SubcriptionPlanDTO>> _baseControllers = new BaseController<List<SubcriptionPlanDTO>>();

    @GetMapping()
    public ResponseEntity<?> get() {
        try {
            return _baseControllers.success(_subcriptionPlanService.getAll());
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return _baseController.success(_subcriptionPlanService.getById(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

}
