/**
 *
 */
package com.github.publiclibs.crawler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.publicLibs.awesome.securefile.SecureFileUtils;
import com.github.publiclibs.crawler.api.DataInterface;
import com.github.publiclibs.crawler.data.EmailData;
import com.github.publiclibs.crawler.data.FileData;
import com.github.publiclibs.crawler.data.UrlData;

/**
 * @author freedom1b2830
 * @date 2023-февраля-18 23:44:39
 */
public class Logger2Path implements DataInterface {

	private final Path path;
	private final Path pathEmails;
	private final Path pathFiles;
	private final Path pathForbidden;

	String dataMask = "what=[%s],where=[%s]";

	public Logger2Path(final Path pathIn) {
		path = pathIn.normalize().toAbsolutePath();
		pathEmails = path.resolve("emails.txt");
		pathFiles = path.resolve("files.txt");
		pathForbidden = path.resolve("forbidden.txt");
	}

	public @Override void detectedEmails(final CopyOnWriteArrayList<EmailData> emails) {
		final StringBuilder builder = new StringBuilder();
		final Iterator<EmailData> iterator = emails.iterator();
		while (iterator.hasNext()) {
			final EmailData email = iterator.next();
			builder.append(String.format(dataMask, email.full, email.getWhere().url));
			if (iterator.hasNext()) {
				builder.append('\n');
			}
			builder.append('\n');
		}
		try {
			if (builder.length() > 0) {
				SecureFileUtils.createOrFixSecureFile(path, pathEmails);
				Files.write(pathEmails, builder.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	public @Override void detectedFile(final FileData fileData) {
		try {
			SecureFileUtils.createOrFixSecureFile(path, pathFiles);
			final String dataStr = (String.format(dataMask, fileData.url.url, fileData.url.parent.url) + '\n');
			final byte[] data = dataStr.getBytes(StandardCharsets.UTF_8);
			Files.write(pathFiles, data, StandardOpenOption.APPEND);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public @Override void forbidden(final UrlData forbiddenData) {
		try {
			SecureFileUtils.createOrFixSecureFile(path, pathForbidden);
			final String dataStr = (String.format(dataMask, forbiddenData.url, forbiddenData.parent.url) + '\n');
			final byte[] data = dataStr.getBytes(StandardCharsets.UTF_8);
			Files.write(pathForbidden, data, StandardOpenOption.APPEND);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
