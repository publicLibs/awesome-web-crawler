/**
 *
 */
package com.github.publiclibs.crawler.instance;

import java.io.IOException;
import java.nio.file.Paths;

import com.github.publiclibs.crawler.Logger2Path;
import com.github.publiclibs.crawler.data.UrlData;

/**
 * @author freedom1b2830
 * @date 2023-февраля-13 22:50:18
 */
public class FULL {

	// "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like
	// Gecko) Chrome/108.0.0.0 Safari/537.36 OPR/94.0.0.0")

	public static void main(final String[] args) throws IOException {
		final Logger2Path logger2Path = new Logger2Path(Paths.get("crawler/data"));

		try (final FullCrawler crawler = new FullCrawler()) {
			crawler.dataInterfaces.add(logger2Path);
			crawler.init();
			crawler.start();
			final UrlData data = new UrlData("http://example.com");
			crawler.qlist.addIfAbsent(data);
			crawler.join();
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}
}
