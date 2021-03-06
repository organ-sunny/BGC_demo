package com.sunny.entity;

import com.sunny.dto.vo.UserVO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_users")
public class UserEntity extends ParentEntity<UserVO> {

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

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "CREATEDTIME")
    private Date createdTime;

    @Column(name = "UPDATEDTIME")
    private Date updatedTime;

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
                ", token='" + token + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                '}';
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

//    /**
//     * Entity转换成VO，定义返回内容
//     */
//    public UserVO getVO(){
//        try {
//            Class<UserVO> userVOClass = UserVO.class;
//            Field[] declaredFields = userVOClass.getDeclaredFields();
//            UserVO userVO = userVOClass.newInstance();
//            for (Field field : declaredFields){
//                Class<UserEntity> userEntityClass = UserEntity.class;
//                Field[] declaredFields1 = userEntityClass.getDeclaredFields();
//                for (Field field1 : declaredFields1){
//                    if(field.getName().equals(field1.getName()) && field.getType() == field1.getType()){
//                        field.setAccessible(true);
//                        field.set(userVO,field1.get(this));
//                    }
//                }
//            }
//            return userVO;
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException("转换出错，错误原因：" + e.getMessage());
//        }
//    }
}
