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
package com.avanza.astrix.serviceunit;

import com.avanza.astrix.beans.factory.AstrixBeanKey;
import com.avanza.astrix.beans.service.ServiceContext;
/**
 * 
 * @author Elias Lindholm (elilin)
 *
 */
public class ServiceBeanDefinition {
	
	private AstrixBeanKey<?> beanKey;
	private String componentName;
	private boolean usesServiceRegistry;
	private boolean alwaysActive;
	private ServiceContext versioningContext;
	
	public ServiceBeanDefinition(AstrixBeanKey<?> beanKey,
								 ServiceContext versioningContext,
							     boolean usesServiceRegistry,
								 String componentName) {
		this.beanKey = beanKey;
		this.versioningContext = versioningContext;
		this.usesServiceRegistry = usesServiceRegistry;
		this.alwaysActive = false;
		this.componentName = componentName;
	}
	
	public ServiceBeanDefinition(AstrixBeanKey<?> beanKey,
			 ServiceContext versioningContext,
		     boolean usesServiceRegistry,
		     boolean alwaysActive,
			 String componentName) {
		this.beanKey = beanKey;
		this.versioningContext = versioningContext;
		this.usesServiceRegistry = usesServiceRegistry;
		this.alwaysActive = alwaysActive;
		this.componentName = componentName;
	}

	/**
	 * Determines whether the given service will be in ACTIVE state, event when the current
	 * application is in INACTIVE state, typically used to allow exporting system-services in
	 * inactive state.
	 * 
	 * @return
	 */
	public boolean isAlwaysActive() {
		return alwaysActive;
	}
	
	public AstrixBeanKey<?> getBeanKey() {
		return this.beanKey;
	}
	
	public String getComponentName() {
		return componentName;
	}

	public ServiceContext getVersioningContext() {
		return versioningContext;
	}

	public Class<?> getBeanType() {
		return beanKey.getBeanType();
	}
	
	public boolean usesServiceRegistry() {
		return this.usesServiceRegistry;
	}

}
