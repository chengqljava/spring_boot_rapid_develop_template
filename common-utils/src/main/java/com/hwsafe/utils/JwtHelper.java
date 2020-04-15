package com.hwsafe.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {

    public static final String BASE64SECURITY = "base64Security";

    public static Claims parsePropertiesJWT(String value,
            String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(
                            DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(value).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String createPropertiesJWT(String value, String audience,
            String issuer, long TTLMillis, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                signatureAlgorithm.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("type-hw", "JWT")
                .claim("value", value).setIssuer(issuer).setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);
        // 添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        // 生成JWT
        return builder.compact();
    }

    public static String createPropertiesJWT(String value, String audience,
            String issuer, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                signatureAlgorithm.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("type-hw", "JWT")
                .claim("value", value).setIssuer(issuer).setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);
        // 生成JWT
        return builder.compact();
    }

    public static void main(String[] args) {
        String token = createPropertiesJWT("DOUBLESAFE", null, null,
                BASE64SECURITY);
        System.out.println(token);
        String sign = Encodes.encodeHex(token.getBytes());
        System.out.println(sign);
        System.out.println(new String(Encodes.decodeHex(sign)));
        // 65794a306558426c4c576833496a6f69536c6455496977695957786e496a6f6953464d794e54596966512e65794a32595778315a534936496e7463496d526c63324e7961584230615739755843493658434c6f76356e6d6d4b5f6f7234486b7561626d6a345f6f7637446b7636486d6761396349697863496d563463476c796556527062575663496a6f784e5463334f4441334f546b354d4441774c46776961584e7a6457566b56476c745a5677694f6a45314e6a51354d7a51304d4445774d44417358434a725a586c5159584e7a5843493658434a77636d6c325958526c5833426863334e3362334a6b4d54497a4e4677694c46776962476c6a5a57357a5a554e6f5a574e725457396b5a577863496a703758434a6a634856545a584a7059577863496a7063496b4a4752554a47516b5a474d4441774f5441325255466349697863496d31685930466b5a484a6c63334e63496a7062584349774d4331454f4330324d5330794e7930334f4330774d56776958537863496d316861573543623246795a464e6c636d6c68624677694f6c776958434a394c46776962476c6a5a57357a5a56426864476863496a7063496d7870593256756332557662476c6a5a57357a5a53357361574e6349697863496e427961585a686447564262476c68633177694f6c776963484a70646d46305a55746c655677694c46776963484a70646d46305a55746c65584e54644739795a56426864476863496a7063496e427961585a686447564c5a586c7a4c6d746c65584e3062334a6c5843497358434a7a644739795a56426863334e63496a7063496e4231596d78705931397759584e7a643239795a4445794d7a526349697863496e4e31596d706c59335263496a7063496d787059325675633256665a4756746231776966534a392e30504b363579574452426f353467506e4f305471554636776e4472454b70464647494c3872635032736e55
        Claims claims = parsePropertiesJWT(new String(Encodes.decodeHex(
                "65794a306558426c4c576833496a6f69536c6455496977695957786e496a6f6953464d794e54596966512e65794a32595778315a534936496e7463496d526c63324e7961584230615739755843493658434c6f76356e6d6d4b5f6f7234486b7561626d6a345f6f7637446b7636486d6761396349697863496d563463476c796556527062575663496a6f784e5463334f4441334f546b354d4441774c46776961584e7a6457566b56476c745a5677694f6a45314e6a51354d7a51304d4445774d44417358434a725a586c5159584e7a5843493658434a77636d6c325958526c5833426863334e3362334a6b4d54497a4e4677694c46776962476c6a5a57357a5a554e6f5a574e725457396b5a577863496a703758434a6a634856545a584a7059577863496a7063496b4a4752554a47516b5a474d4441774f5441325255466349697863496d31685930466b5a484a6c63334e63496a7062584349774d4331454f4330324d5330794e7930334f4330774d56776958537863496d316861573543623246795a464e6c636d6c68624677694f6c776958434a394c46776962476c6a5a57357a5a56426864476863496a7063496d7870593256756332557662476c6a5a57357a5a53357361574e6349697863496e427961585a686447564262476c68633177694f6c776963484a70646d46305a55746c655677694c46776963484a70646d46305a55746c65584e54644739795a56426864476863496a7063496e427961585a686447564c5a586c7a4c6d746c65584e3062334a6c5843497358434a7a644739795a56426863334e63496a7063496e4231596d78705931397759584e7a643239795a4445794d7a526349697863496e4e31596d706c59335263496a7063496d787059325675633256665a4756746231776966534a392e30504b363579574452426f353467506e4f305471554636776e4472454b70464647494c3872635032736e55")),
                BASE64SECURITY);
        System.out.println(claims.get("value"));
        System.out.println(6 < 6);
    }
}
