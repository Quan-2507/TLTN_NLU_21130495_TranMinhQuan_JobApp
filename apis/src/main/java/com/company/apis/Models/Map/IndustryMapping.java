package com.company.apis.Models.Map;


import com.company.apis.Models.DTO.IndustryDTO;
import com.company.apis.Models.Entity.Industry;
import com.company.apis.Models.Request.RequestIndustry;

public class IndustryMapping {
    public static IndustryDTO industryDTO(Industry i) {
        IndustryDTO industryDTO = new IndustryDTO();
        industryDTO.setId(i.getId());
        industryDTO.setName(i.getName());
        return industryDTO;
    }

    public static Industry Industry(RequestIndustry i) {
        Industry industry = new Industry();
        industry.setName(i.getName());
        return industry;
    }

    public static Industry IndustryPut(RequestIndustry ri, Industry i) {
        if (ri.getName() != null) {
            i.setName(ri.getName());
        }
        return i;
    }

}
