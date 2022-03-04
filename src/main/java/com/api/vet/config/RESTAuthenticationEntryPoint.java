package com.api.vet.config;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rodrigo Caro
 */
@Component
public class RESTAuthenticationEntryPoint extends BasicAuthenticationEntryPoint{
	
	@Override
	public void afterPropertiesSet() {
		setRealmName(toString());
		super.afterPropertiesSet();
	}
}
