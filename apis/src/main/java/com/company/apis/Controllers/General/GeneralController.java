package com.company.apis.Controllers.General;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.SearchAll;
import com.company.apis.Models.Request.RequestDataCreateCV;
import com.company.apis.Services.AdminService;
import com.company.apis.Utils.HttpException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/general")
public class GeneralController {
    BaseController<SearchAll> _baseControllers = new BaseController<SearchAll>();

    @Autowired
    AdminService _adminService;
    @GetMapping("/search/{search}")
    public ResponseEntity<?> getMethodName(@PathVariable String search) {
        try {
            return _baseControllers.success(_adminService.searchByNameForAll(search));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

}
