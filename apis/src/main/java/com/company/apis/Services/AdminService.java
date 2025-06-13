package com.company.apis.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.stereotype.Service;

import com.company.apis.Enums.Role;
import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.DTO.ChartForAdmin;
import com.company.apis.Models.DTO.CompanyDTO;
import com.company.apis.Models.DTO.JobDTO;
import com.company.apis.Models.DTO.LevelDTO;
import com.company.apis.Models.DTO.SearchAll;
import com.company.apis.Models.DTO.SkillDTO;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.Company;
import com.company.apis.Models.Entity.FollowCompany;
import com.company.apis.Models.Entity.Jobs;
import com.company.apis.Models.Entity.LevelJob;
import com.company.apis.Models.Entity.Skill;
import com.company.apis.Models.Entity.SkillCompany;
import com.company.apis.Models.Entity.SkillJob;
import com.company.apis.Models.Entity.SubcriptionPlan;
import com.company.apis.Models.Entity.SubcriptionPlanCompany;
import com.company.apis.Models.Map.AccountMapping;
import com.company.apis.Models.Map.CompanyMapping;
import com.company.apis.Models.Map.JobMapping;
import com.company.apis.Models.Map.LevelMapping;
import com.company.apis.Models.Map.SkillMapping;
import com.company.apis.Repository.AccountRepo;
import com.company.apis.Repository.ApplicationRepo;
import com.company.apis.Repository.CompanyRepo;
import com.company.apis.Repository.FollowCompanyRepo;
import com.company.apis.Repository.FollowJobRepo;
import com.company.apis.Repository.JobRepo;
import com.company.apis.Repository.SkillRepo;
import com.company.apis.Repository.SubcriptionPlanRepo;
import com.company.apis.Services.IServices.IAdminService;
import com.company.apis.Utils.Convert;
import com.company.apis.Utils.CurrentAccount;
import com.company.apis.Utils.HandleDate;

@Service
public class AdminService implements IAdminService {

    // service
    @Autowired
    AccountRepo _accountRepo;

    @Autowired
    CompanyRepo _companyRepo;
    @Autowired
    FollowCompanyRepo _followCompanyRepo;
    @Autowired
    JobRepo _jobRepo;
    @Autowired
    ApplicationRepo _applicationRepo;
    @Autowired
    FollowJobRepo _followJobRepo;
    @Autowired
    SkillRepo _skillRepo;
    @Autowired
    SubcriptionPlanRepo _subcriptionPlanRepo;

    // utils
    @Autowired
    CurrentAccount _currentAccount;
    Convert convert = new Convert();
    HandleDate handleDate = new HandleDate();

    @Override
    public SearchAll searchByNameForAdmin(String name) {
        // Get maximum data <3 for web admin
        // get 2 company, 2 account, 2 job
        SearchAll searchAll = new SearchAll();
        // STEP 1: get data account containing by name,
        List<Account> accounts = _accountRepo.findAccountByNameContaining(name);
        List<AccountDTO> accountDTOs = new ArrayList<>();
        if (accounts != null) {
            int accountSize = accounts.size();
            // get a maximum of 2 accounts
            if (accountSize <= 2) {
                for (Account account : accounts) {
                    accountDTOs.add(AccountMapping.accountDTO(account));
                }
            } else if (accountSize > 2) {
                for (int i = 0; i < 2; i++) {
                    accountDTOs.add(AccountMapping.accountDTO(accounts.get(i)));
                }
            }
            // add data account to dto
            searchAll.setAccounts(accountDTOs);
        }
        // STEP 2: handle company: get data by name(title)
        List<Company> companies = _companyRepo.findCompanyByNameContaining(name);
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        if (companies != null) {
            int companiesize = companies.size();
            // Fetch a maximum of 2 companies
            if (companiesize <= 2) {
                for (Company company : companies) {
                    companyDTOs.add(CompanyMapping.CompanyDTO(company));
                }
            } else if (companiesize > 2) {
                for (int i = 0; i < 2; i++) {
                    companyDTOs.add(CompanyMapping.CompanyDTO(companies.get(i)));
                }
            }
            searchAll.setCompanies(companyDTOs);
        }

        // STEP 3: handle job: get data by name(title)
        List<Jobs> jobs = _jobRepo.findJobByTitleContaining(name);
        List<JobDTO> jobDTOs = new ArrayList<>();
        if (jobs != null) {
            int jobsize = jobs.size();
            // Fetch a maximum of 2 jobs
            if (jobsize <= 2) {
                for (Jobs job : jobs) {
                    jobDTOs.add(JobMapping.getJob(job));
                }
            } else if (jobsize > 2) {
                for (int i = 0; i < 2; i++) {
                    jobDTOs.add(JobMapping.getJob(jobs.get(i)));
                }
            }
            searchAll.setJobs(jobDTOs);
        }

        return searchAll;
    }

