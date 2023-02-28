/**
 *
 */
package com.github.publiclibs.crawler.data;

import java.util.Objects;

/**
 * @author freedom1b2830
 * @date 2023-февраля-14 00:58:52
 */
public class EmailData {
	// (([-a-z.0-9]+)@(([-a-z0-9.]+\.)([a-z]+)))
	public String full;
	private UrlData where;

	/**
	 *
	 */
	public EmailData() {
	}

	public EmailData(final String fullInput) {
		Objects.requireNonNull(fullInput);
		this.full = fullInput;
	}

	public EmailData(final UrlData whereInput, final String fullInput) {
		Objects.requireNonNull(whereInput);
		Objects.requireNonNull(fullInput);
		this.full = fullInput;
		this.where = whereInput;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EmailData)) {
			return false;
		}
		final EmailData other = (EmailData) obj;

		final boolean ret1 = Objects.equals(full, other.full);
		if (where != null) {
			return ret1 && Objects.equals(where, other.where);
		}
		return ret1;
	}

	public UrlData getWhere() {
		return where;
	}

	@Override
	public int hashCode() {
		if (where == null) {
			return Objects.hash(full);
		}
		return Objects.hash(full, where);
	}

}
