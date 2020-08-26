package ea4gkq.at.ioniqInfo.greencar;

import android.os.RemoteException;

import com.lge.ivi.greencar.IEVPowerDisplayListener;

import ea4gkq.at.ioniqInfo.ABetterRoutePlanner;
import ea4gkq.at.ioniqInfo.Logger;

public class EVPowerDisplayListener extends IEVPowerDisplayListener.Stub {

  private final String TAG = "at.ea4gkq.ivireceiver.EVPowerDisplay";
  private final Logger mLogger;

  private final NumberHolder mCfBmsFstChaChanged = new NumberHolder();
  private final NumberHolder mCfBmsFstEvseFltAlramChanged = new NumberHolder();
  private final NumberHolder mCfVcuDteOffChanged = new NumberHolder();
  private final NumberHolder mCrBmsQcChgRemainedTimeMinChanged = new NumberHolder();
  private final NumberHolder mCrDatcAcnCompPwrConWChanged = new NumberHolder();
  private final NumberHolder mCrLdcPwrMonWChanged = new NumberHolder();
  private final NumberHolder mCrMcuMotPwrAvnKwChanged = new NumberHolder();
  private final NumberHolder mCrVcuDistEmptyAddKmChanged = new NumberHolder();
  private final NumberHolder mCrVcuDistEmptyKmChanged = new NumberHolder();

  public EVPowerDisplayListener() {
    mLogger = new Logger(TAG);
    mLogger.log("new EVPowerDisplay instance created!");
  }

  @Override
  public void onCfBmsFstChaChanged(int oldValue, int newValue) throws RemoteException {

    if (mCfBmsFstChaChanged.equals(oldValue, newValue)) {
      return;
    }
    mCfBmsFstChaChanged.setValues(oldValue, newValue);
    mLogger.log("onCfBmsFstChaChanged", oldValue, newValue);
  }

  @Override
  public void onCfBmsFstEvseFltAlramChanged(int oldValue, int newValue) throws RemoteException {
    if (mCfBmsFstEvseFltAlramChanged.equals(oldValue, newValue)) {
      return;
    }
    mCfBmsFstEvseFltAlramChanged.setValues(oldValue, newValue);
    mLogger.log("onCfBmsFstEvseFltAlramChanged", oldValue, newValue);
  }

  @Override
  public void onCfVcuDteOffChanged(int oldValue, int newValue) throws RemoteException {
    if (mCfVcuDteOffChanged.equals(oldValue, newValue)) {
      return;
    }
    mCfVcuDteOffChanged.setValues(oldValue, newValue);
    mLogger.log("onCfVcuDteOffChanged", oldValue, newValue);
  }

  @Override
  public void onCrBmsQcChgRemainedTimeMinChanged(int oldValue, int newValue) throws RemoteException {
    if (mCrBmsQcChgRemainedTimeMinChanged.equals(oldValue, newValue)) {
      return;
    }
    mCrBmsQcChgRemainedTimeMinChanged.setValues(oldValue, newValue);
    mLogger.log("onCrBmsQcChgRemainedTimeMinChanged", oldValue, newValue);
  }

  @Override
  public void onCrDatcAcnCompPwrConWChanged(int oldValue, int newValue) throws RemoteException {
    if (mCrDatcAcnCompPwrConWChanged.equals(oldValue, newValue)) {
      return;
    }
    mCrDatcAcnCompPwrConWChanged.setValues(oldValue, newValue);
    mLogger.log("onCrDatcAcnCompPwrConWChanged", oldValue, newValue);
    ABetterRoutePlanner.updateAirconConsumption(newValue * 10);
  }

  @Override
  public void onCrLdcPwrMonWChanged(int oldValue, int newValue) throws RemoteException {
    if (mCrLdcPwrMonWChanged.equals(oldValue, newValue)) {
      return;
    }
    mCrLdcPwrMonWChanged.setValues(oldValue, newValue);
    mLogger.log("onCrLdcPwrMonWChanged", oldValue, newValue);
    //ABetterRoutePlanner.updateElecticalDeviceConsumption(newValue * 10);
  }

  @Override
  public void onCrMcuMotPwrAvnKwChanged(int oldValue, int newValue) throws RemoteException {
    if (mCrMcuMotPwrAvnKwChanged.equals(oldValue, newValue)) {
      return;
    }
    mCrMcuMotPwrAvnKwChanged.setValues(oldValue, newValue);
    mLogger.log("onCrMcuMotPwrAvnKwChanged", oldValue, newValue);
   // ABetterRoutePlanner.updateEngineConsumption((byte)newValue);
  }

  @Override
  public void onCrVcuDistEmptyAddKmChanged(int oldValue, int newValue) throws RemoteException {
    if (mCrVcuDistEmptyAddKmChanged.equals(oldValue, newValue)) {
      return;
    }
    mCrVcuDistEmptyAddKmChanged.setValues(oldValue, newValue);
    mLogger.log("onCrVcuDistEmptyAddKmChanged", oldValue, newValue);
  }

  @Override
  public void onCrVcuDistEmptyKmChanged(int oldValue, int newValue) throws RemoteException {
    if (mCrVcuDistEmptyKmChanged.equals(oldValue, newValue)) {
      return;
    }
    mCrVcuDistEmptyKmChanged.setValues(oldValue, newValue);
    mLogger.log("onCrVcuDistEmptyKmChanged", oldValue, newValue);
  }
}
