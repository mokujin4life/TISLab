package com.mokujin.controllers;

import com.mokujin.other.Profile;
import com.mokujin.other.ProfileService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class WelcomeController {
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private Label firstLabel;
    @FXML
    private Label secondLabel;
    @FXML
    private Label thirdLabel;
    @FXML
    private Button loginBtn;
    @FXML
    private Label informationLabel;
    @FXML
    private Label informationLabelTwo;
    @FXML
    private Hyperlink link;
    @FXML
    private ImageView imageView;
    private static ProfileService profileService;
    private int tryCount = 0;

    static {
        profileService = new ProfileService();
        profileService.createProfiles();
    }

    public void logIn(ActionEvent actionEvent) {
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        List<Profile> profiles = profileService.getProfiles();
        Profile profile = new Profile(username, password);
        if (isContains(profiles, profile)) {
            if (Objects.equals(username, "ADMIN")) {
                FXMLLoader loader;
                try {
                    loader = new FXMLLoader(getClass().getResource("../ui/admin.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Profile");
                    stage.setScene(new Scene((Pane) loader.load(), 500, 500));
                    stage.show();

                    AdminController controller = loader.<AdminController>getController();
                    controller.initData(profile, profileService);
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FXMLLoader loader;
                try {
                    loader = new FXMLLoader(getClass().getResource("../ui/user.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Profile");
                    stage.setScene(new Scene(loader.load(), 500, 500));
                    stage.show();

                    UserController controller = loader.<UserController>getController();
                    controller.initData(profile, profileService);
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            tryCount++;
            if (tryCount >= 3) {
                firstLabel.setText(" There comes out third attempt.  ");
                secondLabel.setText("Come back later.");
                thirdLabel.setText("  Attempts run out!  ");
                usernameTF.setVisible(false);
                passwordTF.setVisible(false);
                loginBtn.setText(" Quit ");
                if (tryCount == 4) {
                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                    stage.close();
                }
            } else if (isDisabled(profiles, profile)) {
                firstLabel.setText("You were banned by administrator.");
                secondLabel.setText("      Sorry!     ");
                thirdLabel.setText(" Maybe another time. ");
            } else {
                firstLabel.setText("Something wrong with credentials.");
                secondLabel.setText("   Try again.   ");
                thirdLabel.setText((3 - tryCount) + " attempts remaining!");
            }
        }

    }

    private boolean isContains(List<Profile> profiles, Profile profile) {
        for (Profile innerProfile : profiles) {
            if (Objects.equals(innerProfile, profile)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDisabled(List<Profile> profiles, Profile profile) {
        for (Profile innerProfile : profiles) {
            if (innerProfile.getUsername().equals(profile.getUsername()) && innerProfile.isDisabled() != profile.isDisabled()) {
                return true;
            }
        }
        return false;
    }

    public void getDetails(ActionEvent actionEvent) {
        informationLabel.setText("Made by Ivan Gulin. (c) All rights reserved.");
        informationLabelTwo.setText("Read More: ");
        link.setText("Click!");
    }


}
