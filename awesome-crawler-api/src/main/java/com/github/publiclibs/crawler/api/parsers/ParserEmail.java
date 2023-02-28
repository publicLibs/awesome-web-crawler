/**
 *
 */
package com.github.publiclibs.crawler.api.parsers;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.publiclibs.crawler.data.EmailData;
import com.github.publiclibs.crawler.data.UrlData;

/**
 * @author freedom1b2830
 * @date 2023-февраля-19 14:19:53
 */
public class ParserEmail {
	static final String regex = "([-_a-zA-Z0-9]+@[-_a-zA-Z0-9.]+\\.[a-zA-Z0-9]+)";

	static final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

	public static CopyOnWriteArrayList<EmailData> parseEmailsFString(final UrlData parentData, final String string) {
		final Matcher matcher = pattern.matcher(string);
		final CopyOnWriteArrayList<EmailData> emails = new CopyOnWriteArrayList<>();
		while (matcher.find()) {
			for (int i = 0; i <= matcher.groupCount(); i++) {
				final EmailData emailData = new EmailData(parentData, matcher.group(i));
				emails.addIfAbsent(emailData);
			}
		}
		return emails;
	}
}
