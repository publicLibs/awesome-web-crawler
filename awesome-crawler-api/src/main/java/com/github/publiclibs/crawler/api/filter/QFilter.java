/**
 *
 */
package com.github.publiclibs.crawler.api.filter;

/**
 * @author freedom1b2830
 * @date 2023-февраля-17 11:38:39
 */
public abstract class QFilter<E> implements QFilterInterFace<E> {
	public @Override boolean denyFull(final E data) {
		if (allow(data)) {
			return false;
		}
		return deny(data);
	}
}
