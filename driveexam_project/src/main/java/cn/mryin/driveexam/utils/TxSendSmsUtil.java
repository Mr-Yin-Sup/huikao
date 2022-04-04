package cn.mryin.driveexam.utils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxSendSmsUtil {


    private static String SecretId = "1";



    private static String SecretKey = "2";

//接口地址

    private static String smsapiurl = "sms.tencentcloudapi.com";

//短信模板ID

    private static String TemplateID = "3";

//短信签名

    private static String Sign = "4";

// 短信应用 SDK AppID，SDK AppID 以1400开头

    private static String SmsSdkAppid = "5";



    public static String txSendSms(String[] phoneNumber,String[] templateParam)

    {

        try{



            Credential cred = new Credential(SecretId, SecretKey);



            HttpProfile httpProfile = new HttpProfile();

            httpProfile.setEndpoint(smsapiurl);



            ClientProfile clientProfile = new ClientProfile();

            clientProfile.setHttpProfile(httpProfile);



            SmsClient client = new SmsClient(cred, "", clientProfile);



            SendSmsRequest req = new SendSmsRequest();

            String[] phoneNumberSet1 = phoneNumber;

            req.setPhoneNumberSet(phoneNumberSet1);



            req.setTemplateID(TemplateID);

            req.setSign(Sign);



            String[] templateParamSet1 = templateParam;

            req.setTemplateParamSet(templateParamSet1);



            req.setSmsSdkAppid(SmsSdkAppid);



            SendSmsResponse resp = client.SendSms(req);



            String res = SendSmsResponse.toJsonString(resp);



//System.out.println(res);



            return res;

//System.out.println(SendSmsResponse.toJsonString(resp));

        } catch (TencentCloudSDKException e) {

            e.printStackTrace();

            return e.toString();

        }

    }



}