package com.scank.organizer.helper;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebViewAccess extends AsyncTask<Void,Void,Void> {
    Elements x;
    @Override
    protected Void doInBackground(Void... voids) {
        String url = "https://scank.azurewebsites.net/Register";
        try {
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
//                System.out.println(doc);
            x = doc.select("div");
            System.out.println(x);
            //System.out.println(x.select("container-fluid"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
    }
}