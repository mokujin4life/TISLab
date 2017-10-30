package com.mokujin.controllers;


import com.mokujin.other.Profile;
import com.mokujin.other.ProfileService;
import com.sun.rowset.internal.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class UsersController {
    private Profile profile;
    private ProfileService profileService;
    private String fxmlPath;
    @FXML
    private VBox vBox;
    private TextField usernameTF;
    private Label status;

    void initialize() {
    }

    void initData(Profile profile, ProfileService profileService, String fxmlPath) {
        this.profile = profile;
        this.profileService = profileService;
        this.fxmlPath = fxmlPath;
        init();
    }

    private void init() {
        List<Profile> profiles = profileService.getProfiles();
        int checkboxCounter = 0;

        HBox headerHBox = new HBox();
        headerHBox.setStyle("-fx-border-color: #112380;");
        Label usernameHeader = new Label("Username");
        usernameHeader.setStyle("-fx-font-family: castellar;");
        headerHBox.getChildren().add(usernameHeader);
        usernameHeader.setPrefWidth(165);
        headerHBox.setMargin(usernameHeader, new Insets(5, 0, 5, 60));

        Label disableHeader = new Label("Is disable?");
        disableHeader.setStyle("-fx-font-family: castellar;");
        headerHBox.getChildren().add(disableHeader);
        disableHeader.setPrefWidth(150);
        headerHBox.setMargin(disableHeader, new Insets(5, 0, 5, 0));


        Label selectHeader = new Label("Select");
        selectHeader.setStyle("-fx-font-family: castellar;");
        headerHBox.getChildren().add(selectHeader);
        headerHBox.setMargin(selectHeader, new Insets(5, 15, 5, 15));

        vBox.getChildren().add(headerHBox);
        for (Profile innerProfile : profiles) {
            if (Objects.equals(innerProfile.getUsername(), "ADMIN")) {
                continue;
            }

            HBox hBox = new HBox();
            hBox.setStyle("-fx-border-color: #112380;");
            Label usernameLabel = new Label(innerProfile.getUsername());
            usernameLabel.setPrefWidth(140);
            hBox.getChildren().add(usernameLabel);
            hBox.setMargin(usernameLabel, new Insets(5, 0, 5, 80));
            Label disabledLabel = new Label(String.valueOf(innerProfile.isDisabled()));
            disabledLabel.setPrefWidth(175);
            hBox.getChildren().add(disabledLabel);
            hBox.setMargin(disabledLabel, new Insets(5, 0, 5, 50));
            CheckBox checkBox = new CheckBox();
            checkBox.setPrefWidth(120);
            checkboxCounter++;
            hBox.getChildren().add(checkBox);
            hBox.setMargin(checkBox, new Insets(5, 5, 5, 25));
            vBox.getChildren().add(hBox);
        }
        Button disableBtn = new Button("Disable/Enable 'em");
        disableBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                disableOrEnable(e);
            }
        });
        disableBtn.setStyle("-fx-background-color: #112380; -fx-background-radius: 15px; -fx-font-family: cambria; " +
                "-fx-font-size: 16px; -fx-text-fill: #ffffff; ");
        disableBtn.setPadding(new Insets(3, 2, 2, 2));

        HBox hBox = new HBox();
        usernameTF = new TextField();
        usernameTF.setPromptText("username");
        Button addNewUser = new Button("Add new user");
        status = new Label();
        addNewUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addNewUser(e);
            }
        });
        addNewUser.setStyle("-fx-background-color: #112380; -fx-background-radius: 15px; -fx-font-family: cambria; " +
                "-fx-font-size: 16px; -fx-text-fill: #ffffff; ");
        addNewUser.setPadding(new Insets(3, 2, 2, 2));

        hBox.getChildren().add(addNewUser);
        hBox.getChildren().add(usernameTF);
        Button back = new Button("Back to profile");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                backToProfile(e);
            }
        });
        back.setStyle("-fx-background-color: #112380; -fx-background-radius: 15px; -fx-font-family: cambria; " +
                "-fx-font-size: 16px; -fx-text-fill: #ffffff; ");
        back.setPadding(new Insets(3, 2, 2, 2));


        vBox.getChildren().add(disableBtn);
        vBox.setMargin(disableBtn, new Insets(10, 10, 10, 345));
        hBox.setMargin(usernameTF, new Insets(0, 0, 0, 20));
        vBox.getChildren().add(hBox);
        vBox.setMargin(hBox, new Insets(10, 10, 10, 140));
        vBox.getChildren().add(status);
        vBox.getChildren().add(back);
        vBox.setMargin(back, new Insets(10, 10, 10, 200));

    }

    public void disableOrEnable(ActionEvent actionEvent) {
        int checkBoxCounter = 0;
        for (Node node : vBox.getChildren()) {
            if (!(node instanceof HBox)) {
                break;
            }
            if (!(((HBox) node).getChildren().get(2) instanceof CheckBox)) {
                continue;
            }
            CheckBox checkBox = (CheckBox) ((HBox) node).getChildren().get(2);
            if (checkBox.isSelected()) {
                Label label = (Label) ((HBox) node).getChildren().get(0);
                List<Profile> profiles = profileService.getProfiles();
                int size = profiles.size();
                for (int i = 0; i < size; i++) {
                    if (label.getText().equals(profiles.get(i).getUsername())) {
                        Profile temp = profiles.get(i);
                        temp.setDisabled(!profiles.get(i).isDisabled());
                        profileService.delete(profiles.get(i));
                        profileService.add(temp);
                        size--;
                    }
                }
            }
        }
        vBox.getChildren().clear();
        init();
    }

    public void addNewUser(ActionEvent actionEvent) {
        Profile profile = new Profile(usernameTF.getText(), "");
        if (profileService.add(profile)) {
            status.setStyle("-fx-text-fill: green;");
            status.setText("New user added successfully.");
        }
        vBox.getChildren().clear();
        init();
    }

    public void backToProfile(ActionEvent actionEvent) {
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("../ui/admin.fxml"));
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
