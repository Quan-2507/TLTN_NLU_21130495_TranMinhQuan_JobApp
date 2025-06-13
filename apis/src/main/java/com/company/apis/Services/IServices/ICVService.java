package com.company.apis.Services.IServices;

import java.util.List;


import com.company.apis.Models.DTO.CVDTO;
import com.company.apis.Models.Request.RequestCV;

public interface ICVService {
    String create(RequestCV requestCV);

    String delete(int id);

    CVDTO getById(int id);

    List<CVDTO> getByAccount(int size, int page);

}
