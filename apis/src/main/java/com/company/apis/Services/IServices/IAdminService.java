package com.company.apis.Services.IServices;

import com.company.apis.Models.DTO.SearchAll;

public interface IAdminService {
    SearchAll searchByNameForAdmin(String name);
    SearchAll searchByNameForAll(String name);
}
