package io.github.therma.jt.csv;

import java.util.function.BiConsumer;

/**
 * Base interface used to describe a POJO field which can be read from a CSV
 * file.
 * 
 * @author Thierry Hermann
 * @param <T>
 *           the type of the POJO
 */
public interface CsvFieldRead<T> extends CsvField<T> {

   /**
    * @return a reference to the setter method of the property in the POJO bean.
    */
   <X> BiConsumer<T, X> setter();
}
