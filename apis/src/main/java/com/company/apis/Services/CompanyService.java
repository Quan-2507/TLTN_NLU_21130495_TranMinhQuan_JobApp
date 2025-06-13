package com.company.apis.Services;

import com.company.apis.Enums.Role;
import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.DTO.ApplicationDTO;
import com.company.apis.Models.DTO.ChartForEmployer;
import com.company.apis.Models.DTO.CompanyDTO;
import com.company.apis.Models.DTO.JobDTO;
import com.company.apis.Models.DTO.SkillDTO;
import com.company.apis.Models.DTO.SubcriptionPlanDTO;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Application;
import com.company.apis.Models.Entity.CityProvince;
import com.company.apis.Models.Entity.Company;
import com.company.apis.Models.Entity.FollowCompany;
import com.company.apis.Models.Entity.FollowJob;
import com.company.apis.Models.Entity.Jobs;
import com.company.apis.Models.Entity.LevelJob;
import com.company.apis.Models.Entity.Skill;
import com.company.apis.Models.Entity.SkillCompany;
import com.company.apis.Models.Entity.SkillJob;
import com.company.apis.Models.Entity.SubcriptionPlanCompany;
import com.company.apis.Models.Entity.ViewedJob;
import com.company.apis.Models.Map.AccountMapping;
import com.company.apis.Models.Map.CompanyMapping;
import com.company.apis.Models.Map.JobMapping;
import com.company.apis.Models.Map.SkillMapping;
import com.company.apis.Models.Map.SubcriptionPlanCompanyMapping;
import com.company.apis.Models.Map.SubcriptionPlanMapping;
import com.company.apis.Models.Request.RequestCompany;
import com.company.apis.Models.Request.RequestCompanyRegister;
import com.company.apis.Models.Request.RequestFilterCompany;
import com.company.apis.Models.Request.RequestFollowCompany;
import com.company.apis.Repository.AccountRepo;
import com.company.apis.Repository.ApplicationRepo;
import com.company.apis.Repository.CityProvinceRepo;
import com.company.apis.Repository.CompanyRepo;
import com.company.apis.Repository.FollowCompanyRepo;
import com.company.apis.Repository.FollowJobRepo;
import com.company.apis.Repository.SkillCompanyRepo;
import com.company.apis.Repository.SkillRepo;
import com.company.apis.Repository.UserRepo;
import com.company.apis.Services.IServices.ICompanyService;
import com.company.apis.Utils.Convert;
import com.company.apis.Utils.CurrentAccount;
import com.company.apis.Utils.HandleDate;
import com.company.apis.Utils.Pagination;
import com.company.apis.Utils.Regex;
import com.company.apis.Utils.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
public class CompanyService implements ICompanyService {
    // call Repository (handle with database)
    @Autowired
    CompanyRepo _companyRepo;
    @Autowired
    AccountRepo _accountRepo;
    @Autowired
    UserRepo _userRepo;
    @Autowired
    AuthenticationManager _authenticationManager;
    @Autowired
    FollowCompanyRepo _followCompanyRepo;
    @Autowired
    ApplicationRepo _applicationRepo;
    @Autowired
    FollowJobRepo _followJobRepo;
    @Autowired
    CityProvinceRepo _cityProvinceRepo;
    @Autowired
    SkillRepo _skillRepo;
    @Autowired
    SkillCompanyRepo _SkillCompanyRepo;
    // call service
    @Autowired
    CloudinaryService _cloudinaryService;
    @Autowired
    PasswordEncoder _passwordEncoder;

    // call util
    Pagination<CompanyDTO> pagination = new Pagination<CompanyDTO>();
    Pagination<AccountDTO> pagination_acc = new Pagination<AccountDTO>();

    Convert convert = new Convert();
    HandleDate handleDate = new HandleDate();
    @Autowired
    CurrentAccount _currentAccount;

    // for method get
    @Override
    public List<CompanyDTO> getAll(int size, int page) {
        List<Company> companies = _companyRepo.findAll();
        return pagination.pagination(size, page, companyDTOMapping(companies));
    }

    // public List<CompanyDTO> getCompanyPending() {
    // List<CompanyDTO> companyDTOs = new ArrayList<>();
    // List<CompanyPendingApproval> companyPendingApproval =
    // _companyPendingApprovalRepo.findAll();
    // for (CompanyPendingApproval cp : companyPendingApproval) {
    // if (cp.getDeleted_at() == null) {
    // CompanyDTO companyDTO = new CompanyDTO();
    // companyDTO.setAccount(AccountMapping.accountDTO(_accountRepo.findById(cp.getAccount_id()).get()));
    // companyDTO.setName(cp.getName());
    // companyDTO.setIntroduction(cp.getIntroduction());
    // companyDTO.setLogo_image(cp.getLogo_image());
    // companyDTO.setBenefit(cp.getBenefit());
    // companyDTO.setLink_website(cp.getLink_website());
    // companyDTO.setNationnality(cp.getNationnality());
    // companyDTO.setId(cp.getId());
    // companyDTOs.add(companyDTO);
    // }
    // }
    // return companyDTOs;
    // }

