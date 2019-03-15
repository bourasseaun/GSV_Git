package com.example.bourasseaun.imcpoidsideal.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bourasseaun.imcpoidsideal.R;
import com.example.bourasseaun.imcpoidsideal.controller.Control;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //faire les liens avec les objets graphqiue
        init();
    }

    //propriétés / attributs
    private EditText txtPoids;
    private EditText txtTaille;
    private RadioButton rdHomme;
    //pas besoin de recuperer les deux boutons radios vu que un seul d'eux est coché
    private TextView lblIMC;
    private ImageView imgIMC;

    private Control control;

    //methode locale pour faire les liens entre les propriétes et les composants graphique

    /**
     * initialisation des liens avec les objets graphiques
     */
    private void init() {
        // (EditText) pour recupérer le bon type d'objet graphique
        txtPoids = (EditText) findViewById(R.id.txtPoids);
        txtTaille = (EditText) findViewById(R.id.txtTaille);
        rdHomme = (RadioButton) findViewById(R.id.rdHomme);
        lblIMC = (TextView) findViewById(R.id.lblIMG);
        imgIMC = (ImageView) findViewById(R.id.imgIMC);
        // création d'une instance de type instance
        this.control = Control.getInstance(this);
        clicCalculer();
    }

    /**
     * Ecoute evenement sur bouton "calculer"
     */
    private void clicCalculer() {
        // appel de l'évènement
        ((Button) findViewById(R.id.btCalc)).setOnClickListener(new Button.OnClickListener() {
            // on redéfini à la volée la méthode onClick
            public void onClick(View v) {
                // va s'executer quand on va cliquer sur le bouton
                float poids = 0;
                float taille = 0;
                int sexe = 0; // femme par défaut
                // Récupération des données saisies
                try {
                    poids = Float.parseFloat(txtPoids.getText().toString());
                    taille = Float.parseFloat(txtTaille.getText().toString());
                } catch (Exception e) {
                    // On ne met rien car on ne veut qu'il ne se passe rien.
                    // On garde les valeurs à zéro pour les variables poids et taille
                }
                if (rdHomme.isChecked()) {
                    sexe=1;
                }

                //Contrôle des données saisies
                if (poids == 0 || taille == 0) {
                    // affichage d'un message temporaire d'erreur à l'écran
                    Toast.makeText(MainActivity.this, "Saisie incorrecte", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(MainActivity.this, "Données mises à jour ?", Toast.LENGTH_SHORT).show();
                    afficherResultat(poids, taille, sexe);
                }
            }
        });
    }
    /**
     * Affichage de l'IMC, du message et de l'image
     * @param poids
     * @param taille
     * @param sexe
     */
    private void afficherResultat(float poids, float taille, int sexe) {
        // Création du profil et récupération des informations
        this.control.creerProfil(0,poids, taille, sexe,null);
        float imc = this.control.getIMC();
        String msg = this.control.getMessage();

        // Gestion de l'affichage des images en fonction des résultats
        if (msg.equals("cas de poids idéal")) {
            imgIMC.setImageResource(R.drawable.normal);
            lblIMC.setTextColor(Color.GREEN);
        } else if (msg.equals ("cas de dénutrition") || msg.equals ("cas de maigreur")) {
            imgIMC.setImageResource(R.drawable.maigre);
            lblIMC.setTextColor(Color.CYAN);
        } else {
            imgIMC.setImageResource(R.drawable.bigmama);
            lblIMC.setTextColor(Color.RED);
        }

        // formatage de l'affichage du résultat de l'IMC à deux chiffres après la virgule
        lblIMC.setText(String.format("%.01f",imc) + " : " + msg);

    }

    /*
     * Récupération du profil si des données ont été enregistrées
     * */
    public void recupererProfil () {
        if (control.getPoids() != null) {
            txtPoids.setText(control.getPoids().toString());
            txtTaille.setText(control.getTaille().toString());
            if (control.getSexe()==1){
                rdHomme.setChecked(true);
            }else{
            }
            ((Button) findViewById(R.id.btCalc)).performClick();
        }
    }


}
