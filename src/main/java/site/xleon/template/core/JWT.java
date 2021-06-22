package site.xleon.template.core;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import site.xleon.template.models.User;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @author leon xu
 * @date 2021/5/31 11:50 上午
 */
@Component
public class JWT {
  /**
   * secret string
   */
  private static String secretString;
  /**
   * expiry minutes
   */
  private static Integer expiry;

  public static Integer getUserId(HttpServletRequest request) throws MyException {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token == null || token.isEmpty()) {
      throw new MyException("token can not be null");
    }

    // remove Bearer from token
    token = token.substring(7);

    try {
      Jws<Claims> claims = Jwts.parserBuilder()
        .setSigningKey(JWT.secretString)
        .build().parseClaimsJws(token);
      String userId = claims.getBody().getAudience();
      if (userId == null) {
        throw new MyException("Invalid token");
      }

      return Integer.parseInt(userId);
    } catch (JwtException e) {
      throw new MyException("Invalid token");
    }
  }

  @Value("${jwt.secret}")
  private void setSecretString(String secret) {
    JWT.secretString = secret;
  }

  @Value("${jwt.expiry}")
  private void setExpiry(Integer expiry) {
    JWT.expiry = expiry;
  }


  /**
   * create token
   * @param user 用户
   * @return token
   */
  public String createTokenByUser(User user) {
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT.secretString));
    long expiration = System.currentTimeMillis() + JWT.expiry * 60 * 1000;

    return Jwts.builder()
      .setIssuer("template.xleon.site")
      .setId(UUID.randomUUID().toString())
      .setAudience(user.getId().toString())
      .setExpiration(new Date(expiration))
      .setIssuedAt(new Date())
      .signWith(key).compact();
  }
}
