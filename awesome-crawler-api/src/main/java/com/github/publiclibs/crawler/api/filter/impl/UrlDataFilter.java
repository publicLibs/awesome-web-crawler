/**
 *
 */
package com.github.publiclibs.crawler.api.filter.impl;

import java.util.ArrayList;

import com.github.publiclibs.crawler.api.filter.QFilter;
import com.github.publiclibs.crawler.data.UrlData;

/**
 * @author freedom1b2830
 * @date 2023-февраля-17 14:29:32
 */
public class UrlDataFilter extends QFilter<UrlData> {
	ArrayList<String> forbiddenDomains = new ArrayList<>();

	//
	public @Override boolean allow(final UrlData data) {
		// TODO
		return false;
	}

	private boolean containsInForbiddenList(final UrlData data) {
		if (forbiddenDomains.isEmpty()) {
			forbiddenDomains.add("pti.icann.org");
			forbiddenDomains.add(".*icann.org");
		}
		final String domain = data.domain;
		if (forbiddenDomains.contains(domain)) {
			return true;
		}
		for (final String domainInList : forbiddenDomains) {
			if (domain.matches(domainInList)) {
				return true;
			}
		}

		return false;
	}

	public @Override boolean deny(final UrlData data) {
		if (containsInForbiddenList(data)) {
			return true;
		}
		return false;
	}

}
