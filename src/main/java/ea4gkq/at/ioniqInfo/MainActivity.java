package ea4gkq.at.ioniqInfo;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.androidplot.Plot;
import com.androidplot.util.PixelUtils;
import com.androidplot.util.Redrawer;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.lge.ivi.carinfo.CarInfoManager;
import com.lge.ivi.carinfo.ICarInfoListener;
import com.lge.ivi.greencar.GreenCarManager;




import com.lge.ivi.greencar.IBatteryChargeListener;
import com.lge.ivi.greencar.IEVPowerDisplayListener;
import com.lge.ivi.greencar.IEnergyFlowListener;
import com.lge.ivi.greencar.IFuelEconomyListener;
import com.lge.ivi.greencar.IOdometryListener;
import com.lge.ivi.greencar.IBatteryLevelListener;
import com.lge.ivi.hvac.HvacManager;
import com.lge.ivi.config.ConfigurationManager;
import com.lge.ivi.hvac.IHvacTempListener;


import ea4gkq.at.ioniqInfo.errorHandler.TopExceptionHandler;
import ea4gkq.at.ioniqInfo.greencar.CarIFListener;
import ea4gkq.at.ioniqInfo.greencar.EnergyFlowListener;
import ea4gkq.at.ioniqInfo.greencar.FuelEconomyListener;
//import ea4gkq.at.ioniqInfo.mqttClient.PahoMqttClient;
import ea4gkq.at.ioniqInfo.settings.settings;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import ea4gkq.at.ioniqInfo.greencar.OdometerListener;
import ea4gkq.at.ioniqInfo.greencar.CarInfoListener;
import ea4gkq.at.ioniqInfo.hvac.HvacTempListener;
import com.lge.ivi.PowerStateManager;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements MqttCallback {
  private static final String VERSION = "V61 MQTT";
  private static String TAG = "at.ea4gkq.ivireceiver";
  private static double mLat, mLon, mAlt;
  private static int    mHeading;
  private static double mAltPrev;
  private static String mwifiName = "-----";
  private static float mElectricKw;
  private static float mArvFuelEf;
  private Logger mLogger;
  public static String logInfo = "";
  protected static GreenCarManager mGreenCarManager = null;
  protected static HvacManager mHvacManager = null;
  protected static CarInfoManager mCarInfoManager = null;
  protected static PowerStateManager mPowerStateManager = null;
  protected ConnectTask tcpIpTask;

  protected static ConfigurationManager mConfigurationManager;

  private static IBatteryChargeListener mBatteryChargeListener;
  private static IEVPowerDisplayListener mEvPowerDisplayListener;
  private static IEnergyFlowListener mEnergyFlowListener;
  private static IOdometryListener mOdometryListener;
  private static ICarInfoListener mCarInfoListener;
  private static IBatteryLevelListener mBatteryLevelListener;
  private static IFuelEconomyListener mFuelEconomyListener;
  private static IHvacTempListener mIHvacTempListener;
  private static CarIFListener mCarIFListener;

  public static MainActivity ins;
  public static Context mContext;


  protected  ProgressBar pbBatt;
  protected  ProgressBar pbBattOBD2;
  //protected  RoundCornerProgressBar rcBattOBD2;
  protected  TextView tv;
  protected  TextView tvWifi;
  protected  TextView tvElectricKw;
  protected  TextView tvBattObd2Text;
  protected  TextView tvCargaObd2Text;
  protected  TextView tvDescargaObd2Text;

  protected  TextView tvCargaParcialObd2Text;
  protected  TextView tvDescargaParcialObd2Text;

  protected  TextView tvBattTemp;
  protected  TextView tvEvRPM;

  protected  TextView tvBattText;
  protected  TextView tvOutTemp;
  protected  TextView tvSpeed;
  protected  TextView tvRumbo;
  protected  TextView tvOdometro1;
  protected  TextView tvOdometro2;
  protected  TextView tvLog;
  protected  TextView tvAutEv;
  protected  TextView tvAutGas;
  protected  TextView tvAutGasLabel;
  protected  TextView tvArvFuelEf;
  protected  TextView tvArvFuelEfLabel;
  protected  TextView tvLabelBTemp;
  protected  TextView tvGearRel;
  protected  TextView tvCons100;
  protected  TextView tvEnergyFlowLabel;
  protected  TextView tvEnergyFlow;
  protected  TextView tvEVHEV;
  protected  TextView tvAltitud;
  protected  Button btnObd2;

  private static String pidToRead;
  private static String carType = "";

  public static boolean appPaused;

  public static String[] byte2101_01;
  public static String[] byte2101_02;
  public static String[] byte2101_04;
  public static String[] byte2101_05;
  public static String[] byte2101_06;
  public static String[] byte2101_08;
  public static float ampBatt;
  public static float volBatt;
  public static double wattsBatt;


  public static int timeToFull;
  public static int timeToFullPrev;

  public static String bmsData = "";
  public static float tempBatt = 999;
  public static float evRPM;
  public static float evRPMPrev;
  public static int isCharging = 0;
  public static boolean chargingReset = false;
//calculo consumo KWh/100
  public static float consBattCargaStart;
  public static float consBattDescargaStart;
  public static float consOdoStart;
  public static float consKwh100;
  public static float consKwh100Prev  = 0;

  public static int speedGPS;
  public static int speedGPSPrev = 999;
  public static boolean booleanContinue;
  private static int kwhMax = 0;
  private static int battPercentOBD2 = 0;

  public static boolean fastOBD;
  public static boolean autoStartOBD;
  public static boolean grabarTramasOBD;

  protected int Measuredwidth = 0;
  protected int Measuredheight = 0;
  public int dalayBucle = 3500;
  protected StringBuilder text;
  public  static Locale spanishLocale = new Locale("es", "ES");
  public  static Locale englishLocale = new Locale("en", "EN");
  String  host;
  int  port;
  int  horasParcial1;
  int  horasParcialBatt;
  int  plotRecordsMax;
  protected Handler handlerUpd = new Handler();
  protected Handler handleCheckOBD2 = new Handler();
  protected Handler handleUpdateFlow = new Handler();
  protected Handler handleMqtt = new Handler();
  protected static Long   lastOBD2;
  protected int delayUpd = 700; //milliseconds

  protected Handler handlerLog = new Handler();
  protected int delayLog = 1000; //milliseconds

  public static boolean obd2Iniciado = false;

  public static int carOdometer = 0;
  public static int carOdometerPrev = 0;
  protected int odometro1Start = 0;
  protected int odometro2Start = 0;
  protected int odometro1 = 0;
  protected int odometro2 = 0;
  private static boolean prefGrabadas = true;
  private static boolean fanWorking   = false;
  public static float tempIn = 0;
  public static float tempOut = 0;
  private static float tempOutPrev = 0;
  private static int mqttInterval = 0;

  //private static MqttClient mqttCliente;
  private MqttAndroidClient mqttCliente;
  private final MemoryPersistence persistence = new MemoryPersistence();
  //private static MqttAndroidClient mqttCliente;
  //private static MqttAndroidClient mqttAndroidClient;

  protected float cargaParcialStart         = (float)0;
  protected float cargaParcialLast          = (float)0;//ultima carga parcial, para poder calcular cuanto cargó el coche
  protected float descargaParcialStart      = (float)0;
  protected float cargaParcial              = (float)0;
  protected float descargaParcial           = (float)0;

  protected static float cargaTotal            = (float)0;
  protected static float descargaTotal         = (float)0;
  protected static float bat12V                = (float)0;
  public static int battPercent;
  public static int battPercentPrev;//el estado de bataría la última vez que se cerro la APP
  public static int battPercentOld          = 0;//antes del último cambio

  public static String sesionInit;
  protected static int   energyFlow         = 0;
  protected static int   energyFlowPrev     = 0;
  public static int evAutonomia             = 0;
  public static int gasAutonomia            = 0;
  public boolean checkedCargaLast           = false;

  private static DateFormat dateFormat ;
  private static Date date;
  private static int request2101;
  public static TcpClient mTcpClient;

//Declaraciones de la gráfica
  private  int HISTORY_SIZE = 200;//hacer esto definible en Settings //todo
  private XYPlot aprHistoryPlot = null;
  private SimpleXYSeries cargaHistorySeries = null;
  private SimpleXYSeries descargaHistorySeries = null;
  private SimpleXYSeries batTempHistorySeries = null;
  private Redrawer redrawer;

//Gráfica Altura
  private XYPlot altHistoryPlot = null;
  private SimpleXYSeries altHistorySeries = null;

  //mqtt
  private static String MQTT_BROKER_URL = "tcp://farmer.cloudmqtt.com:18507";
  // private static String MQTT_BROKER_URL = "wss://farmer.cloudmqtt.com:38507";



    private static String MQTT_PUBLISH_TOPIC = "ioniq/info";
    private static String MQTT_CLIENT_ID = "ioniq";

    private static  String MQTT_USER = "user";
    private static  String MQTT_PASSWORD = "password";

    private static Boolean  enableMqtt;


  static {

    //mCarInfoManager = CarInfoManager.getInstance();
    //mGreenCarManager = GreenCarManager.getInstance(null);
    mHvacManager = HvacManager.getInstance();
    mGreenCarManager = GreenCarManager.getInstance(null);
    mCarInfoManager  = CarInfoManager.getInstance();


/*
    mBatteryChargeListener = new BatteryChargeListener();
    mGreenCarManager.register(mBatteryChargeListener);

    mEvPowerDisplayListener = new EVPowerDisplayListener();
    mGreenCarManager.register(mEvPowerDisplayListener);


    mBatteryLevelListener = new BatteryLevelListener();
    mGreenCarManager.register(mBatteryLevelListener);
*/



    mCarIFListener = new CarIFListener();
    mCarInfoManager.registerCarIFListener(mCarIFListener);

    mFuelEconomyListener = new FuelEconomyListener();
    mGreenCarManager.register(mFuelEconomyListener);



    mEnergyFlowListener = new EnergyFlowListener();
    mGreenCarManager.register(mEnergyFlowListener);


    mOdometryListener = new OdometerListener();
    mGreenCarManager.register(mOdometryListener);

    mCarInfoListener = new CarInfoListener();
    mCarInfoManager.registerCarInfoListener(mCarInfoListener);

    mIHvacTempListener = new HvacTempListener();
    mHvacManager.registerHvacTempListener(mIHvacTempListener);


  }


private void initPlot(){

  HISTORY_SIZE = plotRecordsMax;
  // setup the APR History plot:
  aprHistoryPlot = (XYPlot) findViewById(R.id.firstPlot);
  aprHistoryPlot.setRangeBoundaries(0, 100, BoundaryMode.AUTO );
  aprHistoryPlot.setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);


//cargaHistorySeries
  cargaHistorySeries = new SimpleXYSeries("CARGA");
  cargaHistorySeries.useImplicitXVals();
  aprHistoryPlot.addSeries(cargaHistorySeries,
          new LineAndPointFormatter(Color.BLUE, null, Color.BLUE, null));
//descargaHistorySeries
  descargaHistorySeries = new SimpleXYSeries("DESCARGA");
  descargaHistorySeries.useImplicitXVals();
  aprHistoryPlot.addSeries(descargaHistorySeries,
          new LineAndPointFormatter(Color.GREEN,null,Color.GREEN, null));
//batTempHistorySeries
    batTempHistorySeries = new SimpleXYSeries("Bat Cº");
    batTempHistorySeries.useImplicitXVals();
    aprHistoryPlot.addSeries(batTempHistorySeries,new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels)
    );


  aprHistoryPlot.setDomainStepMode(StepMode.INCREMENT_BY_VAL);
  aprHistoryPlot.setDomainStepValue(HISTORY_SIZE/10);
  aprHistoryPlot.setLinesPerRangeLabel(3);


  aprHistoryPlot.setDomainLabel(VERSION);
  aprHistoryPlot.getDomainTitle().pack();

  XYGraphWidget graphWidget_LEFT = aprHistoryPlot.getGraph();

  graphWidget_LEFT.setMarginRight(0);
  graphWidget_LEFT.setPaddingRight(25);

  aprHistoryPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));

  aprHistoryPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("#"));



  aprHistoryPlot.setLayerType(View.LAYER_TYPE_NONE, null);


  // get a ref to the BarRenderer so we can make some changes to it:
  BarRenderer barRenderer = aprHistoryPlot.getRenderer(BarRenderer.class);
  if(barRenderer != null) {
    // make our bars a little thicker than the default so they can be seen better:
    barRenderer.setBarGroupWidth(
            BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(18));
  }


  aprHistoryPlot.getGraph().setMarginBottom(3);
  aprHistoryPlot.getGraph().setMarginTop(10);
  //this removes the vertical lines
  aprHistoryPlot.getGraph().setDomainGridLinePaint(null);

  //this removes the horizontal lines
  //aprHistoryPlot.getGraph().setRangeGridLinePaint(null);

}


  private void initSecondPlot(){
/*
    HISTORY_SIZE = plotRecordsMax;
    // setup the APR History plot:
    // setup the APR History plot:
    altHistoryPlot = (XYPlot) findViewById(R.id.secondPlot);
    altHistoryPlot.setRangeBoundaries(0, 100, BoundaryMode.AUTO );
    altHistoryPlot.setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);

//altHistorySeries


    altHistorySeries = new SimpleXYSeries("ALTURA");
    altHistorySeries.useImplicitXVals();
    altHistoryPlot.addSeries(altHistorySeries,
            new LineAndPointFormatter(Color.BLUE,null,Color.BLUE, null));


    altHistoryPlot.setDomainStepMode(StepMode.INCREMENT_BY_VAL);
    altHistoryPlot.setDomainStepValue(HISTORY_SIZE/10);
    altHistoryPlot.setLinesPerRangeLabel(2);


    altHistoryPlot.setDomainLabel("");
    altHistoryPlot.getDomainTitle().pack();

    XYGraphWidget graphWidget_LEFT = altHistoryPlot.getGraph();

    graphWidget_LEFT.setMarginRight(0);
    graphWidget_LEFT.setPaddingRight(30);



    altHistoryPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.RIGHT).setFormat(new DecimalFormat("#"));


    altHistoryPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("#"));



    altHistoryPlot.setLayerType(View.LAYER_TYPE_NONE, null);


    // get a ref to the BarRenderer so we can make some changes to it:
    BarRenderer barRenderer = altHistoryPlot.getRenderer(BarRenderer.class);
    if(barRenderer != null) {
      // make our bars a little thicker than the default so they can be seen better:
      barRenderer.setBarGroupWidth(
              BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(18));
    }


    altHistoryPlot.getGraph().setMarginBottom(0);
    altHistoryPlot.getGraph().setMarginTop(10);
    //this removes the vertical lines
    altHistoryPlot.getGraph().setDomainGridLinePaint(null);
*/
  }


  @Override
  protected void finalize() {

    if(mGreenCarManager!=null) {
      mGreenCarManager.unregister(mBatteryChargeListener);
    }

    if(mGreenCarManager!=null) {
     mGreenCarManager.unregister(mEvPowerDisplayListener);
    }

    if(mGreenCarManager!=null) {
      mGreenCarManager.unregister(mOdometryListener);
    }

    if(mGreenCarManager!=null) {
      mGreenCarManager.unregister(mBatteryLevelListener);
    }

    if(mGreenCarManager!=null) {
      mGreenCarManager.unregister(mFuelEconomyListener);
    }

    if(mGreenCarManager!=null) {
      mGreenCarManager.unregister(mEnergyFlowListener);
    }


    if(mCarInfoManager!=null) {
      mCarInfoManager.unRegisterCarInfoListener(mCarInfoListener);
    }



    if(mTcpClient!=null) {
      mTcpClient.stopClient();
    }

  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String texto;
    Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this));
    ins = this;

    date = new Date();
    dateFormat = new SimpleDateFormat("yyMMddHHmm");//para el sesionId

    sesionInit = dateFormat.format(date);
    dateFormat = new SimpleDateFormat("yyyy.MM.dd");//Para el fichero de log


    writeToFile("START APP");
    writeToFile("VERSIÓN: "+VERSION);
    initManagers();
    requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
    getSupportActionBar().hide(); // hide the title bar
    setContentView(R.layout.activity_main);
    mLogger = new Logger(TAG);
    lastOBD2 = System.currentTimeMillis()/1000;
    mContext = this;

    carType      =  mConfigurationManager.getCarType();
    energyFlow   =  mGreenCarManager.getCurrentEnergyFlow();
    carOdometer  =  mGreenCarManager.getCluOdometer();
    tempOut      =  mHvacManager.getAmbientTemperatureC();
    String CarIF = mCarInfoManager.getCarIF();







    tvWifi          = (TextView) findViewById(R.id.tvWifi);
    pbBatt          = (ProgressBar) findViewById(R.id.pbBatt);
    tvElectricKw    = (TextView) findViewById(R.id.tvElectricKw);
    pbBattOBD2      = (ProgressBar) findViewById(R.id.pbBattOBD2);
    //rcBattOBD2 = (RoundCornerProgressBar) findViewById(R.id.rcBattOBD2);

    tvBattObd2Text  = (TextView) findViewById(R.id.tvBattObd2Text);
    tvCargaObd2Text  = (TextView) findViewById(R.id.tvCarga);
    tvDescargaObd2Text  = (TextView) findViewById(R.id.tvDescarga);

    tvCargaParcialObd2Text  = (TextView) findViewById(R.id.tvCargaParcial);
    tvDescargaParcialObd2Text  = (TextView) findViewById(R.id.tvDescargaParcial);

    tvBattTemp      = (TextView) findViewById(R.id.tvBattTemp);
    tvEvRPM         = (TextView) findViewById(R.id.tvEvRPM);
    tvLabelBTemp    = (TextView) findViewById(R.id.tvLabelBTemp);
    tvBattText      = (TextView) findViewById(R.id.tvBattText);
    tvOutTemp       = (TextView) findViewById(R.id.tvOutTemp);
    tvSpeed         = (TextView) findViewById(R.id.tvSpeed);
    tvRumbo         = (TextView) findViewById(R.id.tvRumbo);
    tvOdometro1     = (TextView) findViewById(R.id.tvOdometro1);
    tvOdometro2     = (TextView) findViewById(R.id.tvOdometro2);
    tvLog           = (TextView) findViewById(R.id.tvLog);
    tvAutEv         = (TextView) findViewById(R.id.tvAutEv);
    tvAutGas        = (TextView) findViewById(R.id.tvAutGas);
    tvAutGasLabel   = (TextView) findViewById(R.id.tvAutGasLabel);
    tvArvFuelEf     = (TextView) findViewById(R.id.tvArvFuelEf);
    tvArvFuelEfLabel     = (TextView) findViewById(R.id.tvArvFuelEfLabel);
    tvGearRel       = (TextView) findViewById(R.id.tvGearRel);
    tvCons100       = (TextView) findViewById(R.id.tvCons100);
    btnObd2         = (Button) findViewById(R.id.btn_OBD2);
    tvEnergyFlow    = (TextView) findViewById(R.id.tvEnergyFlow);
    tvEnergyFlowLabel    = (TextView) findViewById(R.id.tvEnergyFlowLabel);
    tvEVHEV         = (TextView) findViewById(R.id.tvEVHEV);
    tvAltitud       = (TextView) findViewById(R.id.tvAltitud);

    /*
    rcBattOBD2.setProgressBackgroundColor(android.R.color.black);
    rcBattOBD2.setMax(100);
    rcBattOBD2.setProgress(100);
*/
      updateLog("CarType: "+carType);


      getPreferences();

    texto = String.format(spanishLocale, "%.1f", consBattCargaStart);
    writeToFile("consBattCargaStart: "+ texto);

    texto = String.format(spanishLocale, "%.1f", consKwh100);
    tvCons100.setText(texto);

      pidToRead = "2101";
      request2101 = 0;
      if(mTcpClient!=null) {
          writeToFile("Error mTcpClient is not NULL");
          updateLog("Error mTcpClient is not NULL");
          mTcpClient.closeConnection();
          mTcpClient.stopClient();
          mTcpClient = null;

      }

    mLat = 0;
    mLon = 0;
    mAlt = 0;
    WifiManager wifi = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    if(wifi!=null) {
      WifiInfo info = wifi.getConnectionInfo();
      if(info!=null) {
        String ssid = info.getSSID();
        updateWifi(ssid);
      }
    }

    screenSize();
    updateValues();

    initSecondPlot();

    initPlot();//inicialización del Plot

    redrawer = new Redrawer(
            Arrays.asList(new Plot[]{aprHistoryPlot}),
            100, false);







    if(carType.contains("AE_EV")) {
      kwhMax = 90;
      energyFlow = 999;//forzamos update
      tvAutGasLabel.setText("100%");
      tvEnergyFlowLabel.setText("RENS");
      tvArvFuelEfLabel.setText("kWh/100");
      updateEnergyFlow();
    }else{
      kwhMax = 50;
    }




    updateBatPercent();

    writeToFile("batPercentPrev: "+battPercentPrev);
    writeToFile("batPercent:   : "+battPercent);
    if(battPercent>battPercentPrev){
      writeToFile(battPercentPrev+"->"+battPercent+", RESET KWh/100");
      updateLog(battPercentPrev+"->"+battPercent+", RESET KWh/100");
      tvCons100.setText("--");
      resetCons100();
    }

