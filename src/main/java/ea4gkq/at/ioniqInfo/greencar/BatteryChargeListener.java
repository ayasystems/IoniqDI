package ea4gkq.at.ioniqInfo.greencar;


import android.os.RemoteException;

import com.lge.ivi.greencar.IBatteryChargeListener;



import ea4gkq.at.ioniqInfo.Logger;


public class BatteryChargeListener extends  IBatteryChargeListener.Stub {

  private final String TAG = "at.ea4gkq.ivireceiver.BatteryChargeListener";
  private final Logger mLogger;

  private final NumberHolder mChargeRemainedTimeminChanged = new NumberHolder();
  private final NumberHolder mPHEVBatteryChargePersentChanged = new NumberHolder();
  private final NumberHolder mPHEVBatteryChargeTime120VChanged = new NumberHolder();
  private final NumberHolder mPHEVBatteryChargeTime240VChanged = new NumberHolder();
  private final NumberHolder mPHEVChargeStatusChanged = new NumberHolder();
  private final NumberHolder mPHEVStandardCharge120VChanged = new NumberHolder();
  private final NumberHolder mPHEVStandardChargeChanged = new NumberHolder();

  public BatteryChargeListener() {
    mLogger = new Logger(TAG);
    mLogger.log("new BatteryChargeListener instance created!");
  }

  @Override
  public void onChargeRemainedTimeminChanged(int oldValue, int newValue) throws RemoteException {
    if (mChargeRemainedTimeminChanged.equals(oldValue, newValue)) {
      return;
    }
    mChargeRemainedTimeminChanged.setValues(oldValue, newValue);
    mLogger.log("onChargeRemainedTimeminChanged", oldValue, newValue);
  }

  @Override
  public void onPHEVBatteryChargePersentChanged(int oldValue, int newValue) throws RemoteException {


    mLogger.log("onPHEVBatteryChargePersentChanged", oldValue, newValue);


    //ABetterRoutePlanner.updateSoC(newValue);


  }

  @Override
  public void onPHEVBatteryChargeTime120VChanged(int oldValue, int newValue) throws RemoteException {
    if (mPHEVBatteryChargeTime120VChanged.equals(oldValue, newValue)) {
      return;
    }
    mPHEVBatteryChargeTime120VChanged.setValues(oldValue, newValue);
    mLogger.log("onPHEVBatteryChargeTime120VChanged", oldValue, newValue);
  }

  @Override
  public void onPHEVBatteryChargeTime240VChanged(int oldValue, int newValue) throws RemoteException {
    if (mPHEVBatteryChargeTime240VChanged.equals(oldValue, newValue)) {
      return;
    }
    mPHEVBatteryChargeTime240VChanged.setValues(oldValue, newValue);
    mLogger.log("PHEVBatteryChargeTime240VChanged", oldValue, newValue);
  }

  @Override
  public void onPHEVChargeStatusChanged(int oldValue, int newValue) throws RemoteException {
    if (mPHEVChargeStatusChanged.equals(oldValue, newValue)) {
      return;
    }
    mPHEVChargeStatusChanged.setValues(oldValue, newValue);
    mLogger.log("onPHEVChargeStatusChanged", oldValue, newValue);
  }

  @Override
  public void onPHEVStandardCharge120VChanged(int oldValue, int newValue) throws RemoteException {
    if (mPHEVStandardCharge120VChanged.equals(oldValue, newValue)) {
      return;
    }
    mPHEVStandardCharge120VChanged.setValues(oldValue, newValue);
    mLogger.log("onPHEVStandardCharge120VChanged", oldValue, newValue);
  }

  @Override
  public void onPHEVStandardChargeChanged(int oldValue, int newValue) throws RemoteException {
    if (mPHEVStandardChargeChanged.equals(oldValue, newValue)) {
      return;
    }
    mPHEVStandardChargeChanged.setValues(oldValue, newValue);
    mLogger.log("onPHEVStandardChargeChanged", oldValue, newValue);
  }
}
