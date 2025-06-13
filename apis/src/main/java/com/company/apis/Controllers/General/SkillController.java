package com.company.apis.Controllers.General;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.SkillDTO;
import com.company.apis.Services.SkillService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/general/skill")
public class SkillController {
    @Autowired
    SkillService _skillService;
    BaseController<SkillDTO> _baseController = new BaseController<SkillDTO>();
    BaseController<List<SkillDTO>> _baseControllers = new BaseController<List<SkillDTO>>();

    @GetMapping()
    public ResponseEntity<?> get() {
        try {
            return _baseControllers.success(_skillService.getAll());
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        try {
            return _baseController.success(_skillService.getById(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getSkillByCompany(@PathVariable int id) {
        try {
            return _baseControllers.success(_skillService.getAllByCompany(id));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }
}
