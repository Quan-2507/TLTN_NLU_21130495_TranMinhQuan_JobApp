package com.company.apis.Models.Map;

import com.company.apis.Models.DTO.LevelDTO;
import com.company.apis.Models.Entity.Level;
import com.company.apis.Models.Request.RequestLevel;

public class LevelMapping {
    public static LevelDTO levelDTO(Level l){
        LevelDTO levelDTO = new LevelDTO();
        levelDTO.setId(l.getId());
        levelDTO.setName(l.getName());
        return levelDTO;
    }

    public static Level level(RequestLevel rl){
        Level level = new Level();
        level.setName(rl.getName());
        return level;
    }

    public static  Level levelPut(RequestLevel rl,Level l){
        if(rl.getName() != null){
            l.setName(rl.getName());
        }
        return l;
    }
}
