package io.github.therma.jt.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class CsvWriterTest {

   @Test
   public void testDefault() throws IOException {
      
      TestPojo testPojo = new TestPojo();
      testPojo.setField1("F1");
      testPojo.setField2(2l);
      
      final CsvWriter csvWriter = CsvWriter.builder().build();
      csvWriter.write(Stream.of(testPojo), TestSchema.values());
   }
   
   @Test
   public void testDefaultResult() throws IOException {
      
      TestPojo testPojo = new TestPojo();
      testPojo.setField1("F1");
      testPojo.setField2(2l);
      
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final PrintWriter writer = new PrintWriter(baos);
      
      final CsvWriter csvWriter = CsvWriter.builder()
               .setWriter(writer)
               .build();
      
      csvWriter.write(Stream.of(testPojo), TestSchema.values());

      Assert.assertEquals(baos.toString(), "Field 1;Field 2" + System.lineSeparator() + "F1;2" + System.lineSeparator());
   }
   
	@Test
	public void testFullConfig() throws IOException {
		
      TestPojo testPojo = new TestPojo();
      testPojo.setField1("F1");
      testPojo.setField2(2l);
	   
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final PrintWriter writer = new PrintWriter(baos);
      
      final CsvWriter csvWriter = CsvWriter.builder()
               .setWriter(writer)
               .setSeparator("|")
               .printHeader(false)
               .printQuotes(true)
               .build();
      
      csvWriter.write(Stream.of(testPojo), TestSchema.values());

      Assert.assertEquals(baos.toString(), "\"F1\"|\"2\"" + System.lineSeparator());
	}
}
