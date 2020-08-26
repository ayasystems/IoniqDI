package ea4gkq.at.ioniqInfo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lge.ivi.carinfo.CarInfoManager;
import com.lge.ivi.greencar.GreenCarManager;
import com.lge.ivi.hvac.HvacManager;
/*
import com.lge.ivi.atcmd.ATCmdManager;
import com.lge.ivi.config.ConfigurationManager;
import com.lge.ivi.config.CarOption;
*/
import com.lge.ivi.atcmd.ATCmdManager;



public class logBusData extends AppCompatActivity {

    protected static GreenCarManager mGreenCarManager = null;
    protected static HvacManager mHvacManager = null;
    protected static CarInfoManager mCarInfoManager = null;
    protected static ATCmdManager mATCmdManager = null;


    protected TextView tv;
    protected static StringBuilder text;
    protected Handler handleUpdateInfo = new Handler();
    private static boolean isPaused = false;

    static {
        mGreenCarManager = GreenCarManager.getInstance(null);
        mCarInfoManager  = CarInfoManager.getInstance();
        mHvacManager = HvacManager.getInstance();
        mATCmdManager = ATCmdManager.getInstance();
     //   mATCmdManager.
     //   mConfigurationManager.
        // mCarOption.


    }


    private void initManagers() {
        if (mGreenCarManager == null) {
            try {
                mGreenCarManager = GreenCarManager.getInstance(this);
            } catch (Exception e) {
                //mLogger.log("error getting GreenCarManager", e);
            }
        }
        if(mHvacManager == null) {
            try {
                mHvacManager = HvacManager.getInstance();
            } catch (Exception e) {
               // mLogger.log("error getting HvacManager", e);
            }

        }
        if(mCarInfoManager == null) {
            try {
                mCarInfoManager = CarInfoManager.getInstance();
            } catch (Exception e) {
               // mLogger.log("error getting CarInfoManager", e);
            }
        }
        if(mATCmdManager == null) {
            try {
                mATCmdManager = ATCmdManager.getInstance();
            } catch (Exception e) {
                // mLogger.log("error getting CarInfoManager", e);
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.logbusdata);
        tv =  (TextView) findViewById(R.id.tvLogBus);
        initManagers();
        updateLogInfoTv();

        handleUpdateInfo.postDelayed(new Runnable(){
            public void run(){
                //do something
                if(!isPaused){
                    updateLogInfoTv();
                }
                handleUpdateInfo.postDelayed(this, 500);
            }
        }, 500);

    }

    public void onClickSalirBusLog(View v)
    {

        finish();

    }



    public String intArrayToString(int[] value){
        StringBuilder text = new StringBuilder();
        int len = value.length;

        for (int i = 0; i < len; ++i) {
            text.append(value[i])
                .append(" ");
        }

        return text.toString();
    }

    public String intArrayToString(float[] value){
        StringBuilder text = new StringBuilder();
        int len = value.length;

        for (int i = 0; i < len; ++i) {
            text.append(value[i])
                    .append(" ");
        }

        return text.toString();
    }

    private void updateLogInfoTv(){


        if (mGreenCarManager != null && mHvacManager != null && mCarInfoManager != null&&mATCmdManager!=null) {

            text =
                    new StringBuilder("")
            //mATCmdManager




                            .append("\n==========mGreenCarManager==========")
                            .append("\ngetCrMcuMotPwrAvnKw: ").append(mGreenCarManager.getCrMcuMotPwrAvnKw())
                            .append("\ngetCrVcuDistEmptyAddKm: ").append(mGreenCarManager.getCrVcuDistEmptyAddKm())
                            .append("\ngetHuGwP03CfTmuRoadType: ").append( intArrayToString(mGreenCarManager.getHuGwP03CfTmuRoadType()) )
                            .append("\ngetHuGwP03CrTmuDrvDistance: ").append(intArrayToString(mGreenCarManager.getHuGwP03CrTmuDrvDistance()))
                            .append("\ngetFuelConsumptionType: ").append(mGreenCarManager.getFuelConsumptionType())
                            .append("\ngetDriveMode: ").append(mGreenCarManager.getDriveMode())
                            .append("\ngetAverageFuelEfficiency: ").append(mGreenCarManager.getAverageFuelEfficiency())
                            .append("\ngetBatteryChargePersent: ").append(mGreenCarManager.getBatteryChargePersent())
                            .append("\ngetSpeedLimitStatu: ").append(mGreenCarManager.getSpeedLimitStatu())
                            .append("\ngetChargeStatus: ").append(mGreenCarManager.getChargeStatus())
                            .append("\ngetCurrentEcoLevel: ").append(mGreenCarManager.getCurrentEcoLevel())
                            .append("\ngetCurrentEnergyFlow: ").append(mGreenCarManager.getCurrentEnergyFlow())
                            .append("\ngetUsedEelctricMotor: ").append(mGreenCarManager.getUsedEelctricMotor())
                            .append("\ngetUsedFuelEfficiency: ").append(mGreenCarManager.getUsedFuelEfficiency())
                            .append("\ngetBatteryChargeTime240V: ").append(mGreenCarManager.getBatteryChargeTime240V())
                            .append("\ngetBatteryLevel: ").append(mGreenCarManager.getBatteryLevel())
                            .append("\ngetBatteryPersent: ").append(mGreenCarManager.getBatteryPersent())
                            .append("\ngetBootingType: ").append(mGreenCarManager.getBootingType())
                            .append("\ngetCanCluP02CrVcuEcoSco: ").append(mGreenCarManager.getCanCluP02CrVcuEcoSco())
                            .append("\ngetCanCluPe00CfVcuEvRdy: ").append(mGreenCarManager.getCanCluPe00CfVcuEvRdy())
                            .append("\ngetCanCluPe00CfVcuLdx12VCharge: ").append(mGreenCarManager.getCanCluPe00CfVcuLdx12VCharge())
                            .append("\ngetCanCluPe00CfVcuRdy: ").append(mGreenCarManager.getCanCluPe00CfVcuRdy())
                            .append("\ngetCfBmsFstCha: ").append(mGreenCarManager.getCfBmsFstCha())
                            .append("\ngetCfBmsFstEvseFltAlram: ").append(mGreenCarManager.getCfBmsFstEvseFltAlram())
                            .append("\ngetCfCluAvgFclDrvInfo: ").append(mGreenCarManager.getCfCluAvgFclDrvInfo())
                            .append("\ngetCfCluAvgFclDrvInfoReset: ").append(mGreenCarManager.getCfCluAvgFclDrvInfoReset())
                            .append("\ngetCfEvDepartSet1DefrostCtr: ").append(mGreenCarManager.getCfEvDepartSet1DefrostCtr())
                            .append("\ngetCfEvDepartSet1Enable: ").append(mGreenCarManager.getCfEvDepartSet1Enable())
                            .append("\ngetCfEvDepartSet1PreCondSet: ").append(mGreenCarManager.getCfEvDepartSet1PreCondSet())
                            .append("\ngetCfEvDepartSet1TempUnit: ").append(mGreenCarManager.getCfEvDepartSet1TempUnit())
                            .append("\ngetCfEvDepartSet1Week: ").append(intArrayToString(mGreenCarManager.getCfEvDepartSet1Week()))
                            .append("\ngetCfEvDepartSet2DefrostCtr: ").append(mGreenCarManager.getCfEvDepartSet2DefrostCtr())
                            .append("\ngetCfEvDepartSet2Enable: ").append(mGreenCarManager.getCfEvDepartSet2Enable())
                            .append("\ngetCfEvDepartSet2PreCondSet: ").append(mGreenCarManager.getCfEvDepartSet2PreCondSet())
                            .append("\ngetCfEvDepartSet2TempUnit: ").append(mGreenCarManager.getCfEvDepartSet2TempUnit())
                            .append("\ngetCfEvDepartSet2Week: ").append(intArrayToString(mGreenCarManager.getCfEvDepartSet2Week()))
                            .append("\ngetCfHcuHevRdy: ").append(mGreenCarManager.getCfHcuHevRdy())
                            .append("\ngetCfHcuRdy: ").append(mGreenCarManager.getCfHcuRdy())
                            .append("\ngetCfObcDCChargingStat: ").append(mGreenCarManager.getCfObcDCChargingStat())
                            .append("\ngetCfObcEvseInCurMode: ").append(mGreenCarManager.getCfObcEvseInCurMode())
                            .append("\ngetCfObcIccbInCurMode: ").append(mGreenCarManager.getCfObcIccbInCurMode())
                            .append("\ngetCfObcRdy: ").append(mGreenCarManager.getCfObcRdy())
                            .append("\ngetCfObcRdy_old: ").append(mGreenCarManager.getCfObcRdy_old())
                            .append("\ngetCfVcuAirconFinish: ").append(mGreenCarManager.getCfVcuAirconFinish())
                            .append("\ngetCfVcuAvnDteCalc: ").append(mGreenCarManager.getCfVcuAvnDteCalc())
                            .append("\ngetCfVcuClimateOperPerm: ").append(mGreenCarManager.getCfVcuClimateOperPerm())
                            .append("\ngetCfVcuDteOff: ").append(mGreenCarManager.getCfVcuDteOff())
                            .append("\ngetCfVcuFailComfirmed: ").append(mGreenCarManager.getCfVcuFailComfirmed())
                            .append("\ngetCfVcuIG3RlyMd: ").append(mGreenCarManager.getCfVcuIG3RlyMd())
                            .append("\ngetCfVcuLowSocLp: ").append(mGreenCarManager.getCfVcuLowSocLp())
                            .append("\ngetCfVcuNoramlAriconCtr: ").append(mGreenCarManager.getCfVcuNoramlAriconCtr())
                            .append("\ngetChargeRemainedTimemin: ").append(mGreenCarManager.getChargeRemainedTimemin())
                            .append("\ngetCluOdometer: ").append(mGreenCarManager.getCluOdometer())
                            .append("\ngetCluP02CfVcuPgmRun5: ").append(mGreenCarManager.getCluP02CfVcuPgmRun5())
                            .append("\ngetCluTripUnit: ").append(mGreenCarManager.getCluTripUnit())
                            .append("\ngetCrBmsQcChgRemainedTimeMin: ").append(mGreenCarManager.getCrBmsQcChgRemainedTimeMin())
                            .append("\ngetCrBmsRealScChgRemTime: ").append(mGreenCarManager.getCrBmsRealScChgRemTime())
                            .append("\ngetCrBmsScChgRemTimeICCBDom: ").append(mGreenCarManager.getCrBmsScChgRemTimeICCBDom())
                            .append("\ngetCrBmsScChgRemTimeICCBEuro: ").append(mGreenCarManager.getCrBmsScChgRemTimeICCBEuro())
                            .append("\ngetCrDatcAcnCompPwrConW: ").append(mGreenCarManager.getCrDatcAcnCompPwrConW())
                            .append("\ngetCrDatcPtcPwrConW: ").append(mGreenCarManager.getCrDatcPtcPwrConW())
                            .append("\ngetCrEvChgCtrMode: ").append(mGreenCarManager.getCrEvChgCtrMode())
                            .append("\ngetCrEvDepartSet1Hour: ").append(mGreenCarManager.getCrEvDepartSet1Hour())
                            .append("\ngetCrEvDepartSet1Min: ").append(mGreenCarManager.getCrEvDepartSet1Min())
                            .append("\ngetCrEvDepartSet1TempSet: ").append(mGreenCarManager.getCrEvDepartSet1TempSet())
                            .append("\ngetCrEvDepartSet2Hour: ").append(mGreenCarManager.getCrEvDepartSet2Hour())
                            .append("\ngetCrEvDepartSet2Min: ").append(mGreenCarManager.getCrEvDepartSet2Min())
                            .append("\ngetCrEvDepartSet2TempSet: ").append(mGreenCarManager.getCrEvDepartSet2TempSet())
                            .append("\ngetCrEvEconEndHrWeekDay: ").append(mGreenCarManager.getCrEvEconEndHrWeekDay())
                            .append("\ngetCrEvEconEndHrWeekEnd: ").append(mGreenCarManager.getCrEvEconEndHrWeekEnd())
                            .append("\ngetCrEvEconEndMinWeekDay: ").append(mGreenCarManager.getCrEvEconEndMinWeekDay())
                            .append("\ngetCrEvEconEndMinWeekEnd: ").append(mGreenCarManager.getCrEvEconEndMinWeekEnd())
                            .append("\ngetCrEvEconStHrWeekDay: ").append(mGreenCarManager.getCrEvEconStHrWeekDay())
                            .append("\ngetCrEvEconStHrWeekEnd: ").append(mGreenCarManager.getCrEvEconStHrWeekEnd())
                            .append("\ngetCrEvEconStMinWeekDay: ").append(mGreenCarManager.getCrEvEconStMinWeekDay())
                            .append("\ngetCrEvEconStMinWeekEnd: ").append(mGreenCarManager.getCrEvEconStMinWeekEnd())
                            .append("\ngetCrLdcPwrMonW: ").append(mGreenCarManager.getCrLdcPwrMonW())
                            .append("\ngetCrVcuDistEmptyKm: ").append(mGreenCarManager.getCrVcuDistEmptyKm())
                            .append("\ngetCrVcuFatcCst: ").append(mGreenCarManager.getCrVcuFatcCst())
                            .append("\ngetDatcEvScnDisp: ").append(mGreenCarManager.getDatcEvScnDisp())
                            .append("\ngetDatcMainBlowerDisp: ").append(mGreenCarManager.getDatcMainBlowerDisp())
                            .append("\ngetEVEcoClimateLimitStatus: ").append(mGreenCarManager.getEVEcoClimateLimitStatus())
                            .append("\ngetEVNormalClimateLimitStatus: ").append(mGreenCarManager.getEVNormalClimateLimitStatus())
                            .append("\ngetEVOdometry: ").append(mGreenCarManager.getEVOdometry())
                            .append("\ngetEVProgCharSet1: ").append(mGreenCarManager.getEVProgCharSet1())
                            .append("\ngetEVProgCharSet1StartHour: ").append(mGreenCarManager.getEVProgCharSet1StartHour())
                            .append("\ngetEVProgCharSet1StartMin: ").append(mGreenCarManager.getEVProgCharSet1StartMin())
                            .append("\ngetEVProgCharSet1StopData: ").append(mGreenCarManager.getEVProgCharSet1StopData())
                            .append("\ngetEVProgCharSet1StopHour: ").append(mGreenCarManager.getEVProgCharSet1StopHour())
                            .append("\ngetEVProgCharSet1StopMin: ").append(mGreenCarManager.getEVProgCharSet1StopMin())
                            .append("\ngetEVProgCharSet1StopOpt: ").append(mGreenCarManager.getEVProgCharSet1StopOpt())
                            .append("\ngetEVProgCharSet1Week: ").append(intArrayToString(mGreenCarManager.getEVProgCharSet1Week()))
                            .append("\ngetEVProgCharSet2: ").append(mGreenCarManager.getEVProgCharSet2())
                            .append("\ngetEVProgCharSet2StartHour: ").append(mGreenCarManager.getEVProgCharSet2StartHour())
                            .append("\ngetEVProgCharSet2StartMin: ").append(mGreenCarManager.getEVProgCharSet2StartMin())
                            .append("\ngetEVProgCharSet2StopData: ").append(mGreenCarManager.getEVProgCharSet2StopData())
                            .append("\ngetEVProgCharSet2StopHour: ").append(mGreenCarManager.getEVProgCharSet2StopHour())
                            .append("\ngetEVProgCharSet2StopMin: ").append(mGreenCarManager.getEVProgCharSet2StopMin())
                            .append("\ngetEVProgCharSet2StopOpt: ").append(mGreenCarManager.getEVProgCharSet2StopOpt())
                            .append("\ngetEVProgCharSet2Week: ").append(intArrayToString(mGreenCarManager.getEVProgCharSet2Week()))
                            .append("\ngetEVSEFaultFlag: ").append(mGreenCarManager.getEVSEFaultFlag())
                            .append("\ngetEVSportClimateLimitStatus: ").append(mGreenCarManager.getEVSportClimateLimitStatus())
                            .append("\ngetEVVCUInvalidStatus: ").append(mGreenCarManager.getEVVCUInvalidStatus())
                            .append("\ngetEcoPotentialEco: ").append(mGreenCarManager.getEcoPotentialEco())
                            .append("\ngetEcoPotentialNormal: ").append(mGreenCarManager.getEcoPotentialNormal())
                            .append("\ngetEcoPotentialSport: ").append(mGreenCarManager.getEcoPotentialSport())
                            .append("\ngetGasOdometry: ").append(mGreenCarManager.getGasOdometry())
                            .append("\ngetGraphEelctricMotor: ").append(mGreenCarManager.getGraphEelctricMotor())
                            .append("\ngetGraphFuelEfficiency: ").append(mGreenCarManager.getGraphFuelEfficiency())
                            .append("\ngetHcuRdy: ").append(mGreenCarManager.getHcuRdy())
                            .append("\ngetHuEvP01CrHuChgCtrMode: ").append(mGreenCarManager.getHuEvP01CrHuChgCtrMode())
                            .append("\ngetHuEvP01CrHuEconEndHrWeekDay: ").append(mGreenCarManager.getHuEvP01CrHuEconEndHrWeekDay())
                            .append("\ngetHuEvP01CrHuEconEndHrWeekEnd: ").append(mGreenCarManager.getHuEvP01CrHuEconEndHrWeekEnd())
                            .append("\ngetHuEvP01CrHuEconEndMinWeekDay: ").append(mGreenCarManager.getHuEvP01CrHuEconEndMinWeekDay())
                            .append("\ngetHuEvP01CrHuEconEndMinWeekEnd: ").append(mGreenCarManager.getHuEvP01CrHuEconEndMinWeekEnd())
                            .append("\ngetHuEvP01CrHuEconStHrWeekDay: ").append(mGreenCarManager.getHuEvP01CrHuEconStHrWeekDay())
                            .append("\ngetHuEvP01CrHuEconStHrWeekEnd: ").append(mGreenCarManager.getHuEvP01CrHuEconStHrWeekEnd())
                            .append("\ngetHuEvP01CrHuEconStMinWeekDay: ").append(mGreenCarManager.getHuEvP01CrHuEconStMinWeekDay())
                            .append("\ngetHuEvP01CrHuEconStMinWeekEnd: ").append(mGreenCarManager.getHuEvP01CrHuEconStMinWeekEnd())
                            .append("\ngetHuEvP02CfHuDepartSet1DefrostCtr: ").append(mGreenCarManager.getHuEvP02CfHuDepartSet1DefrostCtr())
                            .append("\ngetHuEvP02CfHuDepartSet1Enable: ").append(mGreenCarManager.getHuEvP02CfHuDepartSet1Enable())
                            .append("\ngetHuEvP02CfHuDepartSet1PreCondSet: ").append(mGreenCarManager.getHuEvP02CfHuDepartSet1PreCondSet())
                            .append("\ngetHuEvP02CfHuDepartSet1TempUnit: ").append(mGreenCarManager.getHuEvP02CfHuDepartSet1TempUnit())
                            .append("\ngetHuEvP02CfHuDepartSet1Week: ").append(intArrayToString(mGreenCarManager.getHuEvP02CfHuDepartSet1Week()))
                            .append("\ngetHuEvP02CfHuDepartSet2DefrostCtr: ").append(mGreenCarManager.getHuEvP02CfHuDepartSet2DefrostCtr())
                            .append("\ngetHuEvP02CfHuDepartSet2Enable: ").append(mGreenCarManager.getHuEvP02CfHuDepartSet2Enable())
                            .append("\ngetHuEvP02CfHuDepartSet2PreCondSet: ").append(mGreenCarManager.getHuEvP02CfHuDepartSet2PreCondSet())
                            .append("\ngetHuEvP02CfHuDepartSet2TempUnit: ").append(mGreenCarManager.getHuEvP02CfHuDepartSet2TempUnit())
                            .append("\ngetHuEvP02CfHuDepartSet2Week: ").append(intArrayToString(mGreenCarManager.getHuEvP02CfHuDepartSet2Week()))
                            .append("\ngetHuEvP02CrHuDepartSet1Hour: ").append(mGreenCarManager.getHuEvP02CrHuDepartSet1Hour())
                            .append("\ngetHuEvP02CrHuDepartSet1Min: ").append(mGreenCarManager.getHuEvP02CrHuDepartSet1Min())
                            .append("\ngetHuEvP02CrHuDepartSet1TempSet: ").append(mGreenCarManager.getHuEvP02CrHuDepartSet1TempSet())
                            .append("\ngetHuEvP02CrHuDepartSet2Hour: ").append(mGreenCarManager.getHuEvP02CrHuDepartSet2Hour())
                            .append("\ngetHuEvP02CrHuDepartSet2Min: ").append(mGreenCarManager.getHuEvP02CrHuDepartSet2Min())
                            .append("\ngetHuEvP02CrHuDepartSet2TempSet: ").append(mGreenCarManager.getHuEvP02CrHuDepartSet2TempSet())
                            .append("\ngetHuEvPe00CEvClimateScnOnOff: ").append(mGreenCarManager.getHuEvPe00CEvClimateScnOnOff())
                            .append("\ngetHuEvPe00CEvEcoClimateLimit: ").append(mGreenCarManager.getHuEvPe00CEvEcoClimateLimit())
                            .append("\ngetHuEvPe00CEvEcoRegenrationLevel: ").append(mGreenCarManager.getHuEvPe00CEvEcoRegenrationLevel())
                            .append("\ngetHuEvPe00CEvMaxSpeed: ").append(mGreenCarManager.getHuEvPe00CEvMaxSpeed())
                            .append("\ngetHuEvPe00CEvNormalClimateLimit: ").append(mGreenCarManager.getHuEvPe00CEvNormalClimateLimit())
                            .append("\ngetHuEvPe00CEvNormalRegenrationLevel: ").append(mGreenCarManager.getHuEvPe00CEvNormalRegenrationLevel())
                            .append("\ngetHuEvPe00CEvSpeedLimitOnOff: ").append(mGreenCarManager.getHuEvPe00CEvSpeedLimitOnOff())
                            .append("\ngetHuEvPe00CEvSportClimateLimit: ").append(mGreenCarManager.getHuEvPe00CEvSportClimateLimit())
                            .append("\ngetHuEvPe00CEvSportRegenrationLevel: ").append(mGreenCarManager.getHuEvPe00CEvSportRegenrationLevel())
                            .append("\ngetHuEvPe00CfObcEvseInCurModeState: ").append(mGreenCarManager.getHuEvPe00CfObcEvseInCurModeState())
                            .append("\ngetHuEvPe00CfObcIccbInCurModeState: ").append(mGreenCarManager.getHuEvPe00CfObcIccbInCurModeState())
                            .append("\ngetHuGwP02CfTmuProgCharSet1: ").append(mGreenCarManager.getHuGwP02CfTmuProgCharSet1())
                            .append("\ngetHuGwP02CfTmuProgCharSet2: ").append(mGreenCarManager.getHuGwP02CfTmuProgCharSet2())
                            .append("\ngetHuGwP05CfTmuRdy: ").append(mGreenCarManager.getHuGwP05CfTmuRdy())
                            .append("\ngetHuPhevP00CfTmuProgCharSet1: ").append(mGreenCarManager.getHuPhevP00CfTmuProgCharSet1())
                            .append("\ngetHuPhevP00CfTmuProgCharSet1Mode: ").append(mGreenCarManager.getHuPhevP00CfTmuProgCharSet1Mode())
                            .append("\ngetHuPhevP00CfTmuProgCharSet1StopOpt: ").append(mGreenCarManager.getHuPhevP00CfTmuProgCharSet1StopOpt())
                            .append("\ngetHuPhevP00CfTmuProgCharSet1Week: ").append(intArrayToString(mGreenCarManager.getHuPhevP00CfTmuProgCharSet1Week()))
                            .append("\ngetHuPhevP00CrTmuProgCharSet1StartHour: ").append(mGreenCarManager.getHuPhevP00CrTmuProgCharSet1StartHour())
                            .append("\ngetHuPhevP00CrTmuProgCharSet1StartMin: ").append(mGreenCarManager.getHuPhevP00CrTmuProgCharSet1StartMin())
                            .append("\ngetHuPhevP00CrTmuProgCharSet1StopData: ").append(mGreenCarManager.getHuPhevP00CrTmuProgCharSet1StopData())
                            .append("\ngetHuPhevP00CrTmuProgCharSet1StopHour: ").append(mGreenCarManager.getHuPhevP00CrTmuProgCharSet1StopHour())
                            .append("\ngetHuPhevP00CrTmuProgCharSet1StopMin: ").append(mGreenCarManager.getHuPhevP00CrTmuProgCharSet1StopMin())
                            .append("\ngetProgCharSet1: ").append(mGreenCarManager.getProgCharSet1())
                            .append("\ngetProgCharSet1Mode: ").append(mGreenCarManager.getProgCharSet1Mode())
                            .append("\ngetProgCharSet1StartHour: ").append(mGreenCarManager.getProgCharSet1StartHour())
                            .append("\ngetProgCharSet1StartMin: ").append(mGreenCarManager.getProgCharSet1StartMin())
                            .append("\ngetProgCharSet1StopData: ").append(mGreenCarManager.getProgCharSet1StopData())
                            .append("\ngetProgCharSet1StopHour: ").append(mGreenCarManager.getProgCharSet1StopHour())
                            .append("\ngetProgCharSet1StopMin: ").append(mGreenCarManager.getProgCharSet1StopMin())
                            .append("\ngetProgCharSet1StopOpt: ").append(mGreenCarManager.getProgCharSet1StopOpt())
                            .append("\ngetProgCharSet1Week: ").append(intArrayToString(mGreenCarManager.getProgCharSet1Week()))
                            .append("\ngetProgCharSet4: ").append(mGreenCarManager.getProgCharSet4())
                            .append("\ngetProgCharSet4Mon: ").append(mGreenCarManager.getProgCharSet4Mon())
                            .append("\ngetProgConSet1: ").append(mGreenCarManager.getProgConSet1())
                            .append("\ngetProgConSet1StartHour: ").append(mGreenCarManager.getProgConSet1StartHour())
                            .append("\ngetProgConSet1StartMin: ").append(mGreenCarManager.getProgConSet1StartMin())
                            .append("\ngetProgConSet1Week: ").append(intArrayToString(mGreenCarManager.getProgConSet1Week()))
                            .append("\ngetProgConSet2: ").append(mGreenCarManager.getProgConSet2())
                            .append("\ngetProgConSet2StartHour: ").append(mGreenCarManager.getProgConSet2StartHour())
                            .append("\ngetProgConSet2StartMin: ").append(mGreenCarManager.getProgConSet2StartMin())
                            .append("\ngetProgConSet2Week: ").append(intArrayToString(mGreenCarManager.getProgConSet2Week()))
                            .append("\ngetRegenLevelEco: ").append(mGreenCarManager.getRegenLevelEco())
                            .append("\ngetRegenLevelNormal: ").append(mGreenCarManager.getRegenLevelNormal())
                            .append("\ngetRegenLevelSport: ").append(mGreenCarManager.getRegenLevelSport())
                            .append("\ngetReservedclimateState: ").append(mGreenCarManager.getReservedclimateState())
                            .append("\ngetStandardCharge: ").append(mGreenCarManager.getStandardCharge())
                            .append("\ngetStandardCharge120V: ").append(mGreenCarManager.getStandardCharge120V())
                            .append("\ngetTmuDefrostCtr1: ").append(mGreenCarManager.getTmuDefrostCtr1())
                            .append("\ngetTmuDefrostCtr2: ").append(mGreenCarManager.getTmuDefrostCtr2())
                            .append("\ngetTmuTempMd1: ").append(mGreenCarManager.getTmuTempMd1())
                            .append("\ngetTmuTempMd2: ").append(mGreenCarManager.getTmuTempMd2())
                            .append("\ngetTmuTempSet1: ").append(mGreenCarManager.getTmuTempSet1())
                            .append("\ngetTmuTempSet2: ").append(mGreenCarManager.getTmuTempSet2())
                            .append("\ngetTotalOdometry: ").append(mGreenCarManager.getTotalOdometry())
                            .append("\ngetTripUnit: ").append(mGreenCarManager.getTripUnit())
                            .append("\ngetVCUInvalidStatus: ").append(mGreenCarManager.getVCUInvalidStatus())
                            .append("\n==========mCarInfoManager===========")
                            .append("\ngetACCInfo: ").append(mCarInfoManager.getACCInfo())
                            .append("\ngetCANIF: ").append(mCarInfoManager.getCANIF())
                            .append("\ngetCarIF: ").append(mCarInfoManager.getCarIF())
                            .append("\ngetCarSpeed: ").append(mCarInfoManager.getCarSpeed())
                            .append("\ngetDrivingLockOut: ").append(mCarInfoManager.getDrivingLockOut())
                            .append("\ngetEngineerNoticeDrivingLockOut: ").append(mCarInfoManager.getEngineerNoticeDrivingLockOut())
                            .append("\ngetILLPWM: ").append(mCarInfoManager.getILLPWM())
                            .append("\n===========ATCmdManager============")
                            .append("\ngetBCanSignal").append(mATCmdManager.getBCanSignal())
                            .append("\ngetCCanSignal").append(mATCmdManager.getCCanSignal())
                            .append("\ngetAndroidAutoATcmd").append(mATCmdManager.getAndroidAutoATcmd())
                            .append("\ngetBootTime").append(mATCmdManager.getBootTime())
                            .append("\ngetMmCanSignal").append(mATCmdManager.getMmCanSignal())
                            .append("\n===========mHvacManager============")
                            .append("\ngetVentilationValue 0: ").append(mHvacManager.getVentilationValue(0))
                            .append("\ngetVentilationValue 1: ").append(mHvacManager.getVentilationValue(1))
                            .append("\ngetVentilationValue 2: ").append(mHvacManager.getVentilationValue(2))


                            .append("\ngetInnerTemperatureC: ").append(intArrayToString(mHvacManager.getInnerTemperatureC()))
                            .append("\ngetAmbientTemperatureC: ").append(mHvacManager.getAmbientTemperatureC())
                            .append("\nisHvacDisp: ").append(mHvacManager.isHvacDisp())
                            .append("\ngetVentPe03Value: ").append(intArrayToString(mHvacManager.getVentPe03Value()))
                            .append("\ngetVentPe04Value: ").append(intArrayToString(mHvacManager.getVentPe04Value()))
                            .append("\ngetVentPe05Value: ").append(intArrayToString(mHvacManager.getVentPe05Value()))
                            .append("\ngetAmbientStatus: ").append(mHvacManager.getAmbientStatus())
            ;
            tv.setText(text.toString());
        }
    }

}
