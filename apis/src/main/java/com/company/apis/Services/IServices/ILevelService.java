package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.LevelDTO;
import com.company.apis.Models.Request.RequestLevel;

import java.util.List;

public interface ILevelService {
    List<LevelDTO> getAll();

    LevelDTO create(RequestLevel requestLevel);

    LevelDTO put(int id, RequestLevel requestLevel);

    String delete(int id);

    LevelDTO getById(int id);
}
