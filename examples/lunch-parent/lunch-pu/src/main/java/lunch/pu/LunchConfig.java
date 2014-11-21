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
package lunch.pu;

import lunch.api.LunchService;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.avanza.astrix.context.AstrixFrameworkBean;

@Configuration
public class LunchConfig {
	
	@Bean
	public AstrixFrameworkBean astrix(@Value("${externalConfigUri}") String externalConfigUri) {
		AstrixFrameworkBean result = new AstrixFrameworkBean();
		result.setSubsystem("lunch-service");
		result.setServiceDescriptor(LunchServiceDescriptor.class);
		result.setExternalConfigUri(externalConfigUri);
		return result;
	}
	
	@Bean
	public LunchService lunchService(GigaSpace gigaSpace) {
		return new LunchServiceImpl(gigaSpace);
	}
}
