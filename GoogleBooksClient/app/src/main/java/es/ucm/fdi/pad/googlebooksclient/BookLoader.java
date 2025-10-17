package es.ucm.fdi.pad.googlebooksclient;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;


public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {
    private String queryString;
    private String printType;
    private List<BookInfo> result = null;


    public BookLoader(Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Override
    protected void onStartLoading() {
        forceLoad(); //para temas de la cache
    }

    @Override
    public List<BookInfo> loadInBackground() {
        String jsonResponse = null;
        try {
            jsonResponse = getBookInfoJson(queryString, printType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return BookInfo.fromJsonResponse(jsonResponse);
    }

    public String getBookInfoJson(String queryString, String printType) throws IOException {
        // Implementación para llamar a la Google Books API utilizando HttpURLConnection
        // 1. Construir la URL con los parámetros queryString, printType y maxResults
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=" + queryString + "&printType=" + printType);
        // 2. Hacer la conexión, obtener el InputStream y leerlo a un String
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();


        // 3. Retornar el String con la respuesta JSON

    }
}
