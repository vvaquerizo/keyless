package es.upm.vvaquerizo.keyless;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;

public class CodeManager extends AsyncTask<Void, Integer, Long> {
    Context context;
    ProgressDialog progressDialog;
    String newCode;

    CodeManager(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        // Petición al servidor de random.org
        // En condiciones de producción, sería una llamada a un servicio externo con los códigos de las puertas
        newCode = getRemoteCode();

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.getting_code));
        progressDialog.setMessage(context.getResources().getString(R.string.wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        progressDialog.dismiss();

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(context.getResources().getString(R.string.retrieved_code) + " " + newCode);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(android.R.color.holo_blue_light));
    }

    private String getRemoteCode() {
        int remoteCode = 0;
        try {
            URL url = new URL("https://www.random.org/integers/?num=1&min=1&max=10000&col=5&base=10&format=plain&rnd=new");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            String remoteCodeString = content.toString();
            remoteCodeString = remoteCodeString.replaceAll("\\t","");
            remoteCode = Integer.valueOf(remoteCodeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("%04d", remoteCode);
    }
}
