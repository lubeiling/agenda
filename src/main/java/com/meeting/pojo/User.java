package com.meeting.pojo;

import java.util.List;

/**
 * User: FlowingFire
 * Date: 2017/9/21 0021
 * Time: 22:18
 * Description:
 */
public class User  {
    private String username;
    private String password;
    private String email;
    private String phone;
    List<Agenda> attendgendas;
    List<Agenda> createdgendas;


    public User(String jjjj) {
        super();
        this.username=jjjj;
    }
    public User(){
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Agenda> getAttendgendas() {
        return attendgendas;
    }

    public void setAttendgendas(List<Agenda> attendgendas) {
        this.attendgendas = attendgendas;
    }

    public List<Agenda> getCreatedgendas() {
        return createdgendas;
    }

    public void setCreatedgendas(List<Agenda> createdgendas) {
        this.createdgendas = createdgendas;
    }


}
