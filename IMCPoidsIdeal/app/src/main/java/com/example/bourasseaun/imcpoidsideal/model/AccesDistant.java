package com.example.bourasseaun.imcpoidsideal.model;

import android.util.Log;

import com.example.bourasseaun.imcpoidsideal.controller.Control;
import com.example.bourasseaun.imcpoidsideal.outils.AccesHTTP;
import com.example.bourasseaun.imcpoidsideal.outils.AsyncReponse;

import org.json.JSONArray;

public class AccesDistant implements AsyncReponse {

    private Control control;

    // constante de classe
    // cmd > ipconfig > IPv4
    // acces au fichier php dans le dossier de Wamp
    private static final String SERVEURADDR = "http://172.16.4.214/IMCPoidsIdeal/serveurIMCPI.php";

    public AccesDistant() {
        control = Control.getInstance(null);
    }


    /**
     * S'exécute au retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        // ce que l'on va faire avec les infos que l'on récupère du serveur distant
        Log.d("Serveur","********" + output);
        // découpage du message reçu : %
        String[] message = output.split("%");
        // dans message[0] : soit enreg, soit dernier, soit Erreur
        // dans message[1] : reste du message

        // vérification que l'on a bien au moins 2 cases au tableau
        if (message.length > 1) {
            if (message[0].equals("enreg")) {
                Log.d("enreg","********" + message[1]);
            } else if (message[0].equals("dernier")) {
                Log.d("dernier","********" + message[1]);
            } else if (message[0].equals("Erreur")) {
                // cas d'erreur
                Log.d("erreur","********" + message[1]);
            }
        }
    }

    /**
     * Va créer un accès à http
     * @param operation
     * @param lesDonneesJson
     */
    public void envoyer(String operation, JSONArray lesDonneesJson) {
        AccesHTTP accesDonnees = new AccesHTTP();
        // besoin de faire le lien de délégation (entre un Thread
        // et une classe qui n'est pas un Thread
        accesDonnees.delegate = this; // on envoie à delegate l'accès distant actuel
        // on ajoute des paramètres à l'URL
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJson.toString());
        // appel au serveur : on lui envoie l'URL, la chaine complète
        accesDonnees.execute(SERVEURADDR); // appel doInBackground de la classe AccesHttp.
    }
}
