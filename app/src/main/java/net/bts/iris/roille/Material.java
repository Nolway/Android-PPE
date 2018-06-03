package net.bts.iris.roille;

public class Material {
    private int id;
    private int montant;
    private int idTypeMaterial;
    private String libelle;

    public Material(int id, int montant, int idTypeMaterial, String libelle) {
        this.id = id;
        this.montant = montant;
        this.idTypeMaterial = idTypeMaterial;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getIdTypeMaterial() {
        return idTypeMaterial;
    }

    public void setIdTypeMaterial(int idTypeMaterial) {
        this.idTypeMaterial = idTypeMaterial;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
