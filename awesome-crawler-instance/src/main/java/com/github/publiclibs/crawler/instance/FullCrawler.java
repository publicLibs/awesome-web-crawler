/**
 *
 */
package com.github.publiclibs.crawler.instance;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.publicLibs.awesome.securefile.SecureFileUtils;
import com.github.publiclibs.crawler.api.AbstractCrawler;
import com.github.publiclibs.crawler.api.CrawlerWorker;
import com.github.publiclibs.crawler.instance.config.CrawlerConfig;

/**
 * @author freedom1b2830
 * @date 2023-февраля-13 22:47:01
 */
public class FullCrawler extends AbstractCrawler<CrawlerWorker> {
	CrawlerConfig config = new CrawlerConfig();

	Path crawlerDir = Paths.get("crawler");

	Path crawlerConfigPath = crawlerDir.resolve("config.yaml");

	/**
	 * @param dataInterfacesInput
	 */
	public FullCrawler() {
	}

	public @Override void close() throws Exception {
	}

	public @Override void init() throws IOException {
		readConfig();
		initWorkers();
	}

	private void initWorkers() {
		int workerCount;
		if (config.cpuCount == -1) {// read conf
			workerCount = Runtime.getRuntime().availableProcessors();
			if (workerCount != 1) {
				workerCount--;
			}
		} else if (config.cpuCount < 1) {// if [ ..., -3, -2, CONFIG_Value, 0]
			workerCount = 1;
		} else {// >=1
			workerCount = config.cpuCount;
		}
		for (int i = 0; i < workerCount; i++) {
			final CrawlerWorker worker = new CrawlerWorker();
			worker.crawler = this;
			appendWorker(worker);
		}

	}

	private void readConfig() throws IOException {
		final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		if (Files.notExists(crawlerConfigPath)) {
			try (OutputStream os = SecureFileUtils.createSecureFileOutputStream(crawlerConfigPath)) {
				mapper.writeValue(os, config);
			}
		} else {
			try (InputStream is = Files.newInputStream(crawlerConfigPath)) {
				config = mapper.readValue(is, CrawlerConfig.class);
			}
		}

	}

}
