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
package se.avanzabank.asterix.context;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import se.avanzabank.asterix.provider.test.AsterixInMemory;

public class AsterixApiProviderScanner {

	private final Logger log = LoggerFactory.getLogger(AsterixApiProviderScanner.class);
	
	private String basePackage = "se.avanzabank";
	private AsterixApiProviderFactory apiProviderFactory;

	public AsterixApiProviderScanner(String basePackage,
										AsterixApiProviderFactory factory) {
		this.basePackage = basePackage;
		this.apiProviderFactory = factory;
	}

	public List<AsterixApiProvider> scan() {
		return doScan(new ProviderFilter() {
			@Override
			public boolean accept(Class<?> providerType) {
				return !providerType.isAnnotationPresent(AsterixInMemory.class);
			}
		});
	}
	
	public List<AsterixApiProvider> scanInMemoryProviders() {
		return doScan(new ProviderFilter() {
			@Override
			public boolean accept(Class<?> providerType) {
				return providerType.isAnnotationPresent(AsterixInMemory.class);
			}
		});
	}
	
	static interface ProviderFilter {
		boolean accept(Class<?> providerType);
	}
	
	private List<AsterixApiProvider> doScan(ProviderFilter providerFilter) {
		List<AsterixApiProvider> discoveredProviders = new ArrayList<>();
		ClassPathScanningCandidateComponentProvider providerScanner = new ClassPathScanningCandidateComponentProvider(false);
		for (Class<? extends Annotation> providerFactory : this.apiProviderFactory.getProvidedAnnotationTypes()) {
			providerScanner.addIncludeFilter(new AnnotationTypeFilter(providerFactory));
		}
		Set<BeanDefinition> foundCandidateComponents = providerScanner.findCandidateComponents(basePackage);
		for (BeanDefinition beanDefinition : foundCandidateComponents) {
			try {
				Class<?> providerClass = Class.forName(beanDefinition.getBeanClassName());
				if (!providerFilter.accept(providerClass)) {
					continue;
				}
				AsterixApiDescriptor descriptor = new AsterixApiDescriptor(providerClass);
				log.debug("Found api provider {}", descriptor);
				discoveredProviders.add(this.apiProviderFactory.create(descriptor));
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("Unable to load api provider class " + beanDefinition.getBeanClassName(), e);
			}
		}
		return discoveredProviders;
	}
}