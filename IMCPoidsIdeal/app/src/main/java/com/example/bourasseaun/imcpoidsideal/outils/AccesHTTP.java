package com.example.bourasseaun.imcpoidsideal.outils;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AccesHTTP extends AsyncTask<String, Integer,Long> {

    private ArrayList<NameValuePair> parametres;
    private String retour= null;
    public AsyncReponse delegate = null;
    /**
     * Constructeur
     */
    public AccesHTTP() {
        parametres = new ArrayList<NameValuePair>();

    }

    /**
     * Ajout d'un paramètre post
     * @param nom
     * @param valeur
     */
    public void addParam(String nom, String valeur) {
        parametres.add(new BasicNameValuePair(nom, valeur));

    }

    /**
     * Connexion en tâche de fond dans un Tread séparé
     * @param strings
     * @return
     */
    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHttp = new DefaultHttpClient();
        HttpPost paramCnx=new HttpPost(strings[0]);

        try {
            // Encodage des paramètres
            paramCnx.setEntity(new UrlEncodedFormEntity(parametres));
            // connexion et envoi des paramètres : url complète
            // et attente de réponse
            HttpResponse reponse = cnxHttp.execute(paramCnx);
            // transformer la chaine de la réponse
            Log.d ("avant retour", "*****************************************");
            retour = EntityUtils.toString(reponse.getEntity());
            Log.d ("retour ", "*************" + retour.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d ("Erreur encodage", "*************" + e.toString());
        } catch (ClientProtocolException e) {
            Log.d ("Erreur de protocole", "*************" + e.toString());
        } catch (IOException e) {
            Log.d ("Erreur entrée/sortie", "*************" + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long result) {
        Log.d ("retour onpostExecute", "*************" + retour.toString());
        delegate.processFinish(retour.toString());
    }
}
