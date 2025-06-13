package com.company.apis.Models.Map;

import com.company.apis.Models.DTO.CityProvinceDTO;
import com.company.apis.Models.Entity.CityProvince;
import com.company.apis.Models.Request.RequestCityProvince;

public class CityProvinceMapping {

    public static CityProvinceDTO cityProvinceDTO(CityProvince ci){
        CityProvinceDTO cityProvinceDTO = new CityProvinceDTO();
        cityProvinceDTO.setId(ci.getId());
        cityProvinceDTO.setName(ci.getName());
        return cityProvinceDTO;
    }

    public static CityProvince cityProvince(RequestCityProvince rq){
        CityProvince cityProvince = new CityProvince();
        cityProvince.setName(rq.getName());
        return cityProvince;
    }

    public static  CityProvince CityProvincePut(RequestCityProvince rq,CityProvince ci){
        if(rq.getName() != null){
            ci.setName(rq.getName());
        }
        return  ci;
    }
}