/*
      if(updateTimeToFull()) {
          //si se ha cargado el coche reset al contador de kwh/100
          writeToFile("timeToFullPrev: " + timeToFullPrev);
          writeToFile("timeToFull: " + timeToFull);
          if (timeToFullPrev > (timeToFull + 10)) { //todo Solo si accesorio está ON Verifiar como era??? ACCINFO
              writeToFile("El coche ha cargado, RESET KWh/100");
              updateLog("El coche ha cargado, RESET KWh/100");
              tvCons100.setText("--");
              resetCons100();
          }
      }
*/
    //está cargando el cocche?
    if(mGreenCarManager!=null) {
      writeToFile("Charging: " + mGreenCarManager.getChargeStatus());
     if(mGreenCarManager.getChargeStatus()==1) {

       tvCons100.setText("--");
       resetCons100();
     }
    }

    handlerUpd.postDelayed(new Runnable(){
      public void run(){
        //do something
        updateValues();
        handlerUpd.postDelayed(this, delayUpd);
      }
    }, delayUpd);

    handleUpdateFlow.postDelayed(new Runnable(){
      public void run(){
        //do something
        updateEnergyFlow();
        handleUpdateFlow.postDelayed(this, 500);
      }
    }, 500);


      handlerLog.postDelayed(new Runnable(){
          public void run(){
              //do something
              if(logInfo.length()>0){
                  updateLog(logInfo);
                  logInfo = "";
              }
              handlerLog.postDelayed(this, 500);
          }
      }, 500);


      handleCheckOBD2.postDelayed(new Runnable(){
      public void run(){
        //do something
        Long  currentOBD2 = System.currentTimeMillis()/1000;

        mLogger.log("OBDTIME "+(currentOBD2-lastOBD2));
        mLogger.log("currentOBD2 "+(currentOBD2.toString()));
        mLogger.log("lastOBD2 "+(lastOBD2.toString()));
        if((currentOBD2-lastOBD2)>15 && obd2Iniciado==true) {
          obd2Status(false);
          lastOBD2 = currentOBD2;
          initObd2();

        }else if((currentOBD2-lastOBD2)>1){

            obd2Status(false);
        }else{
          obd2Status(true);
        }
        handleCheckOBD2.postDelayed(this, 700);
      }
    }, 700);

    if(enableMqtt) {
      initMqtt();
    }

      if(autoStartOBD){
 //       restartWifi();
        initObd2();
      }

    if(enableMqtt) {
      sendMqttMessage(buildJsonMqtt());
      handleMqtt.postDelayed(new Runnable() {
        public void run() {
          //do something
          sendMqttMessage(buildJsonMqtt());
          handleMqtt.postDelayed(this, mqttInterval*1000);
        }
      }, mqttInterval*1000);
    }

    showToast("Iniciando");
  }




  private  String buildJsonMqtt(){
    Long tsLong = System.currentTimeMillis()/1000;
    String ts = tsLong.toString();
    JSONObject jsonObj = new JSONObject();
    tempOut      =  mHvacManager.getAmbientTemperatureC();
    speedGPS = mCarInfoManager.getCarSpeed();
    updateTimeToFull();
    try {
      jsonObj.put("TimeStamp",ts);
      jsonObj.put("carType",carType);
      jsonObj.put("Latitud", mLat);
      jsonObj.put("Longitud", mLon);
      jsonObj.put("Altitud", mAlt);
      jsonObj.put("Rumbo",mHeading);
      jsonObj.put("Odometro",carOdometer);
      jsonObj.put("Batt",battPercent);
      jsonObj.put("TimeToFull",timeToFull);
      jsonObj.put("AutEv",evAutonomia);
      jsonObj.put("CargaBatt", String.format(englishLocale, "%.1f", cargaTotal));
      jsonObj.put("DescargaBatt",String.format(englishLocale, "%.1f", descargaTotal));
      jsonObj.put("TempBat",tempBatt);
      jsonObj.put("TempOut",tempOut);
      jsonObj.put("isCharging",isCharging);
      jsonObj.put("fanWorking",fanWorking);
      jsonObj.put("bmsData",bmsData);
      jsonObj.put("speed",speedGPS);
      jsonObj.put("volBatt",String.format(englishLocale, "%.1f", volBatt));
      jsonObj.put("AppVersion",VERSION);
      jsonObj.put("Parcial1",odometro1);
      jsonObj.put("Parcial2",odometro2);
      jsonObj.put("Bat12v",String.format(englishLocale, "%.1f", bat12V));
      jsonObj.put("sesionInit",sesionInit);


    }
    catch(JSONException ex) {
      updateLog("Error build JSON");
    }

    return jsonObj.toString();

  }

  public void initMqtt(){
    MqttConnectOptions options = new MqttConnectOptions();

    try {
      //mqttCliente = new MqttAndroidClient (MQTT_BROKER_URL, "ioniq", new MemoryPersistence());
       mqttCliente = new MqttAndroidClient(this.getApplicationContext(), MQTT_BROKER_URL, "ioniqID", persistence);
      /*
      mqttCliente = new MqttAndroidClient(this.getApplicationContext(),MQTT_BROKER_URL, "ioniq", new MemoryPersistence());


      mqttCliente.setCallback(new MqttCallbackExtended() {
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
          if (reconnect) {
            Log.d(TAG, "Reconnected to : " + serverURI);
          } else {
            Log.d(TAG, "Connected to: " + serverURI);
          }
        }

        @Override
        public void connectionLost(Throwable cause) {
          Log.d(TAG, "The Connection was lost.");
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {}

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {}
      });

*/

      options.setUserName(MQTT_USER);
      options.setPassword(MQTT_PASSWORD.toCharArray());
      options.setKeepAliveInterval(10);//seconds
      options.setCleanSession(true);
      options.setAutomaticReconnect(true);

      mqttCliente.setCallback(this);
      updateLog("Init mqtt");
      writeToFile("Init mqtt");
      mqttCliente.connect(options);


/*

      try {
        mqttCliente.connect(options, null, new IMqttActionListener() {
          @Override
          public void onSuccess(IMqttToken asyncActionToken) {
            try {
              asyncActionToken.getSessionPresent();
            } catch (Exception e) {
              String message = e.getMessage();
              Log.d(TAG, "error message is null " + String.valueOf(message == null));
            }
            Log.d(TAG, "connected to: " + MQTT_BROKER_URL);
            Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
            DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
            disconnectedBufferOptions.setBufferEnabled(true);
            disconnectedBufferOptions.setBufferSize(100);
            disconnectedBufferOptions.setPersistBuffer(false);
            disconnectedBufferOptions.setDeleteOldestMessages(false);
            mqttCliente.setBufferOpts(disconnectedBufferOptions);
          }

          @Override
          public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            Log.d(TAG, "Failed to connect to: " + MQTT_BROKER_URL);
          }
        });

      } catch (MqttException ex) {
        updateLog(ex.getMessage());
        writeToFile(ex.getMessage());
        showToast(ex.getMessage());
      }
*/



    } catch (MqttException e) {
      updateLog(e.getMessage());
      writeToFile(e.getMessage());
      //e.printStackTrace();
    }




  }

  public void sendMqttMessage(String msg){
    /*
    if(!mqttCliente.isConnected()) {

      try {
        //mqttCliente.reconnect();
        updateLog("Reconectando mqtt");
        writeToFile("Reconectando mqtt");
        mLogger.log("Reconectando mqtt");
      } catch (MqttException e) {
        updateLog(e.getMessage());
        writeToFile(e.getMessage());
        mLogger.log(e.toString());
        //e.printStackTrace();
      }

    }

     */
    if(mqttCliente!=null) {
      if (mqttCliente.isConnected()) {
        try {
          MqttMessage message = buildMessage(msg, 0);
          mqttCliente.publish(MQTT_PUBLISH_TOPIC, message);
        } catch (UnsupportedEncodingException e) {
          updateLog(e.toString());
          writeToFile(e.toString());
          mLogger.log(e.toString());
        } catch (MqttException e) {
          updateLog(e.toString());
          writeToFile(e.toString());
          mLogger.log(e.toString());
        }

      }
    }

  }

  public MqttMessage buildMessage(@NonNull String msg, int qos)
          throws MqttException, UnsupportedEncodingException {
    byte[] encodedPayload = new byte[0];
    encodedPayload = msg.getBytes("UTF-8");
    MqttMessage message = new MqttMessage(encodedPayload);
    message.setId(5866);
    message.setRetained(true);
    message.setQos(qos);
    //client.publish(topic, message);
    return message;
  }

  @Override
  public void connectionLost(Throwable cause) {
    Log.d(TAG, "connectionLost...."+cause.toString());
    updateLog("connection Mqtt Lost....");
    writeToFile("connection Mqtt Lost....");
  }
  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    String payload = new String(message.getPayload());
    Log.d(TAG, payload);
    switch (payload) {
      case "ON":
        Log.d(TAG, "LED ON");

        break;
      case "OFF":
        Log.d(TAG, "LED OFF");

        break;
      default:
        Log.d(TAG, "Message not supported!");
        break;
    }
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    Log.d(TAG, "deliveryComplete....");
  }

  public void restartWifi(){
    WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    wifiManager.setWifiEnabled(false);
    wifiManager.setWifiEnabled(true);
  }

  public static MainActivity  getInstace(){

    return ins;
  }
  public void onClickOBD2(View v)
  {
    // TODO Auto-g,enerated method stub
    initObd2();

  }
  public void onClickAjustes(View v)
  {
    // TODO Auto-g,enerated method stub
    goToSettings();

  }

  public void onClickMqtt(View v)
  {
    // TODO Auto-g,enerated method stub


  }
 

  public void onClickSalir(View v)
  {

    savePreferences();
    writeToFile("CERRANDO APP");
    finalize();
    tcpIpTask.cancel(true);
    mTcpClient.closeConnection();
    mTcpClient.stopClient();
    mTcpClient = null;
    writeToFile("killProcess(android.os.Process.myPid()");
    android.os.Process.killProcess(android.os.Process.myPid());
    writeToFile("finish()");
    finish();

  }

  public void obd2Status(boolean status){
    if(status==true){
      btnObd2.setTextColor(0xFF08F311);
    }else{
      btnObd2.setTextColor(0xFFFF0000);
    }
  }
  public void initObd2(){

    final Handler handlerInit = new Handler();

    if(obd2Iniciado==false) {
      obd2Status(false);
      TcpClient.SERVER_IP = host;
      TcpClient.SERVER_PORT = port;
      updateKwhOBD2((double) 0);

      handlerInit.postDelayed(new Runnable() {
        @Override
        public void run() {
          //Do something after 100ms
          Log.v(TAG, "Init connection...");

          tcpIpTask =    new ConnectTask();
          tcpIpTask.execute("");

        }
      }, 5);


    }else{
      if(mTcpClient!=null){
        mTcpClient.closeConnection();
        mTcpClient.stopClient();
        handlerInit.postDelayed(new Runnable() {
          @Override
          public void run() {
            //Do something after 100ms
            Log.v(TAG, "Init connection...");

            tcpIpTask =    new ConnectTask();
            tcpIpTask.execute("");

          }
        }, 5);


      }

    }
  }
  public void delayedCommand(final int delay, final String string){
    final Handler handler = new Handler();
    if(mTcpClient!=null) {
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          mTcpClient.sendMessage(string);
        }
      }, delay);
    }
    lastOBD2 = System.currentTimeMillis()/1000;
  }

  private boolean updateTimeToFull(){
    if(mGreenCarManager==null) {
      return false;
    }
    if(carType.contains("AE_EV")) {
      timeToFull = mGreenCarManager.getCrBmsScChgRemTimeICCBEuro();
    }else{
      timeToFull = mGreenCarManager.getBatteryChargeTime240V();
    }
    return true;
  }


  private void savePreferences(){
    long odometro1LastSave = new Date().getTime();

    odometro1LastSave = odometro1LastSave / 1000;
    SharedPreferences.Editor editor = getSharedPreferences("IoniqParciales", MODE_PRIVATE).edit();
    editor.putInt("odometro1Start", odometro1Start);
    editor.putInt("odometro2Start", odometro2Start);
    editor.putInt("batPercentPrev",battPercent);
    editor.putLong("odometro1LastSave",odometro1LastSave);
    editor.putFloat("cargaParcialStart",cargaParcialStart);
    editor.putFloat("descargaParcialStart",descargaParcialStart);
    if(consKwh100>0) {
      editor.putFloat("consKwh100", consKwh100);
    }
    if(consBattDescargaStart>0) {
        editor.putFloat("consBattDescargaStart", consBattDescargaStart);
        editor.putFloat("consBattCargaStart", consBattCargaStart);
        editor.putFloat("consOdoStart",consOdoStart);
    }

    if(descargaTotal>0&&cargaParcialLast>0) {
        editor.putFloat("cargaParcialLast", cargaParcialLast);
    }
    updateTimeToFull();
    editor.putInt("timeToFull", timeToFull);

    editor.apply();
    updateLog("Save Preferences");
  }
  private void getPreferences(){
    long odometro1LastSave;
    long currentTimestamp       = new Date().getTime();
    currentTimestamp            = currentTimestamp /1000;
    SharedPreferences settings  = getSharedPreferences("IoniqParciales", MODE_PRIVATE);
    odometro1Start              = settings.getInt("odometro1Start", 0);
    odometro2Start              = settings.getInt("odometro2Start", 0);
    odometro1LastSave           = settings.getLong("odometro1LastSave",0);
    cargaParcialStart           = settings.getFloat("cargaParcialStart",0);
    descargaParcialStart        = settings.getFloat("descargaParcialStart",0);

    consBattCargaStart          = settings.getFloat("consBattCargaStart",0);//para cálculo kwh/100
    consBattDescargaStart       = settings.getFloat("consBattDescargaStart",0);//para cálculo kwh/100
    consOdoStart                = settings.getFloat("consOdoStart",0);//para cálculo kwh/100
    timeToFullPrev              = settings.getInt("timeToFull",999);
    cargaParcialLast            = settings.getFloat("cargaParcialLast",0);//para cálculo última carga realizada
    consKwh100                  = settings.getFloat("consKwh100",0);
    battPercentPrev              = settings.getInt("batPercentPrev",0);
    consKwh100Prev = consKwh100;

    settings           = getSharedPreferences("IoniqSettings", MODE_PRIVATE);
    host               = settings.getString("host", "obd2.lan");
    port               = settings.getInt("port", 35000);
    horasParcial1      = settings.getInt("horasParcial1",4);
    horasParcialBatt   = settings.getInt("horasParcialBatt",4);
    plotRecordsMax     = settings.getInt("plotRecordsMax",200);
    fastOBD            = settings.getBoolean("fastOBD",true);
    autoStartOBD       = settings.getBoolean("autoStartOBD",false);
    grabarTramasOBD    = settings.getBoolean("grabarTramasOBD", false);

    enableMqtt          = settings.getBoolean("MQTT",true);
    MQTT_BROKER_URL     = settings.getString("mqttHost", "tcp://farmer.cloudmqtt.com:18507");
    MQTT_USER           = settings.getString("mqttUser", "user");
    MQTT_PASSWORD       = settings.getString("mqttPass", "pass");
    mqttInterval        = settings.getInt("mqttInterval",60);

    writeToFile("<<<GET PREFERENCES>>>");
    writeToFile("HOST: " + host);
    writeToFile("odometro1LastSave: " + odometro1LastSave);
    writeToFile("currentOdometer: "+carOdometer);
    writeToFile("consOdoStart: " + consOdoStart);
    writeToFile("odometro1Start: " + odometro1Start);
    writeToFile("odometro2Start: " + odometro2Start);
    writeToFile("resetHorasParcial1: " + horasParcial1);
    writeToFile("ResetHorasParcialBatt: " + horasParcialBatt);
    if(enableMqtt) {
      writeToFile("enableMqtt: 1");
    }else{
      writeToFile("enableMqtt: 0");
    }




    currentTimestamp = currentTimestamp;
    long difTime = (currentTimestamp-odometro1LastSave);

    if(difTime>(horasParcial1*3600)&&horasParcial1>0){
      if (mGreenCarManager == null) {
        try {
          mGreenCarManager = GreenCarManager.getInstance(this);

        } catch (Exception e) {
          mLogger.log("error getting GreenCarManager", e);
        }
      }
      carOdometer          = mGreenCarManager.getCluOdometer();
      if(carOdometer!=0) {
        odometro1Start = carOdometer;
        writeToFile("Reset parcial 1 .difTime: "+difTime);
      }

    }


    if(difTime>(horasParcialBatt*3600)&&horasParcialBatt>0){
      cargaParcialStart    = 0;
      descargaParcialStart = 0;
    }



  }

  private void goToSettings(){

    Intent intent = new Intent(MainActivity.this, settings.class);
    startActivity(intent);
  }

  public static void updateTempIn(float temp){
    tempIn  = temp;
  }

  public static void updateTempOut(float temp){
    tempOut = temp;
  }

  private void updateTemperature(){



    if(appPaused==true){
      return;
    }


    if(tempOut!=tempOutPrev){
      String texto = String.format(spanishLocale, "%.1f", tempOut);
      tvOutTemp.setText(texto);
      tempOut=tempOutPrev;
    }

  }

  private void updateAltitud(){
      if(appPaused==true){
          return;
      }
      if(mAlt!=mAltPrev){
          mAltPrev = mAlt;
          String texto = String.format(spanishLocale, "%.0f", mAlt);
          tvAltitud.setText(texto);
      }

  }
  private void updateConsKwh100(){
    float kmRecorridos;
    float battConsumida;
    if(obd2Iniciado!=true){
      return;
    }
    //8kwh 63kmvfv
    //xkwh km actuales

    String texto ="";
    if(cargaTotal==0){//
      return;//obd2 no conectado o datos erróneos
    }
    //esto es por si se hizo un reset sin estar obd2 conectado
    if(  (consBattCargaStart == 0 ||  consBattDescargaStart   == 0) && carOdometer > 0 ){
      writeToFile("Reset cons100 1 | OBD2 working ");
      consOdoStart              = carOdometer;
      consBattCargaStart        = cargaTotal;
      consBattDescargaStart     = descargaTotal;
      consKwh100                = 0;
      consKwh100Prev            = 0;

      if(tvCons100!=null){
        texto = String.format(spanishLocale, "%.1f", consKwh100);
        tvCons100.setText(texto);
      }
      return;//y salimos!
    }

    kmRecorridos = carOdometer - consOdoStart;

      if (kmRecorridos > 0 && carOdometer>0) {
        battConsumida = (descargaTotal-consBattDescargaStart)-  (cargaTotal-consBattCargaStart) ;
          if(consKwh100Prev>0) {
            consKwh100 = (((100 * battConsumida) / kmRecorridos) + consKwh100Prev) / 2;
          }else{
            consKwh100 = (100 * battConsumida) / kmRecorridos;
          }
          if(consKwh100>100){
            resetCons100();
          }else{
          texto = String.format(spanishLocale, "%.1f", consKwh100);
          tvCons100.setText(texto);
          consKwh100Prev =  consKwh100;
          }
      }

  }

  public void onClickCons100(View v){

    resetCons100();
  }

  public void resetCons100(){

    //ponemos a 0 esté el obd2 o no, si no está el obd2 al estar a 0 cuando conecte hará reset!
    consOdoStart              = 0;
    consBattCargaStart        = 0;
    consBattDescargaStart     = 0;
    consKwh100                = 0;
    savePreferences();
    updateConsKwh100();
    writeToFile("<<<resetCons100>>>");

  }

