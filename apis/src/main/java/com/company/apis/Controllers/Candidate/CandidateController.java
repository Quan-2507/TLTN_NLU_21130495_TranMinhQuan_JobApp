package com.company.apis.Controllers.Candidate;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.Request.RequestAccount;
import com.company.apis.Services.AccountService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class CandidateController {
    @Autowired
    AccountService _accountService;
    BaseController<AccountDTO> _baseController = new BaseController<AccountDTO>();
    BaseController<List<AccountDTO>> _baseControllers = new BaseController<List<AccountDTO>>();

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
            ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> UpdateAccountProfile(@ModelAttribute RequestAccount requestAccount) {
        try {
            return _baseController.success(_accountService.updateProfileByAccount(requestAccount));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
}
