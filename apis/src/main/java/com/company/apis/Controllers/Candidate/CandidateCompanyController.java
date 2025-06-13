package com.company.apis.Controllers.Candidate;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.CompanyDTO;
import com.company.apis.Models.Request.RequestFollowCompany;
import com.company.apis.Services.CompanyService;
import com.company.apis.Utils.HttpException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate/company")
public class CandidateCompanyController {
    @Autowired
    CompanyService _companyService;
    BaseController<CompanyDTO> _baseController = new BaseController<CompanyDTO>();
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<List<CompanyDTO>> _baseControllers = new BaseController<List<CompanyDTO>>();

    @PostMapping("/save")
    public ResponseEntity<?> post(@RequestBody RequestFollowCompany requestCompany) {
        try {
            return _baseController_string.success(_companyService.followCompany(requestCompany));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    // @GetMapping()
    // public ResponseEntity<?> get() {
    // try {
    // return _baseControllers.success(_companyService.getAll());
    // } catch (HttpException e) {
    // return _baseControllers.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseControllers.error(null, 500, e.getMessage());
    // }
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> get(@PathVariable int id) {
    // try {
    // return _baseController.success(_companyService.getById(id));
    // } catch (HttpException e) {
    // return _baseController.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseController.error(null, 500, e.getMessage());
    // }
    // }

    // @GetMapping("/contract")
    // public ResponseEntity<?> companyContract() {
    // try {
    // return _baseControllers.success(_companyService.companyContractAll());
    // } catch (HttpException e) {
    // return _baseControllers.error(null, e.StatusCode, e.message);
    // } catch (Exception e) {
    // return _baseControllers.error(null, 500, e.getMessage());
    // }
    // }

}
