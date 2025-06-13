package com.company.apis.Services.IServices;

import com.company.apis.Models.Entity.Mail;
import com.company.apis.Models.Entity.SubcriptionPlanCompany;

public interface IMailService {
       boolean SendMailForEmployer(String email, SubcriptionPlanCompany subcriptionPlanCompany);
 
       String SendMailWithAttachment(Mail details);
}