    @Override
    public CompanyDTO getById(int id) {
        // STEP 1:
        // get data in repository(database)

        Account account = _currentAccount.getAccount();
        Company company = _companyRepo.findCompanyById(id);

        // check company
        boolean checkCompanyNotFound = (company != null && company.getDeleted_at() == null) ? false : true;
        if (checkCompanyNotFound || company == null) {
            throw Variable.NOT_FOUND;
        }

        // STEP 2: set data dto child
        CompanyDTO companyDTO = CompanyMapping.CompanyDTO(company);
        // handle string image to list image
        String[] convertStringToArray = convert.convertStringToObject(company.getList_image());
        List<String> list_image_mobile = new ArrayList<>();
        if (convertStringToArray != null) {
            for (String image : convertStringToArray) {
                list_image_mobile.add(image);
            }
        }
        companyDTO.setList_image_mobile(list_image_mobile);

        // get location
        // List<LocationDTO> locationDTO = new ArrayList<>();
        // for (Location l : company.getLocations()) {
        // boolean checkLocationDeleted = l.getDeleted_at() != null;
        // if (checkLocationDeleted) {
        // continue;
        // }
        // locationDTO.add(LocationMapping.LocationDTO(l));
        // }
        // get skill
        List<SkillDTO> skillDTOs = new ArrayList<>();
        for (SkillCompany s : company.getSkillCompanies()) {
            boolean checkSkillDeleted = s.getSkill().getDeleted_at() != null;
            if (checkSkillDeleted) {
                continue;
            }
            skillDTOs.add(SkillMapping.getSkill(s.getSkill()));
        }

        if (_currentAccount.getAccount() != null) {
            FollowCompany followCompany = _followCompanyRepo
                    .findByAccountAndCompany(_currentAccount.getAccount(), company);
            if (followCompany != null) {
                companyDTO.setCompany_is_follow(true);
            }
        }
        List<JobDTO> jobDTOS = new ArrayList<>();
        int count = 0;
        // STEP 3: get job
        for (Jobs j : company.getJobs()) {

            Account currentAccount = _currentAccount.getAccount();

            if (currentAccount == null || currentAccount.getRole() == Role.CANDIDATE) {
                if (j.getCompany().getEnable() == 0) {
                    continue;
                }
            }
            boolean checkSubcriptionPlan = checkDateSubcriptionPlan(j.getCompany());
            if (!checkSubcriptionPlan) {
                continue;
            }
            boolean checkJobDeleted = j.getDeleted_at() != null;
            if (checkJobDeleted) {
                continue;
            }
            boolean checkDateJob = j.getStart_date().before(new Date())
                    && j.getEnd_date().after(new Date());
            JobDTO job = new JobDTO();
            if (checkDateJob) {
                job = JobMapping.getJob(j);

                // set job have save and applicatin ( yes or no) by account
                if (account != null) {
                    boolean applied = _applicationRepo.findByAccountAndJobs(account, j) != null;
                    if (applied) {
                        job.setJob_is_apply(true);
                    }
                    boolean saved = _followJobRepo.findByAccountAndJobs(account, j) != null;
                    if (saved) {
                        job.setJob_is_save(true);
                    }
                }
                if (_currentAccount.getAccount() == null || _currentAccount.getAccount().getRole() == Role.CANDIDATE) {
                    if (j.is_active() == false) {
                        continue;
                    }
                }
                count++;
                jobDTOS.add(job);
            }
        }
        // STEP 4: set final data for dto
        companyDTO.setOpening_jobs(count);
        companyDTO.setSkills(skillDTOs);
        // companyDTO.setLocations(locationDTO);
        companyDTO.setJobs(jobDTOS);
        return companyDTO;
    }

