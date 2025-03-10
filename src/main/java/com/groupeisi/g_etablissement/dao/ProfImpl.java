package com.groupeisi.g_etablissement.dao;

import com.groupeisi.g_etablissement.entities.Classe;
import com.groupeisi.g_etablissement.entities.Professeur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class ProfImpl implements IRepository<Professeur> {
    private DBConnexion db = new DBConnexion();
    private ResultSet rs;
    private int ok;
    @Override
    public int add(Professeur professeur) {
        String sql = "INSERT INTO professeurs (nom,matiere) VALUES (?,?)";
        try {
            db.initPrepar(sql);
            db.getPsmt().setString(1, professeur.getNom());
            db.getPsmt().setString(2,professeur.getMatiere());
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override

    public int update(Professeur professeur) {
        String sql = "UPDATE professeurs SET nom = ?, matiere = ? WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPsmt().setString(1, professeur.getNom());  // Nom du professeur
            db.getPsmt().setString(2, professeur.getMatiere());  // Matière du professeur
            db.getPsmt().setInt(3, professeur.getId());  // ID du professeur à mettre à jour

            // Exécuter la mise à jour et obtenir le nombre de lignes affectées
            ok = db.executeMaj();  // Cela renvoie le nombre de lignes affectées
            db.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;  // Retourne 0 en cas d'échec
        }

        return ok;  // Retourne le nombre de lignes affectées par la mise à jour
    }


    @Override
    public int delete(int id) {
        String sql = "DELETE FROM professeurs WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPsmt().setInt(1, id);
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public ObservableList<Professeur> getAll() {
        ObservableList<Professeur> ListeProfs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM professeurs ORDER BY id ASC";
        try {
            db.initPrepar(sql);
            rs = db.executeSelect();
            while (rs.next()) {
                Professeur professeur = new Professeur();
                professeur.setId(rs.getInt("id"));
                professeur.setNom(rs.getString("nom"));
                professeur.setMatiere((rs.getString("matiere")));
                ListeProfs.add(professeur);
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListeProfs;
    }

    @Override
    public Professeur get(int id) {
        return null;
    }
}
