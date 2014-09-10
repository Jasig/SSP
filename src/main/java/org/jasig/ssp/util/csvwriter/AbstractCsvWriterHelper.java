/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.util.csvwriter;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Lists;
import org.hibernate.ScrollableResults;
import org.jasig.ssp.model.PersonSearchResult2;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public abstract class AbstractCsvWriterHelper<T> {

	private final PrintWriter writer;

	public AbstractCsvWriterHelper(PrintWriter writer) {
		super();
		this.writer = writer;
	}

	public CSVWriter initCsvWriter()
			throws IOException {
		return new CSVWriter(writer);
	}

	protected void doWrite(Iterator<T> model, Long maxCount) throws IOException {
		CSVWriter csvWriter = initCsvWriter();
		writeCsvHeader(csvWriter);
		writeCsvBody(csvWriter,model,maxCount);
		writingDone(csvWriter);
	}

	public void write(final ScrollableResults model, final Long maxCount) throws IOException {
		doWrite(new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return !(model.isLast());
			}

			@Override
			public T next() {
				if ( !(model.next()) ) {
					throw new NoSuchElementException(); // per Iterator interface
				}
				return (T)model.get()[0];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}, maxCount);
	}

	public void write(Collection<T> model, Long maxCount) throws IOException {
		doWrite(model.iterator(), maxCount);
	}

	public void writingDone(CSVWriter csvWriter)
			throws IOException {
		csvWriter.flush();
		csvWriter.close();
	}

	public void writeCsvHeader(CSVWriter csvWriter) {
		writeLine(csvHeaderRow(), csvWriter);
	}

	public void writeLine(String[] line, CSVWriter csvWriter) {
		csvWriter.writeNext(normalizeLine(line));
	}

	/**
	 * Have to make sure all elements are wrapped in quotes, even those with
	 * null values. Makes it easier for certain types of clients to consume,
	 * e.g. HSQLDB CSV tables.
	 *
	 * @param line
	 * @return
	 */
	protected String[] normalizeLine(String[] line) {
		for (int i = 0; i < line.length; i++) {
			line[i] = StringUtils.hasLength(line[i]) ? line[i] : "";
		}
		return line;
	}

	protected void writeCsvBody(CSVWriter csvWriter, Iterator<T> model, Long maxCount) {
		int i = 0;

		while ( model.hasNext() && ( maxCount < 0 || i < maxCount ) ) {
			Object result = model.next();
			final List<String[]> bodyRows = csvBodyRows((T)result);
			if ( bodyRows != null && !(bodyRows.isEmpty()) ) {
				for ( String[] bodyRow : bodyRows ) {
					if ( bodyRow != null ) {
						writeLine(bodyRow, csvWriter);
					}
				}
			}
			i++;
		}
	}

	protected abstract String[] csvHeaderRow();

	/**
	 * Map the given model to one or more CSV document body records. Each element in the given list
	 * is interpreted as a separate record. Typically the list contains a single element, i.e. a single array
	 * of field values. Allowing multiple rows to be returned allows for support of nested models which need
	 * to be flatted for CSV representation.
	 *
	 * @param model
	 * @return
	 */
	protected abstract List<String[]> csvBodyRows(T model);

	/**
	 * Utility to support the common case where {@link #csvBodyRows(Object)} only needs to return a single-element
	 * list.
	 *
	 * @param row
	 * @return
	 */
	public List<String[]> wrapCsvRowInList(String[] row) {
		final List<String[]> wrapper = Lists.newArrayListWithCapacity(1);
		wrapper.add(row);
		return wrapper;
	}

	public String formatDate(Date date) {
		return date == null ? null : new SimpleDateFormat("MM-dd-yyyy").format(date);
	}

	public String formatBigDecimal(BigDecimal bigDecimal) {
		return bigDecimal == null ? null : bigDecimal.toString();
	}

	public String formatInt(int integer) {
		return ""+integer;
	}

	public String formatUuid(UUID uuid) {
		return uuid == null ? null : uuid.toString();
	}

	public String formatFriendlyBoolean(Boolean booleanVal) {
		if ( booleanVal == null ) {
			return null;
		}
		return booleanVal ? "Y" : "N";
	}

	public String formatIntegerAsFriendlyBoolean(Integer integer, int threshold, Boolean whenNull) {
		return formatFriendlyBoolean(integer == null ? whenNull : integer > threshold);
	}

	public String formatFriendlyBoolean(Boolean isIlp, boolean whenNull) {
		return formatFriendlyBoolean(isIlp == null ? whenNull : isIlp);
	}

	public String formatLong(Long longVal) {
		return longVal == null ? null : longVal.toString();
	}

	public String formatLong(Long longVal, long whenNull) {
		return formatLong(longVal == null ? whenNull : longVal);
	}
}
