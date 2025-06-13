package com.company.apis.Models.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChartForEmployer {
     // thông tin job, account, company được tạo trong năm;
    //information job, account, company has been craete in current year
    Integer jobs_has_been_created;
    //current month
    Integer total_applicated_by_month;
    // tổng số doanh thu theo năm ($$$)
    //tatol price in current year
    Float overall_payment;

    int opening_jobs;


    List<Integer> number_of_job_applicated;
    List<Integer> number_of_job_saved;
    List<Integer> number_of_job_viewed;
    List<Integer> month;
    List<Integer> jobs;
    List<Float> price_for_subcription_plan;
    List<Integer> subcription_plan;
}
