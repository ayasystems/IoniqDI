package ea4gkq.at.ioniqInfo;


import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class TcpClient {

    public static final String TAG = TcpClient.class.getSimpleName();
    public static  String SERVER_IP = "obd2.lan"; //server IP address
    public static  int SERVER_PORT = 23;

    // message to send to the server
    private String mServerMessage;
    // sends message received notifications
    private OnMessageReceived mMessageListener = null;
    // while this is true, the server will continue running
    private boolean mRun = false;
    public  boolean stopProcess = false;
    // used to send messages
    private PrintWriter mBufferOut;
    // used to read messages from the server
    private BufferedReader mBufferIn;
    private Socket socket;
    public String errorText = "";
    int errorCount = 0;


    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TcpClient(OnMessageReceived listener) {

        mMessageListener = listener;

    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void sendMessage(final String message) {
        if(socket!=null&&stopProcess==false) {
            if (!socket.isClosed()) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (mBufferOut != null) {
                            Log.d(TAG, "Sending: " + message + "\n\r");
                            mBufferOut.println(message + "\n\r");
                            if (mBufferOut != null) {
                                mBufferOut.flush();
                            }
                        }
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }
    }
    /*
        delayedCommand(500,"ATZ");
        delayedCommand(1000,"ATE0");
        delayedCommand(1500,"ATL1");
        delayedCommand(2000,"ATH0");
        delayedCommand(2500,"ATST62");
        delayedCommand(3500,"ATSH 7E4");
        */

    /**
     * Close the connection and release the members
     */
    public void stopClient() {

        mRun = false;
        stopProcess=true;
        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }
        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;

    }

    public void closeConnection(){
        try {
            if(socket!=null) {
                socket.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "S: Error", e);
            errorText = e.toString();
        } finally {
            //the socket must be closed. It is not possible to reconnect to this socket
            // after it is closed, which means a new socket instance has to be created.
            //socket.close();
        }
        stopProcess=true;
    }
    public void run() {

        errorCount=0;
        mRun = true;
        stopProcess=false;
        byte[] messageByte = new byte[1000];
        boolean endRead = false;
        String dataString = "";

        while (mRun){
            if(Ping(SERVER_IP)){
                mRun = false;
                writeLogMain("Ping OK ");
                MainActivity.logInfo = "Ping OK";
            }else{
                errorCount=errorCount+1;
                MainActivity.logInfo = "Ping KO "+errorCount;
            }
            if(errorCount>5){
                mRun = false;
                writeLogMain("Ping KO");
                MainActivity.logInfo = "- Ping KO "+errorCount;
            }
        }
        errorCount=0;
        mRun = true;
        try {
            //here you must put your computer's IP address.
            SocketAddress serverAddr = new  InetSocketAddress(SERVER_IP,SERVER_PORT);

            Log.d("TCP Client", "C: Connecting...");
            Log.v("TCP Client","Conectando con: "+serverAddr.toString());
            if (mServerMessage != null && mMessageListener != null) {
                //call the method messageReceived from MyActivity class

                MainActivity.logInfo = "Conectando con: "+serverAddr.toString();
                }
            writeLogMain("Conectando con: "+serverAddr.toString());
            //create a socket to make the connection with the server
            if(socket==null){
                socket = new Socket();
                socket.connect(serverAddr, 2000);
                socket.setReceiveBufferSize(10000);
            }else{
                if(socket.isClosed()) {
                    socket = new Socket();
                    socket.connect(serverAddr, 2000);
                    socket.setReceiveBufferSize(10000);
                }
                if(socket.isConnected()){
                    writeLogMain("Conectado OBD2.... socket no era Null");

                }else{
                    socket = new Socket();
                    socket.connect(serverAddr, 2000);
                    socket.setReceiveBufferSize(10000);
                }
            }
            try {
                MainActivity.logInfo = "Secuencía inicio OBD";
                //sends the message to the server
                mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                try { Thread.sleep(600); } catch(InterruptedException e) {}
                mBufferOut.println("ATZ"+ "\n\r");
                try { Thread.sleep(600); } catch(InterruptedException e) {}


                if(MainActivity.fastOBD==true){
                    mBufferOut.println("ATH0"+ "\n\r");
                    MainActivity.logInfo = "ATH0";
                    try { Thread.sleep(400); } catch(InterruptedException e) {}
                    mBufferOut.println("ATE0"+ "\n\r");
                    MainActivity.logInfo = "ATE0";
                    try { Thread.sleep(400); } catch(InterruptedException e) {}
                    mBufferOut.println("ATE0"+ "\n\r");
                    MainActivity.logInfo = "ATE0";
                    try { Thread.sleep(400); } catch(InterruptedException e) {}
                    mBufferOut.println("ATM0"+ "\n\r");
                    MainActivity.logInfo = "ATM0";
                    try { Thread.sleep(400); } catch(InterruptedException e) {}
                    mBufferOut.println("ATST62"+ "\n\r");
                    MainActivity.logInfo = "ATST62";
                    try { Thread.sleep(400); } catch(InterruptedException e) {}
                    mBufferOut.println("ATSH 7E4"+ "\n\r");
                    MainActivity.logInfo = "ATSH 7E4";
                    try { Thread.sleep(400); } catch(InterruptedException e) {}
                    mBufferOut.println("ATL0"+ "\n\r");
                    MainActivity.logInfo = "ATL0";
                    try { Thread.sleep(400); } catch(InterruptedException e) {}
                }else{
                    mBufferOut.println("ATH0"+ "\n\r");
                    MainActivity.logInfo = "ATH0";
                    try { Thread.sleep(600); } catch(InterruptedException e) {}
                    mBufferOut.println("ATE0"+ "\n\r");
                    MainActivity.logInfo = "ATE0";
                    try { Thread.sleep(600); } catch(InterruptedException e) {}
                    mBufferOut.println("ATM0"+ "\n\r");
                    MainActivity.logInfo = "ATM0";
                    try { Thread.sleep(600); } catch(InterruptedException e) {}
                    mBufferOut.println("ATST62"+ "\n\r");
                    MainActivity.logInfo = "ATST62";
                    try { Thread.sleep(600); } catch(InterruptedException e) {}
                    mBufferOut.println("ATSH 7E4"+ "\n\r");
                    MainActivity.logInfo = "ATSH 7E4";
                    try { Thread.sleep(600); } catch(InterruptedException e) {}
                    mBufferOut.println("ATL1"+ "\n\r");
                    MainActivity.logInfo = "ATL1";
                    try { Thread.sleep(600); } catch(InterruptedException e) {}
                }
                mBufferOut.println("AT@1"+ "\n\r");
                MainActivity.logInfo = "AT@1";
                try { Thread.sleep(600); } catch(InterruptedException e) {}
                //receives the message which the server sends back
                mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Log.v("TCP","Iniciamos while de escucha");

                MainActivity.logInfo = "WAITING....";
                //in this while the client listens for the messages sent by the server
                errorCount=0;
                mRun = true;
                while (mRun) {


                    mServerMessage = mBufferIn.readLine();
                    if (mServerMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        if(!mServerMessage.equals("")) {
                            mMessageListener.messageReceived(mServerMessage);
                        }
                    }

                }



                errorText = "";

            } catch (Exception e) {
                Log.e(TAG, "S: Error", e);
                errorText = e.toString();
                writeLogMain("TCP_ERROR_A > "+errorText);
                MainActivity.logInfo = "Socket closed due an error "+errorText;
                closeConnection();
            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                //socket.close();
            }

        } catch (Exception e) {
            Log.e(TAG, "C: Error", e);
            errorText = e.toString();
            writeLogMain("TCP_ERROR_B > "+errorText);
            MainActivity.logInfo = "TCP_ERROR_B > "+errorText;
            if(errorCount<5){
                errorCount = errorCount +1;

                if(e.toString().contains("TimeoutException")){

                    mMessageListener.messageReceived("||KO||Socket timeout "+errorCount);
                    MainActivity.logInfo = "Socket timeout "+errorCount;
                }
                if(e.toString().contains("UnknownHost")){

                    mMessageListener.messageReceived("||KO||Socket host desconocido "+errorCount);

                    MainActivity.logInfo = "Socket host desconocido "+errorCount;
                }
                if(e.toString().contains("EHOSTUNREACH")){

                    mMessageListener.messageReceived("||KO||No route to host "+errorCount);
                    MainActivity.logInfo = "No route to host "+errorCount;
                }
                run();
            }

        }

    }

    private boolean Ping(String IP){

        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 " + IP);
            int mExitValue = mIpAddrProcess.waitFor();
            //System.out.println(" mExitValue "+mExitValue);
            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ignore)
        {
            writeLogMain(" PING Exception:"+ignore);
            ignore.printStackTrace();
        }
        catch (IOException e)
        {
            writeLogMain(" PING Exception:"+e);
            e.printStackTrace();
        }
        return false;
    }

    private void writeLogMain(String message){
        if(MainActivity.ins!=null){
            MainActivity.writeToFile(message);
        }
    }
    //Declare the interface. The method messageReceived(String message) will must be implemented in the Activity
    //class at on AsyncTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);

    }

}