    @Override
    public SearchAll searchByNameForAll(String name) {
        // search for mobile (candidate)
        SearchAll searchAll = new SearchAll();
        // STEP 1 : Get data company
        List<Company> companies = _companyRepo.findCompanyByNameContaining(name);
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        if (companies != null) {
            for (Company company : companies) {
                if (company.getDeleted_at() != null || company.getEnable() == 0) {
                    continue;
                }
                if (searchAll.getCompanies() != null && searchAll.getCompanies().size() >= 30) {
                    continue;
                }
                boolean checkCompanyNotDeleted = company.getDeleted_at() == null;
                if (checkCompanyNotDeleted) {
                    // handle Image
                    String[] convertStringToArray = convert.convertStringToObject(company.getList_image());
                    List<String> list_image_mobile = new ArrayList<>();
                    if (convertStringToArray != null) {
                        for (String image : convertStringToArray) {
                            list_image_mobile.add(image);
                        }
                    }
                    CompanyDTO companyDTO = CompanyMapping.CompanyDTO(company);
                    List<JobDTO> jobs = new ArrayList<>();
                    // List<LocationDTO> lDtos = new ArrayList<>();
                    // for (Location l : company.getLocations()) {
                    // if (l.getDeleted_at() == null) {
                    // lDtos.add(LocationMapping.LocationDTO(l));
                    // }
                    // }
                    List<SkillDTO> skillDTOs = new ArrayList<>();
                    for (SkillCompany s : company.getSkillCompanies()) {
                        if (s.getSkill().getDeleted_at() == null) {
                            skillDTOs.add(SkillMapping.getSkill(s.getSkill()));
                        }
                    }
                    int count = 0;
                    for (Jobs j : company.getJobs()) {

                        if (j.is_active() == false) {
                            continue;
                        }
                        boolean checkJobNotDeleted = j.getDeleted_at() == null;
                        boolean checkDateJob = j.getStart_date().before(new Date())
                                && j.getEnd_date().after(new Date());
                        if (checkJobNotDeleted && checkDateJob) {
                            if (checkDateSubcriptionPlan(j)) {
                                continue;
                            }
                            JobDTO jobDTO = JobMapping.getJob(j);
                            jobDTO = setAppliedAndSaved(j, jobDTO);
                            jobDTO = setSkill_level(j, jobDTO);
                            count++;
                            jobs.add(jobDTO);

                        }
                    }
                    if (_currentAccount.getAccount() != null) {
                        FollowCompany followCompany = _followCompanyRepo
                                .findByAccountAndCompany(_currentAccount.getAccount(), company);
                        if (followCompany != null) {
                            companyDTO.setCompany_is_follow(true);
                        }
                    }
                    // STEP 3: set to dto
                    companyDTO.setOpening_jobs(count);
                    companyDTO.setJobs(jobs);
                    // companyDTO.setLocations(lDtos);
                    companyDTO.setSkills(skillDTOs);
                    companyDTOs.add(companyDTO);
                }
            }
            searchAll.setCompanies(companyDTOs);
        }

        Skill getOneSkill = _skillRepo.findSkillByNameContaining(name).size() > 0
                ? _skillRepo.findSkillByNameContaining(name).get(0)
                : null;
        if (getOneSkill != null) {

            for (SkillCompany sc : getOneSkill.getSkillCompanies()) {
                if (searchAll.getCompanies().size() >= 30) {
                    break;
                }
                if (sc.getDeleted_at() != null) {
                    continue;
                }
                boolean checkExist = false;
                for (CompanyDTO c : searchAll.getCompanies()) {
                    if (c.getId() == sc.getCompany().getId()) {
                        checkExist = true;
                    }
                }
                if (checkExist == false) {
                    if (sc.getCompany().getEnable() == 0) {
                        continue;
                    }
                    CompanyDTO companyDTO = CompanyMapping.CompanyDTO(sc.getCompany());
                    List<SkillDTO> skillDTOs = new ArrayList<>();
                    for (SkillCompany s : sc.getCompany().getSkillCompanies()) {
                        if (s.getSkill().getDeleted_at() == null) {
                            skillDTOs.add(SkillMapping.getSkill(s.getSkill()));
                        }
                    }
                    List<JobDTO> jobDTOs = new ArrayList<>();
                    int count = 0;
                    for (Jobs j : sc.getCompany().getJobs()) {

                        if (j.is_active() == false) {
                            continue;
                        }
                        boolean checkJobNotDeleted = j.getDeleted_at() == null;
                        boolean checkDateJob = j.getStart_date().before(new Date())
                                && j.getEnd_date().after(new Date());
                        if (checkJobNotDeleted && checkDateJob) {
                            if (checkDateSubcriptionPlan(j)) {
                                continue;
                            }
                            JobDTO jobDTO = JobMapping.getJob(j);
                            jobDTO = setAppliedAndSaved(j, jobDTO);
                            jobDTO = setSkill_level(j, jobDTO);
                            count++;
                            jobDTOs.add(jobDTO);

                        }
                    }
                    companyDTO.setJobs(jobDTOs);
                    companyDTO.setOpening_jobs(count);
                    companyDTO.setSkills(skillDTOs);
                    searchAll.getCompanies().add(companyDTO);
                }

            }
        }

        List<Jobs> jobs = _jobRepo.findJobByTitleContaining(name);
        List<JobDTO> jobDTOs = new ArrayList<>();
        if (jobs != null) {
            for (Jobs job : jobs) {
                if (checkDateSubcriptionPlan(job)) {
                    continue;
                }
                if (searchAll.getJobs() != null && searchAll.getJobs().size() >= 30) {
                    break;
                }
                if (job.is_active() == false) {
                    continue;
                }
                JobDTO jobDTO = JobMapping.getJob(job);
                List<SkillDTO> skillDTOs = new ArrayList<>();
                for (SkillJob s : job.getSkillJobs()) {
                    if (s.getSkills().getDeleted_at() == null) {
                        skillDTOs.add(SkillMapping.getSkill(s.getSkills()));
                    }
                }
                List<LevelDTO> levelDTOs = new ArrayList<>();
                for (LevelJob l : job.getLevelJobs()) {
                    if (l.getLevel().getDeleted_at() == null) {
                        levelDTOs.add(LevelMapping.levelDTO(l.getLevel()));
                    }
                }
                jobDTO.setSkills(skillDTOs);
                jobDTO.setLevels(levelDTOs);
                jobDTOs.add(jobDTO);
            }
            searchAll.setJobs(jobDTOs);
            if (getOneSkill != null) {
                for (SkillJob s : getOneSkill.getSkillJobs()) {
                    if (s.getDeleted_at() != null) {
                        continue;
                    }
                    boolean checkJobAdded = false;
                    for (JobDTO j : jobDTOs) {
                        if (s.getJobs().getId() == j.getId()) {
                            checkJobAdded = true;
                        }
                    }
                    if (checkJobAdded) {
                        continue;
                    }
                    if (checkDateSubcriptionPlan(s.getJobs())) {
                        continue;
                    }
                    if (searchAll.getJobs().size() >= 30) {
                        continue;
                    }

                    boolean checkExist = false;

                    for (JobDTO j : searchAll.getJobs()) {
                        if (j.getId() == s.getJobs().getId()) {
                            checkExist = true;
                        }
                    }
                    if (checkExist == false) {
                        if (s.getJobs().is_active() == false) {
                            continue;
                        }
                        JobDTO jobDTO = JobMapping.getJob(s.getJobs());

                        List<SkillDTO> skillDTOs = new ArrayList<>();
                        for (SkillJob sj : s.getJobs().getSkillJobs()) {
                            if (sj.getSkills().getDeleted_at() == null) {
                                skillDTOs.add(SkillMapping.getSkill(sj.getSkills()));
                            }
                        }
                        List<LevelDTO> levelDTOs = new ArrayList<>();
                        for (LevelJob l : s.getJobs().getLevelJobs()) {
                            if (l.getLevel().getDeleted_at() == null) {
                                levelDTOs.add(LevelMapping.levelDTO(l.getLevel()));
                            }
                        }
                        jobDTO.setSkills(skillDTOs);
                        jobDTO.setLevels(levelDTOs);
                        jobDTOs.add(jobDTO);
                    }
                }
            }

        }
        searchAll.setJobs(jobDTOs);
        searchAll.setQuantity(searchAll.getCompanies().size() + searchAll.getJobs().size());
        return searchAll;
    }

