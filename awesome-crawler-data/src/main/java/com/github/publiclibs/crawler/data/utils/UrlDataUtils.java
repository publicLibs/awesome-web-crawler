/**
 *
 */
package com.github.publiclibs.crawler.data.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author freedom1b2830
 * @date 2023-февраля-13 13:34:21
 */
public class UrlDataUtils {
	// (((([a-z]+)://)?([-a-z.0-9]+)))(/([-.#a-z0-9/]+(\?[a-z=0-9&]+)?))?

	public static final String FULL_URL_REGEX = "(((([a-z]+)://)?([-a-z.0-9]+)))(/([-.#a-z0-9/]+(\\?[a-z=0-9&]+)?))?";
	public static final Pattern FULL_URL_PATTERN = Pattern.compile(FULL_URL_REGEX, Pattern.MULTILINE);

	public static void parseFullUrl(final String url, final StringBuilder domainBuilder,
			final StringBuilder schemaBuilder, final StringBuilder pathBuilder) {
		Objects.requireNonNull(url);
		Objects.requireNonNull(domainBuilder);
		final Matcher matcher = FULL_URL_PATTERN.matcher(url);
		while (matcher.find()) {
			setNoNull(domainBuilder, matcher, 5);
			if (domainBuilder.length() > 0) {
				setNoNull(schemaBuilder, matcher, 4);
				setNoNull(pathBuilder, matcher, 6);
			}
		}
	}

	private static void setNoNull(final StringBuilder builder, final Matcher matcher, final int index) {
		if (builder != null) {
			final String schem = matcher.group(index);
			builder.setLength(0);
			builder.append(schem);
		}
	}

}
