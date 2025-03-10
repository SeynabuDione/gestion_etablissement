package com.groupeisi.g_etablissement.controller;

import com.groupeisi.g_etablissement.dao.ProfImpl;
import com.groupeisi.g_etablissement.entities.Professeur;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfController implements Initializable {
    private final ProfImpl p = new ProfImpl();

    @FXML
    private TextField tfdnomproff;

    @FXML
    private TextField tfdid;

    @FXML
    private TextField tfdmatiere;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private Button btndelete;

    @FXML
    private TableView<Professeur> tableProf;

    @FXML
    private TableColumn<Professeur, Integer> tid;

    @FXML
    private TableColumn<Professeur, String> tnomprof;

    @FXML
    private TableColumn<Professeur, String> tmatiere;

    @FXML
    void delete(ActionEvent event) {
        String idText = tfdid.getText().trim();

        if (idText.isEmpty()) {
            showAlert("Erreur", "Veuillez saisir un ID valide !", Alert.AlertType.ERROR);
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous vraiment supprimer ce prof ?");
            alert.setContentText("ID du professeur : " + id);

            if (alert.showAndWait().get() == ButtonType.OK) {
                p.delete(id);
                btnsave.setDisable(false);
                loadTable();
                showAlert("Succès", "Suppression réussie pour l'ID : " + id, Alert.AlertType.INFORMATION);
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Format d'ID invalide !", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void save(ActionEvent event) {
        String nomProf = tfdnomproff.getText().trim();
        String matiere = tfdmatiere.getText().trim();

        if (nomProf.isEmpty()) {
            showAlert("Erreur", "Veuillez saisir un nom de professeur !", Alert.AlertType.ERROR);
            return;
        } else if (matiere.isEmpty()) {
            showAlert("Erreur", "Veuillez saisir une matière !", Alert.AlertType.ERROR);
            return;
        }

        Professeur professeur = new Professeur(nomProf, matiere);
        p.add(professeur);
        loadTable();
        showAlert("Succès", "Professeur ajouté avec succès.", Alert.AlertType.INFORMATION);
    }

    @FXML
    void update(ActionEvent event) {
        String idText = tfdid.getText().trim();
        String nomProf = tfdnomproff.getText().trim();
        String matiere = tfdmatiere.getText().trim();

        if (idText.isEmpty() || nomProf.isEmpty() || matiere.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs pour la mise à jour !", Alert.AlertType.ERROR);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Professeur professeur = new Professeur(id, nomProf, matiere);
            btnsave.setDisable(false);
            int rowsUpdated = p.update(professeur);

            if (rowsUpdated > 0) {
                loadTable();
                showAlert("Succès", "Mise à jour réussie pour l'ID : " + id, Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur", "Aucune mise à jour effectuée pour l'ID : " + id, Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Format d'ID invalide !", Alert.AlertType.ERROR);
        }
    }

    public void loadTable() {
        ObservableList<Professeur> ListeProf = p.getAll();
        tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tnomprof.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tmatiere.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        tableProf.setItems(ListeProf);
    }

    @FXML
    private void getData() {
        Professeur selectedProf = tableProf.getSelectionModel().getSelectedItem();
        if (selectedProf != null) {
            tfdid.setText(String.valueOf(selectedProf.getId()));
            tfdnomproff.setText(selectedProf.getNom());
            tfdmatiere.setText(selectedProf.getMatiere());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}