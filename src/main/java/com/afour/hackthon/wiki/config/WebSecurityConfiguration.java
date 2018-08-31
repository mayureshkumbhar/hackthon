package com.afour.hackthon.wiki.config;

//@Configuration
//@EnableOAuth2Sso
public class WebSecurityConfiguration {/*extends WebSecurityConfigurerAdapter {

	@Autowired
	JWTFilter jwtFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
        //.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) //comment to avoid JWT token filter
        .csrf().disable()
        .antMatcher("/**").authorizeRequests()
		//.anyRequest().authenticated() // comment this line if you dont want google security and userprofile is in Mongo 
		.and().logout().permitAll();
	}
*/}
