package com.play.openapi.web.master.pojo;

import java.io.Serializable;

public class Role implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.ID
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.NAME
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.REMARK
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.STATUS
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table role
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.ID
     *
     * @return the value of role.ID
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.ID
     *
     * @param id the value for role.ID
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.NAME
     *
     * @return the value of role.NAME
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.NAME
     *
     * @param name the value for role.NAME
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.REMARK
     *
     * @return the value of role.REMARK
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.REMARK
     *
     * @param remark the value for role.REMARK
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.STATUS
     *
     * @return the value of role.STATUS
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.STATUS
     *
     * @param status the value for role.STATUS
     *
     * @mbggenerated Tue Jun 09 14:25:05 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}