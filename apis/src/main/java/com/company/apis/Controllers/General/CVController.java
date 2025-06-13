package com.company.apis.Controllers.General;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.CVDTO;
import com.company.apis.Services.CVService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/cv")
public class CVController {
    @Autowired
    CVService _cvService;
    BaseController<CVDTO> _baseController = new BaseController<CVDTO>();
    BaseController<List<CVDTO>> _baseControllers = new BaseController<List<CVDTO>>();

    @GetMapping("/{id}")
    public ResponseEntity<?> getByid(@PathVariable int id) {
        try {
            return _baseController.success(_cvService.getById(id));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }
}
