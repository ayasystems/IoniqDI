package ea4gkq.at.ioniqInfo.greencar;

import android.os.RemoteException;

import com.lge.ivi.greencar.IGreenCarGwEvP06ExtraListener;

import ea4gkq.at.ioniqInfo.Logger;
import ea4gkq.at.ioniqInfo.MainActivity;

public class GreenCarGwEvP06ExtraListener extends IGreenCarGwEvP06ExtraListener.Stub {
  private final String TAG = "at.ea4gkq.ivireceiver.BatteryChargeListener";
  private final Logger mLogger;

  private final NumberHolder mCfObcRdyChanged = new NumberHolder();
  private final NumberHolder mCfVcuLowSocLpChanged = new NumberHolder();
  private final NumberHolder mCrDatcPtcPwrConWChanged = new NumberHolder();

  public GreenCarGwEvP06ExtraListener() {
    mLogger = new Logger(TAG);
    if(MainActivity.ins!=null) {
      MainActivity.writeToFile("GreenCarGwEvP06ExtraListener instance created");
    }
  }

  @Override
  public void onCfObcRdyChanged(int oldValue, int newValue) throws RemoteException {
    if (mCfObcRdyChanged.equals(oldValue, newValue)) {
      return;
    }
    mCfObcRdyChanged.setValues(oldValue, newValue);
    mLogger.log("onCfObcRdyChanged", oldValue, newValue);
    if(MainActivity.ins!=null) {
      MainActivity.writeToFile("onCfCluAvgFclDrvInfo: "+oldValue+" / "+ newValue);
    }
  }

  @Override
  public void onCfVcuLowSocLpChanged(int oldValue, int newValue) throws RemoteException {
    if (mCfVcuLowSocLpChanged.equals(oldValue, newValue)) {
      return;
    }
    mCfVcuLowSocLpChanged.setValues(oldValue, newValue);
    mLogger.log("onCfVcuLowSocLpChanged", oldValue, newValue);
    if(MainActivity.ins!=null) {
      MainActivity.writeToFile("onCfVcuLowSocLpChanged: "+oldValue+" / "+ newValue);
    }
  }

  @Override
  public void onCrDatcPtcPwrConWChanged(int oldValue, int newValue) throws RemoteException {
    if (mCrDatcPtcPwrConWChanged.equals(oldValue, newValue)) {
      return;
    }
    mCrDatcPtcPwrConWChanged.setValues(oldValue, newValue);
    mLogger.log("onCrDatcPtcPwrConWChanged", oldValue, newValue);
    //ABetterRoutePlanner.updateHeatingConsumption(newValue * 10);

    if(MainActivity.ins!=null) {
      MainActivity.writeToFile("onCrDatcPtcPwrConWChanged: "+(oldValue*10)+" / "+ (newValue*10));
    }
  }
}
