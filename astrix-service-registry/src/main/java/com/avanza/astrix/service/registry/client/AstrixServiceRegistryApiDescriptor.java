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
package com.avanza.astrix.service.registry.client;

import com.avanza.astrix.context.AstrixSettings;
import com.avanza.astrix.provider.core.AstrixConfigApi;
import com.avanza.astrix.provider.versioning.AstrixVersioned;

/**
 * The service registry api uses Astrix-remoting to export its service. Note that
 * it doesn't use the service registry to bind to the providers, but rather uses
 * the configuration mechanism to locate a provider for the registry.
 * 
 * @author Elias Lindholm (elilin)
 *
 */
@AstrixVersioned (
	apiMigrations = {},
	objectMapperConfigurer = ServiceRegistryObjectMapperConfigurer.class,
	version = 1
)
@AstrixConfigApi(
	exportedApis = {
			AstrixServiceRegistry.class
	},
	entryName = AstrixSettings.ASTRIX_SERVICE_REGISTRY_URI
)
public class AstrixServiceRegistryApiDescriptor {
}


