package com.groupeisi.g_etablissement.controller;

import com.groupeisi.g_etablissement.dao.ClasseImpl;
import com.groupeisi.g_etablissement.dao.EtudiantImpl;
import com.groupeisi.g_etablissement.entities.Classe;
import com.groupeisi.g_etablissement.entities.Etudiant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class EtudiantController implements Initializable {

    private EtudiantImpl etudiantDAO = new EtudiantImpl();
    private ClasseImpl classeDAO = new ClasseImpl();

    @FXML
    private Button btndelete, btnsave, btnupdate;

    @FXML
    private TextField tfdid, tfdnom, tfdprenom, tfdemail, tfdnumerotelephone;

    @FXML
    private DatePicker tfddatenaissance;

    @FXML
    private ComboBox<Classe> cbbnomclasse;

    @FXML
    private TableView<Etudiant> tableEtudiant;

    @FXML
    private TableColumn<Etudiant, Integer> tdid;

    @FXML
    private TableColumn<Etudiant, String> tddnom, tdprenom, tdemail, tdtelephone, tdnomclasse;

    @FXML
    private TableColumn<Etudiant, Date> tddatenaissance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        chargerNomsClasses();
    }


    @FXML
    void save(ActionEvent event) {
        String nom = tfdnom.getText().trim();
        String prenom = tfdprenom.getText().trim();
        String email = tfdemail.getText().trim();
        String telephone = tfdnumerotelephone.getText().trim();
        LocalDate dateNaissanceLocal = tfddatenaissance.getValue();
        Classe classe = cbbnomclasse.getSelectionModel().getSelectedItem();

        if (!nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty() && !telephone.isEmpty() && dateNaissanceLocal != null && classe != null) {

            java.sql.Date dateNaissance = java.sql.Date.valueOf(dateNaissanceLocal);

            Etudiant etudiant = new Etudiant(nom, prenom, email, telephone, dateNaissance, classe);
            etudiantDAO.add(etudiant);

            loadTable();
            clearFields();
            showAlert("Succès", "Étudiant ajouté avec succès !", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Veuillez remplir tous les champs !", Alert.AlertType.ERROR);
        }
    }


    @FXML
    void update(ActionEvent event) {
        String idText = tfdid.getText().trim();
        String nom = tfdnom.getText().trim();
        String prenom = tfdprenom.getText().trim();
        String email = tfdemail.getText().trim();
        String telephone = tfdnumerotelephone.getText().trim();
        LocalDate dateNaissanceLocal = tfddatenaissance.getValue();
        Classe classe = cbbnomclasse.getSelectionModel().getSelectedItem();

        if (!idText.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty() && !telephone.isEmpty() && dateNaissanceLocal != null && classe != null) {
            try {
                int id = Integer.parseInt(idText);

                java.sql.Date dateNaissance = java.sql.Date.valueOf(dateNaissanceLocal);

                Etudiant etudiant = new Etudiant(id, nom, prenom, email, telephone, dateNaissance, classe);
                etudiantDAO.update(etudiant);

                loadTable();
                clearFields();
                btnsave.setDisable(false);
                showAlert("Succès", "Étudiant mis à jour avec succès !", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Format d'ID invalide !", Alert.AlertType.ERROR);
            }
        } else {

            showAlert("Erreur", "Veuillez sélectionner un étudiant à modifier !", Alert.AlertType.ERROR);
        }
    }


    @FXML
    void delete(ActionEvent event) {
        Etudiant etudiant = tableEtudiant.getSelectionModel().getSelectedItem();
        if (etudiant != null) {
            etudiantDAO.delete(etudiant.getId());
            loadTable();
            clearFields();
            btnsave.setDisable(false);
            showAlert("Succès", "Étudiant supprimé avec succès !", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Veuillez sélectionner un étudiant à supprimer !", Alert.AlertType.ERROR);
        }
    }


    @FXML
    void getData(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Etudiant etudiant = tableEtudiant.getSelectionModel().getSelectedItem();
            if (etudiant != null) {
                tfdid.setText(String.valueOf(etudiant.getId()));
                tfdnom.setText(etudiant.getNom());
                tfdprenom.setText(etudiant.getPrenom());
                tfdemail.setText(etudiant.getEmail());
                tfdnumerotelephone.setText(etudiant.getTelephone());


                java.util.Date dateNaissance = new java.util.Date(etudiant.getDateNaissance().getTime());
                tfddatenaissance.setValue(dateNaissance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                cbbnomclasse.setValue(etudiant.getClasse());
                btnsave.setDisable(true);
            }
        }
    }

    private void loadTable() {
        ObservableList<Etudiant> listeEtudiants = FXCollections.observableArrayList(etudiantDAO.getAll());


        tdid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tddnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tdprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tdemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tdtelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        tddatenaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));


        tdnomclasse.setCellValueFactory(cellData -> {
            Etudiant etudiant = cellData.getValue();
            int classeId = etudiant.getClasse().getId();

            Classe classe = classeDAO.get(classeId);


            return new javafx.beans.property.SimpleStringProperty(
                    classe != null ? classe.getNom() : "Aucune classe"
            );
        });


        tableEtudiant.setItems(listeEtudiants);
    }

    private void clearFields() {
        tfdid.clear();
        tfdnom.clear();
        tfdprenom.clear();
        tfdemail.clear();
        tfdnumerotelephone.clear();
        tfddatenaissance.setValue(null);
        cbbnomclasse.getSelectionModel().clearSelection();
        btnsave.setDisable(false);
    }


    private void chargerNomsClasses() {
        ObservableList<Classe> classes = FXCollections.observableArrayList(classeDAO.getAll());
        cbbnomclasse.setItems(classes);


        cbbnomclasse.setCellFactory(cell -> new ListCell<Classe>() {
            @Override
            protected void updateItem(Classe classe, boolean empty) {
                super.updateItem(classe, empty);
                setText(empty || classe == null ? null : classe.getNom());
            }
        });


        cbbnomclasse.setButtonCell(new ListCell<Classe>() {
            @Override
            protected void updateItem(Classe classe, boolean empty) {
                super.updateItem(classe, empty);
                setText(empty || classe == null ? null : classe.getNom());
            }
        });
    }


    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}