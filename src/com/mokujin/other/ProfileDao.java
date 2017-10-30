package com.mokujin.other;


import java.util.ArrayList;
import java.util.List;

public class ProfileDao {
    private List<Profile> profiles = new ArrayList<>();

    public boolean add(Profile profile){
        return profiles.add(profile);
    }

    public boolean delete(Profile profile){
        return profiles.remove(profile);
    }

    public boolean disableUser(Profile profile){
        profiles.remove(profile);
        profile.setDisabled(true);
        return profiles.add(profile);
    }
    public List<Profile> getProfiles() {
        return profiles;
    }

    public void createProfiles(){
        profiles.add(new Profile("user1", "1111"));
        profiles.add(new Profile("user2", "2222"));
        profiles.add(new Profile("user3", "3333"));
        profiles.add(new Profile("ADMIN", "0000"));


    }
}
