package site.xleon.template.core;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.xleon.template.models.User;

import javax.crypto.SecretKey;

/**
 * @author leon xu
 * @date 2021/5/31 11:50 上午
 */
@Component
public class JWT {
    private static String secretString;

    @Value("${jwt.secret}")
    private void setSecretString(String secret) {
      JWT.secretString = secret;
    }

    public String createTokenByUser(User user) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT.secretString));
        return Jwts.builder()
          .setAudience(user.getId())
          .signWith(key).compact();
    }

    public static String getUserId(String token) throws MyException {
      try {

        Jws<Claims> claims = Jwts.parserBuilder()
          .setSigningKey(JWT.secretString)
          .build().parseClaimsJws(token);
        String userId = claims.getBody().getAudience();
        if (userId == null ){
          throw new MyException("Invalid token");
        }

        return userId;
      } catch (JwtException e) {
        throw new MyException(e.getMessage());
      }


    }
}
