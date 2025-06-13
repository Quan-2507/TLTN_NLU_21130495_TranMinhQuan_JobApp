package com.company.apis.Repository;

import com.company.apis.Models.Entity.Account;
import com.company.apis.Models.Entity.FollowJob;
import com.company.apis.Models.Entity.Jobs;
import com.company.apis.Models.Entity.ViewedJob;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ViewedJobRepo extends JpaRepository<ViewedJob, Integer> {
    @Query("select v from ViewedJob v Where  account = :account ORDER BY v.created_at DESC")
    List<ViewedJob> findJobByAccount(@Param("account") Account acc);


    @Query("select v from ViewedJob v Where  account = :account and jobs = :job ORDER BY v.created_at DESC")
    List<ViewedJob> findWithLatestDate(@Param("account") Account account, @Param("job") Jobs job);

    @Query("select v from ViewedJob v Where  v.created_at>:date and v.created_at <:currenDate ORDER BY v.created_at DESC")
    List<ViewedJob> findJobBetweenCurrentAndDateViewedJobExist(@Param("date") Date date,@Param("currenDate") Date currentDate);
}
