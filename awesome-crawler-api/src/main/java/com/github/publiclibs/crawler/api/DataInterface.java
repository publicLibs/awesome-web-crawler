/**
 *
 */
package com.github.publiclibs.crawler.api;

import java.util.concurrent.CopyOnWriteArrayList;

import com.github.publiclibs.crawler.data.EmailData;
import com.github.publiclibs.crawler.data.FileData;
import com.github.publiclibs.crawler.data.UrlData;

/**
 * @author freedom1b2830
 * @date 2023-февраля-18 23:15:00
 */
public interface DataInterface {
	abstract void detectedEmails(CopyOnWriteArrayList<EmailData> emails);

	abstract void detectedFile(FileData fileData);

	abstract void forbidden(UrlData newData);
}
