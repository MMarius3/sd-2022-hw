package com.application.controller;

// TODO -> package separat de url mappings organizat pe controllere
public class UrlMappings {

    public static final String URL_PREFIX = "/clinic";

    public static final String SECRETARY = URL_PREFIX + "/secretary";
    public static final String DOCTOR = URL_PREFIX + "/doctor";
    public static final String ADMINISTRATOR = URL_PREFIX + "/administrator";

    public static final String GET_USER_ACCOUNT_BY_ID = "/getUserAccountById";
    public static final String GET_USER_ACCOUNT_BY_NAME = "/getUserAccountByName";
    public static final String CREATE_USER_ACCOUNT = "/createUserAccount";
    public static final String UPDATE_USER_ACCOUNT = "/updateUserAccount";

    public static final String CREATE_CONSULTATION = "/createConsultation";
    public static final String GET_CONSULTATIONS_BY_PATIENT_ID = "/getConsultationsByPatientId";
    public static final String UPDATE_CONSULTATION = "/updateConsultation";
    public static final String DELETE_CONSULTATION = "/deleteConsultation";

    public static final String CREATE_PATIENT = "/createPatient";
    public static final String UPDATE_PATIENT = "/updatePatient";

    public static final String AUTH = URL_PREFIX + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
}
