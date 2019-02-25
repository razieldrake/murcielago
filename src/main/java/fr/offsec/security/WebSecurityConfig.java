//package fr.offsec.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import fr.offsec.service.UserService;
//import javassist.expr.NewArray;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	private UserService userService;
//	
//	@Bean(name="passwordEncoder")
//	public BCryptPasswordEncoder passwordEncoder() {
//		
//		
//		return new BCryptPasswordEncoder();
//	}
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
//}
