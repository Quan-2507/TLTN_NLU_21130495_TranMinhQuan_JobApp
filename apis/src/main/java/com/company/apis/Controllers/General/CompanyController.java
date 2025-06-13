package com.company.apis.Controllers.General;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.CompanyDTO;
import com.company.apis.Models.Request.RequestCompanyRegister;
import com.company.apis.Models.Request.RequestFilterCompany;
import com.company.apis.Services.CompanyService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/company")
public class CompanyController {
    @Autowired
    CompanyService _companyService;
    BaseController<CompanyDTO> _baseController = new BaseController<CompanyDTO>();
    BaseController<List<CompanyDTO>> _baseControllers = new BaseController<List<CompanyDTO>>();

    @GetMapping()
    public ResponseEntity<?> get(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_companyService.getAll(size, page));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return _baseController.success(_companyService.getById(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/contract")
    public ResponseEntity<?> companyContract(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_companyService.companyContractAll(size, page));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/application_most")
    public ResponseEntity<?> companyApplicationMost(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_companyService.companyApplicationMost(size, page));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<?> companyByProvinceAndIndustry(@RequestBody RequestFilterCompany requestFilterCompany,
                                                          @RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers
                    .success(_companyService.CompanyByProvinceAndIndustry(requestFilterCompany, size, page));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> register(@ModelAttribute RequestCompanyRegister requestCompanyRegister) {
        try {
            return _baseController
                    .success(_companyService.registerCompany(requestCompanyRegister));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }
    @GetMapping("company_follow")
    public ResponseEntity<?> getCompanyFollowByAccount(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_companyService.listCompanyFollowByAccount(size, page));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

}