package ea4gkq.at.ioniqInfo.greencar;

import android.os.RemoteException;

import com.lge.ivi.greencar.IEnergyFlowListener;

import ea4gkq.at.ioniqInfo.Logger;
import ea4gkq.at.ioniqInfo.MainActivity;

public class EnergyFlowListener extends IEnergyFlowListener.Stub {

  private final String TAG = "at.ea4gkq.ivireceiver.EnergyFlowListener";
  private final Logger mLogger;
  private final NumberHolder mHEVEnergyFlowChanged = new NumberHolder();

  public EnergyFlowListener() {
    mLogger = new Logger(TAG);
    mLogger.log("new EnergyFlowListener instance created!");
      if(MainActivity.ins!=null) {
          MainActivity.writeToFile("EnergyFlowListener instance created");
      }
  }

  @Override
  public void onHEVEnergyFlowChanged(int oldValue, int newValue) throws RemoteException {
    if (mHEVEnergyFlowChanged.equals(oldValue, newValue)) {
      return;
    }
    mHEVEnergyFlowChanged.setValues(oldValue, newValue);
      if(MainActivity.ins!=null) {
          MainActivity.updateEnergyFlow(newValue);
      }
    mLogger.log("onHEVEnergyFlowChanged", oldValue, newValue);
  }
}
