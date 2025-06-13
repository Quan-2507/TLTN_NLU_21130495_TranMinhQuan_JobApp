package com.company.apis.Controllers.Admin;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.SkillDTO;
import com.company.apis.Models.Request.RequestSkill;
import com.company.apis.Services.SkillService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/skill")
public class AdminSkillController {
    @Autowired
    SkillService _skillService;
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<SkillDTO> _baseController = new BaseController<SkillDTO>();
    BaseController<List<SkillDTO>> _baseControllers = new BaseController<List<SkillDTO>>();

    @PostMapping()
    public ResponseEntity<?> post(@ModelAttribute RequestSkill requestSkill) {
        try {
            return _baseController.success(_skillService.create(requestSkill));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            return _baseController_string.success(_skillService.delete(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> Put(@PathVariable int id, @ModelAttribute RequestSkill requestSkill) {
        try {
            return _baseController.success(_skillService.put(id, requestSkill));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

}
