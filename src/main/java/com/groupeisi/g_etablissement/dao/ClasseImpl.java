package com.groupeisi.g_etablissement.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.groupeisi.g_etablissement.entities.Classe;

import java.sql.ResultSet;

public class ClasseImpl implements IRepository<Classe> {
    private DBConnexion db = new DBConnexion();
    private ResultSet rs;
    private int ok;


    @Override
    public int add(Classe classe) {
        String sql = "INSERT INTO classes (nom) VALUES (?)";
        try {
            db.initPrepar(sql);
            db.getPsmt().setString(1, classe.getNom());
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }


    @Override
    public int update(Classe classe) {
        String sql = "UPDATE classes SET nom = ? WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPsmt().setString(1, classe.getNom());
            db.getPsmt().setInt(2, classe.getId());
            ok = db.executeMaj();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }


    @Override
    public int delete(int id) {
        String sql = "DELETE FROM classes WHERE id = ?";
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
    public ObservableList<Classe> getAll() {
        ObservableList<Classe> listeClasse = FXCollections.observableArrayList();
        String sql = "SELECT * FROM classes ORDER BY id ASC";
        try {
            db.initPrepar(sql);
            rs = db.executeSelect();
            while (rs.next()) {
                Classe classe = new Classe();
                classe.setId(rs.getInt("id"));
                classe.setNom(rs.getString("nom"));
                listeClasse.add(classe);
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listeClasse;
    }


    @Override
    public Classe get(int id) {
        Classe classe = null;
        String sql = "SELECT * FROM classes WHERE id = ?";
        try {
            db.initPrepar(sql);
            db.getPsmt().setInt(1, id);
            rs = db.executeSelect();
            if (rs.next()) {
                classe = new Classe();
                classe.setId(rs.getInt("id"));
                classe.setNom(rs.getString("nom"));
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classe;
    }
}
