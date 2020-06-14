package com.play.openapi.web.master.service;

import com.play.openapi.web.master.pojo.AdminUser;

public interface AdminUserService {

    AdminUser findUser( String email);
}
