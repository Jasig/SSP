/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.util.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * Allows result set column aliases to be namespaced instead of matching target
 * bean property names exactly. This is a workaround for a problem where
 * Hibernate generates invalid queries when a projection alias and a
 * orderby/groupby column have the same name.
 *
 * <p>See <a href="https://github.com/russlittle/SSP-Open-Source-Project/commit/8b4e3f3226115e383f815ba47bfee96106d6b639">8b4e3f3226</a></p>
 */
public class NamespacedAliasToBeanResultTransformer extends AliasToBeanResultTransformer {

    private String namespace;

    public NamespacedAliasToBeanResultTransformer(Class resultClass, String namespace) {
        super(resultClass);
        this.namespace = namespace;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        if (StringUtils.isBlank(namespace) ) {
            return super.transformTuple(tuple, aliases);
        }
        String[] chompedAliases = new String[aliases.length];
        for ( int i = 0; i < aliases.length ; i++ ) {
            if ( aliases[i].startsWith(namespace) ) {
                chompedAliases[i] = chompAlias(aliases[i]);
            } else {
                chompedAliases[i] = aliases[i];
            }
        }
        return super.transformTuple(tuple,chompedAliases);
    }

    private String chompAlias(String alias) {
        return alias.substring(namespace.length());
    }
}