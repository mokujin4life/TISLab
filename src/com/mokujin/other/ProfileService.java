package com.mokujin.other;

import java.util.List;

public class ProfileService {
    private ProfileDao profileDao = new ProfileDao();
    private int passwordWordsQuantity = 0;

    public int getPasswordWordsQuantity() {
        return passwordWordsQuantity;
    }

    public void setPasswordWordsQuantity(int passwordWordsQuantity) {
        this.passwordWordsQuantity = passwordWordsQuantity;
    }

    public boolean add(Profile profile) {
        return profileDao.add(profile);
    }

    public boolean delete(Profile profile){
        return profileDao.delete(profile);
    }

    public boolean disableUser(Profile profile){
        return profileDao.disableUser(profile);
    }

    public List<Profile> getProfiles(){
        return profileDao.getProfiles();
    }

    public void createProfiles(){
        profileDao.createProfiles();
    }
}
