package com.sunny.dto;

import com.sunny.entity.UserEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.util.StringUtil;

import java.lang.reflect.Field;

public class UserDTO {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", telephoneNum='" + telephoneNum + '\'' +
                ", mailCode=" + mailCode +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    private String newPassword;
    private String telephoneNum;
    private Integer mailCode;
    private String email;
    private String sex;
    private Integer age;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMailCode() {
        return mailCode;
    }

    public void setMailCode(Integer mailCode) {
        this.mailCode = mailCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    /**
     * 入参校验
     */
    public void registerCheckParam() {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            throw new ParamErrorException("用户名或密码不能为空！");
        }
    }

    public void loginCheckParam() {
        if (StringUtil.isEmpty(password)) {
            throw new ParamErrorException("密码不能为空！");
        }
    }

    /**
     * 将入参DTO转换为Entity
     */
    public UserEntity getEntity() {
        try {
            Class<UserEntity> userEntityClass = UserEntity.class;
            Field[] declaredFields = userEntityClass.getDeclaredFields();
            UserEntity userEntity = userEntityClass.newInstance();

            for (Field field : declaredFields) {
                Class<UserDTO> userDTOClass = UserDTO.class;
                Field[] declaredFields1 = userDTOClass.getDeclaredFields();
                for (Field field1 : declaredFields1) {
                    if (field.getName().equals(field1.getName())
                            && field.getType() == field1.getType()) {
                        field.setAccessible(true);
                        field.set(userEntity, field1.get(this));
                    }
                }
            }
            return userEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("转换出错，错误原因：" + e.getMessage());
        }
    }
}