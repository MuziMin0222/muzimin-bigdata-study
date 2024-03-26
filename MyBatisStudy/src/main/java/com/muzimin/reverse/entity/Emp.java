package com.muzimin.reverse.entity;

public class Emp {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_emp.e_id
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    private Integer eId;

    @Override
    public String toString() {
        return "Emp{" +
                "eId=" + eId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", dId=" + dId +
                '}';
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_emp.name
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_emp.email
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_emp.age
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    private Integer age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_emp.d_id
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    private Integer dId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_emp.e_id
     *
     * @return the value of t_emp.e_id
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public Integer geteId() {
        return eId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_emp.e_id
     *
     * @param eId the value for t_emp.e_id
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public void seteId(Integer eId) {
        this.eId = eId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_emp.name
     *
     * @return the value of t_emp.name
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_emp.name
     *
     * @param name the value for t_emp.name
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_emp.email
     *
     * @return the value of t_emp.email
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_emp.email
     *
     * @param email the value for t_emp.email
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_emp.age
     *
     * @return the value of t_emp.age
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_emp.age
     *
     * @param age the value for t_emp.age
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_emp.d_id
     *
     * @return the value of t_emp.d_id
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public Integer getdId() {
        return dId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_emp.d_id
     *
     * @param dId the value for t_emp.d_id
     *
     * @mbggenerated Fri Mar 22 17:51:30 CST 2024
     */
    public void setdId(Integer dId) {
        this.dId = dId;
    }
}