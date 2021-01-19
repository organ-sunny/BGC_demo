package com.sunny.entity;

import javax.persistence.*;
import com.sunny.vo.UserVO;

import java.lang.reflect.Field;

@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TEL")
    private String telephoneNum;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PET_ID")
    private String pet_id;

    @Column(name = "TOKEN")
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPet_id() {
        return pet_id;
    }

    public void setPet_id(String pet_id) {
        this.pet_id = pet_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", telephoneNum='" + telephoneNum + '\'' +
                ", email='" + email + '\'' +
                ", pet_id='" + pet_id + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    /**
     * Entity转换成VO，定义返回内容
     */
    public UserVO getVO(){
        try {
            Class<UserVO> userVOClass = UserVO.class;
            Field[] declaredFields = userVOClass.getDeclaredFields();
            UserVO userVO = userVOClass.newInstance();
            for (Field field : declaredFields){
                Class<UserEntity> userEntityClass = UserEntity.class;
                Field[] declaredFields1 = userEntityClass.getDeclaredFields();
                for (Field field1 : declaredFields1){
                    if(field.getName().equals(field1.getName()) && field.getType() == field1.getType()){
                        field.setAccessible(true);
                        field.set(userVO,field1.get(this));
                    }
                }
            }
            return userVO;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("转换出错，错误原因：" + e.getMessage());
        }
    }
}
