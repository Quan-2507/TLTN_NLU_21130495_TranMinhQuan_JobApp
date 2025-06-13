package com.company.apis.Controllers.General;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.DTO.CityProvinceDTO;
import com.company.apis.Services.CityProvinceService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general/city_province")
public class CityProvinceController {
    @Autowired
    CityProvinceService _cityProvinceService;
    BaseController<CityProvinceDTO> _baseController = new BaseController<CityProvinceDTO>();
    BaseController<List<CityProvinceDTO>> _baseControllers = new BaseController<List<CityProvinceDTO>>();

    @GetMapping()
    public ResponseEntity<?> get() {
        try {
            return _baseControllers.success(_cityProvinceService.getAll());
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return _baseController.success(_cityProvinceService.getById(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }
}
