package com.example.berna.cicekse2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Berna on 29.10.2017.
 */

public class UserInfo {
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("profileURL")
    @Expose
    private String profileURL;
    @SerializedName("photoURL")
    @Expose
    private String photoURL;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("DogumTarihi")
    @Expose
    private String DogumTarihi;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("emailVerified")
    @Expose
    private String emailVerified;
    @SerializedName("HesabTuru")
    @Expose
    private String HesabTuru;
    @SerializedName("OnayLinki")
    @Expose
    private String OnayLinki;
    @SerializedName("IpAdresi")
    @Expose
    private String IpAdresi;
    @SerializedName("Password")
    @Expose
    private String Password;


    public UserInfo(String identifier,String profileURL, String photoURL, String displayName, String firstName, String lastName, String gender, String language, String dogumTarihi, String email, String emailVerified, String hesabTuru, String onayLinki, String ipAdresi,String Password) {
        this.identifier = identifier;
        this.profileURL = profileURL;
        this.photoURL = photoURL;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.language = language;
        this.DogumTarihi = dogumTarihi;
        this.email = email;
        this.emailVerified = emailVerified;
        this.HesabTuru = hesabTuru;
        this.OnayLinki = onayLinki;
        this.IpAdresi = ipAdresi;
        this.Password = Password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDogumTarihi() {
        return DogumTarihi;
    }

    public void setDogumTarihi(String dogumTarihi) {
        DogumTarihi = dogumTarihi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getHesabTuru() {
        return HesabTuru;
    }

    public void setHesabTuru(String hesabTuru) {
        HesabTuru = hesabTuru;
    }

    public String getOnayLinki() {
        return OnayLinki;
    }

    public void setOnayLinki(String onayLinki) {
        OnayLinki = onayLinki;
    }

    public String getIpAdresi() {
        return IpAdresi;
    }

    public void setIpAdresi(String ipAdresi) {
        IpAdresi = ipAdresi;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
