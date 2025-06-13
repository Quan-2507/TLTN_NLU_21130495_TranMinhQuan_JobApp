package com.company.apis.Controllers.Admin;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.CityProvinceDTO;
import com.company.apis.Models.Request.RequestCityProvince;
import com.company.apis.Services.CityProvinceService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/city_province")
public class AdminCityProvinceController {
    @Autowired
    CityProvinceService _cityProvinceService;
    BaseController<CityProvinceDTO> _baseController = new BaseController<CityProvinceDTO>();
    BaseController<String> _baseController_string = new BaseController<String>();
    BaseController<List<CityProvinceDTO>> _baseControllers = new BaseController<List<CityProvinceDTO>>();

    // @GetMapping()
    // public ResponseEntity<?> get() {
    //     try {
    //         return _baseControllers.success(_cityProvinceService.getAll());
    //     } catch (HttpException e) {
    //         return _baseControllers.error(null, e.StatusCode, e.message);
    //     } catch (Exception e) {
    //         return _baseControllers.error(null, 500, e.getMessage());
    //     }
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> getById(@PathVariable int id) {
    //     try {
    //         return _baseController.success(_cityProvinceService.getById(id));
    //     } catch (HttpException e) {
    //         return _baseController.error(null, e.StatusCode, e.message);
    //     } catch (Exception e) {
    //         return _baseController.error(null, 500, e.getMessage());
    //     }
    // }
    @PostMapping()
    public ResponseEntity<?> post(@ModelAttribute RequestCityProvince rqCityProvince) {
        try {
            return _baseController.success(_cityProvinceService.create(rqCityProvince));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable int id, @ModelAttribute RequestCityProvince rqCityProvince) {
        try {
            return _baseController.success(_cityProvinceService.put(id, rqCityProvince));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            return _baseController_string.success(_cityProvinceService.delete(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
}
