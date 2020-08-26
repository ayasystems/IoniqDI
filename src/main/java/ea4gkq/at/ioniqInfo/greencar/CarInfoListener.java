package ea4gkq.at.ioniqInfo.greencar;

import android.os.RemoteException;

import com.lge.ivi.carinfo.ICarInfoListener;

import ea4gkq.at.ioniqInfo.MainActivity;

public class CarInfoListener extends ICarInfoListener.Stub{

    private final NumberHolder mDrivingLockOut = new NumberHolder();

    @Override
    public void onDrivingLockOut(boolean b) throws RemoteException {
        if(MainActivity.ins!=null) {
            if (b) {
                MainActivity.writeToFile("ICarInfoListener - onDrivingLockOut TRUE");
            } else {
                MainActivity.writeToFile("ICarInfoListener - onDrivingLockOut FALSE");
            }
        }

    }
}
