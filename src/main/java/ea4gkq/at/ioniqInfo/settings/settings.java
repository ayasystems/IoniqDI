package ea4gkq.at.ioniqInfo.settings;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import ea4gkq.at.ioniqInfo.MainActivity;
import ea4gkq.at.ioniqInfo.R;

import ea4gkq.at.ioniqInfo.logBusData;




public class settings extends AppCompatActivity {
    EditText mEtHost;
    EditText mEtPort;
    EditText mEtHoraPar1;
    EditText mEtHParcialBatt;
    EditText mEtPlotRecordsMax;
    EditText mEtMqttInterval;

    EditText mEtMQTTHost;
    EditText mEtMQTTUser;
    EditText mEtMQTTPass;

    Switch mSwAutoStart;
    Switch mSwFastOBD;
    Switch mSwGrabarTramasOBD;
    Switch mSwAutoStartOBD;
    Switch mSwMQTT;
    TextView tvMarquee;

    String host;
    int port;
    int horasParcial1;
    int horasParcialBatt;
    int plotRecordsMax;
    boolean autoStart;
    boolean fastOBD;
    boolean grabarTramasOBD;
    boolean downloadFinished;
    boolean autoStartOBD;
    boolean enableMqtt;
    // Progress Dialog
    String mqttHost;
    String mqttUser;
    String mqttPass;
    int mqttInterval;

    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "http://servidor.ayasystems.com/app-debug.apk";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.settings);

        mEtHost             = (EditText) findViewById(R.id.etObd2);
        mEtPort             = (EditText) findViewById(R.id.etObd2Port);
        mEtHoraPar1         = (EditText) findViewById(R.id.etHParcial1);
        mEtHParcialBatt     = (EditText) findViewById(R.id.etHParcialBatt);
        mEtPlotRecordsMax   = (EditText) findViewById(R.id.etPlotRecordsMax);
        mSwAutoStart        = (Switch) findViewById(R.id.swAutoStart);
        mSwFastOBD          = (Switch) findViewById(R.id.swFastOBD);
        mSwGrabarTramasOBD  = (Switch) findViewById(R.id.swGrabarTramasOBD);
        mSwAutoStartOBD     = (Switch) findViewById(R.id.swAutoStartObd);
        mSwMQTT             = (Switch) findViewById(R.id.swMQTT);
        tvMarquee           = (TextView) findViewById(R.id.tvMarquee);

        mEtMQTTHost             = (EditText) findViewById(R.id.etMqttHost);
        mEtMQTTUser             = (EditText) findViewById(R.id.etMqttUser);
        mEtMQTTPass             = (EditText) findViewById(R.id.etMqttPass);
        mEtMqttInterval         = (EditText) findViewById(R.id.etMqttInterval);

        tvMarquee.setSelected(true);  // Set focus to the textview

        getPreferences();
        mEtHost.setText(host);
        mEtPort.setText(String.valueOf(port));
        mEtHoraPar1.setText(String.valueOf(horasParcial1));
        mEtHParcialBatt.setText(String.valueOf(horasParcialBatt));
        mEtPlotRecordsMax.setText(String.valueOf(plotRecordsMax));
        mSwAutoStart.setChecked(autoStart);
        mSwFastOBD.setChecked(fastOBD);
        mSwAutoStartOBD.setChecked(autoStartOBD);
        mSwGrabarTramasOBD.setChecked(grabarTramasOBD);
        mSwMQTT.setChecked(enableMqtt);
        mEtMqttInterval.setText(String.valueOf(mqttInterval));
        mEtMQTTHost.setText(mqttHost);
        mEtMQTTUser.setText(mqttUser);
        mEtMQTTPass.setText(mqttPass);


        downloadFinished = false;
    }


    private void getPreferences() {

        SharedPreferences settings = getSharedPreferences("IoniqSettings", MODE_PRIVATE);
        host                = settings.getString("host", "obd2.lan");
        port                = settings.getInt("port", 23);
        horasParcial1       = settings.getInt("horasParcial1", 4);
        horasParcialBatt    = settings.getInt("horasParcialBatt", 4);
        plotRecordsMax      = settings.getInt("plotRecordsMax", 200);
        autoStart           = settings.getBoolean("autoStart", true);
        fastOBD             = settings.getBoolean("fastOBD", false);
        autoStartOBD        = settings.getBoolean("autoStartOBD",true);
        grabarTramasOBD     = settings.getBoolean("grabarTramasOBD", false);
        enableMqtt          = settings.getBoolean("MQTT",false);

        mqttHost            = settings.getString("mqttHost", "tcp://farmer.cloudmqtt.com:18507");
        mqttUser            = settings.getString("mqttUser", "user");
        mqttPass            = settings.getString("mqttPass", "pass");
        mqttInterval        = settings.getInt("mqttInterval",60);

    }

    private void goToBusLogDdata(){
        Intent myIntent = new Intent(this, logBusData.class);
        startActivity(myIntent);
    }
    private void savePreferences() {

        host = mEtHost.getText().toString();
        try {
            port = Integer.parseInt(mEtPort.getText().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        try {
            horasParcial1 = Integer.parseInt(mEtHoraPar1.getText().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        try {
            horasParcialBatt = Integer.parseInt(mEtHParcialBatt.getText().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        try {
            plotRecordsMax = Integer.parseInt(mEtPlotRecordsMax.getText().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        try {
            mqttInterval = Integer.parseInt(mEtMqttInterval.getText().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        autoStart           = mSwAutoStart.isChecked();
        fastOBD             = mSwFastOBD.isChecked();
        grabarTramasOBD     = mSwGrabarTramasOBD.isChecked();
        autoStartOBD        = mSwAutoStartOBD.isChecked();
        enableMqtt          = mSwMQTT.isChecked();

        mqttHost = mEtMQTTHost.getText().toString();
        mqttUser = mEtMQTTUser.getText().toString();
        mqttPass = mEtMQTTPass.getText().toString();

        if(mqttInterval<5){
            mqttInterval = 5;
        }
        if(horasParcial1>24){
            horasParcial1 = 24;
        }
        if(horasParcialBatt>24){
            horasParcialBatt = 24;
        }
        SharedPreferences.Editor editor = getSharedPreferences("IoniqSettings", MODE_PRIVATE).edit();
        editor.putString("host", host);
        editor.putInt("port", port);
        editor.putInt("horasParcial1", horasParcial1);
        editor.putInt("horasParcialBatt", horasParcialBatt);
        editor.putInt("plotRecordsMax", plotRecordsMax);
        editor.putBoolean("autoStart", autoStart);
        editor.putBoolean("fastOBD", fastOBD);
        editor.putBoolean("autoStartOBD",autoStartOBD);
        editor.putBoolean("grabarTramasOBD", grabarTramasOBD);
        editor.putBoolean("MQTT", enableMqtt);
        editor.putString("mqttHost", mqttHost);
        editor.putString("mqttUser", mqttUser);
        editor.putString("mqttPass", mqttPass);
        editor.putInt("mqttInterval",mqttInterval);
        editor.apply();
    }

    public void onClickLogBus(View v) {

        goToBusLogDdata();
    }

    public void onClickSalir(View v) {

        finish();
    }

    public void onClickSave(View v) {

        savePreferences();
    }

    public void onClickUpdate(View v) {
        downloadUpdate();
    }


    private void downloadUpdate() {

        if(MainActivity.mTcpClient!=null) {
            MainActivity.mTcpClient.closeConnection();
            MainActivity.mTcpClient.stopClient();
        }
        downloadFinished = false;
        new DownloadFileFromURL().execute(file_url);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Espera coño...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }


    public void installNewApk(){
        if(downloadFinished ==false){
            return;
        }
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(new File("/storage/sdcard0/_ioniqInfo/_UpdateApp.apk")), "application/vnd.android.package-archive" );
        Log.d("Lofting", "About to install new .apk");
        this.startActivity(i);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);


                // Output stream to write file
                OutputStream output = new FileOutputStream("/storage/sdcard0/_ioniqInfo/_UpdateApp.apk",false);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }
                if(lenghtOfFile>0){
                    downloadFinished = true;
                }
                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                downloadFinished = false;
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
           dismissDialog(progress_bar_type);

            installNewApk();

        }


    }
}


