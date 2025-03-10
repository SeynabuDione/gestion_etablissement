package com.groupeisi.g_etablissement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class menuController implements Initializable {

    @FXML
    private Button btnclasse;

    @FXML
    private Button btnetudiant;

    @FXML
    private Button btnprofesseur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (btnclasse == null || btnetudiant == null || btnprofesseur == null) {
            System.out.println("Un ou plusieurs boutons ne sont pas injectés.");
        } else {
            btnclasse.setOnAction(event -> openClasseScene());
            btnetudiant.setOnAction(event -> openEtudiantScene());
            btnprofesseur.setOnAction(event -> openProfScene());
        }
    }

    private void openClasseScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/classe.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestion des Classes");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openEtudiantScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/etudiant.fxml"));
            fxmlLoader.setController(new EtudiantController());
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestion des Étudiants");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openProfScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/professeur.fxml"));
            fxmlLoader.setController(new ProfController());
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestion des Professeurs");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
