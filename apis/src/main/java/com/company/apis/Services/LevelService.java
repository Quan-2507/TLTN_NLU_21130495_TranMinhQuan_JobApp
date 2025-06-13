package com.company.apis.Services;

import com.company.apis.Models.DTO.LevelDTO;
import com.company.apis.Models.Entity.Level;
import com.company.apis.Models.Map.LevelMapping;
import com.company.apis.Models.Request.RequestLevel;
import com.company.apis.Repository.LevelRepo;
import com.company.apis.Services.IServices.ILevelService;
import com.company.apis.Utils.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LevelService implements ILevelService {
    @Autowired
    LevelRepo _levelRepo;

    @Override
    public List<LevelDTO> getAll() {
        List<Level> levels = _levelRepo.findAll();
        List<LevelDTO> levelDTOs = new ArrayList<>();
        for (int i = 0; i < levels.size(); i++) {
            if (levels.get(i).getDeleted_at() == null) {
                levelDTOs.add(LevelMapping.levelDTO(levels.get(i)));
            }
        }
        return levelDTOs;
    }

    @Override
    public LevelDTO create(RequestLevel requestLevel) {
        Level level = LevelMapping.level(requestLevel);
        _levelRepo.save(level);
        return (LevelDTO) LevelMapping.levelDTO(level);
    }

    @Override
    public LevelDTO put(int id, RequestLevel requestLevel) {
        Level getLevel = _levelRepo.findIdByLevel(id);
        boolean checkLevelNotFound = (getLevel != null && getLevel.getDeleted_at() == null) ? false : true;
        if (checkLevelNotFound) {
            throw Variable.NOT_FOUND;
        }
        Level level = LevelMapping.levelPut(requestLevel, getLevel);
        level.setId(id);
        _levelRepo.save(level);
        return (LevelDTO) LevelMapping.levelDTO(level);
    }

    @Override
    public String delete(int id) {
        Level level = _levelRepo.findIdByLevel(id);
        boolean checkLevelNotFound = (level != null && level.getDeleted_at() == null) ? false : true;
        if (checkLevelNotFound) {
            throw Variable.NOT_FOUND;
        }
        level.setDeleted_at(new Date());
        _levelRepo.save(level);
        return "Success";
    }

    @Override
    public LevelDTO getById(int id) {
        Level level = _levelRepo.findIdByLevel(id);
        boolean checkLevelNotFound = (level != null && level.getDeleted_at() == null) ? false : true;
        if (checkLevelNotFound) {
            throw Variable.NOT_FOUND;
        }
        LevelDTO levelDTO = LevelMapping.levelDTO(level);
        return levelDTO;
    }
}
