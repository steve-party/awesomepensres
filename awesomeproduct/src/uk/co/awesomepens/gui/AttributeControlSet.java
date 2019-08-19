package uk.co.awesomepens.gui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AttributeControlSet {
	private JPanel panel;
	private JComboBox attributeNameBox;
	private JTextField attributeValueField;

	private JLabel attributeName;
	private JLabel attributeValues;
	
	public AttributeControlSet() {
		
	}
	public AttributeControlSet(JPanel panel, JComboBox attributeName, JTextField attributeValue) {
		this.panel = panel;
		this.attributeNameBox = attributeName;
		this.attributeValueField = attributeValue;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public JComboBox getAttributeNameBox() {
		return attributeNameBox;
	}
	public void setAttributeNameBox(JComboBox attributeNameBox) {
		this.attributeNameBox = attributeNameBox;
	}
	public JTextField getAttributeValueField() {
		return attributeValueField;
	}
	public void setAttributeValueField(JTextField attributeValueField) {
		this.attributeValueField = attributeValueField;
	}
	
	public JLabel getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(JLabel attributeName) {
		this.attributeName = attributeName;
	}
	public JLabel getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(JLabel attributeValues) {
		this.attributeValues = attributeValues;
	}

}
