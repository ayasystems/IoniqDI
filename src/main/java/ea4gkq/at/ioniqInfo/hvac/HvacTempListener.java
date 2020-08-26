package ea4gkq.at.ioniqInfo.hvac;

import android.os.RemoteException;

import com.lge.ivi.hvac.IHvacTempListener;

//import ea4gkq.at.atg4rb4g3ivireceiver.ABetterRoutePlanner;
import ea4gkq.at.ioniqInfo.Logger;
import ea4gkq.at.ioniqInfo.MainActivity;

public class HvacTempListener extends IHvacTempListener.Stub {

  private final String TAG = "at.ea4gkq.ivireceiver.HvacTempListener";
  private final Logger mLogger;

  private static float mAmbientTempMessageC;
  private static float mInnerTempMessageC;

  public HvacTempListener() {
    mLogger = new Logger(TAG);
    mLogger.log("new HvacTempListener instance created!");
  }

  @Override
  public void onAmbientTempMessage(float[] floats, int i) throws RemoteException {
    if(floats[0] == mAmbientTempMessageC) {
      return;
    }
    mAmbientTempMessageC = floats[0];

    MainActivity.tempOut = floats[0];
    //ABetterRoutePlanner.updateTemperature(mAmbientTempMessageC);
    mLogger.log("onAmbientTempMessage", mAmbientTempMessageC, i);
  }

  @Override
  public void onInnerTempMessage(float[] floats, int i) throws RemoteException {
    if(floats[0] == mInnerTempMessageC) {
      return;
    }
    if(MainActivity.ins!=null){
      MainActivity.updateTempIn(floats[0]);
      MainActivity.writeToFile("Temp in: "+floats[0]+" i:"+i);
    }
    mInnerTempMessageC = floats[0];
    mLogger.log("onAmbientTempMessage", mInnerTempMessageC, i);
  }
}
