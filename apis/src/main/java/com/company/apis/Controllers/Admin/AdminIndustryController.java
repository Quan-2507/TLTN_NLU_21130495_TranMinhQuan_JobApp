package com.company.apis.Controllers.Admin;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.IndustryDTO;
import com.company.apis.Models.Request.RequestIndustry;
import com.company.apis.Services.IndustryService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/industry")
public class AdminIndustryController {
    @Autowired
    IndustryService _industryService;
    BaseController<IndustryDTO> _baseController = new BaseController<IndustryDTO>();
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<List<IndustryDTO>> _baseControllers = new BaseController<List<IndustryDTO>>();

    // @GetMapping()
    // public ResponseEntity<?> get() {
    //     try {
    //         return _baseControllers.success(_industryService.getAll());
    //     } catch (HttpException e) {
    //         return _baseControllers.error(null, e.StatusCode, e.message);
    //     } catch (Exception e) {
    //         return _baseControllers.error(null, 500, e.getMessage());
    //     }
    // }

    @PostMapping()
    public ResponseEntity<?> post(@ModelAttribute RequestIndustry industry) {
        try {
            return _baseController.success(_industryService.create(industry));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> getById(@PathVariable int id) {
    //     try {
    //         return _baseController.success(_industryService.getById(id));
    //     } catch (HttpException e) {
    //         return _baseController.error(null, e.StatusCode, e.message);
    //     } catch (Exception e) {
    //         return _baseController.error(null, 500, e.getMessage());
    //     }
    // }

    @PutMapping("/{id}")
    public ResponseEntity<?> Put(@PathVariable int id, @ModelAttribute RequestIndustry industry) {
        try {
            return _baseController.success(_industryService.put(id, industry));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Delete(@PathVariable int id) {
        try {
            return _baseController_string.success(_industryService.delete(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

}
