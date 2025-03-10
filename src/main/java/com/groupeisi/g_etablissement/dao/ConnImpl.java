package com.groupeisi.g_etablissement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConnImpl {

    private DBConnexion dbConnexion;

    public ConnImpl() {
        dbConnexion = new DBConnexion();
    }


    public boolean connexion(String email, String password) {
        String query = "SELECT mot_de_passe FROM utilisateur WHERE email = ?";
        try (Connection conn = dbConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("mot_de_passe");
                return checkPasswordWithMD5(password, storedPasswordHash);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private boolean checkPasswordWithMD5(String mot_de_passe, String storedPasswordHash) {
        try {

            String hashedPassword = hashWithMD5(mot_de_passe);


            return hashedPassword.equals(storedPasswordHash);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private String hashWithMD5(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");


        byte[] hashedBytes = md.digest(password.getBytes());


        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