private void initManagers(){
  if (mGreenCarManager == null) {
    try {
      mGreenCarManager = GreenCarManager.getInstance(this);
    } catch (Exception e) {
      mLogger.log("error getting GreenCarManager", e);
    }
  }
  if(mHvacManager == null) {
    try {
      mHvacManager = HvacManager.getInstance();
    } catch (Exception e) {
      mLogger.log("error getting HvacManager", e);
    }

  }
  if(mCarInfoManager == null) {
    try {
      mCarInfoManager = CarInfoManager.getInstance();
    } catch (Exception e) {
      mLogger.log("error getting CarInfoManager", e);
    }
  }

  if(mConfigurationManager==null){
    try {
       mConfigurationManager = ConfigurationManager.getInstance();
    } catch (Exception e) {
        mLogger.log("error getting CarInfoManager", e);
    }
  }


  writeToFile("<<<initManagers>>>");


}

  @Override
  protected void onResume() {
    super.onResume();
    appPaused = false;
    writeToFile("<<<onResume>>>");
    getPreferences();

    if(redrawer!=null) {
      redrawer.start();

    }
   // redrawerSecondPlot.start();
  }

  @Override
  protected void onPause() {
    super.onPause();
    appPaused = true;
    writeToFile("<<<onPause>>>");
    if(redrawer!=null) {
      redrawer.pause();

    }
    //redrawerSecondPlot.pause();
  }
  private void updateCargaOBD2(float carga){
    String texto;
    cargaTotal = carga;
    texto = String.format(spanishLocale,"%.1f", carga);
    tvCargaObd2Text.setText(texto);
    if(cargaParcialStart==0&&carga>0){
      cargaParcialStart         = carga;
      consOdoStart              = carOdometer;
    }
    if(cargaParcial !=(carga - cargaParcialStart)) {
      cargaParcial = carga - cargaParcialStart;
      texto = String.format(spanishLocale, "%.1f", cargaParcial);
      if(appPaused==false) {
        tvCargaParcialObd2Text.setText(texto);
      }
    }
    if(!checkedCargaLast){
      if((carga-cargaParcialLast)>0.5){
        float cargado = carga-cargaParcialLast ;
        texto = String.format(spanishLocale,"%.1f", cargado);
        resetCons100();
        writeToFile("Cargados "+texto+" kwh");
        updateLog("Cargados "+texto+" kwh");

        String textToast = "Cargado "+texto+" kwh";
        showToastBattInfo(textToast);

      }

      checkedCargaLast=true;
    }

    if(descargaTotal>0&&obd2Iniciado&&cargaTotal>0) {
        cargaParcialLast = cargaTotal;//siempre guardamos kwh actuales de batería cargada
    }
  }


