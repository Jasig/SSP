/**
 * 
 */
package org.jasig.ssp.util.collections;

/**
 * A pair of two data items, a.k.a. a binary tuple.
 * 
 * <p>
 * Argument types must be immutable, or at least ensure that all consumers do
 * not modify the values.
 * 
 * @author lew@lewscanon.com
 * @param <T>
 *            First argument type, must be an immutable type or a mutable type
 *            that is never modified
 * @param <U>
 *            Second argument type, must be an immutable type or a mutable type
 *            that is never modified
 */
public class Pair<T, U>
{
	private final T first;

	private final U second;

	private transient final int hash;

	/**
	 * Construct a Pair with the specified arguments.
	 * 
	 * @param f
	 *            first argument
	 * @param s
	 *            second argument
	 */
	public Pair(final T f, final U s)
	{
		this.first = f;
		this.second = s;
		hash = (first == null ? 0 : first.hashCode() * 31)
				+ (second == null ? 0 : second.hashCode());
	}

	/**
	 * Gets the first argument
	 * 
	 * @return the first argument
	 */
	public T getFirst()
	{
		return first;
	}

	/**
	 * Gets the second argument
	 * 
	 * @return the second argument
	 */
	public U getSecond()
	{
		return second;
	}

	@Override
	public int hashCode()
	{
		return hash;
	}

	@Override
	public boolean equals(final Object oth)
	{
		if (this == oth)
		{
			return true;
		}

		if (oth == null || !(getClass().isInstance(oth)))
		{
			return false;
		}

		// class cast checked in preceding block
		@SuppressWarnings("unchecked")
		final Pair<T, U> other = getClass().cast(oth);

		return (first == null ? other.first == null : first.equals(other.first))
				&& (second == null ? other.second == null : second
						.equals(other.second));
	}
}