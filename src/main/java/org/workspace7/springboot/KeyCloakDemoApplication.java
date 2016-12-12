package org.workspace7.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class KeyCloakDemoApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(KeyCloakDemoApplication.class, args);
    }


    @RequestMapping(value = "/user")
    public Principal user(Principal principal) {
        return principal;
    }

    /**
     * FIXME: make this as authorized
     * @param request
     * @return
     */
    @RequestMapping(value = "/appConfig", method = RequestMethod.GET)
    public @ResponseBody  AppEnvironment appConfig(HttpServletRequest request) {
        AppEnvironment appEnvironment = new AppEnvironment();
        appEnvironment.setKeyCloakUrl(System.getProperty("KEYCLOAK_URL") == null ?
            "http://localhost:8180" : System.getProperty("KEYCLOAK_URL"));

        String redirectUri = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();

        appEnvironment.setRedirectUri(redirectUri);
        return appEnvironment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**").authorizeRequests().antMatchers("/","/appConfig", "/login/**", "/webjars/**")
            .permitAll().anyRequest()
            .authenticated().and().exceptionHandling()
            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
            .logoutSuccessUrl("/").permitAll()
            .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
