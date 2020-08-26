package ea4gkq.at.ioniqInfo.greencar;


import android.os.RemoteException;

import com.lge.ivi.IShutdownListener;
import com.lge.ivi.carinfo.ICarIFListener;

import ea4gkq.at.ioniqInfo.Logger;
import ea4gkq.at.ioniqInfo.MainActivity;


public class CarIFListener extends ICarIFListener.Stub{

    private final String TAG = "at.ea4gkq.ivireceiver.ICarIFListener";
    private final Logger mLogger;


    public CarIFListener() {
        mLogger = new Logger(TAG);
        mLogger.log("new EnergyFlowListener instance created!");
        if(MainActivity.ins!=null) {
            MainActivity.writeToFile("EnergyFlowListener instance created");
        }
    }
    @Override
    public void onACCInfo(int i) throws RemoteException {
        if(MainActivity.ins!=null) {
                MainActivity.writeToFile("onACCInfo: "+ i);
        }
    }

    @Override
    public void onETCStatusChangeInfo(int i, int i1) throws RemoteException {
        if(MainActivity.ins!=null) {
            MainActivity.writeToFile("onETCStatusChangeInfo: "+ i +" "+i1);
        }
    }

    @Override
    public void onPeriodicAVNStatusInfo(int i, int i1) throws RemoteException {
        if(MainActivity.ins!=null) {
       //     MainActivity.writeToFile("onPeriodicAVNStatusInfo: "+ i +" "+i1);
        }
    }

}


/*package ea4gkq.at.atg4rb4g3ivireceiver.greencar;


import android.os.RemoteException;

import com.lge.ivi.carinfo.ICarIFListener;


import com.lge.ivi.IShutdownListener

public class CarIFListener extends IShutdownListener.Stub{

    private final String TAG = "at.ea4gkq.ivireceiver.ICarIFListener";



    @Override
    public void onACCInfo(int i) throws RemoteException {

    }

    @Override
    public void onETCStatusChangeInfo(int i, int i1) throws RemoteException {

    }

    @Override
    public void onPeriodicAVNStatusInfo(int i, int i1) throws RemoteException {

    }

    @Override
    public void onPrepareShutdown() throws RemoteException {

    }
}*/