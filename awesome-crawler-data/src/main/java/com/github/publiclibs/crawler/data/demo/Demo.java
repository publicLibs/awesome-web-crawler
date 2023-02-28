/**
 *
 */
package com.github.publiclibs.crawler.data.demo;

import com.github.publiclibs.crawler.data.UrlData;

/**
 * @author freedom1b2830
 * @date 2023-февраля-13 13:39:10
 */
public class Demo {

	public static void main(final String[] args) {
		final UrlData urlData = new UrlData(
				"https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%B2%D0%B8%D1%80%D1%82%D1%83%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D1%85_%D0%BC%D0%B0%D1%88%D0%B8%D0%BD_Java");

		System.err.println(urlData);

	}

}
