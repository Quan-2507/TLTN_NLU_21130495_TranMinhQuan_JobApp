package com.company.apis.Controllers.Employer;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.DTO.ApplicationDTO;
import com.company.apis.Models.DTO.ChartForEmployer;
import com.company.apis.Services.ApplicationService;
import com.company.apis.Services.CompanyService;
import com.company.apis.Utils.HttpException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    @Autowired
    CompanyService _companyService;
    @Autowired
    ApplicationService _appplicationService;
    BaseController<ChartForEmployer> _baseController = new BaseController<ChartForEmployer>();
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<List<ApplicationDTO>> _baseControllers = new BaseController<List<ApplicationDTO>>();
    BaseController<List<AccountDTO>> _baseControllers_account = new BaseController<List<AccountDTO>>();

    @GetMapping("/chart/{id}")
    public ResponseEntity<?> getCompanyChart(@PathVariable int id) {
        try {
            return _baseController.success(_companyService.chartByCompany(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/application_by_job")
    public ResponseEntity<?> getApplicationByJob(@RequestParam int job_id, @RequestParam int size,
            @RequestParam int page) {
        try {
            return _baseControllers.success(_appplicationService.getByJob(job_id, size, page));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
    @GetMapping("/candidate_followed_by_company")
    public ResponseEntity<?> getCandidateFollowedByCompany ( @RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers_account.success(_companyService.getCandidateFollowedByCompany(size, page));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/send_mail")
    public ResponseEntity<?> sendMailToCandidate(@RequestParam int application_id, @RequestParam int size,
            @RequestParam int page) {
        try {
            return _baseController_string.success(_appplicationService.sendMailToCandidate(application_id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
}