private void showToast(String texto){
  LayoutInflater inflater = getLayoutInflater();
  View layout = inflater.inflate(R.layout.toast,
          (ViewGroup) findViewById(R.id.custom_toast_container));

  TextView text = (TextView) layout.findViewById(R.id.text);
  text.setText(texto);

  Toast toast = new Toast(getApplicationContext());
  toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
  toast.setDuration(Toast.LENGTH_LONG);
  toast.setView(layout);
  toast.show();
}

  private void showToastBattInfo(String texto){
    LayoutInflater inflater = getLayoutInflater();
    View layout = inflater.inflate(R.layout.toastbatt,
            (ViewGroup) findViewById(R.id.custom_toast_container));

    TextView text = (TextView) layout.findViewById(R.id.text);
    text.setText(texto);

    Toast toast = new Toast(getApplicationContext());
    toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
    toast.setDuration(Toast.LENGTH_LONG);
    toast.setView(layout);
    toast.show();
  }

  private void updateDescargaOBD2(float descarga){
    descargaTotal = descarga;
    String texto = String.format(spanishLocale,"%.1f", descarga);
    tvDescargaObd2Text.setText(texto);
    if(descargaParcialStart==0&&descarga>0){
      descargaParcialStart=descarga;
    }
    if(descargaParcial!=(descarga - descargaParcialStart)) {
      descargaParcial = descarga - descargaParcialStart;
      texto = String.format(spanishLocale, "%.1f", descargaParcial);
      if(appPaused==false) {
        tvDescargaParcialObd2Text.setText(texto);
      }
    }

  }


  private void  updateBattTempOBD2(float temp){
    String texto = String.format(spanishLocale,"%.1f", temp);
    if(appPaused==false) {
      tvBattTemp.setText(texto);
    }
    //tvBattTemp.setBackgroundColor(0x684CAF50);

  }

  private void  updateFanStatus(boolean status){
    if(appPaused==false) {
      if (status == true) {
        tvLabelBTemp.setTextColor(getResources().getColor(R.color.green));
      } else {
        tvLabelBTemp.setTextColor(getResources().getColor(R.color.white));
      }
    }
  }
  private void  updateEvRPMOBD2(float rpm){
    String texto;
    if(rpm>100){
      texto = String.format(spanishLocale,"%.0f", rpm);

    }else{
      texto = "0";
    }
    if(appPaused==false) {
      tvEvRPM.setText(texto);
    }
    evRPM = rpm;

  }