    public ChartForAdmin getChart() {
        // STEP 1: get data
        List<Company> companys = _companyRepo.findAll();
        List<Account> accounts = _accountRepo.findAll();

        ChartForAdmin chart = new ChartForAdmin();

        // detail notes in class dto ( Ghi chú chi tiết trong class dto)
        // STEP 2:create variable contain
        int jobs_has_been_created = 0;
        int account_has_been_created = 0;
        int companys_has_been_created = 0;
        Float overall_payment = (float) 0;
        int top_grossing_month = 0;

        List<Integer> listMonth = new ArrayList<>();
        // List<Integer> listApplicated = new ArrayList<>();
        // List<Integer> listViewed = new ArrayList<>();
        // List<Integer> listSaved = new ArrayList<>();
        List<Integer> listJobs = new ArrayList<>();
        List<Integer> listNumberSubcriptionPlan = new ArrayList<>();
        List<Float> listPrice = new ArrayList<>();
        List<List<Integer>> listNumberSubscriptionPlanByMonthAndBySubcription = new ArrayList<>();
        List<String> listScriptionPlan = new ArrayList<>();

        // List<CompanyDTO> top_3_company_by_application = new ArrayList<>();
        // List<CompanyDTO> top_3_company_by_save = new ArrayList<>();

        // get current year (2024) (sài nhiều chỗ)
        int currentYear = handleDate.getYear(new Date());
        int currentMonth = handleDate.getMonth(new Date());

        for (int i = 0; i < 12; i++) {
            // get by month 1, 2, 3, 4, 5, 6, .....; (lấy thông tin theo từng tháng)

            // Variable contain by month
            // int number_of_job_applicated = 0;
            // int number_of_job_saved = 0;
            // int number_of_job_viewed = 0;
            int number_jobs = 0;
            int number_of_subcription_plan = 0;

            Float price_for_subcription_plan = (float) 0;

            // STEP 3: handle data
            for (Company company : companys) {

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
                    boolean checkYearJob = currentYear >= yearStartJob || currentYear <= yearEndJob;
                    if (!checkYearJob) {
                        continue;
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
                    // for (Application app : job.getApplications()) {
                    // // get month applicated
                    // int monthApplicated = handleDate.getMonth(app.getCreated_at());
                    // // check month part 2
                    // boolean checkMonthApplicated = monthApplicated == i + 1;
                    // if (checkMonthApplicated) {
                    // number_of_job_applicated += 1;
                    // }
                    // }
                    // // get applicated by job
                    // for (ViewedJob viewed : job.getViewedJobs()) {
                    // // get month applicated
                    // int monthViewd = handleDate.getMonth(viewed.getCreated_at());
                    // // check month part 2
                    // boolean checkMonthViewed = monthViewd == i + 1;
                    // if (checkMonthViewed) {
                    // number_of_job_viewed += 1;
                    // }
                    // }
                    // // get applicated by job
                    // for (FollowJob saved : job.getFollowJobs()) {
                    // // get month applicated
                    // int monthSaved = handleDate.getMonth(saved.getCreated_at());
                    // // check month part 2
                    // boolean checkMonthSaved = monthSaved == i + 1;
                    // if (checkMonthSaved) {
                    // number_of_job_saved += 1;
                    // }
                    // }
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
                    number_of_subcription_plan += 1;

                }
            }
            // listApplicated.add(number_of_job_applicated);
            // listViewed.add(number_of_job_viewed);
            // listSaved.add(number_of_job_saved);
            listMonth.add(i + 1);
            listJobs.add(number_jobs);
            listPrice.add(price_for_subcription_plan);
            listNumberSubcriptionPlan.add(number_of_subcription_plan);
        }
        // STEP 4: set dto
        // thống kê theo từng tháng (là những thông tin ở giữa trong DTO)
        chart.setMonth(listMonth);
        // chart.setNumber_of_job_applicated(listApplicated);
        // chart.setNumber_of_job_saved(listSaved);
        // chart.setNumber_of_job_viewed(listViewed);
        chart.setJobs(listJobs);
        chart.setPrice_for_subcription_plan(listPrice);

        // STEP 5: get data total for admin
        for (Integer j : listJobs) {
            jobs_has_been_created += j;
        }
        for (Float p : listPrice) {
            overall_payment += p;
        }
        top_grossing_month = listMonth.get(0);
        for (int index = 1; index < listPrice.size(); index++) {
            if (listPrice.get(index - 1) < listPrice.get(index)) {
                top_grossing_month = listMonth.get(index);
            }
        }
        for (Company c : companys) {
            if (c.getDeleted_at() != null) {
                // deleted
                continue;
            }
            int YearCompany = handleDate.getYear(c.getCreated_at());
            if (YearCompany == currentYear) {
                companys_has_been_created += 1;
            }
        }
        for (Account a : accounts) {
            if (a.getDeleted_at() != null) {
                continue;
            }
            int YearCompany = handleDate.getYear(a.getCreated_at());
            if (YearCompany == currentYear) {
                account_has_been_created += 1;
            }
        }

        // STEP 6: get total data
        chart.setAccount_has_been_created(account_has_been_created);
        chart.setJobs_has_been_created(jobs_has_been_created);
        chart.setCompanys_has_been_created(companys_has_been_created);
        chart.setOverall_payment(overall_payment);
        chart.setTop_grossing_month(top_grossing_month);

        // STEP 7: get job highest by application and save job
        // HashMap<Company, Integer> getCompaniesByApplication = new HashMap<Company,
        // Integer>();
        // HashMap<Company, Integer> getCompaniesBySave = new HashMap<Company,
        // Integer>();
        // for (Company c : companys) {
        // int number_application_job_company = 0;
        // int number_save_job_company = 0;
        // if (c.getDeleted_at() != null) {
        // // deleted
        // continue;
        // }
        // for (Jobs j : c.getJobs()) {
        // if (j.getDeleted_at() != null) {
        // // deleted
        // continue;
        // }
        // // get application by jon
        // for (Application a : j.getApplications()) {
        // if (a.getDeleted_at() != null) {
        // // deleted
        // continue;
        // }
        // int yearApplication = handleDate.getYear(a.getCreated_at());
        // if (yearApplication != currentYear) {
        // continue;
        // }
        // number_application_job_company += 1;
        // }
        // // get save by jon

        // for (FollowJob f : j.getFollowJobs()) {
        // if (f.getDeleted_at() != null) {
        // // deleted
        // continue;
        // }
        // int yearApplication = handleDate.getYear(f.getCreated_at());
        // if (yearApplication != currentYear) {
        // continue;
        // }
        // number_save_job_company += 1;
        // }
        // }
        // // STEP 7.1 set data map<key, value> for sort
        // getCompaniesByApplication.put(c, number_application_job_company);
        // getCompaniesBySave.put(c, number_save_job_company);

        // }
        // if (getCompaniesByApplication.size() > 3) {
        // // STEP 7.2: sort
        // PriorityQueue<Map.Entry<Company, Integer>> pq = new PriorityQueue<>(
        // (e1, e2) -> e2.getValue() - e1.getValue());
        // pq.addAll(getCompaniesByApplication.entrySet());
        // if (pq.size() > 5) {
        // pq.poll();
        // }
        // while (!pq.isEmpty()) {
        // Map.Entry<Company, Integer> entry = pq.poll();
        // Company company = entry.getKey();
        // top_3_company_by_application.add(CompanyMapping.CompanyDTO(company));
        // }
        // // STEP 7.3 add 3 company have application heighest to chart
        // chart.setTop_3_company_by_application(top_3_company_by_application.subList(0,
        // 3));
        // }
        // if (getCompaniesBySave.size() > 3) {
        // // STEP 7.2: sort
        // PriorityQueue<Map.Entry<Company, Integer>> pq = new PriorityQueue<>(
        // (e1, e2) -> e2.getValue() - e1.getValue());
        // pq.addAll(getCompaniesBySave.entrySet());
        // if (pq.size() > 5) {
        // pq.poll();
        // }
        // while (!pq.isEmpty()) {
        // Map.Entry<Company, Integer> entry = pq.poll();
        // Company company = entry.getKey();
        // top_3_company_by_save.add(CompanyMapping.CompanyDTO(company));
        // }
        // // STEP 7.3: add 3 company have save heighest to chart
        // chart.setTop_3_company_by_save(top_3_company_by_save.subList(0, 3));
        // }
        // OK
        List<SubcriptionPlan> subcriptionPlans = _subcriptionPlanRepo.findAll();
        for (SubcriptionPlan sp : subcriptionPlans) {
            List<Integer> list_number_subcription_by_month = new ArrayList<>();
            if (sp.getDeleted_at() != null) {
                continue;
            }
            listScriptionPlan.add(sp.getName());

            for (int i = 1; i <= 12; i++) {
                int number_subcription_by_month = 0;

                for (SubcriptionPlanCompany spc : sp.getSubcritionPlanCompanies()) {
                    boolean checkYear = currentYear == handleDate.getYear(spc.getCreated_at());
                    boolean checkMonth = handleDate.getMonth(spc.getCreated_at()) == i;
                    if (checkYear && checkMonth) {
                        number_subcription_by_month += 1;
                    }
                }
                list_number_subcription_by_month.add(number_subcription_by_month);
            }
            listNumberSubscriptionPlanByMonthAndBySubcription.add(list_number_subcription_by_month);
        }
        chart.setName_subcription_plan(listScriptionPlan);
        chart.setArray_list_number_subcription_plan_by_month(listNumberSubscriptionPlanByMonthAndBySubcription);
        chart.setNumber_of_all_subcription_plan_by_month(listNumberSubcriptionPlan);
        return chart;
    }

