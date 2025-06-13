package com.company.apis.Repository;

import com.company.apis.Models.Entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepo extends JpaRepository<Advertisement, Integer> {

}
