package com.company.apis.Services;

import com.company.apis.Enums.Role;
import com.company.apis.Models.DTO.JobDTO;
import com.company.apis.Models.DTO.LevelDTO;
import com.company.apis.Models.DTO.SkillDTO;
import com.company.apis.Models.Entity.*;
import com.company.apis.Models.Map.JobMapping;
import com.company.apis.Models.Map.LevelMapping;
import com.company.apis.Models.Map.SkillMapping;
import com.company.apis.Models.Request.RequestApplication;
import com.company.apis.Models.Request.RequestIntermediaryJob;
import com.company.apis.Models.Request.RequestJob;
import com.company.apis.Repository.*;
import com.company.apis.Services.IServices.IJobService;
import com.company.apis.Utils.CurrentAccount;
import com.company.apis.Utils.Pagination;
import com.company.apis.Utils.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JobService implements IJobService {
    // call repository (database)
    @Autowired
    JobRepo _jobRepo;
    @Autowired
    CompanyRepo _companyRepo;
    @Autowired
    JobTypeRepo _jobTypeRepo;
    @Autowired
    FollowJobRepo _followJobRepo;
    @Autowired
    ViewedJobRepo _ViewedJobRepo;
    @Autowired
    SkillRepo _skillRepo;
    @Autowired
    SkillJobRepo _skillJobRepo;
    @Autowired
    LevelRepo _levelRepo;
    @Autowired
    LevelJobRepo _levelJobRepo;
    @Autowired
    AccountRepo _accountRepo;
    @Autowired
    CVRepo _cvRepo;
    @Autowired
    ApplicationRepo _applicationRepo;
    @Autowired
    UserRepo _userRepo;
    @Autowired
    AuthenticationManager _authenticationManager;

    // call utils
    @Autowired
    CurrentAccount _currentAccount;
    Pagination<JobDTO> pagination = new Pagination<JobDTO>();

    // call service
    @Autowired
    JWTService _jwtservice;
    @Autowired
    MailService _mailService;

    // method for get
    @Override
    public List<JobDTO> getAll(int size, int page) {
        // STEP 1: get data
        List<Jobs> jobs = _jobRepo.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();
        for (int i = 0; i < jobs.size(); i++) {
            if (checkDateSubcriptionPlan(jobs.get(i))) {
                continue;
            }
            boolean checkJobNotDeleted = jobs.get(i).getDeleted_at() == null;
            if (checkJobNotDeleted) {
                boolean checkOpening = (jobs.get(i).getEnd_date()).after(new Date())
                        && jobs.get(i).getStart_date().before(new Date());
                if (!checkOpening) {
                    continue;
                }
                // STEP 2: map to dto
                JobDTO jobDTO = JobMapping.getJob(jobs.get(i));
                // STEP 3: call function check application and saved; set skill
                jobDTO = setAppliedAndSaved(jobs.get(i), jobDTO);
                jobDTO = setSkill_level(jobs.get(i), jobDTO);
                // STEP 4: add final data dto
                if (_currentAccount.getAccount() == null || _currentAccount.getAccount().getRole() == Role.CANDIDATE) {
                    if (jobs.get(i).is_active() == false) {
                        continue;
                    }
                }
                jobDTOS.add(jobDTO);
            }
        }
        return pagination.pagination(size, page, jobDTOS);
    }

    @Override
    public JobDTO getById(int id) {
        // STEP 1: get data
        Jobs job = _jobRepo.findJobId(id);
        boolean checkJobNotFound = (job != null && job.getDeleted_at() == null) ? false : true;
        if (checkJobNotFound) {
            throw Variable.NOT_FOUND;
        }
        if (checkDateSubcriptionPlan(job)) {
            throw Variable.NOT_FOUND;
        }
        // STEP 2: map to dto
        JobDTO jobDTO = JobMapping.getJob(job);
        // STEP 3: call function check application and saved; set skill
        jobDTO = setAppliedAndSaved(job, jobDTO);
        jobDTO = setSkill_level(job, jobDTO);
        if (_currentAccount.getAccount() == null || _currentAccount.getAccount().getRole() == Role.CANDIDATE) {
            if (job.is_active() == false) {
                throw Variable.NOT_FOUND;
            }
        }
        // STEP 4: add final data dto
        return jobDTO;
    }

    public List<JobDTO> getJobsNew(int size, int page) {
        // get account by token
        Account account = _currentAccount.getAccount();
        if (account != null) {
            // if have token (signed in)
            // STEP 1: get data job
            List<Jobs> jobs = _jobRepo.findAllByStartDateBefore(new Date());
            List<JobDTO> jobDTOS = new ArrayList<>();
            for (int i = 0; i < jobs.size(); i++) {
                if (checkDateSubcriptionPlan(jobs.get(i))) {
                    continue;
                }
                if (jobs.get(i).getDeleted_at() == null
                        && (jobs.get(i).getEnd_date()).after(new Date())) {
                    boolean checkOpening = (jobs.get(i).getEnd_date()).after(new Date())
                            && jobs.get(i).getStart_date().before(new Date());
                    if (!checkOpening) {
                        continue;
                    }
                    // STEP 2: map to dto
                    JobDTO jobDTO = JobMapping.getJob(jobs.get(i));
                    // STEP 3: call function check application and saved; set skill
                    jobDTO = setAppliedAndSaved(jobs.get(i), jobDTO);
                    jobDTO = setSkill_level(jobs.get(i), jobDTO);
                    // STEP 4: add final data dto
                    if (_currentAccount.getAccount() == null
                            || _currentAccount.getAccount().getRole() == Role.CANDIDATE) {
                        if (jobs.get(i).is_active() == false) {
                            continue;
                        }
                    }
                    jobDTOS.add(jobDTO);

                }
            }
            return pagination.pagination(size, page, jobDTOS);
        }
        // if have not token (not signed in)
        // STEP 1: get data job
        List<Jobs> jobs = _jobRepo.findAllByStartDateBefore(new Date());
        List<JobDTO> jobDTOS = new ArrayList<>();
        for (int i = 0; i < jobs.size(); i++) {
            if (checkDateSubcriptionPlan(jobs.get(i))) {
                continue;
            }
            if (jobs.get(i).getDeleted_at() == null && (jobs.get(i).getEnd_date()).after(new Date())) {
                boolean checkOpening = (jobs.get(i).getEnd_date()).after(new Date())
                        && jobs.get(i).getStart_date().before(new Date());
                if (!checkOpening) {
                    continue;
                }
                // STEP 2: map to dto
                JobDTO jobDTO = JobMapping.getJob(jobs.get(i));
                // STEP 3: call function set skill
                jobDTO = setSkill_level(jobs.get(i), jobDTO);
                // STEP 4: add final data dto
                if (_currentAccount.getAccount() == null || _currentAccount.getAccount().getRole() == Role.CANDIDATE) {
                    if (jobs.get(i).is_active() == false) {
                        continue;
                    }
                }
                jobDTOS.add(jobDTO);
            }

        }
        return pagination.pagination(size, page, jobDTOS);
    }

    public List<JobDTO> getJobsHot(int size, int page) {
        // get job by skill of job viewed by account :>>
        List<JobDTO> jobDTOs = new ArrayList<>();
        Account account = _currentAccount.getAccount();
        if (account == null) {
            // STEP 1: check login
            throw Variable.NOT_SIGNED_IN;
        }
        // STEP 2: get data jobview by account
        List<ViewedJob> viewedJobs = _ViewedJobRepo.findJobByAccount(_currentAccount.getAccount());
        if (viewedJobs.size() > 0) {
            ViewedJob viewedJob = viewedJobs.get(0);
            List<Skill> skills = new ArrayList<>();
            for (SkillJob skillJob : viewedJob.getJobs().getSkillJobs()) {
                // STEP 3: get list skill job nearest viewed
                if (!skills.contains(skillJob.getSkills())) {
                    skills.add(skillJob.getSkills());
                }
            }
            List<Jobs> listJob = new ArrayList<>();
            for (Skill s : skills) {
                for (SkillJob sj : s.getSkillJobs()) {
                    // STEP 4: add job by skill
                    Jobs j = sj.getJobs();
                    listJob.add(j);
                }
            }
            for (Jobs j : listJob) {
                if (checkDateSubcriptionPlan(j)) {
                    continue;
                }
                // STEP 5: map dto and check saved, aplication,
                JobDTO jobDTO = JobMapping.getJob(j);
                jobDTO = setAppliedAndSaved(j, jobDTO);
                boolean checkOpening = (j.getStart_date()).before(new Date())
                        && (j.getEnd_date()).after(new Date());

                boolean checkSizeJob = jobDTOs.size() <= 30;
                if (!checkSizeJob) {
                    break;
                }
                if (checkOpening && checkSizeJob) {
                    // add skill and level detail job
                    jobDTO = setSkill_level(j, jobDTO);
                    if (_currentAccount.getAccount() == null
                            && _currentAccount.getAccount().getRole() == Role.CANDIDATE) {
                        if (j.is_active() == false) {
                            continue;
                        }
                    }
                    jobDTOs.add(jobDTO);
                }
            }
        }
        return pagination.pagination(size, page, jobDTOs);
    }

    public List<JobDTO> getJobsSave(int size, int page) {
        // STEP 1: check login (token)
        Account account = _currentAccount.getAccount();
        if (account == null) {
            throw Variable.NOT_SIGNED_IN;
        }
        List<JobDTO> jobDTOS = new ArrayList<>();
        // STEP 2: get data
        List<FollowJob> followJobs = _followJobRepo.findJobByAccount(account);
        int sizeJob = followJobs.size() > 6 ? 6 : followJobs.size();
        for (int i = 0; i < sizeJob; i++) {
            if (checkDateSubcriptionPlan(followJobs.get(i).getJobs())) {
                continue;
            }
            if (followJobs.get(i).getDeleted_at() == null) {
                // STEP 3: set to dto
                JobDTO jobDTO = JobMapping.getJob(followJobs.get(i).getJobs());
                jobDTO = setAppliedAndSaved(followJobs.get(i).getJobs(), jobDTO);
                jobDTO = setSkill_level(followJobs.get(i).getJobs(), jobDTO);
                jobDTOS.add(jobDTO);
            }
        }
        return pagination.pagination(size, page, jobDTOS);
    }

    public List<JobDTO> getJobsViewed(int size, int page) {
        // like getJobsSave :>>
        Account account = _currentAccount.getAccount();
        if (account == null) {
            throw Variable.NOT_SIGNED_IN;
        }
        List<ViewedJob> viewedJobs = _ViewedJobRepo.findJobByAccount(_currentAccount.getAccount());
        List<JobDTO> jobDTOS = new ArrayList<>();
        for (int i = 0; i < viewedJobs.size(); i++) {
            if (checkDateSubcriptionPlan(viewedJobs.get(i).getJobs())) {
                continue;
            }
            boolean checkJobDeleted = viewedJobs.get(i).getDeleted_at() != null;
            if (!checkJobDeleted) {
                JobDTO jobDTO = JobMapping.getJob(viewedJobs.get(i).getJobs());
                jobDTO = setAppliedAndSaved(viewedJobs.get(i).getJobs(), jobDTO);
                jobDTO = setSkill_level(viewedJobs.get(i).getJobs(), jobDTO);
                jobDTOS.add(jobDTO);
            }
        }
        return pagination.pagination(size, page, jobDTOS);
    }

    @Override
    public List<JobDTO> getJobApplicationByAccount(int size, int page) {
        // like getJobsSave and getJobsViewed
        Account account = _currentAccount.getAccount();
        if (account == null) {
            throw Variable.NOT_SIGNED_IN;
        }
        List<Application> application = _applicationRepo.findByAccount(account);
        List<JobDTO> jobDTOs = new ArrayList<>();
        for (Application a : application) {
            if (checkDateSubcriptionPlan(a.getJobs())) {
                continue;
            }
            if (a.getDeleted_at() != null) {
                continue;
            }
            JobDTO jobDTO = JobMapping.getJob(a.getJobs());
            jobDTO = setAppliedAndSaved(a.getJobs(), jobDTO);
            jobDTO = setSkill_level(a.getJobs(), jobDTO);
            jobDTOs.add(jobDTO);
        }
        return pagination.pagination(size, page, jobDTOs);
    }

    // method for post
    @Override
    public JobDTO create(RequestJob requestJob) {

        // STEP 1: map to dto
        Jobs job = JobMapping.jobCreate(requestJob);

        // STEP 2: get company
        Company c = _companyRepo.findCompanyById(requestJob.getCompany_id());
        // STEP 3: check company have subcription planing
        boolean checkSubcritionplanExist = false;
        if (_currentAccount.getAccount().getRole() == Role.EMPLOYER) {
            for (SubcriptionPlanCompany sp : c.getSubcritionPlanCompanies()) {
                if (sp.getDeleted_at() != null) {
                    continue;
                }
                boolean checkDate = sp.getStart_date().before(new Date()) && sp.getEnd_date().after(new Date());
                if (!checkDate) {
                    continue;
                }
                if (checkDate) {
                    // have subcription plan
                    checkSubcritionplanExist = true;
                }
                boolean checkCountJob = sp.getSubscription_plan().getExpiry() <= c.getCount_job();
                if (checkCountJob) {
                    // full slot create job -> return
                    throw Variable.LIMIT_JOB;
                }
            }
            if (checkSubcritionplanExist == false) {
                // company not have subcription plan -> return
                throw Variable.SUBCRIPTION_PLAN_NOT_FOUND;
            }
        }
        job.setCompany(c);
        // get location
        // Location location = new Location();
        // for (Location l : c.getLocations()) {
        // if (l.getDeleted_at() == null) {
        // location = l;
        // }
        // }
        // job.setLocation(location);
        // get job type
        JobType jt = _jobTypeRepo.findIdJobType(requestJob.getJob_type_id());
        job.setJobType(jt);
        // STEP 4: create job
        _jobRepo.save(job);
        // STEP 5: handle skill and level (one to many, many to one)
        if (requestJob.getSkill_id() != null && requestJob.getSkill_id() != "") {
            // handle string skill -> array skill for create by ","
            String[] skillId = requestJob.getSkill_id().split(",");
            for (String i : skillId) {
                Skill s = _skillRepo.findById(Integer.parseInt(i)).get();
                SkillJob skillJob = new SkillJob(s, job);
                // save skillCompany (Intermediate table)
                _skillJobRepo.save(skillJob);
            }
        }
        if (requestJob.getLevel_id() != null && requestJob.getLevel_id() != "") {
            // handle string level -> array level for create by ","
            String[] levelId = requestJob.getLevel_id().split(",");
            for (String i : levelId) {
                Level l = _levelRepo.findById(Integer.parseInt(i)).get();
                LevelJob levelJob = new LevelJob(l, job);
                // save LevelCompany (Intermediate table)
                _levelJobRepo.save(levelJob);
            }
        }
        // STEP 6: -1 slot create of subcription plan for company
        if (_currentAccount.getAccount().getRole() == Role.EMPLOYER) {
            c.setCount_job(c.getCount_job() + 1);
        }
        _companyRepo.save(c);
        // STEP 7: send mail for all account saved company
        for (FollowCompany fl : c.getFollowCompany()) {
            if (fl.getDeleted_at() != null) {
                continue;
            }
            _mailService.SendMailForCreateJob(fl.getAccount().getEmail(), c, job);
        }

        // Finished :>>
        return (JobDTO) JobMapping.getJob(job);
    }

    public Boolean postJobsSave(RequestIntermediaryJob requestIntermediaryJob) {
        // method for candidate -> need account
        // STEP 1: check account check job
        Account account = _currentAccount.getAccount();
        Jobs job = _jobRepo.findJobId(Integer.parseInt(requestIntermediaryJob.job_id));
        if (account == null || job == null) {
            return false;
        }
        // STEP 2: check (Intermediate table) FollowJob avoid repetition data
        FollowJob followJob = _followJobRepo.findByAccountAndJobs(account, job);
        if (followJob != null) {
            // data existing
            _followJobRepo.delete(followJob);
            return true;
        }
        // STEP 3: save
        // Change
        _followJobRepo.save(new FollowJob(0, job, account, new Date("11/11/2024")));
        return true;
    }

    public Boolean postJobsViewed(RequestIntermediaryJob requestIntermediaryJob) {
        // method for candidate -> need account
        // STEP 1: check account check job
        Account account = _currentAccount.getAccount();
        Jobs job = _jobRepo.findJobId(Integer.parseInt(requestIntermediaryJob.job_id));
        if (account == null || job == null) {
            return false;
        }
        // STEP 2: handle
        // If the time between the most recently viewed job and the current one contains
        // another job, create a new one. If there is no job in between, update
        // create_at to the current one.
        if (_ViewedJobRepo.findWithLatestDate(account, job).size() > 0) {
            // STEP 2.1: get job have time create_at nearest
            ViewedJob viewedJobExist = _ViewedJobRepo.findWithLatestDate(account, job).get(0);
            if (viewedJobExist != null) {
                // STEP 2.2: Get the job between the most recent job and the current one.
                List<ViewedJob> viewedJobBetween = _ViewedJobRepo
                        .findJobBetweenCurrentAndDateViewedJobExist(viewedJobExist.getCreated_at(), new Date());
                if (viewedJobBetween.size() == 0) {
                    // viewedJobBetween == null -> update create_at job
                    viewedJobExist.setCreated_at(new Date());
                    _ViewedJobRepo.save(viewedJobExist);
                    return true;
                }
            }
        }
        // viewedJobBetween have job -> update create_at job
        // Change
        _ViewedJobRepo.save(new ViewedJob(0, job, account, new Date("11/11/2024")));
        // finished
        return true;
    }

    public String CreatejobApplication(RequestApplication requestApplication) {
        // application need account, cv of account and job for application;
        // STEP 1: get account by token
        Account account = _currentAccount.getAccount();
        // STEP 2: get data
        Jobs job = _jobRepo.findJobId(Integer.parseInt(requestApplication.getJob_id()));
        boolean checkJobOff = job.is_active() == false;
        if (checkJobOff) {
            throw Variable.JOB_OFF;
        }
        CurriculumVitae Cv = _cvRepo.findById(Integer.parseInt(requestApplication.getCv_id())).get();
        if (account == null || job == null || Cv == null) {
            // incomplete data for application
            throw Variable.ACTION_FAIL;
        }
        // STEP 3: check account applicated job
        for (int i = 0; i < account.getApplications().size(); i++) {
            Application application = _applicationRepo.findByAccountAndJobs(account, job);
            boolean checkApplicated = _applicationRepo.findByAccountAndJobs(account, job) != null;
            if (checkApplicated) {
                // applicated by account
                throw Variable.CONFLIG;
            }
        }
        // STEP 4: save application
        // Change
        _applicationRepo
                .save(new Application(0, requestApplication.getNote(), account, job, Cv, new Date("11/11/2024")));
        return "Success";
    }

    // method for put
    @Override
    public JobDTO put(int id, RequestJob requestJob) {
        // STEP 1: get and check data
        Jobs getJob = _jobRepo.findJobId(id);
        boolean checkJobNotFound = (getJob != null && getJob.getDeleted_at() == null) ? false : true;
        if (checkJobNotFound) {
            throw Variable.NOT_FOUND;
        }
        // boolean checkJobOff = getJob.is_active() == false;
        // if (checkJobOff) {
        // throw Variable.JOB_OFF;
        // }
        // STEP 2: handle level and skill (Intermediate table)

        // STEP 3: add request to entity for save
        Jobs job = JobMapping.jobPut(requestJob, getJob);
        if (requestJob.getCompany_id() != 0) {
            Company c = _companyRepo.findCompanyById(requestJob.getCompany_id());
            job.setCompany(c);
            // for (Location l : c.getLocations()) {
            // if (l.getDeleted_at() == null) {
            // job.setLocation(l);
            // }
            // }

            if (requestJob.getJob_type_id() != 0) {
                JobType jt = _jobTypeRepo.findIdJobType(requestJob.getJob_type_id());
                job.setJobType(jt);
            }
            job.setId(id);
            // STEP 4: save
            _jobRepo.save(job);
        }
        List<LevelJob> levelJobs = _levelJobRepo.findByJob(job);
        for (LevelJob levelJob : levelJobs) {
            _levelJobRepo.delete(levelJob);
        }
        if (requestJob.getLevel_id() != null) {
            if (requestJob.getLevel_id() != "") {
                String[] levelId = requestJob.getLevel_id().split(",");
                for (String i : levelId) {
                    int idLevel = Integer.parseInt(i);
                    Level l = _levelRepo.findById(idLevel).get();
                    LevelJob levelJob = new LevelJob(l, getJob);
                    _levelJobRepo.save(levelJob);
                }
            }
        }
        List<SkillJob> skillJobs = _skillJobRepo.findByJob(job);
        for (SkillJob skillJob : skillJobs) {
            _skillJobRepo.delete(skillJob);
        }
        if (requestJob.getSkill_id() != null) {
            if (requestJob.getSkill_id() != "") {
                String[] skillId = requestJob.getSkill_id().split(",");
                for (String i : skillId) {
                    int idLevel = Integer.parseInt(i);
                    Skill s = _skillRepo.findById(idLevel).get();
                    SkillJob skillJob = new SkillJob(s, getJob);
                    _skillJobRepo.save(skillJob);
                }
            }
        }
        return (JobDTO) JobMapping.getJob(job);

    }

    public JobDTO editJobOnOrOff(int id) {
        Jobs getJob = _jobRepo.findJobId(id);
        boolean checkJobNotFound = (getJob != null && getJob.getDeleted_at() == null) ? false : true;
        if (checkJobNotFound || getJob == null) {
            throw Variable.NOT_FOUND;
        }
        getJob.set_active(getJob.is_active() == true ? false : true);
        _jobRepo.save(getJob);
        return JobMapping.getJob(getJob);
    }

    @Override
    public String delete(int id) {
        Jobs job = _jobRepo.findJobId(id);
        boolean checkJobNotFound = (job != null && job.getDeleted_at() == null) ? false : true;
        if (checkJobNotFound) {
            throw Variable.NOT_FOUND;
        }
        job.setDeleted_at(new Date());
        _jobRepo.save(job);
        return "Success";
    }

    // public boolean deleteJobsSave(RequestIntermediaryJob requestIntermediaryJob)
    // {
    // Account account = _currentAccount.getAccount();
    // Jobs job =
    // _jobRepo.findJobId(Integer.parseInt(requestIntermediaryJob.getJob_id()));
    // if (account == null && job == null) {
    // return false;
    // }
    // FollowJob followJob = _followJobRepo.findByAccountAndJobs(account, job);
    // if (followJob == null) {
    // return false;
    // }
    // _followJobRepo.deleteById(followJob.getId());
    // return true;

    // }

    // handle
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
