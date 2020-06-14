package com.play.openapi.web.master.shrio.realm;

import com.play.openapi.web.master.mapper.RoleMapper;
import com.play.openapi.web.master.pojo.AdminUser;
import com.play.openapi.web.master.service.AdminUserService;
import com.play.openapi.web.master.service.MenuService;
import com.play.openapi.web.master.util.ShiroUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UsersRealm extends AuthorizingRealm {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleMapper roleMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo( PrincipalCollection principalCollection ) {
        List <String> roles = roleMapper.findRoleByUid(ShiroUtil.getUserId());
        List <String> perms = menuService.findPerms();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //角色
        info.addRoles(roles);
        //权限
        info.addStringPermissions(perms);
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken authenticationToken ) throws AuthenticationException {
        String email = authenticationToken.getPrincipal().toString();
        String password = new String((char[]) authenticationToken.getCredentials());
        AdminUser adminUser = adminUserService.findUser(email);
        if (adminUser == null) {
            throw new AccountException("账号不存在");
        }
        if (!adminUser.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("密码错误");
        }
        if (adminUser.getStatus() == 0) {
            throw new LockedAccountException("账号被冻结");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(adminUser, password, this.getName());
        return info;
    }
}
