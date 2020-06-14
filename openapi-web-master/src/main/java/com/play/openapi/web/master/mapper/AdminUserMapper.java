package com.play.openapi.web.master.mapper;

import com.play.openapi.web.master.pojo.AdminUser;

public interface AdminUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_user
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_user
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    int insert(AdminUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_user
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    int insertSelective(AdminUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_user
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    AdminUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_user
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    int updateByPrimaryKeySelective(AdminUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_user
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    int updateByPrimaryKey(AdminUser record);

    AdminUser selectByEmail(String email);
}