package io.github.therma.jt.csv;

import java.util.function.Function;

/**
 * Base interface used to describe a POJO field which can be written to a CSV
 * file.
 * 
 * @author Thierry Hermann
 * @param <T>
 *           the type of the POJO
 */
public interface CsvFieldWrite<T> extends CsvField<T> {

   /**
    * @return a reference to the getter method of the property in the POJO
    *         bean.
    */
   <X> Function<T, X> getter();
}
