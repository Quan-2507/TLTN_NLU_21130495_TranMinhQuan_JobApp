package com.company.apis.Services.IServices;

import java.util.List;

import com.company.apis.Models.DTO.SaveCVDTO;

public interface ISaveCVService {
    List<SaveCVDTO> getAllSaveCV(int size, int page);
    String saveOrUnsaveCV(int application_id); 

}