private void updateKwhOBD2(Double kwh){


  String texto = String.format(spanishLocale,"%.1f", kwh);
  if(appPaused==false) {
    tvBattObd2Text.setText(texto + " kW");
  }
  int percentValue = 0;



  if(kwh<0){
    percentValue = (int) (100*(kwh*-1))/kwhMax;
   // rcBattOBD2.setReverse(true);
    pbBattOBD2.getProgressDrawable().setColorFilter( Color.BLUE, PorterDuff.Mode.SRC_IN);
   // rcBattOBD2.setProgressColor(Color.BLUE);
  }else{
    percentValue = (int) (100*kwh)/kwhMax;
    //rcBattOBD2.setReverse(false);
    pbBattOBD2.getProgressDrawable().setColorFilter( Color.GREEN, PorterDuff.Mode.SRC_IN);
    //rcBattOBD2.setProgressColor(Color.GREEN);
  }

  pbBattOBD2.setProgress(percentValue);


}

private void updateEnergyFlow(){

  //int currentEnergyFlow = mGreenCarManager.getCurrentEnergyFlow();
  if(energyFlow!=energyFlowPrev){
    energyFlowPrev = energyFlow;
    tvEnergyFlow.setText(Integer.toString(energyFlow));
    if(energyFlow==2||energyFlow==6||energyFlow==1||carType.contains("AE_EV")){
      tvEVHEV.setBackgroundColor(getResources().getColor(R.color.green));
      tvEVHEV.setTextColor(getResources().getColor(R.color.blue));
      tvEVHEV.setText("-EV-");
    }else if(energyFlow==0) {
      tvEVHEV.setBackgroundColor(getResources().getColor(R.color.black));
      tvEVHEV.setTextColor(getResources().getColor(R.color.black));
      tvEVHEV.setText("");
    }else{
      tvEVHEV.setBackgroundColor(getResources().getColor(R.color.orange));
      tvEVHEV.setTextColor(getResources().getColor(R.color.white));
      tvEVHEV.setText("-HEV-");
    }
  }

  if(carType.contains("AE_EV")){
    StringBuilder texto = new StringBuilder();
    texto.append(mGreenCarManager.getRegenLevelEco()+1)
            .append("/")
            .append(mGreenCarManager.getRegenLevelNormal()+1)
            .append("/")
            .append(mGreenCarManager.getRegenLevelSport()+1);
    tvEnergyFlow.setText(texto);
  }
  if(isCharging==1){
    tvEVHEV.setBackgroundColor(getResources().getColor(R.color.black));
    tvEVHEV.setTextColor(getResources().getColor(R.color.blue));
    tvEVHEV.setText("CARGANDO");
  }

  //en el ev tenemos mGreenCarManager.getDriveMode() que nos da

}

  public static void updateEnergyFlow(int valule){

    energyFlow = valule;
  }

  public static void updateGps(double lat, double lon, double alt, int heading ) {
    mLat = lat;
    mLon = lon;
    mAlt = alt;
    mHeading = heading;
    //updateValues();
  }

  public void updateHeading(){
    tvRumbo.setText(Integer.toString(mHeading));
  }

  public void updateElectricKw() {


    if(carType.contains("AE_EV")) {
      mElectricKw = mGreenCarManager.getCrMcuMotPwrAvnKw();
      if(mElectricKw>=128){
        mElectricKw = (255-mElectricKw)*-1;
      }
      tvElectricKw.setText(String.format(spanishLocale,"%.0f", mElectricKw));
    }else{
      mElectricKw = mGreenCarManager.getUsedEelctricMotor();
      tvElectricKw.setText(String.format(spanishLocale,"%.0f", mElectricKw));
    }

    //updateLog("update ElectricKw");

  }

  public void updateAverageFuelEfficiency() {
    mArvFuelEf =  mGreenCarManager.getAverageFuelEfficiency();
    if(mArvFuelEf>70){
        tvArvFuelEf.setText("---");
    }else{
        tvArvFuelEf.setText(String.format(spanishLocale,"%.1f", mArvFuelEf));
    }

}

  public void updateWifi(String wifiName) {

    mwifiName = wifiName;
    if(tvWifi!=null) {
      tvWifi.setText("Wifi: "+mwifiName.replaceAll("\"", ""));
    }
    writeToFile("WiFi: "+mwifiName);
  }



  public void updateBatPercent(){


    if(battPercentOld==0){
      battPercentOld = battPercent;
    }
    if(carType.contains("AE_EV")||carType.contains("AE_PHEV")) {
      battPercent = mGreenCarManager.getBatteryChargePersent();
    }else{
      battPercent = battPercentOBD2;
    }
    StringBuilder percentTxt =
            new StringBuilder().append(battPercent).append("%");
    tvBattText.setText(percentTxt);
    //updateLog("updateBatPercent");


    pbBatt.setProgress(battPercent);

    if(battPercent>50) {
      pbBatt.getProgressDrawable().setColorFilter(
              Color.BLUE, PorterDuff.Mode.SRC_IN);
    }else if(battPercent>30){
      pbBatt.getProgressDrawable().setColorFilter(
              Color.YELLOW, PorterDuff.Mode.SRC_IN);
    }else if(battPercent>0){
      pbBatt.getProgressDrawable().setColorFilter(
              Color.RED, PorterDuff.Mode.SRC_IN);
    }

    if(carType.contains("AE_EV")||carType.contains("AE_PHEV")) {
      if (battPercentOld > 75 && battPercent <= 75) {
        showToastBattInfo("Batería < 75%");
      }
      if (battPercentOld > 50 && battPercent <= 50) {
        showToastBattInfo("Batería < 50%");
      }
      if (battPercentOld > 25 && battPercent <= 25) {
        showToastBattInfo("Batería < 25%");
      }
      if (battPercentOld > 10 && battPercent <= 10) {
        showToastBattInfo("Batería < 10%");
      }
      if (battPercentOld != battPercent) {
        battPercentOld = battPercent;
      }
    }
  }

