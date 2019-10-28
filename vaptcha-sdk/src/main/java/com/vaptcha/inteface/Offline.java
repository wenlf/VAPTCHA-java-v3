package com.vaptcha.inteface;

import com.google.gson.Gson;
import com.vaptcha.constant.Constant;
import com.vaptcha.domain.VerifyResp;
import com.vaptcha.sdk.Vaptcha;
import com.vaptcha.domain.ImgResp;
import com.vaptcha.utils.Common;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Offline {
    private Vaptcha vaptcha = Vaptcha.getInstance();

    /**
     * 离线模式交互接口
     */
    @RequestMapping(value = "/offline")
    @ResponseBody
    public Object VaptchaOffline(com.vaptcha.domain.Offline offline, HttpServletRequest request) {
        String callback = offline.getCallback().trim();
        if ("".equals(callback)) {
            return new VerifyResp(Constant.Fail, Constant.Fail, "");
        }
        HttpSession session = request.getSession();
        if (Constant.ActionGet.equals(offline.getOffline_action())) {
            String vid = offline.getVid();
            String knock = offline.getKnock();
            // 从session中获取offlineKey 获取失败则调用vaptcha.GetOfflineKey(vid)获取并存入session
            String offlineKey = (String) session.getAttribute(vid);
            if (offlineKey == null || "".equals(offlineKey)) {
                offlineKey = vaptcha.GetOfflineKey(vid);
                session.setAttribute(vid, offlineKey);
            }
            // 获取验证图
            ImgResp imgResp = vaptcha.GetImg(knock, offlineKey);
            // 生成imgData并以challenge为key存入session
            // key:knock value:unix+imgId
            String timestamp = String.valueOf(Common.GetTimeStamp());
            String imgData = timestamp + imgResp.getImgId();
            session.setAttribute(imgResp.getKnock(), imgData);
            // 拼接并返回
            String res = new Gson().toJson(imgResp);
            String[] resultArray = new String[]{callback, "(", res, ")"};
            return Common.StrAppend(resultArray);
        } else {
            String userLocus = offline.getV();
            // imgData=unix(11)+imgId(32)
            // 根据knock从session中获取imgData 获取成功则移除掉 一个knock只能使用一次
            String imgData = (String) session.getAttribute(offline.getKnock());
            session.removeAttribute(offline.getKnock());
            String imgId = imgData.substring(10);
            String offlineKey = vaptcha.GetOfflineKey(offline.getVid());
            VerifyResp verify = vaptcha.Validate(imgId, userLocus, offlineKey);
            if (Constant.Success.equals(verify.getCode())) {
                // 校验成功则生成token并存入session
                // session中只存uid 返回前端时拼接 offline+knock+uid
                String uuid = Common.GetUUID();
                session.setAttribute(offline.getKnock(), uuid);
                String respToken = "offline" + offline.getKnock() + uuid;
                verify.setToken(respToken);
            }
            String result = new Gson().toJson(verify);
            String[] resultArray = new String[]{offline.getCallback(), "(", result, ")"};
            return Common.StrAppend(resultArray);
        }
    }
}
