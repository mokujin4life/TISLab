package com.mokujin.controllers;


import com.mokujin.other.Profile;
import com.mokujin.other.ProfileService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ChangePasswordController {
    private Profile profile;

    @FXML
    private TextField oldPassword;
    @FXML
    private TextField oldPasswordRepeating;
    @FXML
    private TextField newPassword;
    @FXML
    private Label message;

    private ProfileService profileService;
    private String fxmlPath;

    void initialize() {
    }

    void initData(Profile profile, ProfileService profileService, String fxmlPath) {
        this.profile = profile;
        this.profileService = profileService;
        this.fxmlPath = fxmlPath;
    }

    public void changePassword(ActionEvent actionEvent) {
        if (Objects.equals(oldPassword.getText(), profile.getPassword())) {
            if (Objects.equals(oldPassword.getText(), oldPasswordRepeating.getText())) {
                if (newPassword.getText().length() > profileService.getPasswordWordsQuantity()) {
                    profileService.delete(profile);
                    profile.setPassword(newPassword.getText());
                    profileService.add(profile);
                    message.setStyle("-fx-text-fill: green;");
                    message.setText("           Password changed successfully!       ");
                } else {
                    message.setStyle("-fx-text-fill: red;");
                    message.setText("You need at least " + (profileService.getPasswordWordsQuantity()+1) + " symbol(s) for new password");
                }
            } else {
                message.setStyle("-fx-text-fill: red;");
                message.setText("                Passwords don't match");
            }
        } else {
            message.setStyle("-fx-text-fill: red;");
            message.setText("  There's no correct old password. Try again. ");
        }
    }

    public void backToProfile(ActionEvent actionEvent) {
        if (fxmlPath.contains("user")){
            FXMLLoader loader;
            try {
                loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Stage stage = new Stage();
                stage.setTitle("Profile");
                stage.setScene(new Scene((Pane) loader.load()));
                stage.show();

                UserController controller = loader.<UserController>getController();
                controller.initData(profile, profileService);
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FXMLLoader loader;
            try {
                loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Stage stage = new Stage();
                stage.setTitle("Profile");
                stage.setScene(new Scene((Pane) loader.load()));
                stage.show();

                AdminController controller = loader.<AdminController>getController();
                controller.initData(profile, profileService);
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
