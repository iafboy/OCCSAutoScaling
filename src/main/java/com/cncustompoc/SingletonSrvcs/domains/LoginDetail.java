package com.cncustompoc.SingletonSrvcs.domains;

public class LoginDetail {
    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String token;
    private int type;
    private int role;
    private String group;
    private String last_password_reset_date;

    @Override
    public String toString() {
        return "LoginDetail{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                ", role=" + role +
                ", group='" + group + '\'' +
                ", last_password_reset_date=" + last_password_reset_date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLast_password_reset_date() {
        return last_password_reset_date;
    }

    public void setLast_password_reset_date(String last_password_reset_date) {
        this.last_password_reset_date = last_password_reset_date;
    }
}
