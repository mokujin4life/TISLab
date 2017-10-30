package com.mokujin.controllers;

import com.mokujin.other.Profile;
import com.mokujin.other.ProfileService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField quantityTF;
    @FXML
    private Label completionLabel;


    private Profile profile;
    private ProfileService profileService;


    void initialize() {
    }

    void initData(Profile profile, ProfileService profileService) {
        this.profile = profile;
        this.profileService = profileService;
        usernameLabel.setText(profile.getUsername());
    }

    public void changePassword(ActionEvent actionEvent) {
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("../ui/change_password.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Change password");
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();

            ChangePasswordController controller = loader.<ChangePasswordController>getController();
            controller.initData(profile, profileService, "../ui/admin.fxml");
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logOut(ActionEvent actionEvent) {
        FXMLLoader  loader;
        try {
            loader = new FXMLLoader(getClass().getResource("../ui/welcome.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Welcome");
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllUsers(ActionEvent actionEvent){
        FXMLLoader  loader;
        try {
            loader = new FXMLLoader(getClass().getResource("../ui/user_list.fxml"));
            Stage stage = new Stage();
            stage.setTitle("User list");
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
            UsersController controller = loader.<UsersController>getController();
            controller.initData(profile, profileService, "../ui/user.fxml");
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setPasswordSymbolsQuantity(ActionEvent actionEvent){
        profileService.setPasswordWordsQuantity(Integer.parseInt(quantityTF.getText()));
        completionLabel.setText("Done. Password words quantity equals "+ quantityTF.getText());
    }

}
