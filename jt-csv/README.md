## CSV Tool Box

The CSV tool box offers Java utility classes allowing to easily write CSV files 
from standard Java Stream of POJO objects.

Sample code to write a CSV file from a POJO Stream:

```java
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
```

Where TestSchema is:

```java
import java.util.function.BiConsumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public enum TestSchema implements CsvFieldRW<TestPojo> {
	
	field1("Field 1", TestPojo::getField1, TestPojo::setField1),
	field2("Field 2", TestPojo::getField2, TestPojo::setField2);

	private String header;
	private Function<TestPojo, Object> getter;
	private BiConsumer<TestPojo, Object> setter;
	
	private <X> TestSchema(String header, Function<TestPojo, X> getter, BiConsumer<TestPojo, X> setter) {
		this.header = header;
		this.getter = (Function<TestPojo, Object>) getter;
		this.setter = (BiConsumer<TestPojo, Object>) setter;
	}

	public String getHeader() {
		return header;
	}

	public <X> Function<TestPojo, X> getter() {
		return (Function<TestPojo, X>) getter;
	}

	@Override
	public <X> BiConsumer<TestPojo, X> setter() {
		return (BiConsumer<TestPojo, X>) setter;
	}
}
```

and TestPojo is:

```java
public class TestPojo {

	private String field1;
	private Long field2;

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public Long getField2() {
		return field2;
	}

	public void setField2(Long field2) {
		this.field2 = field2;
	}
}
```