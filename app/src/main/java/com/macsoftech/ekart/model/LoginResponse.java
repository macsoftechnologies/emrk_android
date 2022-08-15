package com.macsoftech.ekart.model;

import java.util.ArrayList;

public class LoginResponse {
    public String _id;
    public String firstName;
    public String lastName;
    private String entityImage;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getAvailableLocation() {
        return availableLocation;
    }

    public void setAvailableLocation(ArrayList<String> availableLocation) {
        this.availableLocation = availableLocation;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAltNumber() {
        return altNumber;
    }

    public void setAltNumber(String altNumber) {
        this.altNumber = altNumber;
    }

    public String getChooseLanguage() {
        return chooseLanguage;
    }

    public void setChooseLanguage(String chooseLanguage) {
        this.chooseLanguage = chooseLanguage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String mobileNum;
    public String entityName;
    public String password;
    public ArrayList<String> availableLocation;
    public String emailId;
    public String altNumber;
    public String chooseLanguage;
    public String userId;
    public String createdAt;
    public String updatedAt;

    public String getEntityImage() {
        return entityImage;
    }

    public void setEntityImage(String entityImage) {
        this.entityImage = entityImage;
    }
}