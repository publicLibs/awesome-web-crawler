/**
 *
 */
package com.github.publiclibs.crawler.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.publiclibs.crawler.api.parsers.ParserEmail;
import com.github.publiclibs.crawler.data.EmailData;
import com.github.publiclibs.crawler.data.FileData;
import com.github.publiclibs.crawler.data.UrlData;

/**
 * @author freedom1b2830
 * @date 2023-февраля-13 23:08:34
 */
public class CrawlerWorker extends Thread {

	public AbstractCrawler<?> crawler;

	private void parsePage(final UrlData parentData, final Document root) {
		final String url = parentData.url;
		if (url.endsWith(".css")) {
			return;
		}

		final Elements elements = root.getAllElements();
		for (final Element element : elements) {
			final String nodeName = element.nodeName();
			if (nodeName.equals("html") || nodeName.equals("#document")) {
				continue;
			}
			if (nodeName.equals("script")) {
				continue;
			}
			final String text = element.text();
			if (text.contains("@")) {
				final CopyOnWriteArrayList<EmailData> emails = ParserEmail.parseEmailsFString(parentData, text);
				crawler.detectedEmails(emails);
			}
		}
	}

	public @Override void run() {
		while (true) {

			UrlData data = null;
			try {
				data = crawler.qlist.next();
				setName(data.url);
				/*
				 * if (data.url.toLowerCase().contains("index")) { for (final UrlData parsed :
				 * crawler.qlist.parsedList) { if (parsed.url.toLowerCase().contains("index")) {
				 * System.err.println(data.url + "    " + parsed.url); } } }
				 */

				// System.err.println(data);

				final Document document = Jsoup.connect(data.url).get();

				parsePage(data, document);

				final Elements imports = document.select("link[href]");
				final Elements links = document.select("a[href]");
				final Elements media = document.select("[src]");

				for (final Element element : imports) {
					String link = element.attr("abs:href");
					if (link.contains("#")) {
						link = link.split("#")[0];
					}
					final UrlData newData = new UrlData(data, link);
					if (crawler.filter != null && crawler.filter.denyFull(newData)) {
						crawler.forbidden(newData);
					} else {
						crawler.qlist.addIfAbsent(newData);
					}
				}
				for (final Element element : links) {
					String link = element.attr("abs:href");
					if (link.contains("#")) {
						link = link.split("#")[0];
					}
					final UrlData newData = new UrlData(data, link);
					if (crawler.filter != null && crawler.filter.denyFull(newData)) {
						crawler.forbidden(newData);
					} else {
						crawler.qlist.addIfAbsent(newData);
					}
				}
				for (final Element element : media) {
					String link = element.attr("abs:src");
					if (link.contains("#")) {
						link = link.split("#")[0];
					}
					final UrlData newData = new UrlData(data, link);
					if (crawler.filter != null && crawler.filter.denyFull(newData)) {
						crawler.forbidden(newData);
					} else {
						crawler.qlist.addIfAbsent(newData);
					}
				}

			} catch (final UnknownHostException e) {
				System.err.println(e.getMessage());
				crawler.qlist.parsedList.remove(data);
				continue;
			} catch (final UnsupportedMimeTypeException e) {
				crawler.detectedFile(new FileData(data));
			} catch (final MalformedURLException | java.lang.IllegalArgumentException e) {
				// ignore
			} catch (final InterruptedException | IOException e) {
				e.printStackTrace();
				crawler.qlist.parsedList.remove(data);
				continue;
			}
			data = null;
		}

	}
}
