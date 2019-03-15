package com.example.bourasseaun.imcpoidsideal.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProfilTest {

    //creation profil
    private Profil profil = new Profil(59.9f,1.69f,1);
    //resultat IMC attendu
    private float imc=40;
    //message attendu
    private String message = "cas de poids idéal";

    @Test
    public void getResultatIMC() {
        assertEquals("#### message si erreur ####", imc, profil.getResultatIMC(), (float) 0.1);
    }
}