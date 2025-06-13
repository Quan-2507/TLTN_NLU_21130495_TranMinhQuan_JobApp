package com.company.apis.Services;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.apis.Models.DTO.CVDTO;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.CurriculumVitae;
import com.company.apis.Models.Map.CVMapping;
import com.company.apis.Models.Request.RequestCV;
import com.company.apis.Repository.AccountRepo;
import com.company.apis.Repository.CVRepo;
import com.company.apis.Services.IServices.ICVService;
import com.company.apis.Utils.CurrentAccount;
import com.company.apis.Utils.Pagination;
import com.company.apis.Utils.Variable;

@Service
public class CVService implements ICVService {

    @Autowired
    CVRepo _cvRepo;
    @Autowired
    AccountRepo _accountRepo;

    @Autowired
    CloudinaryService _cloudinaryService;

    @Autowired
    CurrentAccount _currentAccount;

    Pagination<CVDTO> pagination = new Pagination<>();

    @Override
    public String create(RequestCV requestCV) {
        CurriculumVitae CV = new CurriculumVitae();
        Account account = _currentAccount.getAccount();
        boolean checkAccountNotFound = account == null || account.getDeleted_at() != null;
        if (checkAccountNotFound) {
            throw Variable.ACCOUNT_NOT_FOUND;
        }
        if (requestCV.getFile() != null) {
            try {
                // create image in cloudinary
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadCV(requestCV.getFile(), requestCV.getFile().toString());
                CV.setFile_name(check.get("url").toString());
                CV.setAccount(account);
                CV.setName(requestCV.getName());
                _cvRepo.save(CV);
                return "Success";
            } catch (IOException e) {
                throw Variable.ACTION_FAIL;
            }
        }
        throw Variable.ACTION_FAIL;

    }

    @Override
    public String delete(int id) {
        CurriculumVitae CV = _cvRepo.findById(id).get();
        boolean checkExisting = CV == null || CV.getDeleted_at() != null;
        if (checkExisting) {
            throw Variable.NOT_FOUND;
        }
        if (CV.getApplications().size() > 0) {
            CV.setDeleted_at(new Date());
            _cvRepo.save(CV);
            return "Success";
        }
        CV.setDeleted_at(new Date());
        _cvRepo.delete(CV);
        return "Success";
    }

    @Override
    public CVDTO getById(int id) {
        CurriculumVitae CV = _cvRepo.findById(id).get();
        boolean checkExisting = CV == null || CV.getDeleted_at() != null;
        if (checkExisting) {
            throw Variable.NOT_FOUND;
        }
        return CVMapping.CVDTO(CV);
    }

    @Override
    public List<CVDTO> getByAccount(int size, int page) {
        Account account = _currentAccount.getAccount();
        if (account == null) {
            throw Variable.ACCOUNT_NOT_FOUND;
        }
        List<CurriculumVitae> CVs = _cvRepo.findByAccount(account);
        List<CVDTO> cvdto = new ArrayList<>();
        for (CurriculumVitae CV : CVs) {
            if (CV.getDeleted_at() == null) {
                cvdto.add(CVMapping.CVDTO(CV));
            }
        }
        return pagination.pagination(size, page, cvdto);
    }

}
