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
package com.avanza.astrix.integration.tests.domain.apiruntime;

import org.openspaces.core.GigaSpace;

import com.avanza.astrix.integration.tests.domain.api.LunchApi;
import com.avanza.astrix.integration.tests.domain.api.LunchService;
import com.avanza.astrix.integration.tests.domain.api.LunchStatistics;
import com.avanza.astrix.integration.tests.domain.api.LunchUtil;
import com.avanza.astrix.integration.tests.domain.apiruntime.feeder.InternalLunchApi;
import com.avanza.astrix.provider.core.AstrixApiProvider;
import com.avanza.astrix.provider.core.AstrixQualifier;
import com.avanza.astrix.provider.library.AstrixExport;
import com.avanza.astrix.provider.versioning.AstrixObjectSerializerConfig;

@AstrixObjectSerializerConfig(
	version = 2,
	objectSerializerConfigurer = LunchApiObjectSerializerConfigurer.class
)
@AstrixApiProvider({
	LunchApi.class, 
	InternalLunchApi.class
})
public class LunchApiProvider {

	@AstrixExport
	public LunchUtil lunchUtil(LunchService lunchService) {
		return new LunchUtilImpl(lunchService);
	}
	
	@AstrixExport
	public LunchStatistics createLunchGraderUtil(@AstrixQualifier("lunch-space") GigaSpace lunchSpaceProxy) {
		return new LunchStatisticsImpl(lunchSpaceProxy);
	}
}
