package com.groupeisi.g_etablissement.entities;

public class Professeur {
    private int id;
    private String nom;
    private String matiere;


    public Professeur() {
    }


    public Professeur(String nom, String matiere) {
        this.nom = nom;
        this.matiere = matiere;
    }


    public Professeur(int id, String nom,String matiere) {
        this.id = id;
        this.nom = nom;
        this.matiere = matiere;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }
}