public void updateGPSSpeed(){

  speedGPS = mCarInfoManager.getCarSpeed();
  tvSpeed.setText(Integer.toString(speedGPS));
  if(speedGPS<1&&prefGrabadas==false){
    savePreferences();
    prefGrabadas=true;
  }
  if(speedGPS>1&&prefGrabadas==true ){
    prefGrabadas=false;
  }

  String texto;
  //test para conocer la marcha engranada.
  if(speedGPS!=speedGPSPrev) {
    if(evRPM>100) {
        float gearRelation = evRPM / speedGPS * 10;
        texto = String.format(spanishLocale, "%.0f", gearRelation);
        if (gearRelation < 225) {
          tvGearRel.setText("6");
        } else if (gearRelation < 260) {
          tvGearRel.setText("5");
        } else if (gearRelation < 360) {
          tvGearRel.setText("4");
        } else if (gearRelation < 520) {
          tvGearRel.setText("3");
        } else if (gearRelation < 870) {
          tvGearRel.setText("2");
        } else if (gearRelation > 870) {
          tvGearRel.setText("1");
        } else {
          tvGearRel.setText("");
        }
    }
    if(speedGPS<1){
      tvGearRel.setText("1");
    }
    evRPM=evRPMPrev;
    speedGPS=speedGPSPrev;
  }

/*
  210 6;
  240 5;
  340 4;
  520 3;
  860 2;
  */

}

  private void updatePlot(){
    if(tempBatt==999){
      return;//aun no inicializado
    }
    if(cargaHistorySeries==null) {
      return;
    }
      // get rid the oldest sample in history:
      if (cargaHistorySeries.size() > HISTORY_SIZE) {
        cargaHistorySeries.removeFirst();
        descargaHistorySeries.removeFirst();
        batTempHistorySeries.removeFirst();

      }


      if (wattsBatt == 0) {
        cargaHistorySeries.addLast(null, 0);
        descargaHistorySeries.addLast(null, 0);
      } else if (wattsBatt < 0) {
        cargaHistorySeries.addLast(null, ((int)wattsBatt* -1));
        descargaHistorySeries.addLast(null, 0);
      } else if (wattsBatt > 0) {
        cargaHistorySeries.addLast(null, 0);
        descargaHistorySeries.addLast(null, ((int)wattsBatt ));
      }

      batTempHistorySeries.addLast(null, tempBatt);

  }
