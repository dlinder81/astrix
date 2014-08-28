/*
 * Copyright 2014-2015 Avanza Bank AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.avanzabank.service.suite.context;

import java.lang.annotation.Annotation;
import java.util.List;



public interface AstrixServiceProviderPlugin {
	
	AstrixServiceProvider create(Class<?> descriptorHolder);
	
	@Deprecated // TODO: remove, replaced by consumes
	Class<? extends Annotation> getProviderAnnotationType();
	
	boolean consumes(Class<?> descriptorHolder);
	
	// TODO: how to avoid circular api creation when injecting astrix instance into each plugin??
//	void setAstrix(Astrix astrix);
	
//	void setContext(AstrixContext context);
	
	void setPlugins(AstrixPlugins plugins);
	
	/**
	 * Returns a list of all services that this plugin depends on. 
	 * 
	 * Services might be used by the plugin by invoking AstrixContext.getService(type).
	 * 
	 *  
	 * 
	 * 
	 * @return
	 */
	// TODO: Should we use another mechanism to retrieve plugin dependencies than AstrixContext.getService(type)?
	List<Class<?>> getDependencies(); // TODO: ensure that plugin can only get services it depends on.
	
	
}
