package ea4gkq.at.ioniqInfo.greencar;


import com.lge.ivi.greencar.IOdometryListener;


import android.os.RemoteException;
import ea4gkq.at.ioniqInfo.Logger;
import ea4gkq.at.ioniqInfo.MainActivity;




public class OdometerListener extends IOdometryListener.Stub{

    private final String TAG = "at.ea4gkq.ivireceiver.OdometerListener";
    private final Logger mLogger;

    private final NumberHolder mCluOdometerChanged = new NumberHolder();
    private final NumberHolder mEVOdometryChanged  = new NumberHolder();
    private final NumberHolder mGasOdometryChanged = new NumberHolder();
    private final NumberHolder mTotalOdometryChanged = new NumberHolder();


    public OdometerListener() {
        mLogger = new Logger(TAG);
        mLogger.log("new OdometerListener instance created!");
    }

    public void onCluOdometerChanged(int oldValue, int newValue) throws RemoteException {
        if(MainActivity.ins!=null&&oldValue!=newValue) {
            MainActivity.carOdometer = newValue;
            //MainActivity.writeToFile("onCluOdometerChanged "+newValue+" / "+oldValue);
        }
        if (mCluOdometerChanged.equals(oldValue, newValue)) {
            return;
        }
        mCluOdometerChanged.setValues(oldValue, newValue);

        mLogger.log("onCluOdometerChanged", oldValue, newValue);


    }
    public void onCluTripUnitChanged(int oldValue, int newValue) throws RemoteException {



    }


    public void onEVOdometryChanged(int oldValue, int newValue) throws RemoteException {

        if(MainActivity.ins!=null&&oldValue!=newValue) {
            //MainActivity.writeToFile("onEVOdometryChanged "+newValue+" / "+oldValue);
            MainActivity.evAutonomia = newValue;
        }
        if (mEVOdometryChanged.equals(oldValue, newValue)) {
            return;
        }
        mEVOdometryChanged.setValues(oldValue, newValue);
        mLogger.log("onEVOdometryChanged", oldValue, newValue);


    }


    public void onGasOdometryChanged(int oldValue, int newValue) throws RemoteException {
        if(MainActivity.ins!=null&&oldValue!=newValue) {
            MainActivity.gasAutonomia = newValue;
           // MainActivity.writeToFile("onGasOdometryChanged "+newValue+" / "+oldValue);
        }
        if (mGasOdometryChanged.equals(oldValue, newValue)) {
            return;
        }

        mGasOdometryChanged.setValues(oldValue, newValue);
        mLogger.log("onGasOdometryChanged "+newValue+" / "+oldValue);

    }



    public void onTotalOdometryChanged(int oldValue, int newValue) throws RemoteException {
        if (mTotalOdometryChanged.equals(oldValue, newValue)) {
            return;
        }
        mCluOdometerChanged.setValues(oldValue, newValue);
        //mLogger.log("onHEVEnergyFlowChanged "+newValue+" / "+oldValue);


    }




    public void onTripUnitChanged(int oldValue, int newValue) throws RemoteException {



    }
}
