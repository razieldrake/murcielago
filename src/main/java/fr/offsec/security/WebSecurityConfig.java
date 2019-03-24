package fr.offsec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fr.offsec.service.UserService;
import io.jsonwebtoken.Jwt;
import javassist.expr.NewArray;

@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public final static String AUTHORIZATION_HEADER = "Authorization";

	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private AuthenticationManager mng;
	
	
	
	
	//@Autowired
	//private AuthenticationProvider authProvider;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
			
		//auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		
		
	}
	
		
	@Bean(name="passwordEncoder")
	public BCryptPasswordEncoder passwordEncoder() {
		
		
		return new BCryptPasswordEncoder();
	}
	
/*	@Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity
            .ignoring()
                // All of Spring Security will ignore the requests
                //.antMatchers("/resources/**")
                .antMatchers("/login");
    }*/

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		 JWTAuthenticationFilter customFilter = new JWTAuthenticationFilter(this.tokenProvider);
		
		http
		
		
		
					
					.addFilter(customFilter)
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.authorizeRequests().antMatchers(HttpMethod.POST,"login").permitAll()
					//.antMatchers("/jobs**").authenticated()
					.and()
					
					.cors().and()
					.csrf().disable();
					
					
					
    }


	
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
      /* UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;*/
    	
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	    CorsConfiguration config = new CorsConfiguration();
    	    config.setAllowCredentials(true);
    	    config.addAllowedOrigin("*");
    	    config.addExposedHeader("Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
    	            "Content-Type, Access-Control-Request-Method, token");
    	    config.addAllowedHeader("*");config.checkOrigin("localhost:4200");
    	    config.addAllowedMethod("*");
    	    
    	    /*config.addAllowedMethod("OPTIONS");
    	    config.addAllowedMethod("GET");
    	    config.addAllowedMethod("POST");
    	    config.addAllowedMethod("PUT");
    	    config.addAllowedMethod("DELETE");*/
    	    source.registerCorsConfiguration("/**", config);
    	    return source;
    }

}

//	
//	@Autowired
//	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
//		
//		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http)throws Exception{
//		http.authorizeRequests()
//							.antMatchers("/*").authenticated()
//							.antMatchers("/admin**").hasAuthority("ADMIN")
//							
//			.and()
//							
//							.formLogin().defaultSuccessUrl("/jobs").failureForwardUrl("/login")
//							.usernameParameter("username").passwordParameter("password")
//			.and()
//							.logout().invalidateHttpSession(false)
//							.logoutUrl("/logout")
//							.logoutSuccessUrl("/login");
//		//	.and()
//					//		.csrf()
//		//	.and()
//						//	.sessionManagement().maximumSessions(1).expiredUrl("/login");
//		http.exceptionHandling().accessDeniedPage("/403");
//	}
//

