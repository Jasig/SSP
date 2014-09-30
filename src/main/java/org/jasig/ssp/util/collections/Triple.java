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
/**
 * 
 */
package org.jasig.ssp.util.collections;

/**
 * A triple of three data items, a.k.a. a triplet tuple.
 * 
 * <p>
 * Argument types must be immutable, or at least ensure that all consumers do
 * not modify the values.
 *
 * @param <L>
 *            First argument type, must be an immutable type or a mutable type
 *            that is never modified
 * @param <M>
 *            Second argument type, must be an immutable type or a mutable type
 *            that is never modified
 * @param <R>
 *            Third argument type, must be an immutable type or a mutable type
 *            that is never modified
 */
public class Triple<L, M, R> {

	private final L left;

	private final M middle;

    private final R right;

	private transient final int hash;

	/**
	 * Construct a Pair with the specified arguments.
	 *
	 * @param l
	 *            left argument
	 * @param m
	 *            middle argument
     * @param r
     *            right argument
	 */
	public Triple (final L l, final M m, final R r)
	{
		this.left = l;
		this.middle = m;
        this.right = r;
		hash = (left == null ? 0 : left.hashCode() * 31)
				+ (middle == null ? 0 : middle.hashCode()
                + (right == null ? 0 : right.hashCode()));
	}

	/**
	 * Gets the first argument
	 * 
	 * @return the first argument
	 */
	public L getLeft()
	{
		return left;
	}

	/**
	 * Gets the second argument
	 * 
	 * @return the second argument
	 */
	public M getMiddle()
	{
		return middle;
	}

    /**
     * Gets the third argument
     *
     * @return the third argument
     */
    public R getRight()
    {
        return right;
    }

	@Override
	public int hashCode()
	{
		return hash;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (!(Triple.class.isInstance(obj))
				|| !(getClass().equals(obj.getClass()))) {
			return false;
		}

		// class cast checked in preceding block
		@SuppressWarnings("unchecked")
		final Triple<L, M, R> other = getClass().cast(obj);

		return (left == null ? other.left == null : left.equals(other.left))
				&& (middle == null ? other.middle == null : middle.equals(other.middle)
                && (right == null ? other.right == null : right.equals(other.right)));
	}

	/* public Map<T,U> toMap() {
		Map<T,U> map = Maps.newHashMapWithExpectedSize(1);
		map.put(first, second);
		return map;
	}  */
}