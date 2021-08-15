package com.zjut.ucenterservice.controller;

import com.google.gson.Gson;
import com.zjut.serviceBase.exceptionHandler.MyException;
import com.zjut.ucenterservice.entity.UcenterMember;
import com.zjut.ucenterservice.service.UcenterMemberService;
import com.zjut.ucenterservice.utils.ConstantPropertiesUtils;
import com.zjut.ucenterservice.utils.HttpClientUtils;
import com.zjut.ucenterservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller  // 不用RestController，因为此处返回的是一个二维码，不是返回json格式的字符串
//@CrossOrigin  // 使用spring cloud gateway时此处不能加注解，会造成二次跨域
@RequestMapping(value = "/eduuct/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("/login")
    public String genQrConnect(HttpSession session) {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtils.WX_OPEN_APP_REDIRECT_URL;
        try {
            // 需要传递utf8格式的地址
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new MyException(20001,e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state="+state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtils.WX_OPEN_APP_ID,
                redirectUrl,
                state
        );
        return "redirect:" + qrcodeUrl;
    }

    // 获取扫描人信息，添加数据
    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session){
        // 得到授权的临时票据
        System.out.println("code:" + code);
        System.out.println("state:" + state);

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtils.WX_OPEN_APP_ID,
                ConstantPropertiesUtils.WX_OPEN_APP_SECRET,
                code
                );

        String result = null;

        try {
            // 从字符串中获得用户的信息
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=" + result);
        } catch (Exception e) {
            throw new MyException(20001,"获取accessToken失败");
        }

        // 将result字符串解析成json格式
        Gson gson = new Gson();
        // 将json格式转换成HashMap格式
        HashMap map = gson.fromJson(result,HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");

        // 查询数据库当前用户是否曾使用过微信登录
        UcenterMember member = ucenterMemberService.getByOpenid(openid);
        if (member == null){
            System.out.println("注册新用户");
            // 访问微信的资源服务器，获取用户的信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                // 获取用户信息的json格式
                resultUserInfo = HttpClientUtils.get(resultUserInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 解析json格式成hashmap格式
            HashMap<String,Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headimgurl = (String) mapUserInfo.get("headimgurl");

            // 向数据库加入一条记录
            member = new UcenterMember();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            ucenterMemberService.save(member);
        }

        // TODO 如果不是新用户直接登录

        // 生成JWT
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        // URL带token访问
        return "redirect:http://localhost:3000?token=" + token;

    }








}
