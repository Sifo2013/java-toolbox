package io.github.therma.jt.csv;

/**
 * Base interface used to describe a POJO field which can be read or written
 * from/to a CSV file.
 * 
 * @author Thierry Hermann
 * @param <T>
 *           the type of the POJO
 */
public interface CsvFieldRW<T> extends CsvFieldWrite<T>, CsvFieldRead<T> {

}
