package com.company.apis.Repository;

import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.FollowJob;
import com.company.apis.Models.Entity.Jobs;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowJobRepo extends JpaRepository<FollowJob, Integer> {
    @Query("select v from FollowJob v Where  account = :account ORDER BY v.created_at DESC")
    List<FollowJob> findJobByAccount(@Param("account") Account acc);

    @Query("select v from FollowJob v Where  account = :account and jobs = :job ORDER BY v.created_at DESC")
    FollowJob findByAccountAndJobs(@Param("account") Account account, @Param("job") Jobs job);

}
