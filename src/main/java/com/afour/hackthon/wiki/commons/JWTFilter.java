package com.afour.hackthon.wiki.commons;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

//@Component
public class JWTFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}/*

	private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);

	@Autowired
    private TokenUtils tokenUtils;

	
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
			ServletException {

		String path = ((HttpServletRequest) servletRequest).getRequestURI();
		if (!"/authenticate".equalsIgnoreCase(path)) {

			try {
				HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
				String jwt = resolveToken(httpServletRequest);
				if ( !StringUtils.hasText(jwt) && tokenUtils.validateToken(jwt)) {
					((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
					
				}else {
					Map<String, String> map = tokenUtils.parseToken(jwt);
					if (!map.isEmpty()) {
						
						
					}
				}
				
			} catch (JwtException eje) {
				LOGGER.info("Security exception for user- {}", eje.getMessage());
				((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				LOGGER.debug("Exception " + eje.getMessage(), eje);
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
		resetAuthenticationAfterRequest();
	}

    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }
        return null;
    }
*/}
