package com.model;

public class User {
    int id;
    String username;
    String password;
    String age;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword(){
    	return password;
    }
    public void setPassword(String password){
    	this.password=password;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

}