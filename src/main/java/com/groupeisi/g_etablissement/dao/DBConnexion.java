package com.groupeisi.g_etablissement.dao;

import java.sql.*;

public class DBConnexion {
    private Connection cnx;
    private PreparedStatement psmt;
    private ResultSet rs;
    private int ok;

    // Méthode pour obtenir la connexion à la base de données
    public Connection getConnection() {
        String host = "localhost";
        String port = "5432";
        String db = "gestion_etablissement";
        String username = "postgres";
        String password = "dione";
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

        try {
                Class.forName("org.postgresql.Driver");
                this.cnx = DriverManager.getConnection(url, username, password);
                System.out.println("Connexion réussie");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Vous pouvez également propager l'exception
        }
        return cnx;
    }

    // Initialiser le PreparedStatement avec une requête SQL
    public void initPrepar(String sql) {
        try {
            getConnection();
            if (cnx != null) {
                psmt = cnx.prepareStatement(sql);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Vous pouvez également propager l'exception
        }
    }

    // Exécuter une requête de type SELECT
    public ResultSet executeSelect() {
        rs = null;
        try {
            if (psmt != null) {
                rs = psmt.executeQuery();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Vous pouvez également propager l'exception
        }
        return rs;
    }

    // Exécuter une mise à jour (INSERT, UPDATE, DELETE)
    public int executeMaj() {
        ok = 0;
        try {
            if (psmt != null) {
                ok = psmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Vous pouvez également propager l'exception
        }
        return ok;
    }

    // Fermer la connexion et autres ressources
    public void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (psmt != null) {
                psmt.close();
            }
            if (cnx != null) {
                cnx.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public PreparedStatement getPsmt() {
        return psmt;
    }
    public boolean testConnection() {
        Connection connection = getConnection();
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("La connexion à la base de données est établie.");
                return true;
            } else {
                System.out.println("La connexion à la base de données a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
