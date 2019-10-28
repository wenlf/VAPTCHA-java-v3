package com.vaptcha.inteface;

import com.google.gson.Gson;
import com.vaptcha.sdk.Vaptcha;
import com.vaptcha.constant.Constant;
import com.vaptcha.domain.SecondVerifyResp;
import com.vaptcha.utils.Common;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Verify {
    private Vaptcha vaptcha = Vaptcha.getInstance();

    /**
     * 二次验证
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public Object VaptchaVerify(@RequestBody com.vaptcha.domain.Verify verify, HttpServletRequest request) {
        SecondVerifyResp secondVerifyResp;
        HttpSession session = request.getSession(true);
        //  reqToken offline-knock-uid
        String reqToken = verify.getToken();

        if (reqToken.length() < 7) {
            return "错误的Token";
        }
        String mode = reqToken.substring(0, 7);
        if (Constant.Offline.equals(mode)) {
            // 离线模式
            String knock = reqToken.substring(7, 39);
            String sessionToken = (String) session.getAttribute(knock);
            secondVerifyResp = vaptcha.OfflineVerify(reqToken, sessionToken);
            session.removeAttribute(knock);
            return new Gson().toJson(secondVerifyResp);
        } else {
            // 正常模式
            String ipAddress = Common.GetIpAddress(request);
            secondVerifyResp = vaptcha.Verify(verify.getId(), verify.getSecretkey(), verify.getScene(), ipAddress, verify.getToken());
            return new Gson().toJson(secondVerifyResp);
        }
    }
}
