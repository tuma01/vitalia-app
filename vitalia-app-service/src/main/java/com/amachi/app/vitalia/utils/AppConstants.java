package com.amachi.app.vitalia.utils;

import org.springframework.data.domain.Sort.Direction;

public class AppConstants {

    public static final String HEADER_MISSING = "has.msg.header.missing";
    public static final String HEADER_FORMAT_ERROR = "has.msg.header.format.error";
    public static final String PARAM_FORMAT_ERROR = "has.msg.format.error";
    public static final String ACCEPT_VERSION_NOT_SUPPORTED = "has.msg.header.version.supported";
    public static final String ACCEPT_NOT_SUPPORTED = "has.msg.header.version.format.supported";
    public static final String INTERNAL_SERVER_ERROR = "has.msg.internal.server.error";
    public static final String OBJECT_NOT_FOUND = "has.msg.object.not.found";
    public static final String UNHANDLED_ERROR = "has.msg.unhandler.error";

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final Direction DEFAULT_SORT_DIRECTION = Direction.ASC;

    public static final String USER_PATIENT = "patient";
    public static final String USER_DOCTOR = "doctor";
    public static final String USER_NURSE = "nurse";
    public static final String USER_ADMIN = "admin";


    private AppConstants() {
    }
}