package ea4gkq.at.ioniqInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import static android.content.Context.MODE_PRIVATE;

public class StartMyActivityAtBootReceiver extends BroadcastReceiver {

    //String  host;
    //int  port;
    //int  horasParcial1;
    boolean autoStart;

    @Override
    public void onReceive(final Context context, Intent intent) {

        SharedPreferences settings  = context.getSharedPreferences("IoniqSettings", MODE_PRIVATE);

        autoStart                   = settings.getBoolean("autoStart",true);

        if(autoStart==true) {
           // Intent i = new Intent(context, MainActivity.class);
           // //i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
           // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // context.startActivity(i);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i=new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }, 10000);
        }
    }


}
