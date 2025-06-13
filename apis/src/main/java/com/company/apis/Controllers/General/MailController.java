package com.company.apis.Controllers.General;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.Entity.Mail;
import com.company.apis.Services.MailService;
import com.company.apis.Utils.HttpException;

@RestController
@RequestMapping("/general")
public class MailController {

    @Autowired
    MailService _mailService;

    BaseController<String> _baseController = new BaseController<String>();

    // @PostMapping("/send_mail")
    // public ResponseEntity<?> SendMail(@RequestBody Mail mail) {
    //     try {
    //         boolean result = _mailService.SendMailForEmployer(null,null);
    //         if (result) {
    //             return _baseController.success("");
    //         }
    //         return _baseController.error(null, 500, "FAIL!!");
    //     } catch (HttpException e) {
    //         return _baseController.error(null, e.StatusCode, e.message);
    //     } catch (Exception e) {
    //         return _baseController.error(null, 500, e.getMessage());
    //     }
    // }
}
