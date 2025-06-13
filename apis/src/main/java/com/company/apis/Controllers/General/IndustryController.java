package com.company.apis.Controllers.General;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.IndustryDTO;
import com.company.apis.Services.IndustryService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/industry")
public class IndustryController {
    @Autowired
    IndustryService _industryService;
    BaseController<IndustryDTO> _baseController = new BaseController<IndustryDTO>();
    BaseController<List<IndustryDTO>> _baseControllers = new BaseController<List<IndustryDTO>>();

    @GetMapping()
    public ResponseEntity<?> get() {
        try {
            return _baseControllers.success(_industryService.getAll());
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return _baseController.success(_industryService.getById(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

}
