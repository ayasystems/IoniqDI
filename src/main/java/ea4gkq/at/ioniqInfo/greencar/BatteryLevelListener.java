package ea4gkq.at.ioniqInfo.greencar;

import android.os.RemoteException;

import com.lge.ivi.greencar.IBatteryLevelListener;


import ea4gkq.at.ioniqInfo.Logger;
import ea4gkq.at.ioniqInfo.MainActivity;

public class BatteryLevelListener extends IBatteryLevelListener.Stub {
  private final String TAG = "at.ea4gkq.ivireceiver.BatteryLevelListener";
  private final Logger mLogger;
  private final NumberHolder mHEVBatteryLevelChanged = new NumberHolder();
  private final NumberHolder mHEVBatteryPersentChanged = new NumberHolder();

  public BatteryLevelListener() {
    mLogger = new Logger(TAG);
    mLogger.log("new BatteryLevelListener instance created!");
    if(MainActivity.ins!=null) {
      MainActivity.writeToFile("new BatteryLevelListener instance created");
    }
  }

  @Override
  public void onHEVBatteryLevelChanged(int oldValue, int newValue) throws RemoteException {
    if (mHEVBatteryLevelChanged.equals(oldValue, newValue)) {
      return;
    }
    mHEVBatteryLevelChanged.setValues(oldValue, newValue);
    mLogger.log("onHEVBatteryLevelChanged", oldValue, newValue);
  }

  @Override
  public void onHEVBatteryPersentChanged(int oldValue, int newValue) throws RemoteException {
    if (mHEVBatteryPersentChanged.equals(oldValue, newValue)) {
      return;
    }
    mHEVBatteryPersentChanged.setValues(oldValue, newValue);
    mLogger.log("onHEVBatteryPersentChanged", oldValue, newValue);
  }
}
