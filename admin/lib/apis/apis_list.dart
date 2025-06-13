import 'package:admin/apis/api_config.dart';

class AuthApi extends ApiConfig {
  AuthApi(
      {super.module = 'general',
        required super.path,
        required super.method,
        super.isAuth = false});

  static final login = AuthApi(path: '/login', method: RequestMethod.post);
  static final checkToken = AuthApi(path: '/check_token', method: RequestMethod.post);
}

class AdminChartApi extends ApiConfig {
  AdminChartApi(
      {super.module = 'admin',
        required super.path,
        required super.method,
        super.isAuth = false});

  static final getChartData = AdminChartApi(path: '/employer_chart', method: RequestMethod.get);
  static final getProfile = AdminChartApi(path: '/employer_account', method: RequestMethod.get);
  static final getJobs = AdminChartApi(path: '/employer_account', method: RequestMethod.get);
}

class AdminEmployerApi extends ApiConfig {
  AdminEmployerApi(
      {super.module = 'employer',
        required super.path,
        required super.method,
        super.isAuth = true});

  static final getJobs = AdminEmployerApi(path: '/account', method: RequestMethod.get);
  static final getSaveCV = AdminEmployerApi(path: '/cv_save', method: RequestMethod.get);
  static final saveCV = AdminEmployerApi(path: '/cv_save', method: RequestMethod.post);
}

class JobApi extends ApiConfig {
  JobApi(
      {super.module = 'general',
        required super.path,
        required super.method,
        super.isAuth = true});

  static final getJobDetail = JobApi(path: '/job', method: RequestMethod.get);
}

class EmployerJobApi extends ApiConfig {
  EmployerJobApi(
      {super.module = 'employer',
        required super.path,
        required super.method,
        super.isAuth = false});

  static final enableDisbaleJob = EmployerJobApi(path: '/job/on_or_off', method: RequestMethod.put);
  static final getCvByJob = EmployerJobApi(path: '/application_by_job', method: RequestMethod.get);
}