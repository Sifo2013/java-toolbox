package io.github.therma.jt.csv;

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