    @Override
    public List<CompanyDTO> companyContractAll(int size, int page) {
        // get all company contract with apis4 (we-> developer =)))))
        // STEP 1: get data
        List<Company> companies = _companyRepo.findCompanyByContract();
        List<CompanyDTO> companyDTOS = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            Account currentAccount = _currentAccount.getAccount();

            if (currentAccount == null || currentAccount.getRole() == Role.CANDIDATE) {
                if (companies.get(i).getEnable() == 0) {
                    continue;
                }
            }
            boolean checkNotDeleted = companies.get(i).getDeleted_at() == null;
            boolean checkContract = companies.get(i).getContract() == 1;
            if (checkNotDeleted && checkContract) {
                // STEP 2: map dto
                CompanyDTO companyDTO = CompanyMapping.CompanyDTO(companies.get(i));

                List<SkillDTO> skillDTOs = new ArrayList<>();
                for (SkillCompany s : companies.get(i).getSkillCompanies()) {
                    boolean checkSkillNotDeleted = s.getSkill().getDeleted_at() == null;
                    if (checkSkillNotDeleted) {
                        skillDTOs.add(SkillMapping.getSkill(s.getSkill()));
                    }
                }
                companyDTO.setSkills(skillDTOs);

                // handle Image
                String[] convertStringToArray = convert.convertStringToObject(companies.get(i).getList_image());
                List<String> list_image_mobile = new ArrayList<>();
                if (convertStringToArray != null) {
                    for (String image : convertStringToArray) {
                        list_image_mobile.add(image);
                    }
                }
                // STEP 3: set number job opening
                int count = 0;
                for (Jobs j : companies.get(i).getJobs()) {
                    boolean checkSubcriptionPlan = checkDateSubcriptionPlan(j.getCompany());
                    if (!checkSubcriptionPlan) {
                        continue;
                    }
                    boolean checkJobNotDeleted = j.getDeleted_at() == null;
                    boolean checkDateJob = j.getStart_date().before(new Date())
                            && j.getEnd_date().after(new Date());
                    if (checkJobNotDeleted && checkDateJob) {
                        count++;
                    }
                }
                companyDTO.setOpening_jobs(count);
                // STEP 4: add final data dto
                companyDTOS.add(companyDTO);
            }

        }
        return pagination.pagination(size, page, companyDTOS);
    }

    public List<CompanyDTO> companyApplicationMost(int size, int page) {
        // sort company by job have application most
        // STEP 1: get data;
        List<Company> companies = _companyRepo.findAll();
        List<CompanyDTO> companyDTOS = new ArrayList<>();
        if (companies.size() < 5) {
            // if company < 5 => return all;
            return pagination.pagination(size, page, companyDTOMapping(companies));
        }
        // company >5
        HashMap<Company, Integer> companiesWithCounts = new HashMap<Company, Integer>();
        for (int i = 0; i < companies.size(); i++) {
            int count = 0;
            boolean checkCompanyDeleted = companies.get(i).getDeleted_at() != null;
            if (!checkCompanyDeleted) {
                // get number all application of job by company
                for (Jobs job : companies.get(i).getJobs()) {
                    boolean checkJobDeleted = job.getDeleted_at() != null;
                    if (!checkJobDeleted) {
                        count += job.getApplications().size();
                    }
                }
                // STEP 2: set HashMap for sort :<<
                if (count != 0) {
                    companiesWithCounts.put(companies.get(i), count);
                }
            }

        }
        // STEP 3: sort data
        if (companiesWithCounts.size() > 5) {
            PriorityQueue<Map.Entry<Company, Integer>> pq = new PriorityQueue<>(
                    (e1, e2) -> e2.getValue() - e1.getValue());
            pq.addAll(companiesWithCounts.entrySet());
            if (pq.size() > 5) {
                pq.poll();
            }
            List<Company> listCompany = new ArrayList<>();

            while (!pq.isEmpty() && companyDTOS.size() < 5) {
                Map.Entry<Company, Integer> entry = pq.poll();
                Company company = entry.getKey();
                // STEP 4: add final data
                listCompany.add(company);
            }
            return pagination.pagination(size, page, companyDTOMapping(listCompany));
        }
        return pagination.pagination(size, page, companyDTOMapping(companies));

    }

    public List<CompanyDTO> CompanyByProvinceAndIndustry(RequestFilterCompany requestFilterCompany, int size,
            int page) {
        // STEP 1: get data
        List<Company> getCompanies = _companyRepo.findCompanyByIndustryAndProvinceCity();
        List<Company> companies = new ArrayList<>();
        int CASE = 0;
        // STEP 2: set case
        if (requestFilterCompany.province_city_id != 0 && requestFilterCompany.industry_id == 0) {
            CASE = 1;
        }
        if (requestFilterCompany.province_city_id == 0 && requestFilterCompany.industry_id != 0) {
            CASE = 2;
        }
        if (requestFilterCompany.industry_id != 0 && requestFilterCompany.province_city_id != 0) {
            CASE = 3;
        }
        if (requestFilterCompany.industry_id == 0 && requestFilterCompany.province_city_id == 0) {
            // get default
            List<CompanyDTO> companyDTOs = companyDTOMapping(getCompanies);
            return pagination.pagination(size, page, companyDTOs);
        }
        // STEP 3: handle logic
        switch (CASE) {
            case 1:
                // filter by province_city
                for (int i = 0; i < getCompanies.size(); i++) {
                    if (getCompanies.get(i).getDeleted_at() != null) {
                        continue;
                    }

                    boolean checkDeleted = getCompanies.get(i).getCityProvince().getDeleted_at() == null;
                    boolean checkCityProvinceTrue = requestFilterCompany.province_city_id == getCompanies.get(i)
                            .getCityProvince().getId();
                    if (checkDeleted && checkCityProvinceTrue) {
                        companies.add(getCompanies.get(i));
                    }
                }
                return pagination.pagination(size, page, companyDTOMapping(companies));
            case 2:
                // filter by industry
                for (int i = 0; i < getCompanies.size(); i++) {
                    if (getCompanies.get(i).getDeleted_at() != null) {
                        continue;
                    }
                    for (SkillCompany s : getCompanies.get(i).getSkillCompanies()) {
                        boolean checkIndustryTrue = requestFilterCompany.industry_id == s.getSkill().getIndustry()
                                .getId();
                        boolean checkIndustryNotDeleted = s.getSkill().getDeleted_at() == null
                                && s.getSkill().getIndustry().getDeleted_at() == null;
                        if (checkIndustryNotDeleted && checkIndustryTrue) {
                            companies.add(getCompanies.get(i));
                            break;
                        }
                    }
                }
                return pagination.pagination(size, page, companyDTOMapping(companies));
            case 3:
                // filter by industry and province_city
                for (int i = 0; i < getCompanies.size(); i++) {
                    if (getCompanies.get(i).getDeleted_at() != null) {
                        continue;
                    }
                    // get company by province_city for check Industry (step next)
                    boolean checkIndustryConstainId = false;
                    boolean checkProvince = requestFilterCompany.province_city_id == getCompanies.get(i)
                            .getCityProvince()
                            .getId();
                    if (checkProvince && getCompanies.get(i).getCityProvince().getDeleted_at() == null) {
                        checkIndustryConstainId = true;
                    }
                    // check by industry
                    for (SkillCompany s : getCompanies.get(i).getSkillCompanies()) {
                        // check company have industry and have province_city => add
                        boolean checkLocation = requestFilterCompany.industry_id == s.getSkill().getIndustry().getId();
                        if (checkLocation && s.getSkill().getDeleted_at() == null
                                && s.getSkill().getIndustry().getDeleted_at() == null
                                && checkIndustryConstainId == true) {
                            companies.add(getCompanies.get(i));
                            break;
                        }
                    }
                }
                return pagination.pagination(size, page, companyDTOMapping(companies));
            default:
                // different
                return pagination.pagination(size, page, companyDTOMapping(getCompanies));
        }

    }

    public ChartForEmployer chartByCompany(int id) {
        Account account = _accountRepo.getAccountById(id);
        if (account == null) {
            throw Variable.ACCOUNT_NOT_FOUND;
        }
        // STEP 1: get data
        Company company = _companyRepo.findOneCompanyWithAccount(account);
        if (company == null || company.getDeleted_at() != null) {
            throw Variable.COMPANY_NOT_FOUND;
        }

        ChartForEmployer chart = new ChartForEmployer();

        // detail notes in class dto ( Ghi chú chi tiết trong class dto)
        // STEP 2:create variable contain
        int jobs_has_been_created = 0;
        int opening_job = 0;
        Float overall_payment = (float) 0;

        List<Integer> listMonth = new ArrayList<>();
        List<Integer> listApplicated = new ArrayList<>();
        List<Integer> listViewed = new ArrayList<>();
        List<Integer> listSaved = new ArrayList<>();
        List<Integer> listJobs = new ArrayList<>();
        List<Float> listPrice = new ArrayList<>();
        for (Jobs job : company.getJobs()) {
            boolean checkDeleted = job.getDeleted_at() != null;
            if (checkDeleted) {
                continue;
            }
            boolean checkOpening = (job.getStart_date().before(new Date())
                    && job.getEnd_date().after(new Date()));
            if (checkOpening) {
                opening_job += 1;
            }
        }
        chart.setOpening_jobs(opening_job);
        // get current year (2024) (sài nhiều chỗ)
        int currentYear = handleDate.getYear(new Date());
        for (int i = 0; i < 12; i++) {
            // get by month 1, 2, 3, 4, 5, 6, .....; (lấy thông tin theo từng tháng)

            if (company == null) {
                continue;
            }
            // Variable contain by month
            int number_of_job_applicated = 0;
            int number_of_job_saved = 0;
            int number_of_job_viewed = 0;
            int number_jobs = 0;
            Float price_for_subcription_plan = (float) 0;

            // STEP 3: handle data
            for (Jobs job : company.getJobs()) {
                // count job
                if (handleDate.getYear(job.getCreated_at()) == currentYear
                        && handleDate.getMonth(job.getCreated_at()) == i + 1) {
                    number_jobs += 1;
                }
                // get year start of job
                int yearStartJob = handleDate.getYear(job.getStart_date());
                // get year end of job
                int yearEndJob = handleDate.getYear(job.getEnd_date());
                boolean checkYearJob = currentYear >= yearStartJob && currentYear <= yearEndJob;
                if (!checkYearJob) {
                    continue;
                }
                // get applicated by job
                for (Application app : job.getApplications()) {
                    // get month applicated
                    int monthApplicated = handleDate.getMonth(app.getCreated_at());
                    // check month part 2
                    boolean checkMonthApplicated = monthApplicated == (i + 1) ? true : false;
                    if (checkMonthApplicated) {
                        number_of_job_applicated += 1;
                    }
                }
                // get month start date
                int month_start = handleDate.getMonth(job.getStart_date());
                // get month end date
                int month_end = handleDate.getMonth(job.getEnd_date());
                // check month of job == i+1(month 1,2,3,4,5....) ---- get job by month;
                // check month part 1;
                boolean checkMonthJob = month_start <= i + 1 && month_end >= i + 1;
                if (!checkMonthJob) {
                    // if month of job(month start or month end) different month(i+1)
                    continue;
                }

                // get applicated by job
                for (ViewedJob viewed : job.getViewedJobs()) {
                    // get month applicated
                    int monthViewd = handleDate.getMonth(viewed.getCreated_at());
                    // check month part 2
                    boolean checkMonthViewed = monthViewd == i + 1;
                    if (checkMonthViewed) {
                        number_of_job_viewed += 1;
                    }
                }
                // get applicated by job
                for (FollowJob saved : job.getFollowJobs()) {
                    // get month applicated
                    int monthSaved = handleDate.getMonth(saved.getCreated_at());
                    // check month part 2
                    boolean checkMonthSaved = monthSaved == i + 1;
                    if (checkMonthSaved) {
                        number_of_job_saved += 1;
                    }
                }
            }

            for (SubcriptionPlanCompany spc : company.getSubcritionPlanCompanies()) {
                // get year subcription plan
                int yearSubcriptionPlanCompany = handleDate.getYear(spc.getCreated_at());
                boolean checkYear = currentYear == yearSubcriptionPlanCompany;
                if (!checkYear) {
                    continue;
                }
                // get month
                int month = handleDate.getMonth(spc.getCreated_at());
                boolean checkMonth = month == i + 1;
                if (!checkMonth) {
                    // if month of subcription plan different month(i+1)
                    continue;
                }
                price_for_subcription_plan += spc.getSubscription_plan().getPrice();

            }
            listApplicated.add(number_of_job_applicated);
            listViewed.add(number_of_job_viewed);
            listSaved.add(number_of_job_saved);
            listMonth.add(i + 1);
            listJobs.add(number_jobs);
            listPrice.add(price_for_subcription_plan);
        }
        // STEP 4: set dto
        // thống kê theo từng tháng (là những thông tin ở giữa trong DTO)
        chart.setMonth(listMonth);
        chart.setNumber_of_job_applicated(listApplicated);
        chart.setNumber_of_job_saved(listSaved);
        chart.setNumber_of_job_viewed(listViewed);
        chart.setJobs(listJobs);
        chart.setPrice_for_subcription_plan(listPrice);

        // STEP 5: get data total for admin
        for (Integer j : listJobs) {
            jobs_has_been_created += j;
        }
        for (Float p : listPrice) {
            overall_payment += p;
        }

        // STEP 7: get job highest by application and save job
        int total_applicated_by_month = 0;
        for (int i = 0; i < listApplicated.size(); i++) {
            if (handleDate.getMonth(new Date()) == i + 1) {
                total_applicated_by_month = listApplicated.get(i);

            }
        }

        chart.setTotal_applicated_by_month(total_applicated_by_month);
        chart.setJobs_has_been_created(jobs_has_been_created);
        chart.setOverall_payment(overall_payment);
        // OK
        return chart;
    }

    // for method post
    @Override
    public CompanyDTO create(RequestCompany requestCompany) {

        // STEP 1: get data
        Company company = CompanyMapping.Company(requestCompany);
        Account a = _accountRepo.findById(requestCompany.getAccount_id()).get();
        // check account
        if (a == null || a.getDeleted_at() != null) {
            throw Variable.ACCOUNT_NOT_FOUND;
        }
        // check company
        for (Company c : a.getCompanies()) {
            // check list company of account is deleted
            if (c.getDeleted_at() == null) {
                throw Variable.COMPANY_ACCOUNT_EXISTING;
            }
        }
        company.setAccount(a);
        // STEP 2: push image to cloudinary
        if (requestCompany.getBackground() != null) {
            try {
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestCompany.getBackground(),
                        company.getBackground_image());
                company.setBackground_image(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        if (requestCompany.getLogo() != null) {
            try {
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestCompany.getLogo(), company.getLogo_image());
                company.setLogo_image(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        company.setList_image(requestCompany.getList_image());
        company.setCount_job(-1);
        // STEP 3: save database
        CityProvince cityProvince = _cityProvinceRepo.findIdCityProvince(requestCompany.getCity_province_id());
        company.setCityProvince(cityProvince);
        _companyRepo.save(company);
        if (requestCompany.getSkill_id() != "") {
            // handle string skill -> array skill for create by ","
            String[] skillId = requestCompany.getSkill_id().split(",");
            for (String i : skillId) {
                Skill s = _skillRepo.findById(Integer.parseInt(i)).get();
                SkillCompany skillCompany = new SkillCompany(s, company);
                // save skillCompany (Intermediate table)
                _SkillCompanyRepo.save(skillCompany);
            }
        }
        return (CompanyDTO) CompanyMapping.CompanyDTO(company);
    }

    public String followCompany(RequestFollowCompany requestCompany) {
        // STEP 1: get data
        Account account = _currentAccount.getAccount();
        Company company = _companyRepo.findCompanyById(Integer.parseInt(requestCompany.getCompany_id()));
        // check account
        if (account == null || company == null) {
            throw Variable.ACTION_FAIL;
        }
        // STEP 2: get data if followed => destroy; if not followed => follow (save
        // company :)))
        FollowCompany followCompany = _followCompanyRepo.findByAccountAndCompany(account, company);
        if (followCompany != null) {
            _followCompanyRepo.delete(followCompany);
            return "Success";
        }
        _followCompanyRepo.save(new FollowCompany(0, company, account));
        return "Success";

    }

    public CompanyDTO registerCompany(RequestCompanyRegister requestCompanyRegister) {
        boolean checkEmail = Regex.regexEmail(requestCompanyRegister.getEmail());
        if (!checkEmail) {
            throw Variable.EMAIL_INVALID;
        }
        // STEP 1: get account
        Account account = _userRepo.getAccountByEmail(requestCompanyRegister.getEmail());
        if (account == null) {
            // account not found -> create
            Account newAccount = new Account();
            newAccount.setEmail(requestCompanyRegister.getEmail());
            newAccount.setPassword(_passwordEncoder.encode(requestCompanyRegister.getPassword()));
            newAccount.setRole(Role.EMPLOYER);
            _accountRepo.save(newAccount);
            account = _userRepo.getAccountByEmail(requestCompanyRegister.getEmail());
        }
        if (account.getCompanies() != null) {
            for (Company c : account.getCompanies()) {
                // STEP 2: check company existing
                if (c.getDeleted_at() == null) {
                    throw Variable.COMPANY_CONFLIG;
                }
            }
        }

        try {
            // STEP 3: check email and password
            _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestCompanyRegister.getEmail(),
                            requestCompanyRegister.getPassword()));
        } catch (Exception e) {
            // login fail;
            throw Variable.EMAIL_OR_PASSWORD_INCORRECT;
        }
        if (requestCompanyRegister.getCity_province_id() == 0) {
            throw Variable.ACTION_FAIL;
        }
        if (account.getRole() == Role.CANDIDATE) {
            account.setRole(Role.EMPLOYER);
            _accountRepo.save(account);
        }
        if (account.getRole() == Role.EMPLOYER) {
            if (account.getCompanies() != null) {
                for (Company c : account.getCompanies()) {
                    if (c.getDeleted_at() == null) {
                        throw Variable.COMPANY_ACCOUNT_EXISTING;
                    }
                }
            }
        }

        // STEP 4: add data from client to entity for save database
        Company company = new Company();
        // data not null
        company.setName(requestCompanyRegister.getName_company());
        if (requestCompanyRegister.getLogo() != null) {
            try {
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestCompanyRegister.getLogo(),
                        requestCompanyRegister.getLogo().getName());
                company.setLogo_image(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        if (requestCompanyRegister.getBackground() != null) {
            try {
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestCompanyRegister.getBackground(),
                        requestCompanyRegister.getBackground().getName());
                company.setBackground_image(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        company.setCityProvince(_cityProvinceRepo.findIdCityProvince(requestCompanyRegister.getCity_province_id()));
        // data can null
        company.setAccount(account);
        if (requestCompanyRegister.getIntroduction() != null) {
            company.setIntroduction(requestCompanyRegister.getIntroduction());
        }
        if (requestCompanyRegister.getBenefit() != null) {
            company.setBenefit(requestCompanyRegister.getBenefit());
        }
        if (requestCompanyRegister.getProfession() != null) {
            company.setProfession(requestCompanyRegister.getProfession());
        }
        if (requestCompanyRegister.getSize() != null) {
            company.setSize(requestCompanyRegister.getSize());
        }
        if (requestCompanyRegister.getLink_website() != null) {
            company.setLink_website(requestCompanyRegister.getLink_website());
        }
        if (requestCompanyRegister.getNationnality() != null) {
            company.setNationnality(requestCompanyRegister.getNationnality());
        }
        if (requestCompanyRegister.getLocation() != null) {
            company.setLocation(requestCompanyRegister.getLocation());
        }
        company.setList_image("");
        _companyRepo.save(company);

        // STEP 5: save
        return CompanyMapping.CompanyDTO(company);
    }

    // for method put
    @Override
    public CompanyDTO put(int id, RequestCompany requestCompany) {
        // STEP 1: get and check company not null
        Company getCompany = _companyRepo.findCompanyById(id);
        boolean checkCompanyNotFound = (getCompany != null && getCompany.getDeleted_at() == null) ? false : true;
        if (checkCompanyNotFound || getCompany == null) {
            // null => return
            throw Variable.NOT_FOUND;
        }
        // STEP 2: push image to cloudinary
        if (requestCompany.getBackground() != null) {
            try {
                // create image in cloudinary
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestCompany.getBackground(),
                        getCompany.getBackground_image());
                getCompany.setBackground_image(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        if (requestCompany.getLogo() != null) {
            try {
                // create image in cloudinary
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestCompany.getLogo(), getCompany.getLogo_image());
                getCompany.setLogo_image(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        // STEP 3: map request to company entity
        Company company = CompanyMapping.CompanyPut(requestCompany, getCompany);
        if (requestCompany.getAccount_id() != 0) {
            company.setAccount(_accountRepo.getAccountById(requestCompany.getAccount_id()));
        }
        company.setList_image(requestCompany.getList_image());
        company.setId(id);
        company.setCityProvince(_cityProvinceRepo.findIdCityProvince(requestCompany.getCity_province_id()));
        // STEP 4
        _companyRepo.save(company);

        List<SkillCompany> skillCompanies = _SkillCompanyRepo.findByCompany(company);
        for (SkillCompany i : skillCompanies) {
            _SkillCompanyRepo.delete(i);
        }
        if (requestCompany.getSkill_id() != null && requestCompany.getSkill_id() != "") {
            // handle string skill -> array skill for create by ","
            String[] skillId = requestCompany.getSkill_id().split(",");
            for (String i : skillId) {
                Skill s = _skillRepo.findById(Integer.parseInt(i)).get();
                SkillCompany skillCompany = new SkillCompany(s, company);
                // save skillCompany (Intermediate table)
                _SkillCompanyRepo.save(skillCompany);
            }
        }
        return (CompanyDTO) CompanyMapping.CompanyDTO(company);
    }

    // for method delete
    @Override
    public String delete(int id) {
        // STEP 1: get data and check
        Company company = _companyRepo.findCompanyById(id);
        boolean checkCompanyNotFound = (company != null && company.getDeleted_at() == null) ? false : true;
        if (checkCompanyNotFound || company == null) {
            throw Variable.NOT_FOUND;
        }
        // STEP 2: set deleted_at different null => finishe deleted
        company.setDeleted_at(new Date());
        _companyRepo.save(company);
        return "Success";
    }

    // handle mapping and data like set skill set location...
    // return number job by company
    List<CompanyDTO> companyDTOMapping(List<Company> companies) {
        List<CompanyDTO> companyDTOS = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            List<JobDTO> jobs = new ArrayList<>();
            if (companies.get(i).getDeleted_at() == null) {
                Account currentAccount = _currentAccount.getAccount();
                if (currentAccount == null || currentAccount.getRole() != Role.ADMIN) {
                    // boolean checkSubcriptionPlan = checkDateSubcriptionPlan(companies.get(i));
                    // if (!checkSubcriptionPlan) {
                    // continue;
                    // }
                }
                if (currentAccount == null || currentAccount.getRole() == Role.CANDIDATE) {
                    if (companies.get(i).getEnable() == 0) {
                        continue;
                    }
                }
                // STEP 1: map entity to dto for return
                CompanyDTO companyDTO = CompanyMapping.CompanyDTO(companies.get(i));
                if (currentAccount != null && currentAccount.getRole() == Role.ADMIN) {
                    for (SubcriptionPlanCompany spc : companies.get(i).getSubcritionPlanCompanies()) {
                        boolean checkNotDeleted = spc.getDeleted_at() == null;
                        boolean checkDate = spc.getStart_date().before(new Date())
                                && spc.getEnd_date().after(new Date());
                        if (checkNotDeleted && checkDate) {
                            SubcriptionPlanDTO sp = SubcriptionPlanMapping
                                    .subcriptionPlanDTO(spc.getSubscription_plan());
                            sp.setSubcriptionPlanCompany(SubcriptionPlanCompanyMapping.getSubcriptionPlanCompany(spc));
                            companyDTO.setSubcriptionPlan(sp);
                        }
                    }
                }
                //// STEP 2: handle and add dto (image, location, skill, count job, job, company
                //// is_save ?);
                // : handle string image to array path image.
                String[] convertStringToArray = convert.convertStringToObject(companies.get(i).getList_image());
                List<String> list_image_mobile = new ArrayList<>();
                if (convertStringToArray != null) {
                    for (String image : convertStringToArray) {
                        list_image_mobile.add(image);
                    }
                }

                companyDTO.setList_image_mobile(list_image_mobile);
                List<SkillDTO> skillDTOs = new ArrayList<>();
                for (SkillCompany s : companies.get(i).getSkillCompanies()) {
                    if (s.getSkill().getDeleted_at() == null) {
                        skillDTOs.add(SkillMapping.getSkill(s.getSkill()));
                    }
                }
                // List<LocationDTO> locationDTOs = new ArrayList<>();
                // for (Location l : companies.get(i).getLocations()) {
                // if (l.getDeleted_at() == null) {
                // locationDTOs.add(LocationMapping.LocationDTO(l));
                // }
                // }
                // companyDTO.setLocations(locationDTOs);
                companyDTO.setSkills(skillDTOs);
                // set count job opening for company and add all job opening
                int count = 0;
                for (Jobs j : companies.get(i).getJobs()) {
                    boolean checkSubcriptionPlan = checkDateSubcriptionPlan(companies.get(i));
                    if (!checkSubcriptionPlan) {
                        continue;
                    }
                    boolean checkJobNotDeleted = j.getDeleted_at() == null;
                    boolean checkDateJob = j.getStart_date().before(new Date())
                            && j.getEnd_date().after(new Date());
                    if (checkJobNotDeleted && checkDateJob) {
                        if (_currentAccount.getAccount() == null
                                || _currentAccount.getAccount().getRole() == Role.CANDIDATE) {
                            if (j.is_active() == false) {
                                continue;
                            }
                        }
                        count++;
                        jobs.add(JobMapping.getJob(j));
                    }
                }
                if (_currentAccount.getAccount() != null) {
                    FollowCompany followCompany = _followCompanyRepo
                            .findByAccountAndCompany(_currentAccount.getAccount(), companies.get(i));
                    if (followCompany != null) {
                        companyDTO.setCompany_is_follow(true);
                    }
                }
                // STEP 3: set to dto
                companyDTO.setOpening_jobs(count);
                companyDTO.setJobs(jobs);
                companyDTOS.add(companyDTO);
            }
        }

        // STEP 4: Sort by openingJob in descending order
        List<CompanyDTO> sortedCompanies = companyDTOS.stream()
                .sorted(Comparator.comparingInt(CompanyDTO::getOpening_jobs).reversed())
                .collect(Collectors.toList());
        return sortedCompanies;
    }

    boolean checkDateSubcriptionPlan(Company company) {
        for (SubcriptionPlanCompany sp : company.getSubcritionPlanCompanies()) {
            boolean checkNotDeleted = sp.getDeleted_at() == null;
            boolean checkDate = sp.getStart_date().before(new Date())
                    && sp.getEnd_date().after(new Date());
            if (checkNotDeleted && checkDate) {
                return true;
            }
        }
        return false;
    }

    public String editCompanyOnOrOff(int id) {
        Company company = _companyRepo.findCompanyById(id);
        if (company == null || company.getDeleted_at() != null) {
            throw Variable.COMPANY_NOT_FOUND;
        }
        company.setEnable(company.getEnable() == 1 ? 0 : 1);
        _companyRepo.save(company);
        return "Success!!";
    }

    public List<CompanyDTO> listCompanyFollowByAccount(int size, int page) {
        Account account = _currentAccount.getAccount();
        if (account == null || account.getDeleted_at() != null) {
            throw Variable.ACCOUNT_NOT_FOUND;
        }
        List<Company> companies = new ArrayList<>();
        List<FollowCompany> followCompany = _followCompanyRepo.findByAccount(account);
        for (FollowCompany fc : followCompany) {
            if (fc.getDeleted_at() == null) {
                companies.add(fc.getCompany());
            }
        }
        return pagination.pagination(size, page, companyDTOMapping(companies));
    }

    public List<AccountDTO> getCandidateFollowedByCompany(int size, int page) {
        Account account = _currentAccount.getAccount();
        if (account == null || account.getDeleted_at() != null) {
            throw Variable.ACCOUNT_NOT_FOUND;
        }
        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (Company c : account.getCompanies()) {
            if (c.getDeleted_at() != null) {
                continue;
            }
            for (FollowCompany fc : c.getFollowCompany()) {
                if (fc.getDeleted_at() != null) {
                    continue;
                }
                accountDTOs.add(AccountMapping.accountDTO(fc.getAccount()));
            }

        }
        return pagination_acc.pagination(size, page, accountDTOs);
    }
}
