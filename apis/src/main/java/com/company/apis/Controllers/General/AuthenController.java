package com.company.apis.Controllers.General;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.AccountDTO;
import com.company.apis.Models.DTO.TokenUser;
import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Request.RequestAccount;
import com.company.apis.Models.Request.RequestLogin;
import com.company.apis.Models.Request.RequestToken;
import com.company.apis.Services.AccountService;
import com.company.apis.Services.AuthenticateService;
import com.company.apis.Utils.HttpException;

@RestController
@RequestMapping("/general")
public class AuthenController {
    @Autowired
    AccountService _accountService;
    @Autowired
    AuthenticateService _iauthenRepo;

    BaseController<TokenUser> _baseController = new BaseController<TokenUser>();
    BaseController<Account> _baseControllerAccount = new BaseController<Account>();
    BaseController<AccountDTO> _baseControllerInfo = new BaseController<AccountDTO>();
    BaseController<String> _baseController_string = new BaseController<String>();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLogin requestLogin) {
        try {
            TokenUser tokenUser = _iauthenRepo.login(requestLogin);
            return _baseController.success(tokenUser);
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RequestLogin requestLogin) {
        try {
            Account account = _iauthenRepo.register(requestLogin);
            return _baseControllerAccount.success(account);
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PostMapping("/check_token")
    public ResponseEntity<?> postToken(@RequestBody RequestToken requestToken) {
        try {
            return _baseControllerInfo.success(_iauthenRepo.checkToken(requestToken.getToken()));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PostMapping("/check_token_web")
    public ResponseEntity<?> postTokenWeb(@RequestBody String token) {
        try {
            return _baseControllerInfo.success(_iauthenRepo.checkTokenWeb(token));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PutMapping(value = "/account", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> Put(@RequestParam MultipartFile image) {
        try {
            return _baseController_string.success(_accountService.putImage(image));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
}
