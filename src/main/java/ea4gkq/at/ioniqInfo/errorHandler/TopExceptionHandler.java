package ea4gkq.at.ioniqInfo.errorHandler;

import android.app.Activity;
import android.content.Context;
import android.text.format.Time;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TopExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultUEH;
    private Activity app = null;
    private static DateFormat dateFormat ;


    public TopExceptionHandler(Activity app) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.app = app;
    }

    public void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();



        String report = today.format("%H:%M:%S")+"\n\n\n\n"+e.toString()+"\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (int i=0; i<arr.length; i++) {
            report += "    "+arr[i].toString()+"\n";
        }
        report += "-------------------------------\n\n";

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause

        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if(cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i=0; i<arr.length; i++) {
                report += "    "+arr[i].toString()+"\n";
            }
        }
        report += "-------------------------------\n\n";

        try {
            final File file = new File("/storage/sdcard0/_ioniqInfo", "crash__log.txt");
            //FileOutputStream trace = app.openFileOutput("stack.trace",Context.MODE_PRIVATE);
            FileOutputStream fOut = new FileOutputStream(file,false);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(report+"\r");
            myOutWriter.close();
            fOut.flush();
            fOut.close();
            //trace.write(report.getBytes());
            //trace.close();
        } catch(IOException ioe) {
            // ...
        }

        defaultUEH.uncaughtException(t, e);
    }
}