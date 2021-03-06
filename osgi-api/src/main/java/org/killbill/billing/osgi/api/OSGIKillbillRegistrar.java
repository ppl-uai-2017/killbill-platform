/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2014 Groupon, Inc
 * Copyright 2014 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.osgi.api;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class OSGIKillbillRegistrar {

    private final Map<String, ServiceRegistration> serviceRegistrations;

    public OSGIKillbillRegistrar() {
        this.serviceRegistrations = new HashMap<String, ServiceRegistration>();
    }

    public <S> void registerService(final BundleContext context, final Class<S> svcClass, final S service, final Dictionary<String, ?> props) {
        final ServiceRegistration svcRegistration = context.registerService(svcClass.getName(), service, props);
        serviceRegistrations.put(svcClass.getName(), svcRegistration);
    }

    public <S> void unregisterService(final Class<S> svcClass) {
        final ServiceRegistration svc = serviceRegistrations.remove(svcClass.getName());
        if (svc != null) {
            svc.unregister();
        }
    }

    public void unregisterAll() {
        for (final ServiceRegistration cur : serviceRegistrations.values()) {
            cur.unregister();
        }
        serviceRegistrations.clear();
    }
}
