package com.company.apis.Controllers.Candidate;

import com.cloudinary.provisioning.Account;
import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.CVDTO;
import com.company.apis.Models.Request.RequestCV;
import com.company.apis.Models.Request.RequestDataCreateCV;
import com.company.apis.Services.CVService;
import com.company.apis.Services.CloudinaryService;
import com.company.apis.Utils.Convert;
import com.company.apis.Utils.CurrentAccount;
import com.company.apis.Utils.Html;
import com.company.apis.Utils.HttpException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/candidate/cv")
public class CandidateCVController {
    @Autowired
    CVService _cvService;
    @Autowired
    CurrentAccount account;

    @Autowired
    CloudinaryService _cloudinaryService;

    BaseController<CVDTO> _baseController = new BaseController<CVDTO>();
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<List<CVDTO>> _baseControllers = new BaseController<List<CVDTO>>();
    BaseController<String> _baseControllerString = new BaseController<String>();

    @GetMapping("/account")
    public ResponseEntity<?> getByAccount(@RequestParam int size, @RequestParam int page) {
        try {
            return _baseControllers.success(_cvService.getByAccount(size, page));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> post(@RequestParam MultipartFile file, @RequestParam String name) {
        try {
            return _baseController_string.success(_cvService.create(new RequestCV(file, name)));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam int id) {
        try {
            return _baseController_string.success(_cvService.delete(id));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable int id, @RequestParam String name) {
        try {
            return _baseController_string.success(_cvService.delete(id));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @PostMapping("/generate_pdf")
    public ResponseEntity<?> generatePdf(@RequestBody RequestDataCreateCV requestDataCreateCV) {
        try {
            String htmlContent = Html.GET_HTML_CV(requestDataCreateCV, account.getAccount());
            byte[] pdfBytes = Convert.convertHtmlToPdf(htmlContent);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment().filename("document.pdf").build());

            MultipartFile multipartFile = new MockMultipartFile(
                    "cv",
                    "cv.pdf",
                    "pdf",
                    pdfBytes);

            _cvService.create(new RequestCV(multipartFile, requestDataCreateCV.getName_cv()));
            return _baseControllerString.success("Success");
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

}