    JobDTO setSkill_level(Jobs job, JobDTO jobDTO) {
        //
        List<SkillDTO> skillDTOs = new ArrayList<>();
        for (SkillJob s : job.getSkillJobs()) {
            if (s.getDeleted_at() == null) {
                skillDTOs.add(SkillMapping.getSkill(s.getSkills()));
            }
        }
        jobDTO.setSkills(skillDTOs);
        List<LevelDTO> levelDTOs = new ArrayList<>();
        for (LevelJob s : job.getLevelJobs()) {
            if (s.getDeleted_at() == null) {
                levelDTOs.add(LevelMapping.levelDTO(s.getLevel()));
            }
        }
        jobDTO.setLevels(levelDTOs);
        return jobDTO;
    }

    JobDTO setAppliedAndSaved(Jobs job, JobDTO jobDTO) {
        //
        Account account = _currentAccount.getAccount();
        if (account != null) {
            boolean applied = _applicationRepo.findByAccountAndJobs(account, job) != null;
            if (applied) {
                jobDTO.setJob_is_apply(true);
            }
            boolean saved = _followJobRepo.findByAccountAndJobs(account, job) != null;
            if (saved) {
                jobDTO.setJob_is_save(true);
            }
        }
        return jobDTO;
    }

    boolean checkDateSubcriptionPlan(Jobs job) {
        if (job == null) {
            return true;
        }
        Account currentAccount = _currentAccount.getAccount();
        if (currentAccount != null && currentAccount.getRole() == Role.ADMIN) {
            return false;
        }
        if (currentAccount != null && currentAccount.getRole() == Role.EMPLOYER) {
            return false;
        }

        if (currentAccount == null || currentAccount.getRole() == Role.CANDIDATE) {
            if ((job.getStart_date().after(new Date()) || job.getEnd_date().before(new Date()))) {
                return true;
            }
            if (job.getCompany().getEnable() == 0) {
                return true;
            }
            for (SubcriptionPlanCompany sp : job.getCompany().getSubcritionPlanCompanies()) {
                boolean checkDeleted = sp.getDeleted_at() != null;
                boolean checkDate = sp.getStart_date().before(new Date())
                        && sp.getEnd_date().after(new Date());
                if (checkDeleted || !checkDate) {
                    continue;
                }
                if (checkDate) {
                    return false;
                }
            }
        }
        return false;
    }
}
