package com.groupeisi.g_etablissement.controller;

import com.groupeisi.g_etablissement.dao.ClasseImpl;
import com.groupeisi.g_etablissement.entities.Classe;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ClasseController implements Initializable {
    private final ClasseImpl c = new ClasseImpl();

    @FXML
    private TextField tfdnomclasse;

    @FXML
    private TextField tfdid;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private Button btndelete;

    @FXML
    private TableView<Classe> tableClasse;

    @FXML
    private TableColumn<Classe, Integer> tid;

    @FXML
    private TableColumn<Classe, String> tnomclasse;


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
            alert.setHeaderText("Voulez-vous vraiment supprimer cette classe ?");
            alert.setContentText("ID de la classe : " + id);

            if (alert.showAndWait().get() == ButtonType.OK) {
                c.delete(id);
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
        String nomClasse = tfdnomclasse.getText().trim();

        // Vérification si le champ nom est vide
        if (nomClasse.isEmpty()) {
            showAlert("Erreur", "Veuillez saisir un nom de classe !", Alert.AlertType.ERROR);
            return;
        }

        Classe classe = new Classe(nomClasse);
        c.add(classe);
        loadTable();
        showAlert("Succès", "Classe ajoutée avec succès.", Alert.AlertType.INFORMATION);
    }


    @FXML
    void update(ActionEvent event) {
        String idText = tfdid.getText().trim();
        String nomClasse = tfdnomclasse.getText().trim();


        if (idText.isEmpty() || nomClasse.isEmpty()) {
            showAlert("Erreur", "Veuillez saisir l'ID et le nom de la classe !", Alert.AlertType.ERROR);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Classe classe = new Classe(id, nomClasse);
            c.update(classe);
            btnsave.setDisable(false);  // Réactiver le bouton "Ajouter"
            loadTable();
            showAlert("Succès", "Classe mise à jour avec succès.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Format d'ID invalide !", Alert.AlertType.ERROR);
        }
    }


    public void loadTable() {
        ObservableList<Classe> listeClasse = c.getAll();
        tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tnomclasse.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableClasse.setItems(listeClasse);
    }


    @FXML
    void getData(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Classe classe = tableClasse.getSelectionModel().getSelectedItem();
            if (classe != null) {
                tfdid.setText(String.valueOf(classe.getId()));
                tfdnomclasse.setText(classe.getNom());
                btnsave.setDisable(true);
            }
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