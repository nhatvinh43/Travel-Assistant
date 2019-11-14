package com.example.doan.data.model;

public class RegisterResponse {
    private int id,gender;
    private boolean email_verified,phone_verified;
    private String username,full_name,email,phone,address,dob;

    public RegisterResponse(int id, int gender, boolean email_verified, boolean phone_verified,
                            String username, String full_name, String email,
                            String phone, String address, String dob) {
        this.id = id;
        this.gender = gender;
        this.email_verified = email_verified;
        this.phone_verified = phone_verified;
        this.username = username;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public int getGender() {
        return gender;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public boolean isPhone_verified() {
        return phone_verified;
    }

    public String getUsername() {
        return username;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDob() {
        return dob;
    }
}
