package com.syeinfo.catnip.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JWTUtil {

    public static final int PERMANENT = -1;

    public static String generateSecKey() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String generateToken(int validHours, String issuer, String secKey, String account) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secKey);

            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(issuer)
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("account", account);

            if (validHours != PERMANENT) {

                Date now = DateTimeUtil.getNow();
                DateTime end = new DateTime(now);
                end = end.plusHours(validHours);

                builder.withExpiresAt(end.toDate());

            }

            return builder.sign(algorithm);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static boolean verifyToken(String token, String issuer, String secKey, boolean validateExpiredTime) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secKey);
            JWTVerifier.BaseVerification verification = (JWTVerifier.BaseVerification) JWT.require(algorithm).withIssuer(issuer);

            Clock clock = new Clock() {
                @Override
                public Date getToday() {
                    return DateTimeUtil.getNow();
                }
            };

            JWTVerifier verifier = validateExpiredTime ? verification.build(clock) : verification.build();
            verifier.verify(token);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static String getAccount(String token) {

        try {

            DecodedJWT jwt = JWT.decode(token);

            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("account");

            return claim.asString();

        } catch (JWTDecodeException e){
            e.printStackTrace();
        }

        return null;

    }

}
