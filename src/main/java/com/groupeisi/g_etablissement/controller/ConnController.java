package com.groupeisi.g_etablissement.controller;

import com.groupeisi.g_etablissement.dao.ConnImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnController {

    @FXML
    private TextField fdemail;

    @FXML
    private PasswordField tfdpassword;

    private ConnImpl connImpl;

    public ConnController() {
        connImpl = new ConnImpl();
    }

    @FXML
    public void connexion() {
        String email = fdemail.getText();
        String password = tfdpassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        if (connImpl.connexion(email, password)) {
            // Redirection vers la page menu.fxml
            loadMenuPage();
        } else {
            showAlert("Erreur", "Email ou mot de passe incorrect.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadMenuPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) fdemail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page menu.");
        }
    }
}