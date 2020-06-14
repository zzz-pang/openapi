package com.play.openapi.web.master.service;

import com.play.openapi.web.master.pojo.Menu;
import com.play.openapi.web.master.util.R;

import java.util.List;

public interface MenuService {
     R menu();
     List<String> findPerms();
}
