package com.hwsafe.ottService.base.utils;

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

    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(
                            DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String createJWT(String userId, String audience,
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
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("userId", userId).setIssuer(issuer).setAudience(audience)
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

    public static String createJWT(String userId, String audience,
            String issuer, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                signatureAlgorithm.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("userid", userId).setIssuer(issuer).setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);
        // 生成JWT
        return builder.compact();
    }

    public static void main(String[] args) {
        String token = createJWT("474324CDE4F746B78C49F20F7EC19CBE", null, null,
                BASE64SECURITY);
        System.out.println(token);
        Claims claims = parseJWT(token, BASE64SECURITY);
        System.out.println(claims.get("userid"));
    }
}
