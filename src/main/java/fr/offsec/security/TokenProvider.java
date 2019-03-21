package fr.offsec.security;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import fr.offsec.domain.User;
import fr.offsec.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class TokenProvider {

	
	@Autowired
	UserService userService;
	
	
    private static final String AUTHORITIES_KEY = "auth";

   // @Value("${spring.security.authentication.jwt.validity}")
    private long tokenValidityInMilliSeconds = 3600*24*30;

   // @Value("${spring.security.authentication.jwt.secret}")
    private String secretKey = "ThisIsAn1nCRd1bl3Fuck1ngLongAndInsaneS3cr3Tk3y";

    public String createToken(Authentication authentication) {

    	System.out.println("Creating token");
        String authorities = authentication.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(","));

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationDateTime = now.plus(this.tokenValidityInMilliSeconds, ChronoUnit.MILLIS);

        Date issueDate = Date.from(now.toInstant());
        Date expirationDate = Date.from(expirationDateTime.toInstant());

        return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
                    .signWith(SignatureAlgorithm.HS512, this.secretKey).setIssuedAt(issueDate).setExpiration(expirationDate).compact();
    }

    public Authentication getAuthentication(String token) {
    	
    	System.out.println("Get authentication through token");

        Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream()
                    .map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        
        System.out.println("claimings the variablke of the claims");
        System.out.println("authorities are: "+claims.get("auth").toString());
        System.out.println("name or subjetct is:"+claims.getSubject());

        User principal = userService.findUserByUsername(claims.getSubject());
        System.out.println("principal got a name of :"+principal.getUsername());

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String authToken) {

    	System.out.println("token validation process");
        try {
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
            System.out.println("Exception " + e.getMessage());
            return false;
        }
    }
}
