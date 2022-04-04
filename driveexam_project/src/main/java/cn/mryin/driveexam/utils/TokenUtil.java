package cn.mryin.driveexam.utils;

import cn.mryin.driveexam.controller.AdminController;
import cn.mryin.driveexam.pojo.User;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author MrYin
 * @date 2022/3/31
 */
@Component
public class TokenUtil {




    private static   String TOKEN_SECRET = "mryin";//token秘钥

    private static long TOKEN_EXPIRE = 86400000;   //过期时间

    public static String getToken(User user) {
        String token="";
        String userStr = JSON.toJSONString(user);
        Date expireDate = new Date(System.currentTimeMillis() + TOKEN_EXPIRE);
        token= JWT.create()
                .withClaim("user",userStr)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
        return token;
    }


    /**
     * 检验token是否正确
     * @param **token**
     * @return
     */
    public static User passToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userStr = jwt.getClaim("user").asString();
            User user = JSON.parseObject(userStr, User.class);
            return user;
        } catch (Exception e){
            throw new RuntimeException("token验证失败,用户身份不正确");
        }
    }
}
