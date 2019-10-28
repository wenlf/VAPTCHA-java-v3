package com.vaptcha.sdk;

import com.google.gson.Gson;
import com.vaptcha.constant.Constant;
import com.vaptcha.domain.*;
import com.vaptcha.utils.Common;
import com.vaptcha.utils.HttpClientUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Vaptcha sdk
 */
public class Vaptcha {

    private static volatile Vaptcha vaptcha;

    private Vaptcha() {
    }

    public static Vaptcha getInstance() {
        if (vaptcha == null) {
            synchronized (Vaptcha.class) {
                if (vaptcha == null) {
                    vaptcha = new Vaptcha();
                }
            }
        }
        return vaptcha;
    }

    /**
     * 获取离线验证图
     *
     * @return ImgResp
     */
    public ImgResp GetImg(String knock, String offlineKey) {
        if ("".equals(offlineKey)) {
            return new ImgResp("0104", "", "", "离线key获取失败", "");
        }
        String randomStr = Common.GetRandomStr();
        String imgId = Common.MD5Encode(offlineKey + randomStr);
        if (knock == null || "".equals(knock)) {
            knock = Common.GetUUID();
        }
        return new ImgResp("0103", imgId, knock, "", offlineKey);
    }

    /**
     * 根据vid获取offlineKey
     *
     * @param vid 验证单元ID
     * @return offlineKey
     */
    public String GetOfflineKey(String vid) {
        try {
            List<NameValuePair> parametersBody = new ArrayList<>();
            HttpResp httpResp = HttpClientUtil.getRequest(Constant.BaseUrl + "config/" + vid, parametersBody);
            String replace = httpResp.getResp().replace("static(", "");
            String replace1 = replace.replace(")", "");
            Gson gson = new Gson();
            GetResp offlineKey = gson.fromJson(replace1, GetResp.class);
            return offlineKey.getOfflineKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param imgId      MD5(offlineKey + randomStr)
     * @param v          用户绘制轨迹数据
     * @param offlineKey 离线key
     * @return 轨迹校验结果
     */
    public VerifyResp Validate(String imgId, String v, String offlineKey) {
        String url = Common.MD5Encode(v + imgId);
        String fullUrl = Constant.ValidateUrl + offlineKey + "/" + url;
        try {
            List<NameValuePair> parametersBody = new ArrayList<>();
            HttpResp httpResp = HttpClientUtil.getRequest(fullUrl, parametersBody);
            if (200 == httpResp.getCode()) {
                return new VerifyResp(Constant.Success, "", "");
            } else {
                return new VerifyResp(Constant.Fail, Constant.Fail, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new VerifyResp(Constant.Fail, Constant.Fail, "");
    }

    /**
     * 离线模式二次校验
     *
     * @param reqToken     前端请求的token
     * @param sessionToken 后端session中存的token
     */
    public SecondVerifyResp OfflineVerify(String reqToken, String sessionToken) {
        if (reqToken == null || "".equals(reqToken) || sessionToken == null || "".equals(sessionToken)) {
            return new SecondVerifyResp(0, "验证失败", 0);
        }
        // reqToken offline(7)+knock(32)+uid(32)
        // sessionToken uid(32)
        String uid = reqToken.substring(39);
        if (uid.equals(sessionToken)) {
            return new SecondVerifyResp(1, "验证通过", 100);
        } else {
            return new SecondVerifyResp(0, "验证失败", 0);
        }
    }

    /**
     * 正常模式二次校验
     */
    public SecondVerifyResp Verify(String vid, String secretkey, String scene, String ip, String token) {
        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("id", vid));
        parametersBody.add(new BasicNameValuePair("secretkey", secretkey));
        parametersBody.add(new BasicNameValuePair("scene", scene));
        parametersBody.add(new BasicNameValuePair("ip", ip));
        parametersBody.add(new BasicNameValuePair("token", token));
        try {
            String result = HttpClientUtil.postForm(Constant.SecondVerifyUrl, parametersBody);
            Gson gson = new Gson();
            return gson.fromJson(result, SecondVerifyResp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SecondVerifyResp();
    }
}