package com.example.bourasseaun.imcpoidsideal.controller;

import android.content.Context;

import com.example.bourasseaun.imcpoidsideal.model.AccesDistant;
import com.example.bourasseaun.imcpoidsideal.model.Profil;
import com.example.bourasseaun.imcpoidsideal.view.MainActivity;

import org.json.JSONArray;

public final class Control {
    // static : accessible directement par la classe et pas l'objet (à portée de classe)
    private static Control instance = null;
    private static Profil profil;
    private static AccesDistant accesDistant;
    private static Context contexte;

    /**
     * Constructeur prive
     */
    private Control() {
        super();
    }

    // Méthode pour n'avoir qu'une instance de Control sur toute l'application
    // On crée un singleton
    /**
     * Creation de l'instance
     * @return : instance
     */
    public static final Control getInstance(Context contexte) {
        if (contexte!= null) {
            Control.contexte = contexte;
        }
        // on ne pourra pas faire plus d'une instance : notion de singleton
        if (Control.instance == null) {
            Control.instance = new Control();
            accesDistant = new AccesDistant();
            accesDistant.envoyer("dernier",new JSONArray());
        }
        return Control.instance;
    }

    /**
     * Creation du profil
     * @param choix : 0 = IMC et 1 = poids idéal
     * @param poids : en kg
     * @param taille : en m
     * @param sexe : 1 pour homme et 0 pour femme
     */
    public void creerProfil(int choix, float poids, float taille, int sexe, Context contexte) {
        profil = new Profil(poids, taille, sexe);
        accesDistant.envoyer("enreg", profil.convertirProfilJSON());
    }


    /**
     *  Recuperation de la valeur de l'IMC
     * @return : renvoie la valeur de l'IMC
     */
    public float getIMC() {
        return profil.getResultatIMC();
    }

    /**
     * Recuperation du message à afficher
     * @return : renvoie le message
     */
    public String getMessage() {
        return profil.getMessage();
    }

    public Float getPoids() {
        if (profil == null) {
            return null;
        }else {
            return profil.getPoids();
        }
    }

    public Float getTaille() {
        if (profil == null) {
            return null;
        }else {
            return profil.getTaille();
        }
    }

    public Float getSexe() {
        if (profil == null) {
            return null;
        }else {
            return (float) profil.getSexe();
        }
    }

    public void setProfil(Profil profil) {
        Control.profil = profil;
        ((MainActivity) contexte).recupererProfil();
    }


}
