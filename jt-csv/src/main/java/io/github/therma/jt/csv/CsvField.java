package io.github.therma.jt.csv;

/**
 * Base interface to describe a field of a POJO bean.
 * 
 * @author Thierry Hermann
 * @param <T> the type of the POJO
 */
public interface CsvField<T> {

	/**
	 * @return the header value to be printed 
	 */
	String getHeader();
}
