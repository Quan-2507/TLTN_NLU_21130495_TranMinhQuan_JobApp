package com.company.apis.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.company.apis.Models.Entity.Company;
import com.company.apis.Models.Entity.Jobs;
import com.company.apis.Models.Entity.Mail;
import com.company.apis.Models.Entity.SubcriptionPlanCompany;
import com.company.apis.Services.IServices.IMailService;
import com.company.apis.Utils.Html;
import com.company.apis.Utils.Variable;

@Service
public class MailService implements IMailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public boolean SendMailForEmployer(String email, SubcriptionPlanCompany subcriptionPlanCompany) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject("apis4: BUY SUBCRIPTION PLAN");

			String html = Html.GETHTMLSUBCRIPTIONPLAN(subcriptionPlanCompany);

			helper.setText(html, true);
			helper.setTo(email);
			helper.setFrom(sender);
			javaMailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			throw Variable.ACTION_FAIL;
		}

	}

	public String SendMailWithAttachment(Mail details) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setSubject(
					details.getSubject());

			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		}

		catch (MessagingException e) {

			return "Error while sending mail!!!";
		}
	}

	public void SendMailForApplication(String email, Company c, Jobs jobs) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject("apis4: FROM " + c.getName() + "");

			String html = "<div>\n" + //
					"  <div>Hi " + email + "</div> <br/>\n" + //
					"  <div>We are excited to see your resume! Your experience and passion for " + jobs.getTitle()
					+ " really impressed us. </div> <br/>\n"
					+ //
					"  <div>We believe you will be a valuable member of our team. To learn more about you and this opportunity, we would like to invite you to an interview. </div>\n"
					+ //
					"  <br/>\n" + //
					"  <div>We will contact you soon to schedule an interview. </div><br/>\n" + //
					"  <div>Sincerely</div>\n" + //
					"  <div>" + c.getName() + "</div>\n" + //
					"  <div>" + jobs.getTitle() + "</div>\n" + //
					"<div/>";

			helper.setText(html, true);
			helper.setTo(email);
			helper.setFrom(sender);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
		}
	}

	public void SendMailForCreateJob(String email, Company c, Jobs job) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject("apis4: COMPANY HAVE NEW JOB");

			String html = "<div>\n" + //
					" Are you looking for a dynamic working environment with many development\n" + //
					" opportunities? Sign up to receive recruitment notifications at " + c.getName() + ". We\n" + //
					" always welcome talents from all fields.\n" + //
					"  <br/>\n" + //
					" Don't miss the opportunity to explore interesting projects and create new values\n" + //
					" with us.\n" + //
					"  <br/>\n" + //
					" New job: " + job.getTitle() + ".\n" + //
					"<div/>";

			helper.setText(html, true);
			helper.setTo(email);
			helper.setFrom(sender);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
		}
	}
}
