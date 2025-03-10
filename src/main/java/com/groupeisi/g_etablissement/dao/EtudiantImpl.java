package com.groupeisi.g_etablissement.dao;

import com.groupeisi.g_etablissement.entities.Classe;
import com.groupeisi.g_etablissement.entities.Etudiant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.ResultSet;

public class EtudiantImpl implements IRepository<Etudiant> {
    private DBConnexion db = new DBConnexion();
    private ResultSet rs;
    private int ok;


    @Override
    public int add(Etudiant etudiant) {
        String sql = "INSERT INTO etudiants (nom, prenom, email, telephone, dateNaissance, classe_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            db.initPrepar(sql);
            db.getPsmt().setString(1, etudiant.getNom());
            db.getPsmt().setString(2, etudiant.getPrenom());
            db.getPsmt().setString(3, etudiant.getEmail());
            db.getPsmt().setString(4, etudiant.getTelephone());
            db.getPsmt().setDate(5, new java.sql.Date(etudiant.getDateNaissance().getTime()));
            db.getPsmt().setInt(6, etudiant.getClasse().getId()); // Utilisation de getClasse().getId() pour la clé étrangère
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }


    @Override
    public int update(Etudiant etudiant) {
        String sql = "UPDATE etudiants SET nom = ?, prenom = ?, email = ?, telephone = ?, dateNaissance = ?, classe_id = ? WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPsmt().setString(1, etudiant.getNom());
            db.getPsmt().setString(2, etudiant.getPrenom());
            db.getPsmt().setString(3, etudiant.getEmail());
            db.getPsmt().setString(4, etudiant.getTelephone());
            db.getPsmt().setDate(5, new java.sql.Date(etudiant.getDateNaissance().getTime()));
            db.getPsmt().setInt(6, etudiant.getClasse().getId()); // Utilisation de getClasse().getId() pour la clé étrangère
            db.getPsmt().setInt(7, etudiant.getId());
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM etudiants WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPsmt().setInt(1, id);
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }


    @Override
    public ObservableList<Etudiant> getAll() {
        ObservableList<Etudiant> listeEtudiants = FXCollections.observableArrayList();
        String sql = "SELECT * FROM etudiants ORDER BY id ASC";
        try {
            db.initPrepar(sql);
            rs = db.executeSelect();
            while (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(rs.getInt("id"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setPrenom(rs.getString("prenom"));
                etudiant.setEmail(rs.getString("email"));
                etudiant.setTelephone(rs.getString("telephone"));
                etudiant.setDateNaissance(rs.getDate("dateNaissance"));


                int classeId = rs.getInt("classe_id");
                Classe classe = new Classe();
                classe.setId(classeId);
                etudiant.setClasse(classe);

                listeEtudiants.add(etudiant);
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listeEtudiants;
    }


    public void chargerNomsClasses(ComboBox<String> comboBox) {
        String sql = "SELECT nom FROM classes ORDER BY id ASC";
        try {
            db.initPrepar(sql);
            rs = db.executeSelect();
            comboBox.getItems().clear();
            while (rs.next()) {
                comboBox.getItems().add(rs.getString("nom"));
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Etudiant get(int id) {
        return null;
    }
}
