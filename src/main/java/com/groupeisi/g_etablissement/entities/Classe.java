package com.groupeisi.g_etablissement.entities;

public class Classe {
    private int id;
    private String nom;

    // Constructeur par défaut
    public Classe() {
    }

    // Constructeur avec nom (pour l'ajout d'une nouvelle classe)
    public Classe(String nom) {
        this.nom = nom;
    }

    // Constructeur avec id et nom (pour la mise à jour d'une classe existante)
    public Classe(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getter et Setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
