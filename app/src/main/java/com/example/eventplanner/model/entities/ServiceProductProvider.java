package com.example.eventplanner.model.entities;

import java.util.HashSet;
import java.util.Set;

public class ServiceProductProvider extends User{

    private String companyName;
    private String companyDescription;

    private Set<String> images=new HashSet<String>();

    public ServiceProductProvider(String name, String lastName, String email, String password) {
        super(name, lastName, email, password);
    }
    public ServiceProductProvider(User user) {
        super(user.getName(),user.getLastName(),user.getEmail(),user.getPassword(),user.getCity(),user.getAddress(),user.getAddressNum(),user.getPhoneNumber(),user.getProfileImage());
    }
    public ServiceProductProvider(String name, String lastName, String email, String password, String city,
                                  String address, String addressNum, String phoneNumber, String profileImage, String companyName,
                                  String companyDescription, Set<String> images) {
        super(name, lastName, email, password, city, address, addressNum, phoneNumber, profileImage);
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.images = images;
    }

    /*public ServiceProductProvider(SppRegistrationDTO spp) {
        super(spp.getNameSPP(), spp.getLastNameSPP(), spp.getEmailSPP(), spp.getPasswordSPP(),
                spp.getCitySPP(), spp.getAddressSPP(), spp.getAddressNumSPP(), spp.getPhoneNumberSPP(), null);
        this.companyName = spp.getCompanyNameSPP();
        this.companyDescription = spp.getCompanyDescriptionSPP();
        this.images = null;
    }*/
    public ServiceProductProvider(QuickUser user) {
        super(user.getName(),user.getLastName(),user.getEmail(),user.getPassword());
    }
}
