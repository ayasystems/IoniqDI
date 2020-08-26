package ea4gkq.at.ioniqInfo.greencar;

import android.os.RemoteException;

import com.lge.ivi.greencar.IFuelEconomyListener;

import ea4gkq.at.ioniqInfo.Logger;
import ea4gkq.at.ioniqInfo.MainActivity;

public class FuelEconomyListener extends IFuelEconomyListener.Stub {
  private final String TAG = "at.ea4gkq.ivireceiver.FuelEconomyListener";
  private final Logger mLogger;

  private final NumberHolder mCfCluAvgFclDrvInfo = new NumberHolder();
  private final NumberHolder mCfCluAvgFclDrvInfoReset = new NumberHolder();
  private final NumberHolder mHEVAverageFuelEfficiencyChanged = new NumberHolder();
  private final NumberHolder mHEVFuelConsumptionTypeChanged = new NumberHolder();
  private final NumberHolder mHEVGraphEelctricMotorChanged = new NumberHolder();
  private final NumberHolder mHEVGraphFuelEfficiencyChanged = new NumberHolder();
  private final NumberHolder mHEVUsedEelctricMotorChanged = new NumberHolder();
  private final NumberHolder mHEVUsedFuelEfficiencyChanged = new NumberHolder();
  private final NumberHolder HEVUsedEelctricMotorChanged = new NumberHolder();


  public FuelEconomyListener() {
    mLogger = new Logger(TAG);
    mLogger.log("new FuelEconomyListener instance created!");
    if(MainActivity.ins!=null) {
      MainActivity.writeToFile("FuelEconomyListener instance created");
    }
  }

  @Override
  public void onCfCluAvgFclDrvInfo(float oldValue, float newValue) throws RemoteException {
    if (mCfCluAvgFclDrvInfo.equals(oldValue, newValue)) {
      return;
    }
    mCfCluAvgFclDrvInfo.setValues(oldValue, newValue);
    mLogger.log("onCfCluAvgFclDrvInfo", oldValue, newValue);
      if(MainActivity.ins!=null) {
          MainActivity.writeToFile("onCfCluAvgFclDrvInfo: "+oldValue+" / "+ newValue);
      }
  }

  @Override
  public void onCfCluAvgFclDrvInfoReset(int oldValue, int newValue) throws RemoteException {
    if (mCfCluAvgFclDrvInfoReset.equals(oldValue, newValue)) {
      return;
    }
    mCfCluAvgFclDrvInfoReset.setValues(oldValue, newValue);
    mLogger.log("onCfCluAvgFclDrvInfoReset", oldValue, newValue);
      if(MainActivity.ins!=null) {
          MainActivity.writeToFile("onCfCluAvgFclDrvInfoReset: "+oldValue+" / "+ newValue);
      }
  }

  @Override
  public void onHEVAverageFuelEfficiencyChanged(float oldValue, float newValue) throws RemoteException {
    if (mHEVAverageFuelEfficiencyChanged.equals(oldValue, newValue)) {
      return;
    }
    mHEVAverageFuelEfficiencyChanged.setValues(oldValue, newValue);
    mLogger.log("onHEVAverageFuelEfficiencyChanged", oldValue, newValue);
      if(MainActivity.ins!=null) {
          MainActivity.writeToFile("onHEVAverageFuelEfficiencyChanged: "+oldValue+" / "+ newValue);
      }
  }

  @Override
  public void onHEVFuelConsumptionTypeChanged(int oldValue, int newValue) throws RemoteException {
    if (mHEVFuelConsumptionTypeChanged.equals(oldValue, newValue)) {
      return;
    }
    mHEVFuelConsumptionTypeChanged.setValues(oldValue, newValue);
    mLogger.log("onHEVFuelConsumptionTypeChanged", oldValue, newValue);
      if(MainActivity.ins!=null) {
          MainActivity.writeToFile("onHEVFuelConsumptionTypeChanged: "+oldValue+" / "+ newValue);
      }
  }

  @Override
  public void onHEVGraphEelctricMotorChanged(float oldValue, float newValue) throws RemoteException {
    if (mHEVGraphEelctricMotorChanged.equals(oldValue, newValue)) {
      return;
    }
    mHEVGraphEelctricMotorChanged.setValues(oldValue, newValue);
    mLogger.log("onHEVGraphEelctricMotorChanged", oldValue, newValue);
      if(MainActivity.ins!=null) {
          MainActivity.writeToFile("onHEVGraphEelctricMotorChanged: "+oldValue+" / "+ newValue);
      }
  }

  @Override
  public void onHEVGraphFuelEfficiencyChanged(float oldValue, float newValue) throws RemoteException {
    if (mHEVGraphFuelEfficiencyChanged.equals(oldValue, newValue)) {
      return;
    }
    mHEVGraphFuelEfficiencyChanged.setValues(oldValue, newValue);
    mLogger.log("onHEVGraphFuelEfficiencyChanged", oldValue, newValue);
      if(MainActivity.ins!=null) {
          MainActivity.writeToFile("onHEVGraphFuelEfficiencyChanged: "+oldValue+" / "+ newValue);
      }
  }

  @Override
  public void onHEVUsedEelctricMotorChanged(float oldValue, float newValue) throws RemoteException {
      if (mHEVUsedEelctricMotorChanged.equals(oldValue, newValue)) {
          return;
      }
      mHEVUsedEelctricMotorChanged.setValues(oldValue, newValue);
      mLogger.log("onHEVGraphFuelEfficiencyChanged", oldValue, newValue);
      if(MainActivity.ins!=null) {
         // MainActivity.writeToFile("onHEVUsedEelctricMotorChanged: "+oldValue+" / "+ newValue);
      }



  }

  @Override
  public void onHEVUsedFuelEfficiencyChanged(float oldValue, float newValue) throws RemoteException {
    if (mHEVUsedFuelEfficiencyChanged.equals(oldValue, newValue)) {
      return;
    }
    mHEVUsedFuelEfficiencyChanged.setValues(oldValue, newValue);
    mLogger.log("onHEVUsedFuelEfficiencyChanged", oldValue, newValue);
      if(MainActivity.ins!=null) {
          MainActivity.writeToFile("onHEVUsedFuelEfficiencyChanged: "+oldValue+" / "+ newValue);
      }
  }
}
