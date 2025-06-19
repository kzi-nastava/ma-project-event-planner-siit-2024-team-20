package com.example.eventplanner.model.registration;

public class EoRegistrationRequest {
    private String nameEO;
    private String lastNameEO;
    private String emailEO;
    private String passwordEO;
    private String cityEO;
    private String addressEO;
    private String addressNumEO;
    private String phoneNumberEO;

    public EoRegistrationRequest(String nameEO, String lastNameEO, String emailEO, String passwordEO, String cityEO, String addressEO, String addressNumEO, String phoneNumberEO) {
        this.nameEO = nameEO;
        this.lastNameEO = lastNameEO;
        this.emailEO = emailEO;
        this.passwordEO = passwordEO;
        this.cityEO = cityEO;
        this.addressEO = addressEO;
        this.addressNumEO = addressNumEO;
        this.phoneNumberEO = phoneNumberEO;
    }
    public String getNameEO() {
        return nameEO;
    }

    public void setNameEO(String nameEO) {
        this.nameEO = nameEO;
    }

    public String getLastNameEO() {
        return lastNameEO;
    }

    public void setLastNameEO(String lastNameEO) {
        this.lastNameEO = lastNameEO;
    }

    public String getEmailEO() {
        return emailEO;
    }

    public void setEmailEO(String emailEO) {
        this.emailEO = emailEO;
    }

    public String getPasswordEO() {
        return passwordEO;
    }

    public void setPasswordEO(String passwordEO) {
        this.passwordEO = passwordEO;
    }

    public String getCityEO() {
        return cityEO;
    }

    public void setCityEO(String cityEO) {
        this.cityEO = cityEO;
    }

    public String getAddressEO() {
        return addressEO;
    }

    public void setAddressEO(String addressEO) {
        this.addressEO = addressEO;
    }

    public String getAddressNumEO() {
        return addressNumEO;
    }

    public void setAddressNumEO(String addressNumEO) {
        this.addressNumEO = addressNumEO;
    }

    public String getPhoneNumberEO() {
        return phoneNumberEO;
    }

    public void setPhoneNumberEO(String phoneNumberEO) {
        this.phoneNumberEO = phoneNumberEO;
    }
}
