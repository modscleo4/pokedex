/*
 * Copyright 2019 Dhiego Cassiano Fogaça Barbosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.modscleo4.framework.web.route;

import com.modscleo4.framework.collection.Collection;

public class RouteCollection extends Collection<Route> {
    public Route getMatch(String find) {
        for (Route r : this) {
            if (r.matches(find)) {
                return r;
            }
        }

        return null;
    }

    public Route getMatch(String find, String method) {
        for (Route r : this) {
            if (r.getHttpMethod().equalsIgnoreCase(method) && r.matches(find)) {
                return r;
            }
        }

        return null;
    }

    public Route getByName(String name) {
        for (Route r : this) {
            if (r.getName().equals(name)) {
                return r;
            }
        }

        return null;
    }
}
