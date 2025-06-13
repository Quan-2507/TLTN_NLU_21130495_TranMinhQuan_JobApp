package com.company.apis.Controllers.Employer;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.CompanyDTO;
import com.company.apis.Models.Request.RequestCompany;
import com.company.apis.Services.CompanyService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer/company")
public class EmployerCompanyController {
    @Autowired
    CompanyService _companyService;
    BaseController<CompanyDTO> _baseController = new BaseController<CompanyDTO>();
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<List<CompanyDTO>> _baseControllers = new BaseController<List<CompanyDTO>>();

    @PostMapping()
    public ResponseEntity<?> post(@ModelAttribute RequestCompany requestCompany) {
        try {
            return _baseController.success(_companyService.create(requestCompany));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        try {
            return _baseController.success(_companyService.getById(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            return _baseController_string.success(_companyService.delete(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> Put(@PathVariable int id, @ModelAttribute RequestCompany company) {
        try {
            return _baseController.success(_companyService.put(id, company));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

}