private void updateSecondPlot(){

/*
    if(altHistorySeries==null){
      return;
    }

  if (altHistorySeries.size() > HISTORY_SIZE) {
    altHistorySeries.removeFirst();
  }

  altHistorySeries.addLast(null, mAlt);
*/


}
public void updateChargingStatus(){
    isCharging = mGreenCarManager.getChargeStatus();
    if(isCharging==1&&chargingReset==false){
      chargingReset = true;
      onClickCarga();
      onClickDescarga();
    }
    if(isCharging==0&&chargingReset==true){
        chargingReset = false;
    }
  }

  public  void  updateValues(){
    if(appPaused==false) {
        updateGPSSpeed();
        updateElectricKw();
        updateBatPercent();
        updateOdometer();
        updateAutEv();

        updateAutGas();
        updateAverageFuelEfficiency();
        updateTemperature();
        updateAltitud();
        updateHeading();
    }

    //updateEnergyFlow();Tiene un handle propio cada 500ms
    //mGreenCarManager.getHuEvPe00CEvMaxSpeed();

      updatePlot(); //el render conrola no hacerlo si no está visible, nosotros añadimos los datos siempre
      updateSecondPlot();
      updateChargingStatus();

  }

  public  void updateOdometer(){

    //int currentOdometer;
    //currentOdometer = mGreenCarManager.getCluOdometer();

    if(carOdometerPrev!=carOdometer){
      carOdometerPrev=carOdometer;
      updateConsKwh100();//calculo consumo kwh / 100
    }
    updateOdometro1();
    updateOdometro2();
  }

  private void updateOdometro1(){
    if(odometro1Start==0&&carOdometer>0){
      odometro1Start = carOdometer;
    }
    if(odometro1 < carOdometer-odometro1Start){
      odometro1 = carOdometer-odometro1Start;
      tvOdometro1.setText(Integer.toString(odometro1));
    }
  }

  private void updateOdometro2(){
    if(odometro2 < carOdometer-odometro2Start){
      odometro2 = carOdometer-odometro2Start;
      tvOdometro2.setText(Integer.toString(odometro2));
    }
  }

  public void onClickOdometro1(View v){

    onClickOdometro1();
  }

  public void onClickWifi(View v){

    restartWifi();

  }

  public void onClickOdometro1(){
    updateOdometer();
    odometro1Start = carOdometer;
    odometro1 = 0;
    tvOdometro1.setText(Integer.toString(odometro1));
    savePreferences();
    updateOdometro1();
  }

    public void onClickOdometro2(View v){

        onClickOdometro2();
    }

    public void onClickOdometro2(){
        updateOdometer();
        odometro2Start = carOdometer;
        odometro2 = 0;
        tvOdometro2.setText(Integer.toString(odometro2));
        savePreferences();
        updateOdometro1();
    }

  public void onClickDescarga(View v){

    onClickDescarga();
  }

  public void onClickDescarga(){
    descargaParcialStart = 0;
    savePreferences();
  }
  public void onClickCarga(View v){

    onClickCarga();
  }

  public void onClickCarga(){
    cargaParcialStart = 0;
    savePreferences();
  }



