package com.example.eventplanner.model.registration;

public class SppRegistrationRequest {
    private String nameSPP;

    private String lastNameSPP;

    private String emailSPP;

    private String passwordSPP;

    private String citySPP;

    private String addressSPP;

    private String addressNumSPP;

    private String phoneNumberSPP;

    private String companyNameSPP;

    private String companyDescriptionSPP;

    public SppRegistrationRequest(String nameSPP, String lastNameSPP, String emailSPP, String passwordSPP,
                                  String citySPP, String addressSPP, String addressNumSPP, String phoneNumberSPP,
                                  String companyNameSPP, String companyDescriptionSPP) {
        this.nameSPP = nameSPP;
        this.lastNameSPP = lastNameSPP;
        this.emailSPP = emailSPP;
        this.passwordSPP = passwordSPP;
        this.citySPP = citySPP;
        this.addressSPP = addressSPP;
        this.addressNumSPP = addressNumSPP;
        this.phoneNumberSPP = phoneNumberSPP;
        this.companyNameSPP = companyNameSPP;
        this.companyDescriptionSPP = companyDescriptionSPP;
    }

    // Getteri i setteri
    public String getNameSPP() {
        return nameSPP;
    }

    public void setNameSPP(String nameSPP) {
        this.nameSPP = nameSPP;
    }

    public String getLastNameSPP() {
        return lastNameSPP;
    }

    public void setLastNameSPP(String lastNameSPP) {
        this.lastNameSPP = lastNameSPP;
    }

    public String getEmailSPP() {
        return emailSPP;
    }

    public void setEmailSPP(String emailSPP) {
        this.emailSPP = emailSPP;
    }

    public String getPasswordSPP() {
        return passwordSPP;
    }

    public void setPasswordSPP(String passwordSPP) {
        this.passwordSPP = passwordSPP;
    }

    public String getCitySPP() {
        return citySPP;
    }

    public void setCitySPP(String citySPP) {
        this.citySPP = citySPP;
    }

    public String getAddressSPP() {
        return addressSPP;
    }

    public void setAddressSPP(String addressSPP) {
        this.addressSPP = addressSPP;
    }

    public String getAddressNumSPP() {
        return addressNumSPP;
    }

    public void setAddressNumSPP(String addressNumSPP) {
        this.addressNumSPP = addressNumSPP;
    }

    public String getPhoneNumberSPP() {
        return phoneNumberSPP;
    }

    public void setPhoneNumberSPP(String phoneNumberSPP) {
        this.phoneNumberSPP = phoneNumberSPP;
    }

    public String getCompanyNameSPP() {
        return companyNameSPP;
    }

    public void setCompanyNameSPP(String companyNameSPP) {
        this.companyNameSPP = companyNameSPP;
    }

    public String getCompanyDescriptionSPP() {
        return companyDescriptionSPP;
    }

    public void setCompanyDescriptionSPP(String companyDescriptionSPP) {
        this.companyDescriptionSPP = companyDescriptionSPP;
    }
}
