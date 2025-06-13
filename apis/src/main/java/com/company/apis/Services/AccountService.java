package com.company.apis.Services;

import com.company.apis.Enums.Role;
import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.DTO.AccountDTOForEmployer;
import com.company.apis.Models.DTO.CompanyDTO;
import com.company.apis.Models.DTO.CompanyForEmployerDTO;
import com.company.apis.Models.DTO.JobDTO;
import com.company.apis.Models.DTO.SubcriptionPlanDTO;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Company;
import com.company.apis.Models.Entity.CurriculumVitae;
import com.company.apis.Models.Entity.Jobs;
import com.company.apis.Models.Entity.SubcriptionPlan;
import com.company.apis.Models.Entity.SubcriptionPlanCompany;
import com.company.apis.Models.Map.AccountMapping;
import com.company.apis.Models.Map.CompanyMapping;
import com.company.apis.Models.Map.JobMapping;
import com.company.apis.Models.Map.SubcriptionPlanMapping;
import com.company.apis.Models.Request.RequestAccount;
import com.company.apis.Repository.AccountRepo;
import com.company.apis.Repository.CompanyRepo;
import com.company.apis.Repository.UserRepo;
import com.company.apis.Services.IServices.IAccountService;
import com.company.apis.Utils.CurrentAccount;
import com.company.apis.Utils.Regex;
import com.company.apis.Utils.Variable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AccountService implements IAccountService {
    // call repository(database)
    @Autowired
    AccountRepo _accountRepo;
    @Autowired
    UserRepo _userRepo;
    @Autowired
    CompanyRepo _companyRepo;

    // call service
    @Autowired
    CloudinaryService _cloudinaryService;
    @Autowired
    JobService _jobService;

    // call utils
    @Autowired
    PasswordEncoder _passwordEncoder;
    @Autowired
    CurrentAccount _currentAccount;

    @PersistenceContext
    private EntityManager entityManager;

    public AccountService(PasswordEncoder _passwordEncoder) {
        this._passwordEncoder = _passwordEncoder;
    }


    @Transactional
    public Account updateAccount(Account account) {
        Account managedAccount = entityManager.merge(account);
        return _accountRepo.save(managedAccount);
    }

    @Override
    public List<AccountDTO> getAll() {
        List<Account> accounts = _accountRepo.findAll();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getDeleted_at() == null) {
                accountDTOS.add(AccountMapping.accountDTO(accounts.get(i)));
            }
        }
        return accountDTOS;
    }

    public List<AccountDTO> getEmployer() {
        List<Account> accounts = _accountRepo.findAllEmployer();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getDeleted_at() == null) {
                AccountDTO accountDTO = AccountMapping.accountDTO(accounts.get(i));
                for (Company c : accounts.get(i).getCompanies()) {
                    if (c.getDeleted_at() == null) {
                        accountDTO.setCompany(CompanyMapping.CompanyDTO(c));
                    }
                }
                accountDTOS.add(accountDTO);
            }
        }
        return accountDTOS;
    }

    public List<AccountDTO> getCandidate() {
        List<Account> accounts = _accountRepo.findAllCandidate();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getDeleted_at() == null) {
                accountDTOS.add(AccountMapping.accountDTO(accounts.get(i)));
            }
        }
        return accountDTOS;
    }

    @Override
    public AccountDTO create(RequestAccount requestAccount) {
        boolean checkEmail = Regex.regexEmail(requestAccount.getEmail());
        if (!checkEmail) {
            throw Variable.EMAIL_INVALID;
        }
        boolean checkPassword = Regex.regexPassword(requestAccount.getPassword());
        if (!checkPassword) {
            throw Variable.PASSWORD_INVALID;
        }
        Account getAccount = _userRepo.getAccountByEmail(requestAccount.getEmail());
        boolean checkMailExisting = getAccount != null;
        if (checkMailExisting) {
            if (getAccount.getDeleted_at() == null) {
                throw Variable.EMAIL_EXISTING;
            }
        }

        requestAccount.setPassword(_passwordEncoder.encode(requestAccount.getPassword()));
        Account account = AccountMapping.account(requestAccount);
        if (requestAccount.UploadFile != null) {
            try {
                // create image in cloudinary
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestAccount.UploadFile, account.getImage());
                account.setImage(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        _accountRepo.save(account);
        return (AccountDTO) AccountMapping.accountDTO(account);
    }

    @Override
    public AccountDTO put(int id, RequestAccount r) {
        boolean checkEmailValid = Regex.regexEmail(r.getEmail());
        if (!checkEmailValid) {
            throw Variable.EMAIL_INVALID;
        }
        boolean checkPasswordValid = Regex.regexPassword(r.getPassword());
        if (!checkPasswordValid) {
            throw Variable.PASSWORD_INVALID;
        }
        Account getAccount = _accountRepo.findIdAccount(id);
        boolean checkAccountNotFound = (getAccount != null && getAccount.getDeleted_at() == null) ? false : true;

        if (checkAccountNotFound) {
            throw Variable.NOT_FOUND;
        }
        r.setPassword(_passwordEncoder.encode(r.getPassword()));
        if (r.UploadFile != null) {
            try {
                // create image in cloudinary
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(r.UploadFile, getAccount.getImage());
                getAccount.setImage(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        Account account = AccountMapping.AccountPut(r, getAccount);
        account.setId(id);
        _accountRepo.save(account);
        return (AccountDTO) AccountMapping.accountDTO(account);
    }

    @Override
    public AccountDTO delete(int id) {
        Account account = _accountRepo.findIdAccount(id);
        boolean checkAccountNotFound = (account != null && account.getDeleted_at() == null) ? false : true;
        if (checkAccountNotFound) {
            throw Variable.NOT_FOUND;
        }
        if (account.getRole() == Role.EMPLOYER) {
            for (Company c : account.getCompanies()) {
                if (c.getDeleted_at() == null) {
                    c.setDeleted_at(new Date());
                    _companyRepo.save(c);
                    for (Jobs job : c.getJobs()) {
                        _jobService.delete(job.getId());
                    }
                }
            }
        }
        account.setEmail("apis4");
        account.setDeleted_at(new Date());
        _accountRepo.save(account);
        return AccountMapping.accountDTO(account);
    }

    @Override
    public AccountDTO getById(int id) {
        Account account = _accountRepo.findIdAccount(id);
        boolean checkAccountNotFound = (account != null && account.getDeleted_at() == null) ? false : true;
        if (checkAccountNotFound) {
            throw Variable.NOT_FOUND;
        }
        AccountDTO accountDTO = AccountMapping.accountDTO(account);
        // accountDTO.setCompany(acc);
        return accountDTO;
    }

    @Override
    public AccountDTO updateProfileByAccount(RequestAccount r) {
        Account getAccount = _currentAccount.getAccount();
        if (getAccount == null) {
            throw Variable.NOT_FOUND;
        }
        boolean checkEmailValid = Regex.regexEmail(r.getEmail());
        if (!checkEmailValid) {
            throw Variable.EMAIL_INVALID;
        }
        boolean checkPasswordValid = Regex.regexPassword(r.getPassword());
        if (!checkPasswordValid) {
            throw Variable.PASSWORD_INVALID;
        }
        boolean checkAccountNotFound = (getAccount != null && getAccount.getDeleted_at() == null) ? false : true;

        if (checkAccountNotFound) {
            throw Variable.NOT_FOUND;
        }

        if (r.UploadFile != null) {
            try {
                // create image in cloudinary
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(r.UploadFile, getAccount.getImage());
                getAccount.setImage(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        getAccount.setPassword(_passwordEncoder.encode(r.getPassword()));
        Account account = AccountMapping.AccountPut(r, getAccount);
        // account.setId(id);
        _accountRepo.save(account);
        return (AccountDTO) AccountMapping.accountDTO(account);
    }

    @Override
    public AccountDTOForEmployer getAccountCompanyJob(int id) {
        // contemporaneous get company, job, subcription plan by account
        // STEP 1: get and check not delete account
        Account account = new Account();
        if (id == 0) {
            account = _currentAccount.getAccount();
        } else {
            account = _accountRepo.getAccountById(id);
        }
        AccountDTOForEmployer accountDTO = new AccountDTOForEmployer();
        accountDTO = AccountMapping.accountDTOForEmployer(account);
        boolean checkAccountNotFound = (account != null && account.getDeleted_at() == null) ? false : true;
        if (checkAccountNotFound) {
            // check account
            throw Variable.NOT_FOUND;
        }
        // STEP 2: get and check company by account
        Company company = _companyRepo.findOneCompanyWithAccount(account);
        boolean checkCompanyNotExisting = company == null || company.getDeleted_at() != null;
        if (checkCompanyNotExisting) {
            return accountDTO;
        }
        CompanyForEmployerDTO companyDTO = CompanyMapping.CompanyForEmployerDTO(company);
        // STEP 3: handle job
        if (company.getJobs().size() > 0) {
            // (3 case : not start, starting , started)
            List<JobDTO> jobDTOsNotOpen = new ArrayList<>();
            List<JobDTO> jobDTOsOpening = new ArrayList<>();
            List<JobDTO> jobDTOsOpened = new ArrayList<>();

            for (Jobs job : company.getJobs()) {
                boolean checkJobDeleted = job.getDeleted_at() != null;
                if (checkJobDeleted) {
                    continue;
                }
                if (job.getStart_date().after(new Date())) {
                    jobDTOsNotOpen.add(JobMapping.getJob(job));
                } else if (job.getEnd_date().before(new Date())) {
                    jobDTOsOpened.add(JobMapping.getJob(job));
                } else {
                    jobDTOsOpening.add(JobMapping.getJob(job));
                }
            }
            // STEP 4: get subCription plane
            for (SubcriptionPlanCompany sub : company.getSubcritionPlanCompanies()) {
                boolean checkSubcritionplan = sub.getDeleted_at() == null
                        && (sub.getStart_date().before(new Date())
                                && sub.getEnd_date().after(new Date()));
                if (checkSubcritionplan) {
                    SubcriptionPlanDTO subDTO = SubcriptionPlanMapping.subcriptionPlanDTO(sub.getSubscription_plan());
                    subDTO.setEnd_date(sub.getEnd_date());
                    subDTO.setStart_date(sub.getStart_date());
                    companyDTO.setSubcriptionPlan(subDTO);
                    accountDTO.setLimit_job(sub.getSubscription_plan().getExpiry());

                }
            }

            // STEP 5: add data
            companyDTO.setJobsNotOpen(jobDTOsNotOpen);
            companyDTO.setJobsOpening(jobDTOsOpening);
            companyDTO.setJobsOpened(jobDTOsOpened);
            companyDTO.setOpening_jobs(jobDTOsOpening.size());

        }
        accountDTO.setCompanyForEmployer(companyDTO);
        accountDTO.setCount_jobs(company.getCount_job());

        return accountDTO;
    }

    public String putImage(MultipartFile image) {
        Account account = _currentAccount.getAccount();
        if (account == null) {
            throw Variable.ACCOUNT_NOT_FOUND;
        }
        if (image != null) {
            try {
                // create image in cloudinary
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(image, image.getName());
                account.setImage(check.get("url").toString());
                _accountRepo.save(account);
                return "Success";
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        return "Success";
    }

    public List<AccountDTO> getNonCompanyAccount() {
        List<Account> accounts = _accountRepo.findAllEmployer();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            boolean checkCompany = false;
            for (Company company : accounts.get(i).getCompanies()) {
                if (company.getDeleted_at() == null) {
                    checkCompany = true;
                }
            }
            if (accounts.get(i).getDeleted_at() == null && checkCompany == false) {
                accountDTOS.add(AccountMapping.accountDTO(accounts.get(i)));
            }
        }
        return accountDTOS;
    }
}
