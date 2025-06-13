package com.company.apis.Repository;

import com.company.apis.Models.Entity.CityProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityProvinceRepo extends JpaRepository<CityProvince,Integer> {
    // CityProvince getLocationById (int id);
    @Query("select ci from CityProvince ci Where ci.id = ?1 ORDER BY ci.created_at DESC")
    CityProvince findIdCityProvince(int id);
}
