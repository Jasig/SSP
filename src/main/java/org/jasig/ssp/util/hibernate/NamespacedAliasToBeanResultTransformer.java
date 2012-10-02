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
