package com.example.bourasseaun.imcpoidsideal.model;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Profil {

    private static final float denutrition=16.f;
    private static final float maigreur=18.5f;
    private static final float ideal=25;
    private static final float surpoids=30;
    private static final float obesiteModeree=35;
    private static final float obesiteSevere=40;

    // properties
    private float poids;
    private float taille;
    private int sexe; // 0 pour femme ou 1 pour homme
    private float resultatIMC;
    private String message;

    public Profil (float poids, float taille, int sexe){
        this.poids = poids;
        this.taille = taille;
        this.sexe = sexe;
        calculIMG();
        genererMessageIMC();
    }
    public float getPoids() { return poids; }

    public float getTaille() { return taille; }

    public int getSexe() { return sexe; }

    public float getResultatIMC() { return resultatIMC; }

    public String getMessage() { return message; }

    //imc: poids/taille²
    private void calculIMG(){
        this.resultatIMC = poids/(taille*taille);
    }

    private void genererMessageIMC(){
            if (resultatIMC < denutrition){
                message = "cas de dénutrition";
            } else if (resultatIMC >= denutrition && resultatIMC <maigreur){
                message = "cas de maigreur";
            } else if (resultatIMC >=maigreur && resultatIMC <ideal){
                message = "cas de poids idéal";
            } else if (resultatIMC >= ideal && resultatIMC <surpoids){
                message = "cas de surpoids";
            } else if  (resultatIMC >= surpoids && resultatIMC <obesiteModeree){
                message = "cas d'obésité modérée";
            } else if (resultatIMC >= obesiteModeree && resultatIMC <obesiteSevere){
                message = "cas d'obésité sévère";
            } else if (resultatIMC >= obesiteSevere){
                message = "cas d'obésité morbide";
            }

            }

    /**
     * Conversion du profil au format JSONArray
     * @return JSONArray
     */
    public JSONArray convertirProfilJSON() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateMesure = simpleDateFormat.format(new Date());
        List laListe = new ArrayList();
        laListe.add(dateMesure);
        laListe.add(poids);
        laListe.add(taille);
        laListe.add(sexe);
        return new JSONArray(laListe);
    }




}

