/**
 *
 */
package com.github.publiclibs.crawler.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.publiclibs.crawler.api.filter.impl.UrlDataFilter;
import com.github.publiclibs.crawler.data.EmailData;
import com.github.publiclibs.crawler.data.FileData;
import com.github.publiclibs.crawler.data.UrlData;

/**
 * @author freedom1b2830
 * @date 2023-февраля-13 22:47:01
 */
public abstract class AbstractCrawler<E extends CrawlerWorker> extends Thread implements AutoCloseable {
	public CopyOnWriteArrayList<E> workers = new CopyOnWriteArrayList<>();
	public final Object stopLock = new Object();
	public final UrlDataFilter filter = new UrlDataFilter();
	public final QCAL<UrlData> qlist = new QCAL<>(filter);

	public final ArrayList<DataInterface> dataInterfaces = new ArrayList<>();

	public final void appendWorker(final E worker) {
		workers.addIfAbsent(worker);
	}

	/**
	 * @param emails
	 */
	public final void detectedEmails(final CopyOnWriteArrayList<EmailData> emails) {
		for (final DataInterface dataInterface : dataInterfaces) {
			dataInterface.detectedEmails(emails);
		}
	}

	/**
	 * @param fileData
	 */
	public final void detectedFile(final FileData fileData) {
		for (final DataInterface dataInterface : dataInterfaces) {
			dataInterface.detectedFile(fileData);
		}
	}

	public void end() {
		synchronized (stopLock) {
			stopLock.notifyAll();
		}
	}

	public final void forbidden(final UrlData newData) {
		for (final DataInterface dataInterface : dataInterfaces) {
			dataInterface.forbidden(newData);
		}
	}

	public abstract void init() throws IOException;

	public final @Override void run() {
		setName(getClass().getSimpleName() + "-" + System.currentTimeMillis());

		for (final E worker : workers) {
			worker.crawler = this;
			worker.start();
		}
		try {
			synchronized (stopLock) {
				stopLock.wait();
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}
}
