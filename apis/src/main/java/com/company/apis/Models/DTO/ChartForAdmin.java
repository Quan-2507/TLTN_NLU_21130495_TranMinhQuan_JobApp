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
public class ChartForAdmin {
    // thông tin job, account, company được tạo trong năm;
    //information job, account, company has been craete in current year
    Integer jobs_has_been_created;
    Integer account_has_been_created;
    Integer companys_has_been_created;
    // tổng số doanh thu theo năm ($$$)
    //tatol price in current year
    Float overall_payment;
    // tháng có doanh thu cao nhất trong năm
    //month have price heghest in current year
    Integer top_grossing_month;

    // thống kê thông tin tất cả các job của tất cả company theo từng tháng;
    // List<Integer> number_of_job_applicated;
    // List<Integer> number_of_job_saved;
    // List<Integer> number_of_job_viewed;
    List<Integer> month;
    // thống kê số job được tạo theo tháng
    List<Integer> jobs;
    List<Float> price_for_subcription_plan;
    List<Integer> number_of_all_subcription_plan_by_month;

    List<List<Integer>> array_list_number_subcription_plan_by_month;
    List<String> name_subcription_plan;
    // // (table) thông tin top 3 company có appli, save cao nhất
    // List<CompanyDTO> top_3_company_by_application;
    // List<CompanyDTO> top_3_company_by_save;
}
