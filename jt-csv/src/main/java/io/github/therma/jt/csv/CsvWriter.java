package io.github.therma.jt.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Writer utility class helping to write {@link Stream} of standard POJO beans
 * to a CSV {@link File} or {@link PrintWriter}.
 * 
 * Sample usage:
 * 
 * <pre>
 * TestPojo testPojo = new TestPojo();
 * testPojo.setField1("F1");
 * testPojo.setField2(2l);
 * 
 * CsvWriter csvWriter = CsvWriter.builder()
 *          .printHeader(true)
 *          .printQuotes(true)
 *          .setSeparator("|")
 *          .build();
 * 
 * csvWriter.write(Stream.of(testPojo), TestSchema.values());
 * </pre>
 * 
 * @author Thierry Hermann
 */
@SuppressWarnings("unchecked")
public class CsvWriter {

   /**
    * The instance of the PrintWriter to be used
    */
   private final PrintWriter writer;

   /**
    * The CSV Separator
    */
   private final String separator;

   /**
    * Flag used to configure if Quotes must be printed
    */
   private final boolean printQuotes;

   /**
    * Flag used to configure if Header row must be printed
    */
   private final boolean printHeader;

   /**
    * Create a new instance of this {@link CsvWriter} class
    * 
    * @param builder
    *           the builder instance
    * @throws IOException
    *            in case an {@link IOException} occurs
    */
   private CsvWriter(Builder builder) throws IOException {

      if (builder.writer != null) {
         this.writer = builder.writer;
      }
      else if (builder.file != null) {
         this.writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(builder.file), builder.charset));
      }
      else {
         this.writer = new PrintWriter(System.out);
      }
      this.separator = builder.separator;
      this.printQuotes = builder.printQuotes;
      this.printHeader = builder.printHeader;
   }

   /**
    * @return a new instance of this class {@link Builder}
    */
   public static Builder builder() {

      return new Builder();
   }

   /**
    * Write the given {@link Stream} of POJO objects to the configured
    * {@link File} or {@link PrintWriter} using the given writable schema.
    * 
    * @param dataStream
    *           {@link Stream} of POJO objects
    * @param schema
    *           the CSV Schema
    * @throws IOException
    *            in case an {@link IOException} occurs while writing to the
    *            {@link File} or {@link PrintStream}.
    */
   public <T> void write(Stream<T> dataStream, CsvFieldWrite<T>... schema) throws IOException {

      try {
         printHeaderIfNeeded(schema);
         dataStream.map(r -> formatRow(r, schema)).forEach(writer::println);
      }
      finally {
         this.writer.close();
      }
   }

   private <T> void printHeaderIfNeeded(CsvFieldWrite<T>... schema) {

      if (printHeader) {
         final String header = Stream.of(schema)
                  .map(f -> formatWithQuotes(f.getHeader()))
                  .collect(Collectors.joining(separator));
         writer.println(header);
      }
   }

   private <T> String formatRow(T data, CsvFieldWrite<T>... schema) {

      return Stream.of(schema)
               .map(f -> formatWithQuotes(mapDataField(data, f)))
               .collect(Collectors.joining(separator));
   }

   private <T> String mapDataField(T data, CsvFieldWrite<T> field) {

      return Optional.ofNullable(field)
               .map(CsvFieldWrite::getter)
               .map(f -> f.apply(data))
               .map(Object::toString)
               .orElse("");
   }

   private String formatWithQuotes(String value) {

      if (printQuotes) {
         return String.format("\"%s\"", value);
      }
      return value;
   }

   /**
    * Builder class for the {@link CsvWriter}.
    * 
    * @author CTG Luxembourg P.S.F.
    */
   public static class Builder {

      /**
       * The optional File on which to write the CSV file
       */
      private File file;

      /**
       * The optional instance of the PrintWriter to be used
       */
      private PrintWriter writer;

      /**
       * The optional {@link Charset}. Default UFT-8
       */
      private Charset charset = StandardCharsets.UTF_8;

      /**
       * The optional separator {@link String}. Default: ";"
       */
      private String separator = ";";

      /**
       * flag to configure if Quotes must to be printed. Default "false"
       */
      private boolean printQuotes = false;

      /**
       * flag to configure if the first header row must to be printed. Default
       * "true"
       */
      private boolean printHeader = true;

      /**
       * Set the optional File on which to write the CSV file.
       * 
       * @param file
       * @return the {@link Builder} instance.
       */
      public Builder toFile(File file) {

         this.file = file;
         return this;
      }

      /**
       * Set the optional {@link Charset}. Default UFT-8
       * 
       * @param charset
       * @return the {@link Builder} instance.
       */
      public Builder setCharset(Charset charset) {

         this.charset = charset;
         return this;
      }

      /**
       * Set the optional instance of the PrintWriter to be used
       * 
       * @param writer
       * @return the {@link Builder} instance.
       */
      public Builder setWriter(PrintWriter writer) {

         this.writer = writer;
         return this;
      }

      /**
       * Set the optional separator {@link String}. Default: ";"
       * 
       * @param separator
       * @return the {@link Builder} instance.
       */
      public Builder setSeparator(String separator) {

         this.separator = separator;
         return this;
      }

      /**
       * Set the flag to configure if Quotes must to be printed. Default "false"
       * 
       * @param printQuotes
       * @return the {@link Builder} instance.
       */
      public Builder printQuotes(boolean printQuotes) {

         this.printQuotes = printQuotes;
         return this;
      }

      /**
       * Set the flag to configure if the first header row must to be printed.
       * Default "true"
       * 
       * @param printHeader
       * @return the {@link Builder} instance.
       */
      public Builder printHeader(boolean printHeader) {

         this.printHeader = printHeader;
         return this;
      }

      /**
       * Create a new instance of {@link CsvWriter}
       * 
       * @return a new instance of {@link CsvWriter}
       * @throws IOException
       */
      public CsvWriter build() throws IOException {

         return new CsvWriter(this);
      }
   }
}
