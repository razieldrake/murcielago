package fr.offsec.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.offsec.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import fr.offsec.domain.User;
import fr.offsec.security.Constants;

//@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{ //extends GenericFilterBean {
	
	@Autowired
	private UserService userService;
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORITIES_KEY = "roles";
	private final TokenProvider tokenProvider;
	
	public JWTAuthenticationFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
        ServletException {

        try {
        	
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            System.out.println(httpServletRequest.getParameter("username"));
            String jwt = this.resolveToken(httpServletRequest);
            System.out.println("string jwt in do filter is :"+jwt);
            if (jwt!=null) {
            	System.out.println(httpServletRequest.getRequestURI());
            	System.out.println("Token null should not enter here");
                if (this.tokenProvider.validateToken(jwt)) {
                    Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("user connected from:"+authentication.getName());
                    System.out.println("Contxt :"+SecurityContextHolder.getContext().getAuthentication().getName().toString());
                    ((HttpServletResponse) servletResponse).setHeader("Authorization","Bearer"+jwt);
                }
            }
                else if (httpServletRequest.getParameterMap() != null){
                	System.out.println("login form has been invoked");
                	filterChain.doFilter(servletRequest, servletResponse);// a supprimer pour optiumu func
                }
            {
                	System.out.println("Token null should enter here");
                	System.out.println(httpServletRequest.getRequestURI());
                //	((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                	//((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header.");
                
                	
                	//a reimplementer pour authentification
                }
            
            

            this.resetAuthenticationAfterRequest();
        } catch (ExpiredJwtException eje) {
            System.out.println("Security exception for user "+ eje.getClaims().getSubject()+" "+ eje.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("Exception " + eje.getMessage()+" "+ eje);
        }
        
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private String resolveToken(HttpServletRequest request) {
    	
    	System.out.println("Resolving token");

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        System.out.println("Resolving token :" + bearerToken);
        if (bearerToken!=null && bearerToken.startsWith("Bearer")) {
            String jwt = bearerToken.substring(6, bearerToken.length());
            System.out.println("After the resolving process of the token the jwt is equal to :"+ jwt);
            return jwt;
        }
        return null;
    }


	
	
	
	/*@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String authHeader = request.getHeader("token");
		System.out.println("aiuth header"+authHeader);
		if (authHeader == null || !authHeader.startsWith("token")) {
			System.out.println("invalid token");
			if (req.)
			((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header.");
			((HttpServletResponse) rep).
		} else {
			try {
				String anothertoken = authHeader.substring(7);
				String token = authHeader.substring(10, 163);
				System.out.println("toke is :"+token);
				System.out.println("toke is :"+anothertoken);
				Jws<Claims> claimss = Jwts.parser()
						.setSigningKey("secretKey".getBytes("UTF-8"))
						.parseClaimsJws(token);
				System.out.println(claimss.getBody().get("roles").toString());
				//Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
				System.out.println(claimss.toString());
				request.setAttribute("claims", claimss);
				filterChain.doFilter(req, res);
			} catch (SignatureException e) {
				((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
			}

		}
	}

	/**
	 * Method for creating Authentication for Spring Security Context Holder
	 * from JWT claims
	 * 
	 * @param claims
	 * @return
	 */
	public Authentication getAuthentication(Claims claims) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		Collection<String> roles = (Collection<String>) claims.get(AUTHORITIES_KEY);
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		User principal = userService.findUserByUsername((claims.get("username",User.class).getUsername()));
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				principal, "", authorities);
		return usernamePasswordAuthenticationToken;
	}
}