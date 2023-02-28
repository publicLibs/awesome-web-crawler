/**
 *
 */
package com.github.publiclibs.crawler.api;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.publiclibs.crawler.api.filter.QFilter;

/**
 * @author freedom1b2830
 * @date 2023-февраля-13 23:18:32
 */
public class QCAL<E> extends CopyOnWriteArrayList<E> {
	private QFilter<E> filter;

	final CopyOnWriteArrayList<E> parsedList = new CopyOnWriteArrayList<>();
	private final Object lockNext = new Object();

	public QCAL() {
	}

	public QCAL(final Collection<? extends E> c) {
		super(c);
	}

	public QCAL(final E[] toCopyIn) {
		super(toCopyIn);
	}

	public QCAL(final QFilter<E> filterIn) {
		this.filter = filterIn;
	}

	public final @Override boolean add(final E e) {
		throw new UnsupportedOperationException();
	}

	public final @Override void add(final int index, final E element) {
		throw new UnsupportedOperationException();
	}

	public final @Override boolean addIfAbsent(final E e) {

		if (parsedList.contains(e)) {
			return false;
		}
		final boolean result = super.addIfAbsent(e);
		synchronized (lockNext) {
			if (result) {
				lockNext.notifyAll();
			}
		}
		return result;
	}

	public final @Override E get(final int index) {
		try {
			if (index == 0) {
				return next();
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		throw new UnsupportedOperationException();
	}

	public final E next() throws InterruptedException {
		E data;
		synchronized (lockNext) {
			while (isEmpty()) {
				lockNext.wait();
			}
			while (true) {
				data = super.get(0);
				if (!parsedList.contains(data)) {
					parsedList.addIfAbsent(data);
					break;
				}
				remove(data);
				lockNext.wait();
			}
			remove(data);
			lockNext.notifyAll();
			return data;
		}
	}

}
