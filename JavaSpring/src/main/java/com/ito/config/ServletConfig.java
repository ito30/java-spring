package com.ito.config;

import org.eclipse.jetty.server.NetworkTrafficServerConnector;
import org.eclipse.jetty.server.Server;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {
	
	@Bean
	public JettyEmbeddedServletContainerFactory tomcatConnectorCustomizer() {
		JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
		factory.addServerCustomizers(new JettyServerCustomizer() {

			@Override
			public void customize(Server server) {
				NetworkTrafficServerConnector connector = new NetworkTrafficServerConnector(server);
				connector.setPort(9000);
				server.addConnector(connector);
			}
			
		});
		
		// NIO TOMCAT CONFIGURATION
//		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//
//			@Override
//			public void customize(Connector connector) {
////				connector.setProtocol("org.apache.coyote.http11.Http11NioProtocol");
//				
//				Http11NioProtocol handler = (Http11NioProtocol) connector.getProtocolHandler();
//				handler.setMaxThreads(100);
//				handler.setPort(9000);
//				handler.setConnectionTimeout(20000);
//				handler.setSslProtocol("org.apache.coyote.http11.Http11NioProtocol");
//			}
//			
//		});
		
		return factory;
	}
}