public void screenSize(){

  Point size = new Point();
  WindowManager w = getWindowManager();


    w.getDefaultDisplay().getSize(size);
    Measuredwidth = size.x;
    Measuredheight = size.y;

}


  public  void updateAutEv(){
    if(carType.contains("AE_EV")) {
      evAutonomia = mGreenCarManager.getCrVcuDistEmptyKm();
    }else{
      evAutonomia = mGreenCarManager.getEVOdometry();

    }
    tvAutEv.setText(Integer.toString(evAutonomia));
    //updateLog("update Autonomía EV");
  }
  public  void updateAutGas(){
    if(carType.contains("AE_EV")) {
      //gasAutonomia = mGreenCarManager.getEcoPotentialEco();
      gasAutonomia = evAutonomia*100/battPercent;
    }else if(carType.contains("AE_PHEV")){
      gasAutonomia = mGreenCarManager.getGasOdometry();
    }

    tvAutGas.setText(Integer.toString(gasAutonomia));
    //updateLog("update Autonomía Gas");
  }

  public void updateLog(String textLog){
    Time today = new Time(Time.getCurrentTimezone());
    today.setToNow();
    tvLog.setText(today.format("%H:%M:%S")+": "+textLog);


  }

  public void onCaptureScreen(View v){

    captureScreen();
  }

  private void captureScreen() {
    View v = getWindow().getDecorView().getRootView();
    v.setDrawingCacheEnabled(true);
    Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
    v.setDrawingCacheEnabled(false);
    try {
      final File file = new File("/storage/sdcard0/_ioniqInfo", "SCREEN"+ System.currentTimeMillis() + ".png");
      FileOutputStream fos = new FileOutputStream(file);
      bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
      fos.flush();
      fos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    updateLog("<<--Screenshot-->>");
  }

  public static void writeToFile(String data)
  {

    Time today = new Time(Time.getCurrentTimezone());
    today.setToNow();
    String textLogSave = today.format("%H:%M:%S")+": "+data;
    String textLogFilePrefix =  dateFormat.format(date);

    //final File file = new File(getFilesDir(), "log.txt");
    final File file = new File("/storage/sdcard0/_ioniqInfo", textLogFilePrefix+  "__log.txt");
    // Save your stream, don't forget to flush() it before closing it.

    try
    {
      if(!file.exists()){
        final File newFile = new File("/storage/sdcard0/_ioniqInfo");
        newFile.mkdir();
      }
      if(!file.exists()){
        file.createNewFile();
      }else{


      }
      FileOutputStream fOut = new FileOutputStream(file,true);
      OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
      myOutWriter.append(textLogSave+"\r");

      myOutWriter.close();

      fOut.flush();
      fOut.close();
    }
    catch (java.io.IOException e)
    {
      Log.e("Exception", "File write failed: " + e.toString());
    }
  }
  public static float round2(float number, int scale) {
    int pow = 10;
    for (int i = 1; i < scale; i++)
      pow *= 10;
    float tmp = number * pow;
    return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
  }

   //mqtt
  //https://github.com/Thumar/MQTT/blob/master/app/src/main/java/com/app/androidkt/mqtt/Constants.java
//https://medium.com/@abhi007tyagi/android-things-led-control-via-mqtt-b7509576c135

  public class ConnectTask extends AsyncTask<String, String, TcpClient> {



    @Override
    protected TcpClient doInBackground(String... message) {
      if(mTcpClient!=null){
        writeToFile("Error mTcpClient is not NULL");
        mTcpClient.stopClient();
        mTcpClient.closeConnection();
      }

      //we create a TCPClient object
      mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
        @Override
        //here the messageReceived method is implemented
        public void messageReceived(String message) {
          //this method calls the onProgressUpdate
          publishProgress(message);
        }
      });
      Log.v(TAG, "ConnectTask");
      mTcpClient.run();


      return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
      super.onProgressUpdate(values);
      boolean tratado = false;

      float firstPart  = 0;
      float secondPart = 0;
      float thirdPart  = 0;
      float fourthPart = 0;
      StringBuilder texto = new StringBuilder();
      //response received from server
      //Log.v("TCP", "response " + values[0]);
      //process server response here....

      if(grabarTramasOBD){
        writeToFile(values[0]);
      }

      if(values[0].contains("||OK||")){
        updateLog(values[0]);
        writeToFile(values[0].replace("||OK||",""));
        obd2Status(true);
        return;
      }
      if(values[0].contains("||KO||")){
        updateLog(values[0]);
        writeToFile(values[0].replace("||KO||",""));
        obd2Status(false);
        return;
      }

      if(values[0].length()>1) {
        if(values[0].contains("OBDII")||values[0].contains("ICSolution")){
          updateLog("OBDII Conectado");
          writeToFile("OBDII Conectado "+values[0]);
          obd2Iniciado = true;
          delayedCommand(200,"2101");
          obd2Status(true);
          MainActivity.logInfo = "";
          return;
        }
        if(values[0].contains("ERROR SOCKET")){
          updateLog(values[0]);
          obd2Status(false);
          return;
        }

        if(fastOBD==true){
          //obd2Status(true);
          //writeToFile(values[0]);
          //delayedCommand(100,pidToRead);
          //return;
        }

        if(pidToRead.contains("2101")){
          if(values[0].contains("03D")){
            tratado = true;
          }
          if(values[0].contains("0: ")){
            tratado = true;
          }
          if(values[0].contains("1: ")){
            byte2101_01 = stringToStringArray(values[0].replace("1: ",""));
            tratado = true;
          }
          if(values[0].contains("2: ")){
            byte2101_02 = stringToStringArray(values[0].replace("2: ",""));
            tratado = true;
          }
          if(values[0].contains("3: ")){
            tratado = true;
          }
          if(values[0].contains("4: ")){
            byte2101_04 = stringToStringArray(values[0].replace("4: ",""));
            tratado = true;
          }
          if(values[0].contains("5: ")){
            byte2101_05 = stringToStringArray(values[0].replace("5: ",""));
            tratado = true;
          }
          if(values[0].contains("6: ")){
            byte2101_06 = stringToStringArray(values[0].replace("6: ",""));
            tratado = true;
          }
          if(values[0].contains("7: ")){
            tratado = true;
          }
          if(values[0].contains("8: ")  ){
            byte2101_08 = stringToStringArray(values[0].replace("8: ",""));
            tratado = true;

            booleanContinue = true;
            try {
                    // if(byte2101_01[6]>128) {//negativo
                  //     ampBatt = ((byte2101_01[6] * 256) + byte2101_02[0]) / 10;
                  // }else{
                  //     ampBatt = (((byte2101_01[6] - 256)*256) + byte2101_02[0]) / 10;
                  // }
                  //String ampBattString = byte2101_01[6] + byte2101_02[0];
                  firstPart  = Integer.parseInt(byte2101_01[6],16);;
                  secondPart = Integer.parseInt(byte2101_02[0],16);
                  if(firstPart>127) {//negativo
                    //(((CELDA H4-256)*256)+CELDA B2/10)  ((254-256)*256)+41)/10= -47,1 Amperios
                    ampBatt = (((firstPart-256)*256)+secondPart)/10;
                  }else{
                    //((CELDA H4*256)+CELDA B2/10)  ((1*256)+41)/10=29,7 Amperios

                    ampBatt = ( (firstPart*256)+secondPart )/10;
                  }

                  //mTextAmperios.setText(String.valueOf(ampBatt));

                  firstPart  = Integer.parseInt(byte2101_02[1],16);;
                  secondPart = Integer.parseInt(byte2101_02[2],16);
                  volBatt = ((firstPart*256)+secondPart)/10;
                  wattsBatt = (int)((volBatt * ampBatt)/10);
                  wattsBatt = wattsBatt / 100; //dejando en kwh con un decimal
                  updateKwhOBD2(wattsBatt);


                  texto = new StringBuilder();
                  firstPart  = Integer.parseInt(byte2101_05[6]+byte2101_06[0]+byte2101_06[1]+byte2101_06[2],16);
                  texto.append(firstPart);
                  cargaTotal = round2((Float.parseFloat(texto.toString())/10),2);
                  updateCargaOBD2(cargaTotal);


                  texto = new StringBuilder();
                  firstPart  = Integer.parseInt(byte2101_06[3]+byte2101_06[4]+byte2101_06[5]+byte2101_06[6],16);
                  texto.append(firstPart);
                  descargaTotal = round2((Float.parseFloat(texto.toString())/10),2);
                  updateDescargaOBD2(descargaTotal);


                  //temperatura batería

                  firstPart  = Integer.parseInt(byte2101_02[5],16);
                  secondPart = Integer.parseInt(byte2101_02[6],16);
                  tempBatt = (firstPart+secondPart)/2;
                  updateBattTempOBD2(tempBatt);

                  //cargando?¿
                   firstPart  = Integer.parseInt(byte2101_01[5],16);;
                  bmsData = byte2101_01[5].substring(0,1);
                  //fan status
                  firstPart  = Integer.parseInt(byte2101_04[2],16);
                  if(firstPart>0&&fanWorking==false) {
                    fanWorking = true;
                    updateFanStatus(fanWorking);
                  }
                  if(firstPart==0&&fanWorking==true) {
                    fanWorking = false;
                    updateFanStatus(fanWorking);
                  }

              firstPart  = Integer.parseInt(byte2101_04[4],16);
              bat12V     = firstPart/10;


                  //RPM EV
                    firstPart = Integer.parseInt(byte2101_08[0], 16);
                    secondPart = Integer.parseInt(byte2101_08[1], 16);
                  if(firstPart<125) {//negativo
                    evRPM = (firstPart * 256) + secondPart;
                    updateEvRPMOBD2(evRPM);
                  }else{
                    updateEvRPMOBD2(0);
                  }

                  //% de la batería que en el HEV no está en el BUS
                  firstPart  = Integer.parseInt(byte2101_01[0],16);
                  battPercentOBD2 = (int) firstPart/2;
            } catch(Exception e) {
              updateLog("ERROR TRY "+e.toString());
              writeToFile("ERROR TRY "+e.toString());

            }

            delayedCommand(5,pidToRead);
            //mTcpClient.sendMessage(pidToRead);
          }

          if( values[0].contains("CAN ERROR")||
                  values[0].contains("?")||
                  values[0].contains("NO DATA")||
                  values[0].contains("STOPPED")||
                  values[0].contains("7F 10 79")||
                  values[0].contains("7F 21 12")||
                  values[0].contains("7F 21 79")){

            writeToFile(values[0]);
            booleanContinue = true;
            tratado=false;
            obd2Status(false);
            //dalayBucle = 500;



            delayedCommand(150,pidToRead);
            //mTcpClient.sendMessage(pidToRead);
          }
          if(tratado==false){
            updateLog(values[0]);
            writeToFile(values[0]);
            obd2Status(false);
          }else{
            obd2Status(true);

          }
        }


        if(pidToRead.contentEquals("05")){
          updateLog("PID 05: "+values[0]);
          delayedCommand(150,"2101");
        }

      }
    }


    public  String[] stringToStringArray(String s) {

      String[] separated = s.split(" ");
      return separated;
    }

  }



  @Override
  protected void onDestroy() {
    super.onDestroy();

    if(redrawer!=null) {
      redrawer.finish();

    }


  }
}