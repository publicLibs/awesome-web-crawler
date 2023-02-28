/**
 *
 */
package com.github.publiclibs.crawler.api.filter;

/**
 * @author freedom1b2830
 * @date 2023-февраля-17 11:44:13
 */
public interface QFilterInterFace<E> {
	public boolean allow(final E data);

	/**
	 *
	 * @param data data for test
	 * @return true if forbidden
	 */
	public boolean deny(final E data);

	/**
	 *
	 * @param data
	 * @return true if forbidden
	 */
	public boolean denyFull(final E data);
}
