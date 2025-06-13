package com.company.apis.Utils;


public interface Variable {
        // 400
        public static HttpException TOKEN_ERROR = new HttpException(400, "Understand token request from client");
        public static HttpException EMAIL_OR_PASSWORD_INCORRECT = new HttpException(400,
                        "Email or Password incorrect!!");
        public static HttpException EMAIL_INVALID = new HttpException(400,
                        "Enter Email invalid. please change email!!");
        public static HttpException PASSWORD_INVALID = new HttpException(400, "Enter Password invalid!!");
        public static HttpException LIMIT_JOB = new HttpException(400, "Company not create more job!!");
        public static HttpException SUBCRIPTION_PLAN_NOT_FOUND = new HttpException(400,
                        "Company not create job, because subcription plan not found!!");
        // 401
        public static HttpException NOT_SIGNED_IN = new HttpException(401, "Not signed in");
        // 404
        public static HttpException NOT_FOUND = new HttpException(404, "Not Found");
        public static HttpException ACCOUNT_NOT_FOUND = new HttpException(404, "Account not found ");
        // 409
        public static HttpException EMAIL_EXISTING = new HttpException(409,
                        "Email existing in database. please change email!!");
        public static HttpException SUBCRIPTION_PLAN_EXIST = new HttpException(409, "SubScription plan existing");
        public static HttpException COMPANY_NOT_FOUND = new HttpException(400, "Company not found!!");
        public static HttpException COMPANY_CONFLIG = new HttpException(409, "Company is follow!!");
        public static HttpException CONFLIG = new HttpException(409, "Action Confliig data!!");
        // 500
        public static Exception FAIL = new HttpException(500, "Action Fail");
        public static HttpException JOB_OFF = new HttpException(500, "Action Fail because job off");
        public static HttpException ADD_IMAGE_FAIL = new HttpException(500, "Action add image Fail");
        public static HttpException COMPANY_ACCOUNT_EXISTING = new HttpException(500,
                        "The account already exists for this company. Please delete the existing company before creating a new one.");
        public static HttpException ACTION_FAIL = new HttpException(500, "Action Fail");
        public static String PATH_IMAGE = "https://res.cloudinary.com/dj7xlmndj/image/upload/v1719238271/";

        // date
        public static String MONTH = "MONTH";
        public static String YEAR = "YEAR";

       

}
