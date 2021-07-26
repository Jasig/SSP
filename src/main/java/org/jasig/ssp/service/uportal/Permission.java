/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.uportal;

public class Permission {

    private final String owner;

    private final String activity;

    private final String principal;

    private final String target;

    private final boolean inherited;

    public Permission(String owner, String activity, String principal, String target, boolean inherited) {
        this.owner = owner;
        this.activity = activity;
        this.principal = principal;
        this.target = target;
        this.inherited = inherited;
    }

    public String getOwner() {
        return owner;
    }

    public String getActivity() {
        return activity;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getTarget() {
        return target;
    }

    public boolean isInherited() {
        return inherited;
    }

}
