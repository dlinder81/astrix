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
package tutorial.t1.provider;

import tutorial.t1.api.LunchRestaurantFinder;
import tutorial.t1.api.LunchUtil;

import com.avanza.astrix.provider.library.AstrixExport;
import com.avanza.astrix.provider.library.AstrixLibraryProvider;

@AstrixLibraryProvider
public class LunchLibraryProvider {
	
	@AstrixExport
	public LunchUtil lunchUtil(LunchRestaurantFinder restaurantFinder) {
		return new LunchUtilImpl(restaurantFinder);
	}
	
	@AstrixExport
	public LunchRestaurantFinder lunchRestaurantFinder() {
		return new LunchRestaurantsFinderImpl();
	}

}
