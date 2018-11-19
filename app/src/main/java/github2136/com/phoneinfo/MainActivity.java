package github2136.com.phoneinfo;

import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.ScatteringByteChannel;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StringBuilder sb = new StringBuilder();

        TextView tvInfo = (TextView) findViewById(R.id.tv_info);
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String meid = tm.getMeid();
            sb.append("MEID：");
            sb.append(meid);
            sb.append("\n");

            String deviceId1 = tm.getImei(0);
            sb.append("IMEI1：");
            sb.append(deviceId1);
            sb.append("\n");

            String deviceId2 = tm.getImei(1);
            sb.append("IMEI2：");
            sb.append(deviceId2);
            sb.append("\n");

        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Method method = tm.getClass().getMethod("getDeviceId", int.class);
                String deviceId1 = (String) method.invoke(tm, 1);
                String deviceId2 = (String) method.invoke(tm, 2);

                if (deviceId1 != null) {
                    if (deviceId1.length() == 15) {
                        sb.append("IMEI1：");
                        sb.append(deviceId1);
                        sb.append("\n");
                    } else {
                        sb.append("MEID：");
                        sb.append(deviceId1);
                        sb.append("\n");
                    }
                }

                if (deviceId2 != null) {
                    if (deviceId2.length() == 15) {
                        sb.append("IMEI2：");
                        sb.append(deviceId2);
                        sb.append("\n");
                    } else {
                        sb.append("MEID：");
                        sb.append(deviceId2);
                        sb.append("\n");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String deviceId1 = tm.getDeviceId(0);
            String deviceId2 = tm.getDeviceId(1);
            if (deviceId1 != null) {
                if (deviceId1.length() == 15) {
                    sb.append("IMEI1：");
                    sb.append(deviceId1);
                    sb.append("\n");
                } else {
                    sb.append("MEID：");
                    sb.append(deviceId1);
                    sb.append("\n");
                }
            }

            if (deviceId2 != null) {
                if (deviceId2.length() == 15) {
                    sb.append("IMEI2：");
                    sb.append(deviceId2);
                    sb.append("\n");
                } else {
                    sb.append("MEID：");
                    sb.append(deviceId2);
                    sb.append("\n");
                }
            }
        } else {
            String deviceId1 = tm.getDeviceId();
            sb.append("IMED/EMID/ESN：");
            sb.append(deviceId1);
            sb.append("\n");
        }

        //手机号(有些手机号无法获取，是因为运营商在SIM中没有写入手机号)
        String tel = tm.getLine1Number();
        sb.append("手机号：");
        sb.append(tel);
        sb.append("\n");
        //获取手机SIM卡的序列号ICCID
        String simNum = tm.getSimSerialNumber();
        sb.append("ICCID：");
        sb.append(simNum);
        sb.append("\n");
        //获取客户id，在gsm中是imsi号
        String imsi = tm.getSubscriberId();
        sb.append("IMSI：");
        sb.append(imsi);
        sb.append("\n");
        //电话方位
//        CellLocation str = tm.getCellLocation();
        //运营商名称,注意：仅当用户已在网络注册时有效,在CDMA网络中结果也许不可靠
        String networkoperatorName = tm.getNetworkOperatorName();
        sb.append("NetworkOperatorName：");
        sb.append(networkoperatorName);
        sb.append("\n");
        //取得和语音邮件相关的标签，即为识别符
        String voiceMail = tm.getVoiceMailAlphaTag();
        sb.append("VoiceMailAlphaTag：");
        sb.append(voiceMail);
        sb.append("\n");
        //获取语音邮件号码：
        String voiceMailNumber = tm.getVoiceMailNumber();
        sb.append("VoiceMailNumber：");
        sb.append(voiceMailNumber);
        sb.append("\n");
        //获取ISO国家码，相当于提供SIM卡的国家码。
        String simCountryIso = tm.getSimCountryIso();
        sb.append("SimCountryIso：");
        sb.append(simCountryIso);
        sb.append("\n");

        //androidid可在系统设置还原时重置他在版本为2.2时不是100%可靠的有的设备会统一返回9774d56d682e549c
        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        sb.append("android_id：");
        sb.append(android_id);
        sb.append("\n");

        DisplayMetrics dm = getResources().getDisplayMetrics();
        //屏幕信息
        sb.append("屏幕信息");
        sb.append("\n");
        sb.append("宽度(widthPixels)：");
        sb.append(dm.widthPixels);
        sb.append("\n");
        sb.append("高度(heightPixels)：");
        sb.append(dm.heightPixels);
        sb.append("\n");
        sb.append("逻辑密度(density)：");
        sb.append(dm.density);
        sb.append("\n");
        sb.append("密度(densityDpi)：");
        sb.append(dm.densityDpi);
        sb.append("\n");
        sb.append("缩放密度(scaledDensity)：");
        sb.append(dm.scaledDensity);
        sb.append("\n");
        sb.append("X轴密度(xdpi)：");
        sb.append(dm.xdpi);
        sb.append("\n");
        sb.append("Y轴密度(ydpi)：");
        sb.append(dm.ydpi);
        sb.append("\n");
        sb.append("资源文件夹：");
        sb.append(getResources().getString(R.string.density_str));
        sb.append("\n");
        //build
        sb.append("\n\n");
        sb.append("底板信息(BOARD)：\n");
        sb.append(Build.BOARD);
        sb.append("\n\n");
        sb.append("系统引导版本号(BOOTLOADER)：\n");
        sb.append(Build.BOOTLOADER);
        sb.append("\n\n");
        sb.append("品牌名(BRAND)：\n");
        sb.append(Build.BRAND);
        sb.append("\n\n");
        sb.append("设备型号(DEVICE)：\n");
        sb.append(Build.DEVICE);
        sb.append("\n\n");
        sb.append("显示ID(DISPLAY)：\n");
        sb.append(Build.DISPLAY);
        sb.append("\n\n");
        sb.append("唯一标示字符(FINGERPRINT)：\n");
        sb.append(Build.FINGERPRINT);
        sb.append("\n\n");
        sb.append("硬件名(HARDWARE)：\n");
        sb.append(Build.HARDWARE);
        sb.append("\n\n");
        sb.append("(HOST)：\n");
        sb.append(Build.HOST);
        sb.append("\n\n");
        sb.append("id(ID)：\n");
        sb.append(Build.ID);
        sb.append("\n\n");
        sb.append("制造商(MANUFACTURER)：\n");
        sb.append(Build.MANUFACTURER);
        sb.append("\n\n");
        sb.append("设备名(MODEL)：\n");
        sb.append(Build.MODEL);
        sb.append("\n\n");
        sb.append("产品名名(PRODUCT)：\n");
        sb.append(Build.PRODUCT);
        sb.append("\n\n");
        sb.append("硬件编号(SERIAL)：\n");
        sb.append(Build.SERIAL);
        sb.append("\n\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (int i = 0, len = Build.SUPPORTED_32_BIT_ABIS.length; i < len; i++) {
                String supported32BitAbi = Build.SUPPORTED_32_BIT_ABIS[i];
                sb.append("CPU32位ABI(SUPPORTED_32_BIT_ABIS)：\n");
                sb.append(supported32BitAbi);
                sb.append("\n\n");
            }

            for (int i = 0, len = Build.SUPPORTED_64_BIT_ABIS.length; i < len; i++) {
                String supported64BitAbi = Build.SUPPORTED_64_BIT_ABIS[i];
                sb.append("CPU64位ABI(SUPPORTED_64_BIT_ABIS)：\n");
                sb.append(supported64BitAbi);
                sb.append("\n\n");
            }

            for (int i = 0, len = Build.SUPPORTED_ABIS.length; i < len; i++) {
                String supportedBitAbi = Build.SUPPORTED_ABIS[i];
                sb.append("CPU所有ABI(SUPPORTED_ABIS)：\n");
                sb.append(supportedBitAbi);
                sb.append("\n\n");
            }
        } else {
            sb.append("CPU_ABI(CPU_ABI)：\n");
            sb.append(Build.CPU_ABI);
            sb.append("\n\n");
            sb.append("CPU_ABI2(CPU_ABI2)：\n");
            sb.append(Build.CPU_ABI2);
            sb.append("\n\n");
        }
        sb.append("构建类型(TAGS)：\n");
        sb.append(Build.TAGS);
        sb.append("\n\n");
        sb.append("(TIME)：\n");
        sb.append(Build.TIME);
        sb.append("\n\n");
        sb.append("构建类型(TYPE)：\n");
        sb.append(Build.TYPE);
        sb.append("\n\n");
        sb.append("构建的未知属性值(UNKNOWN)：\n");
        sb.append(Build.UNKNOWN);
        sb.append("\n\n");
        sb.append("(USER)：\n");
        sb.append(Build.USER);
        sb.append("\n\n");
        sb.append("无线电固件版本(getRadioVersion)：\n");
        sb.append(Build.getRadioVersion());
        sb.append("\n\n");
        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sb.append("基础系统版本(BASE_OS)：\n");
            sb.append(Build.VERSION.BASE_OS);
            sb.append("\n\n");
        }
        sb.append("开发代号(CODENAME)：\n");
        sb.append(Build.VERSION.CODENAME);
        sb.append("\n\n");
        sb.append("(INCREMENTAL)：\n");
        sb.append(Build.VERSION.INCREMENTAL);
        sb.append("\n\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sb.append("预览SDK版本号(PREVIEW_SDK_INT)：\n");
            sb.append(Build.VERSION.PREVIEW_SDK_INT);
            sb.append("\n\n");
        }
        sb.append("用户可见版本号(RELEASE)：\n");
        sb.append(Build.VERSION.RELEASE);
        sb.append("\n\n");
        sb.append("SDK版本(SDK_INT)：\n");
        sb.append(Build.VERSION.SDK_INT);
        sb.append("\n\n");
        sb.append("SDK版本(SDK)：\n");
        sb.append(Build.VERSION.SDK);
        sb.append("\n\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sb.append("用户可见的安全补丁级别(SECURITY_PATCH)：\n");
            sb.append(Build.VERSION.SECURITY_PATCH);
            sb.append("\n\n");
        }
        tvInfo.append(sb.toString());
    }
}
