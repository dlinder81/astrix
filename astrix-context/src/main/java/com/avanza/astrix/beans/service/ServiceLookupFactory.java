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
package com.avanza.astrix.beans.service;

import java.lang.annotation.Annotation;

import com.avanza.astrix.beans.factory.AstrixBeanKey;
/**
 * 
 * @author Elias Lindholm (elilin)
 *
 * @param <T>
 */
public final class ServiceLookupFactory<T extends Annotation> {
	
	private final ServiceLookupMetaFactoryPlugin<T> factory;
	private final T annotation;
	
	public ServiceLookupFactory(ServiceLookupMetaFactoryPlugin<T> factory, T annotation) {
		this.factory = factory;
		this.annotation = annotation;
	}

	public ServiceLookup create(AstrixBeanKey<?> beanKey) {
		return factory.create(beanKey, annotation);
	}
	

}
