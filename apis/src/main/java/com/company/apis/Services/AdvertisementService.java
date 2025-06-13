package com.company.apis.Services;

import com.company.apis.Models.Entity.Advertisement;
import com.company.apis.Models.Request.RequestAdvertisement;
import com.company.apis.Repository.AdvertisementRepo;
import com.company.apis.Services.IServices.IAdvertisementService;
import com.company.apis.Utils.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AdvertisementService implements IAdvertisementService {
    @Autowired
    AdvertisementRepo _AdvertisementRepo;

    // call service
    @Autowired
    CloudinaryService _cloudinaryService;

    @Override
    public List<Advertisement> getAll() {
        return _AdvertisementRepo.findAll();
    }

    @Override
    public Advertisement create(RequestAdvertisement requestAdvertisement) {
        Advertisement advertisement = new Advertisement();

        if (requestAdvertisement.getImage() != null) {
            try {
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestAdvertisement.getImage(),
                        requestAdvertisement.getImage().getName());
                advertisement.setImage(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }
        advertisement.setPath(requestAdvertisement.getPath());
        _AdvertisementRepo.save(advertisement);
        return advertisement;
    }

    @Override
    public Advertisement put(int id, RequestAdvertisement requestAdvertisement) {
        Advertisement advertisement = _AdvertisementRepo.findById(id).get();
        if (advertisement == null) {
            throw Variable.NOT_FOUND;
        }
        if (requestAdvertisement.getImage() != null) {
            try {
                @SuppressWarnings("rawtypes")
                Map check = _cloudinaryService.uploadImage(requestAdvertisement.getImage(),
                        requestAdvertisement.getImage().getName());
                advertisement.setImage(check.get("url").toString());
            } catch (IOException e) {
                throw Variable.ADD_IMAGE_FAIL;
            }
        }

        if (requestAdvertisement.getPath() != null) {
            advertisement.setPath(requestAdvertisement.getPath());
        }
        _AdvertisementRepo.save(advertisement);
        return null;
    }

    @Override
    public String delete(int id) {
        Advertisement advertisement = _AdvertisementRepo.findById(id).get();
        if (advertisement == null) {
            throw Variable.NOT_FOUND;
        }
        _AdvertisementRepo.delete(advertisement);
        return "Success";
    }

    @Override
    public Advertisement getById(int id) {
        Advertisement advertisement = _AdvertisementRepo.findById(id).get();
        if (advertisement == null) {
            throw Variable.NOT_FOUND;
        }
        return advertisement;
    }

}
