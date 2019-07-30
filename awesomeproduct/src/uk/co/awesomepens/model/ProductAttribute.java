package uk.co.awesomepens.model;

import java.util.List;


public class ProductAttribute {
	
	private String name;
	private List<String> Values;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getValues() {
		return Values;
	}
	public void setValues(List<String> values) {
		Values = values;
	}

}
