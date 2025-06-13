package com.company.apis.Services;

import com.company.apis.Models.DTO.SubcriptionPlanDTO;
import com.company.apis.Models.DTO.SubcriptionPlanForEmployer;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Company;
import com.company.apis.Models.Entity.SubcriptionPlan;
import com.company.apis.Models.Entity.SubcriptionPlanCompany;
import com.company.apis.Models.Map.SubcriptionPlanMapping;
import com.company.apis.Models.Request.RequestSubcriptionPlan;
import com.company.apis.Repository.AccountRepo;
import com.company.apis.Repository.CompanyRepo;
import com.company.apis.Repository.SubcriptionPlanCompanyRepo;
import com.company.apis.Repository.SubcriptionPlanRepo;
import com.company.apis.Services.IServices.ISubcriptionPlanService;
import com.company.apis.Utils.CurrentAccount;
import com.company.apis.Utils.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class SubcriptionPlanService implements ISubcriptionPlanService {
    @Autowired
    SubcriptionPlanRepo _subcriptionPlanRepo;

    @Autowired
    CompanyRepo _companyRepo;

    @Autowired
    MailService _mailService;

    @Autowired
    SubcriptionPlanCompanyRepo _SubcriptionPlanCompanyRepo;

    @Autowired
    CurrentAccount _currentAccount;
    @Autowired
    AccountRepo _accountRepo;

    @Override
    public List<SubcriptionPlanDTO> getAll() {
        List<SubcriptionPlan> subcriptionPlans = _subcriptionPlanRepo.findAll();
        List<SubcriptionPlanDTO> subcriptionPlanDTOS = new ArrayList<>();
        for (int i = 0; i < subcriptionPlans.size(); i++) {
            if (subcriptionPlans.get(i).getDeleted_at() == null) {
                subcriptionPlanDTOS.add(SubcriptionPlanMapping.subcriptionPlanDTO(subcriptionPlans.get(i)));
            }
        }
        return subcriptionPlanDTOS;
    }

    @Override
    public SubcriptionPlanDTO create(RequestSubcriptionPlan requestSubcriptionPlan) {
        SubcriptionPlan subcriptionPlan = SubcriptionPlanMapping.SubcriptionPlan(requestSubcriptionPlan);
        _subcriptionPlanRepo.save(subcriptionPlan);
        return (SubcriptionPlanDTO) SubcriptionPlanMapping.subcriptionPlanDTO(subcriptionPlan);
    }

    @Override
    public SubcriptionPlanDTO put(int id, RequestSubcriptionPlan requestSubcriptionPlan) {
        SubcriptionPlan getSubcriptionPlan = _subcriptionPlanRepo.findIdBySubcriptionPlan(id);
        boolean checkSubcriptionPlanNotFound = (getSubcriptionPlan != null
                && getSubcriptionPlan.getDeleted_at() == null)
                        ? false
                        : true;
        if (checkSubcriptionPlanNotFound) {
            throw Variable.NOT_FOUND;
        }
        SubcriptionPlan subcriptionPlan = SubcriptionPlanMapping.SubcriptionPlanPut(requestSubcriptionPlan,
                getSubcriptionPlan);
        subcriptionPlan.setId(id);
        _subcriptionPlanRepo.save(subcriptionPlan);
        return (SubcriptionPlanDTO) SubcriptionPlanMapping.subcriptionPlanDTO(subcriptionPlan);
    }

    @Override
    public String delete(int id) {
        SubcriptionPlan subcriptionPlan = _subcriptionPlanRepo.findIdBySubcriptionPlan(id);
        boolean checkSubcriptionPlanNotFound = (subcriptionPlan != null && subcriptionPlan.getDeleted_at() == null)
                ? false
                : true;
        if (checkSubcriptionPlanNotFound) {
            throw Variable.NOT_FOUND;
        }
        if (subcriptionPlan.getSubcritionPlanCompanies().size() > 0) {
            throw Variable.ACTION_FAIL;
        }
        _subcriptionPlanRepo.delete(subcriptionPlan);
        return "Success";
    }

    @Override
    public SubcriptionPlanDTO getById(int id) {
        SubcriptionPlan subcriptionPlan = _subcriptionPlanRepo.findIdBySubcriptionPlan(id);
        boolean checkSubcriptionPlanNotFound = (subcriptionPlan != null && subcriptionPlan.getDeleted_at() == null)
                ? false
                : true;
        if (checkSubcriptionPlanNotFound) {
            throw Variable.NOT_FOUND;
        }
        SubcriptionPlanDTO subcriptionPlanDTO = SubcriptionPlanMapping.subcriptionPlanDTO(subcriptionPlan);
        return subcriptionPlanDTO;
    }

    public SubcriptionPlanForEmployer getByAccount(int id) {
        Account account = new Account();
        if (id == 0) {
            account = _currentAccount.getAccount();
        } else {
            account = _accountRepo.findIdAccount(id);
        }
        boolean checkAccountNotFound = (account != null && account.getDeleted_at() == null) ? false : true;
        if (account == null || checkAccountNotFound) {
            throw Variable.NOT_FOUND;
        }
        Company company = null;
        for (Company c : account.getCompanies()) {
            if (c.getDeleted_at() == null) {
                company = c;
            }
        }
        SubcriptionPlanForEmployer subcriptionPlan = new SubcriptionPlanForEmployer();
        if (company != null && company.getDeleted_at() == null) {
            List<SubcriptionPlanDTO> sbp = new ArrayList<>();
            for (SubcriptionPlanCompany sub : company.getSubcritionPlanCompanies()) {
                if (sub.getDeleted_at() == null) {
                    SubcriptionPlanDTO subcriptionPlanDTO = SubcriptionPlanMapping
                            .subcriptionPlanDTO(sub.getSubscription_plan());
                    subcriptionPlanDTO.setEnd_date(sub.getEnd_date());
                    subcriptionPlanDTO.setStart_date(sub.getStart_date());
                    boolean checkSubcritionplan = (sub.getStart_date().before(new Date())
                            && sub.getEnd_date().after(new Date()));
                    if (checkSubcritionplan) {
                        subcriptionPlan.setSubcriptionPlanDTO(subcriptionPlanDTO);
                    } else {
                        sbp.add(subcriptionPlanDTO);
                    }
                }

            }
            subcriptionPlan.setSubcriptionPlanDTOs(sbp);
        }
        return subcriptionPlan;
    }

    public void createForEmployer(int id_sub) {
        Account account = _currentAccount.getAccount();
        if (account == null) {
            throw Variable.ACTION_FAIL;
        }
        SubcriptionPlan subcriptionPlan = _subcriptionPlanRepo.findIdBySubcriptionPlan(id_sub);
        if (subcriptionPlan == null) {
            throw Variable.ACTION_FAIL;
        }
        Company company = _companyRepo.findOneCompanyWithAccount(account);
        for (SubcriptionPlanCompany sp : company.getSubcritionPlanCompanies()) {
            if (sp.getDeleted_at() != null) {
                continue;
            }
            boolean checkDate = sp.getStart_date().before(new Date()) && sp.getEnd_date().after(new Date());
            if (checkDate) {
                // if having subcription plan
                SubcriptionPlanCompany subcriptionPlanCompany = _SubcriptionPlanCompanyRepo.getOne(sp.getId());
                subcriptionPlanCompany.setEnd_date(new Date());
                _SubcriptionPlanCompanyRepo.save(subcriptionPlanCompany);
            }
        }
        if (company != null) {
            SubcriptionPlanCompany subcriptionPlanCompany = new SubcriptionPlanCompany();
            subcriptionPlanCompany.setCompany(company);
            subcriptionPlanCompany.setSubscription_plan(subcriptionPlan);
            subcriptionPlanCompany.setStart_date(new Date());
            Calendar calendar = new GregorianCalendar(/* remember about timezone! */);
            calendar.setTime(subcriptionPlanCompany.getStart_date());
            calendar.add(Calendar.DATE, subcriptionPlan.getExpiry());
            Date date = calendar.getTime();
            subcriptionPlanCompany.setEnd_date(date);
            _SubcriptionPlanCompanyRepo.save(subcriptionPlanCompany);
            company.setCount_job(0);
            _companyRepo.save(company);

            _mailService.SendMailForEmployer(account.getEmail(), subcriptionPlanCompany);

        }

    }

}
