package com.company.apis.Controllers.Admin;

import com.company.apis.Controllers.BaseController;
import com.company.apis.Models.Entity.Advertisement;
import com.company.apis.Models.Request.RequestAdvertisement;
import com.company.apis.Services.AdvertisementService;
import com.company.apis.Utils.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/advertisement")
public class AdminAdvertisementController {
    @Autowired
    AdvertisementService _AdvertisementService;
    BaseController<Advertisement> _baseController = new BaseController<Advertisement>();
    BaseController<String> _baseController_string = new BaseController<String>();

    BaseController<List<Advertisement>> _baseControllers = new BaseController<List<Advertisement>>();


    @PostMapping()
    public ResponseEntity<?> post(@ModelAttribute RequestAdvertisement requestAdvertisement) {
        try {
            return _baseController.success(_AdvertisementService.create(requestAdvertisement));
        } catch (HttpException e) {
            return _baseControllers.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseControllers.error(null, 500, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable int id, @ModelAttribute RequestAdvertisement requestAdvertisement) {
        try {
            return _baseController.success(_AdvertisementService.put(id, requestAdvertisement));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            return _baseController_string.success(_AdvertisementService.delete(id));
        } catch (HttpException e) {
            return _baseController.error(null, e.StatusCode, e.message);
        } catch (Exception e) {
            return _baseController.error(null, 500, e.getMessage());
        }
    }

}
