package com.company.apis.Services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.apis.Models.DTO.ApplicationDTO;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Application;
import com.company.apis.Models.Entity.Company;
import com.company.apis.Models.Entity.Jobs;
import com.company.apis.Models.Entity.SaveCV;
import com.company.apis.Models.Map.AccountMapping;
import com.company.apis.Models.Map.CVMapping;
import com.company.apis.Repository.AccountRepo;
import com.company.apis.Repository.ApplicationRepo;
import com.company.apis.Repository.JobRepo;
import com.company.apis.Repository.SaveCvRepo;
import com.company.apis.Services.IServices.IApplicationService;
import com.company.apis.Utils.CurrentAccount;
import com.company.apis.Utils.Pagination;
import com.company.apis.Utils.Variable;

@Service
public class ApplicationService implements IApplicationService {

    @Autowired
    ApplicationRepo _applicationRepo;
    @Autowired
    AccountRepo _accountRepo;
    @Autowired
    SaveCvRepo _saveCvRepo;

    CloudinaryService _cloudinaryService;

    @Autowired
    JobRepo _jobRepo;

    @Autowired
    CurrentAccount _currentAccount;

    @Autowired
    MailService _mailService;

    Pagination<ApplicationDTO> pagination = new Pagination<>();

    @Override
    public List<ApplicationDTO> getByJob(int job_id, int size, int page) {
        // STEP 1: get data job
        Jobs job = _jobRepo.findJobId(job_id);
        if (job == null || job.getDeleted_at() != null) {
            // check job
            // job not found
            throw Variable.ACTION_FAIL;
        }
        List<ApplicationDTO> listApplications = new ArrayList<>();
        for (Application application : job.getApplications()) {
            if (application.getDeleted_at() != null) {
                continue;
            }
            // STEP 2: set data to dto
            ApplicationDTO applicationDTO = new ApplicationDTO();
            applicationDTO.setAccount(AccountMapping.accountDTO(application.getAccount()));
            applicationDTO.setId(application.getId());
            applicationDTO.setNote(application.getNote());
            applicationDTO.setCv(CVMapping.CVDTO(application.getCurriculumVitae()));
            SaveCV saveCV = _saveCvRepo.findByCompanyAndCv(job.getCompany(), application);
            if (saveCV != null) {
                applicationDTO.setCv_is_save(true);
            }
            listApplications.add(applicationDTO);
        }
        return pagination.pagination(size, page, listApplications);

    }

    public String sendMailToCandidate(int application_id) {
        Account account = _currentAccount.getAccount();
        if (account == null || account.getDeleted_at() != null) {
            throw Variable.ACCOUNT_NOT_FOUND;
        }
        for (Company c : account.getCompanies()) {
            if (c.getDeleted_at() == null) {
                @SuppressWarnings("deprecation")
                Application application = _applicationRepo.getOne(application_id);
                _mailService.SendMailForApplication(application.getAccount().getEmail(), c, application.getJobs());
                _applicationRepo.save(application);
                return "Success!!";
            }
        }
        throw Variable.ACTION_FAIL;

    }
}
