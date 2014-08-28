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
package se.avanzabank.service.suite.remoting.plugin.consumer;

import java.util.Collections;
import java.util.List;

import se.avanzabank.service.suite.context.AstrixContext;
import se.avanzabank.service.suite.context.AstrixFaultTolerancePlugin;
import se.avanzabank.service.suite.context.AstrixServiceFactory;
import se.avanzabank.service.suite.context.AstrixVersioningPlugin;
import se.avanzabank.service.suite.context.ExternalDependencyAware;
import se.avanzabank.service.suite.core.AstrixObjectSerializer;
import se.avanzabank.service.suite.remoting.client.AstrixRemotingProxy;
import se.avanzabank.service.suite.remoting.client.AstrixRemotingTransport;
import se.avanzabank.space.SpaceLocator;

public class AstrixRemotingServiceFactory<T> implements AstrixServiceFactory<T>, ExternalDependencyAware<AstrixRemotingPluginDependencies> {
	
	private final Class<T> serviceApi;
	private final String targetSpace;
    private final Class<?> descriptorHolder;
	private AstrixRemotingPluginDependencies dependencies;
	
	public AstrixRemotingServiceFactory(Class<T> serviceApi,
										String targetSpaceName, 
										Class<?> descriptorHolder) {
		this.serviceApi = serviceApi;
		this.targetSpace = targetSpaceName;
		this.descriptorHolder = descriptorHolder;
	}

	@Override
	public T create(AstrixContext context) {
		AstrixRemotingTransport remotingTransport = createRemotingTransport(context); // dependency
		AstrixObjectSerializer objectSerializer = createObjectSerializer(context); // plugin
		AstrixFaultTolerancePlugin faultTolerance = createFaultTolerance(context); // plugin
		T proxy = AstrixRemotingProxy.create(serviceApi, remotingTransport, objectSerializer);
		T proxyWithFaultTolerance = faultTolerance.addFaultTolerance(serviceApi, proxy);
		return proxyWithFaultTolerance;
	}

	private AstrixFaultTolerancePlugin createFaultTolerance(AstrixContext context) {
		return context.getPlugin(AstrixFaultTolerancePlugin.class);
	}

	private AstrixObjectSerializer createObjectSerializer(AstrixContext context) {
		return context.getPlugin(AstrixVersioningPlugin.class).create(descriptorHolder);
	}

	private AstrixRemotingTransport createRemotingTransport(AstrixContext context) {
//		SpaceLocator spaceLocator = context.getService(SpaceLocator.class);
		SpaceLocator spaceLocator = dependencies.getSpaceLocator();
		 // TODO: caching of created proxies, fault tolerance?
		return AstrixRemotingTransport.remoteSpace(spaceLocator.createClusteredProxy(targetSpace));
	}

	@Override
	public Class<T> getServiceType() {
		return serviceApi;
	}
	
	@Override
	public void setDependency(AstrixRemotingPluginDependencies dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public Class<AstrixRemotingPluginDependencies> getDependencyBeanClass() {
		return AstrixRemotingPluginDependencies.class;
	}
	
	@Override
	public List<Class<?>> getServiceDependencies() {
		return Collections.emptyList();
	}

}
