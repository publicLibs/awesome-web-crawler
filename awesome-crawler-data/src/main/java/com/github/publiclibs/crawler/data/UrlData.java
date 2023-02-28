/**
 *
 */
package com.github.publiclibs.crawler.data;

import java.util.Objects;

import com.github.publiclibs.crawler.data.utils.UrlDataUtils;

/**
 * @author freedom1b2830
 * @date 2023-февраля-13 13:29:16
 */
public class UrlData {

	public UrlData parent;
	public String url;
	public String domain;
	private String shema;
	private String path;

	public UrlData() {
	}

	public UrlData(final String urlInput) {
		Objects.requireNonNull(urlInput);
		this.url = urlInput;
		parseUrl();
	}

	public UrlData(final UrlData parentInput, final String urlInput) {
		Objects.requireNonNull(parentInput);
		Objects.requireNonNull(urlInput);
		this.parent = parentInput;
		this.url = urlInput;
		parseUrl();
	}

	public @Override boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UrlData)) {
			return false;
		}
		final UrlData other = (UrlData) obj;
		return Objects.equals(url, other.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(url);
	}

	private void parseUrl() {
		final StringBuilder domainBuilder = new StringBuilder();
		final StringBuilder schemaBuilder = new StringBuilder();
		final StringBuilder pathBuilder = new StringBuilder();
		UrlDataUtils.parseFullUrl(url, domainBuilder, schemaBuilder, pathBuilder);
		this.shema = schemaBuilder.toString();
		this.domain = domainBuilder.toString();
		this.path = pathBuilder.toString();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UrlData [");
		if (parent != null) {
			builder.append("parent=exist");
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
			builder.append(", ");
		}
		if (domain != null) {
			builder.append("domain=");
			builder.append(domain);
			builder.append(", ");
		}
		if (shema != null) {
			builder.append("shema=");
			builder.append(shema);
			builder.append(", ");
		}
		if (path != null) {
			builder.append("path=");
			builder.append(path);
		}
		builder.append("]");
		return builder.toString();
	}

}
