package uk.co.awesomepens.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import uk.co.awesomepens.beans.ProductMgr;
import uk.co.awesomepens.model.Product;
import uk.co.awesomepens.model.ProductAttribute;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.TitledBorder;


import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class ProductmetaMainGui {
	
	private int attributeCount = 1;
	private ProductMgr productMgr;
	private List<ProductAttribute> attributes;
	private JTextField selectedAttribute;
	private String selectPath;
	private List<AttributeControlSet> attributeCtrls;

	private JFrame frame;
	private JTextField textField;
	private ImagePanel imgPanel;
	private JLabel lblSku;
	private JTextField txtSku;
	private JLabel lblName;
	private JTextField txtName;
	private JLabel lblShortDescription;
	private JTextField txtStock;
	private JLabel lblRegularPrice;
	private JTextField txtRegularPrice;
	private JLabel lblSalePrice;
	private JTextField txtSalePrice;
	private JLabel lblNewLabel_1;
	private JTextField txtCategories;
	private JLabel lblImages;
	private JTextField txtImages;
	private JLabel lblTags;
	private JTextField txtTags;
	private JLabel lblMetawpmgtincode;
	private JTextField txtGtin;
	private JLabel lblWeightkg;
	private JTextField txtWeight;
	private JLabel lblLengthcm;
	private JLabel lblWidthcm;
	private JTextField txtWidth;
	private JTextField txtLength;
	private JLabel lblHeightcm;
	private JTextField txtHeight;
	private JLabel lblAttributeName1;
	private JComboBox comboAttributeName1;
	private JLabel lblAttributeValues1;
	private JTextField txtAttributeValue1;
	private JButton btnMetaCsv;
	private JButton btnLessAttribute;
	private JPanel panel_1;
	private JLabel lblAttributeName2;
	private JComboBox comboAttributeName2;
	private JLabel lblAttributeValues2;
	private JTextField txtAttributeValue2;
	private JPanel panel_2;
	private JLabel lblAttributeName3;
	private JComboBox comboAttributeName3;
	private JLabel lblAttributeValues3;
	private JTextField txtAttributeValue3;
	private JPanel panel_3;
	private JLabel lblAttributeName4;
	private JComboBox comboAttributeName4;
	private JLabel lblAttributeValues4;
	private JTextField txtAttributeValue4;
	private JPanel panel_4;
	private JLabel lblAttributeName5;
	private JComboBox comboAttributeName5;
	private JLabel lblAttributeValues5;
	private JTextField txtAttributeValue5;
	private JPanel panel_5;
	private JLabel lblAttributeName6;
	private JComboBox comboAttributeName6;
	private JLabel lblAttributeValues6;
	private JTextField txtAttributeValue6;
	private JPanel panel_6;
	private JLabel lblAttributeName7;
	private JComboBox comboAttributeName7;
	private JLabel lblAttributeValues7;
	private JTextField txtAttributeValue7;
	private JPanel panel_7;
	private JLabel lblAttributeName8;
	private JComboBox comboAttributeName8;
	private JLabel lblAttributeValues8;
	private JTextField txtAttributeValue8;
	private JPanel panel_8;
	private JLabel lblAttributeName9;
	private JComboBox comboAttributeName9;
	private JLabel lblAttributeValues9;
	private JTextField txtAttributeValue9;
	private JPanel panel_9;
	private JLabel lblAttributeName10;
	private JComboBox comboAttributeName10;
	private JLabel lblAttributeValues;
	private JTextField txtAttributeValue10;
	private JPanel panel_10;
	private JLabel lblAttributeName11;
	private JComboBox comboAttributeName11;
	private JLabel lblAttributeValues11;
	private JTextField txtAttributeValue11;
	private JList AttributeList;
	private JLabel lblChooseAttributeValues;
	private JCheckBox checkbxPublished;
	private JTextArea textAreaShortDescription;
	private JTextArea textAreaDescription;
	private JLabel lblMsg;
	private JMenuItem mntmBatchProcess_1;
	private JMenuItem mntmBatchProcess;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductmetaMainGui window = new ProductmetaMainGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProductmetaMainGui() { 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.productMgr = new ProductMgr();
		this.attributes = productMgr.getAttributes();
		List<String> attributeNames = productMgr.getAttributeNameList(attributes);
		this.attributeCtrls = new ArrayList<AttributeControlSet>();
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1387, 949);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(6, 40, 596, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Product Folder:");
		lblNewLabel.setBounds(6, 24, 166, 16);
		frame.getContentPane().add(lblNewLabel);
		
		imgPanel = new ImagePanel();
		imgPanel.setBounds(1161, 6, 220, 220);
		//imgPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imgPanel.setBackground(new Color(255,255,255));
		frame.getContentPane().add(imgPanel);
			
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Preferences prefs = Preferences.userRoot().node(getClass().getName());
				JFileChooser fc = new JFileChooser(prefs.get("LAST_USED_FOLDER",
					    new File(".").getAbsolutePath()));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int status = fc.showOpenDialog(null);
				

				if (status == JFileChooser.APPROVE_OPTION) {
					lblMsg.setText("");
					textField.setText(fc.getSelectedFile().toString());
					selectPath = fc.getSelectedFile().toString();
					imgPanel.setImage(ProductMgr.getThumbnailPath(fc.getSelectedFile().toString()));
					frame.repaint();
					
					//Remember the last use
					prefs.put("LAST_USED_FOLDER", fc.getSelectedFile().getParent());
					
					// reset all fields
					
					txtSku.setText("");
					txtName.setText("");
					checkbxPublished.setSelected(false);
					textAreaShortDescription.setText("");
					textAreaDescription.setText("");
					txtStock.setText("");
					txtWeight.setText("");
					txtLength.setText("");
					txtWidth.setText("");
					txtHeight.setText("");
					txtSalePrice.setText("");
					txtRegularPrice.setText("");
					txtCategories.setText("");
					txtTags.setText("");
					txtImages.setText("");
					txtGtin.setText("");
					comboAttributeName1.setSelectedItem("Brand");
					txtAttributeValue1.setText("");
					
					for(AttributeControlSet ac : attributeCtrls) {
						ac.getAttributeNameBox().setSelectedIndex(-1);
						ac.getAttributeValueField().setText("");
						ac.getPanel().setVisible(false);
					}
					
					Product product = ProductMgr.loadProductFromCsv(selectPath);
					if (product == null){
						attributeCount = 1;
						ProductMgr.renameProductImages(selectPath);
						txtName.setText( ProductMgr.getProductNameFromPath(fc.getSelectedFile().toString()));
						txtCategories.setText(ProductMgr.getCategoryFromPath(fc.getSelectedFile().toString()));
						txtImages.setText(ProductMgr.getImageURLFromProductDir(fc.getSelectedFile().toString()));
					}else {
						attributeCount = product.getAttributes().size();
						txtSku.setText(product.getSku());
						txtName.setText(product.getName());
						checkbxPublished.setSelected(product.getPublished());
						textAreaShortDescription.setText(product.getShortDesciption());
						textAreaDescription.setText(product.getDescription());
						txtStock.setText(Integer.toString(product.getStock()));
						txtWeight.setText(product.getWeight()==0 ? "" : Double.toString(product.getWeight()));
						txtLength.setText(product.getLength()==0 ? "" : Double.toString(product.getLength()));
						txtWidth.setText(product.getWidth()==0 ? "" : Double.toString(product.getWidth()));
						txtHeight.setText(product.getHeight()==0 ? "" : Double.toString(product.getHeight()));
						txtSalePrice.setText(product.getSalePrice()==0 ? "" : Double.toString(product.getSalePrice()));
						txtRegularPrice.setText(product.getRegularPrice()==0 ? "" : Double.toString(product.getRegularPrice()));
						txtCategories.setText(product.getCategories());
						txtTags.setText(product.getTags());
						txtImages.setText(product.getImages());
						txtGtin.setText(product.getGtin());
						
						Iterator<Entry<String, String>> it = product.getAttributes().entrySet().iterator();
						Entry<String, String> firstEntry = it.next();
						comboAttributeName1.setSelectedItem(firstEntry.getKey());
						txtAttributeValue1.setText(firstEntry.getValue());
						Iterator<AttributeControlSet> cIt = attributeCtrls.iterator();
						while (it.hasNext() && cIt.hasNext()) {
							Entry<String, String> entry = it.next();
							AttributeControlSet acs = cIt.next();
							acs.getAttributeNameBox().setSelectedItem(entry.getKey());
							acs.getAttributeValueField().setText(entry.getValue());
							acs.getPanel().setVisible(true);
						}
						
					}
					
					

				} else if (status == JFileChooser.CANCEL_OPTION) {
					System.out.println("calceled");
				}

			}
		});
		btnBrowse.setBounds(603, 40, 117, 29);
		frame.getContentPane().add(btnBrowse);
		
		lblSku = new JLabel("Sku");
		lblSku.setBounds(6, 94, 61, 16);
		frame.getContentPane().add(lblSku);
		
		txtSku = new JTextField();
		txtSku.setBounds(84, 89, 146, 26);
		frame.getContentPane().add(txtSku);
		txtSku.setColumns(10);
		
		lblName = new JLabel("Name");
		lblName.setBounds(6, 122, 61, 16);
		frame.getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(84, 117, 637, 26);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		lblShortDescription = new JLabel("Short description");
		lblShortDescription.setBounds(6, 238, 117, 16);
		frame.getContentPane().add(lblShortDescription);
		
		textAreaShortDescription = new JTextArea();
		JScrollPane spaneShortDescription = new JScrollPane(textAreaShortDescription);
		spaneShortDescription.setBounds(6, 265, 715, 46);
		frame.getContentPane().add(spaneShortDescription);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(6, 320, 94, 16);
		frame.getContentPane().add(lblDescription);
		
		textAreaDescription = new JTextArea();
		JScrollPane spaneDescription = new JScrollPane(textAreaDescription);
		spaneDescription.setBounds(6, 344, 715, 551);
		frame.getContentPane().add(spaneDescription);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(242, 94, 42, 16);
		frame.getContentPane().add(lblStock);
		
		txtStock = new JTextField();
		txtStock.setBounds(292, 89, 77, 26);
		frame.getContentPane().add(txtStock);
		txtStock.setColumns(10);
		
		lblRegularPrice = new JLabel("Regular price");
		lblRegularPrice.setBounds(381, 94, 88, 16);
		frame.getContentPane().add(lblRegularPrice);
		
		txtRegularPrice = new JTextField();
		txtRegularPrice.setBounds(482, 89, 79, 26);
		frame.getContentPane().add(txtRegularPrice);
		txtRegularPrice.setColumns(10);
		
		lblSalePrice = new JLabel("Sale price");
		lblSalePrice.setBounds(573, 94, 61, 16);
		frame.getContentPane().add(lblSalePrice);
		
		txtSalePrice = new JTextField();
		txtSalePrice.setBounds(646, 89, 75, 26);
		frame.getContentPane().add(txtSalePrice);
		txtSalePrice.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Categories");
		lblNewLabel_1.setBounds(6, 150, 79, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtCategories = new JTextField();
		txtCategories.setBounds(84, 145, 637, 26);
		frame.getContentPane().add(txtCategories);
		txtCategories.setColumns(10);
		
		lblImages = new JLabel("Images");
		lblImages.setBounds(6, 178, 61, 16);
		frame.getContentPane().add(lblImages);
		
		txtImages = new JTextField();
		txtImages.setColumns(10);
		txtImages.setBounds(84, 173, 637, 26);
		frame.getContentPane().add(txtImages);
		
		lblTags = new JLabel("Tags");
		lblTags.setBounds(6, 206, 61, 16);
		frame.getContentPane().add(lblTags);
		
		txtTags = new JTextField();
		txtTags.setBounds(84, 201, 527, 26);
		frame.getContentPane().add(txtTags);
		txtTags.setColumns(10);
		
		lblMetawpmgtincode = new JLabel("Meta: _wpm_gtin_code");
		lblMetawpmgtincode.setBounds(733, 150, 154, 16);
		frame.getContentPane().add(lblMetawpmgtincode);
		
		txtGtin = new JTextField();
		txtGtin.setBounds(731, 173, 306, 26);
		frame.getContentPane().add(txtGtin);
		txtGtin.setColumns(10);
		
		lblWeightkg = new JLabel("Weight (kg)");
		lblWeightkg.setBounds(733, 89, 79, 16);
		frame.getContentPane().add(lblWeightkg);
		
		txtWeight = new JTextField();
		txtWeight.setBounds(812, 84, 61, 26);
		frame.getContentPane().add(txtWeight);
		txtWeight.setColumns(10);
		
		lblLengthcm = new JLabel("Length (cm)");
		lblLengthcm.setBounds(885, 89, 79, 16);
		frame.getContentPane().add(lblLengthcm);
		
		lblWidthcm = new JLabel("Width (cm)");
		lblWidthcm.setBounds(733, 122, 79, 16);
		frame.getContentPane().add(lblWidthcm);
		
		txtWidth = new JTextField();
		txtWidth.setColumns(10);
		txtWidth.setBounds(812, 117, 61, 26);
		frame.getContentPane().add(txtWidth);
		
		txtLength = new JTextField();
		txtLength.setColumns(10);
		txtLength.setBounds(976, 84, 61, 26);
		frame.getContentPane().add(txtLength);
		
		lblHeightcm = new JLabel("Height (cm)");
		lblHeightcm.setBounds(885, 122, 79, 16);
		frame.getContentPane().add(lblHeightcm);
		
		txtHeight = new JTextField();
		txtHeight.setColumns(10);
		txtHeight.setBounds(976, 117, 61, 26);
		frame.getContentPane().add(txtHeight);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Attributes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(727, 227, 654, 672);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblAttributeName1 = new JLabel("Attribute 1 name");
		lblAttributeName1.setBounds(6, 23, 114, 16);
		panel.add(lblAttributeName1);
		
		lblAttributeValues1 = new JLabel("Attribute 1 value(s)");
		lblAttributeValues1.setBounds(6, 54, 130, 16);
		panel.add(lblAttributeValues1);
		
		comboAttributeName1 = new JComboBox(attributeNames.toArray());
		comboAttributeName1.setSelectedItem("Brand");
		comboAttributeName1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAttribute = txtAttributeValue1;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
					
				AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName1.getSelectedItem().toString()).toArray());
			}
		});
		comboAttributeName1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
			}
		});
		comboAttributeName1.setBounds(132, 19, 182, 27);
		panel.add(comboAttributeName1);
		
		
		JButton btnAddToAttribute = new JButton("Add to Attribute");
		btnAddToAttribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> list = AttributeList.getSelectedValuesList();
				if (!list.isEmpty()) {
					String value= "";
					for (String item : list) {
						value += item + ", ";
					}
					value = value.substring(0, value.lastIndexOf(", "));
					selectedAttribute.setText(value);
				}
			}
		});
		btnAddToAttribute.setBounds(463, 639, 182, 29);
		panel.add(btnAddToAttribute);
		
		txtAttributeValue1 = new JTextField();
		txtAttributeValue1.setBounds(132, 49, 303, 26);
		panel.add(txtAttributeValue1);
		txtAttributeValue1.setColumns(10);
		
		JButton btnMoreAttribute = new JButton("+");
		btnMoreAttribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (attributeCount) {
				case 1:
					panel_1.setVisible(true);
					break;
				case 2:
					panel_2.setVisible(true);
					break;
				case 3:
					panel_3.setVisible(true);
					break;
				case 4:
					panel_4.setVisible(true);
					break;
				case 5:
					panel_5.setVisible(true);
					break;
				case 6:
					panel_6.setVisible(true);
					break;
				case 7:
					panel_7.setVisible(true);
					break;
				case 8:
					panel_8.setVisible(true);
					break;
				case 9:
					panel_9.setVisible(true);
					break;
				case 10:
					panel_10.setVisible(true);
					btnMoreAttribute.setEnabled(false);
					break;

				default:
					break;
				}
				if (attributeCount<11)
					attributeCount++;
				if (attributeCount > 1)
					btnLessAttribute.setEnabled(true);
			}
		});
		btnMoreAttribute.setBounds(318, 18, 55, 29);
		panel.add(btnMoreAttribute);
		
		btnLessAttribute = new JButton("-");
		btnLessAttribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (attributeCount) {
				case 11:
					panel_10.setVisible(false);
					break;
				case 10:
					panel_9.setVisible(false);
					break;
				case 9:
					panel_8.setVisible(false);
					break;
				case 8:
					panel_7.setVisible(false);
					break;
				case 7: 
					panel_6.setVisible(false);
					break;
				case 6:
					panel_5.setVisible(false);
					break;
				case 5:
					panel_4.setVisible(false);
					break;
				case 4:
					panel_3.setVisible(false);
					break;
				case 3:
					panel_2.setVisible(false);
					break;
				case 2:
					panel_1.setVisible(false);
					btnLessAttribute.setEnabled(false);
					break;

				default:
					
					break;
				}
				if (attributeCount>1)
					attributeCount--;
				if (attributeCount<11)
					btnMoreAttribute.setEnabled(true);
			}
		});
		btnLessAttribute.setEnabled(false);
		btnLessAttribute.setBounds(380, 18, 55, 29);
		panel.add(btnLessAttribute);
		
		panel_1 = new JPanel();
		panel_1.setBounds(6, 73, 429, 60);
		panel.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setVisible(false);
		
		lblAttributeName2 = new JLabel("Attribute 2 name");
		lblAttributeName2.setBounds(0, 10, 114, 16);
		panel_1.add(lblAttributeName2);
		
		comboAttributeName2 = new JComboBox(attributeNames.toArray());
		comboAttributeName2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName2.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName2.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue2;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
				
			}
		});
		comboAttributeName2.setSelectedIndex(-1);
		comboAttributeName2.setBounds(126, 6, 182, 27);
		panel_1.add(comboAttributeName2);
		
		lblAttributeValues2 = new JLabel("Attribute 2 value(s)");
		lblAttributeValues2.setBounds(0, 41, 130, 16);
		panel_1.add(lblAttributeValues2);
		
		txtAttributeValue2 = new JTextField();
		txtAttributeValue2.setColumns(10);
		txtAttributeValue2.setBounds(126, 36, 303, 26);
		panel_1.add(txtAttributeValue2);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(6, 132, 429, 60);
		panel.add(panel_2);
		panel_2.setVisible(false);
		
		lblAttributeName3 = new JLabel("Attribute 3 name");
		lblAttributeName3.setBounds(0, 10, 114, 16);
		panel_2.add(lblAttributeName3);
		
		comboAttributeName3 = new JComboBox(attributeNames.toArray());
		comboAttributeName3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName3.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName3.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue3;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName3.setSelectedIndex(-1);
		comboAttributeName3.setBounds(126, 6, 182, 27);
		panel_2.add(comboAttributeName3);
		
		lblAttributeValues3 = new JLabel("Attribute 3 value(s)");
		lblAttributeValues3.setBounds(0, 41, 130, 16);
		panel_2.add(lblAttributeValues3);
		
		txtAttributeValue3 = new JTextField();
		txtAttributeValue3.setColumns(10);
		txtAttributeValue3.setBounds(126, 36, 303, 26);
		panel_2.add(txtAttributeValue3);
		
		panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(6, 191, 429, 60);
		panel.add(panel_3);
		panel_3.setVisible(false);
		
		lblAttributeName4 = new JLabel("Attribute 4 name");
		lblAttributeName4.setBounds(0, 10, 114, 16);
		panel_3.add(lblAttributeName4);
		
		comboAttributeName4 = new JComboBox(attributeNames.toArray());
		comboAttributeName4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName4.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName4.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue4;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName4.setSelectedIndex(-1);
		comboAttributeName4.setBounds(126, 6, 182, 27);
		panel_3.add(comboAttributeName4);
		
		lblAttributeValues4 = new JLabel("Attribute 4 value(s)");
		lblAttributeValues4.setBounds(0, 41, 130, 16);
		panel_3.add(lblAttributeValues4);
		
		txtAttributeValue4 = new JTextField();
		txtAttributeValue4.setColumns(10);
		txtAttributeValue4.setBounds(126, 36, 303, 26);
		panel_3.add(txtAttributeValue4);
		
		panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBounds(6, 250, 429, 60);
		panel.add(panel_4);
		panel_4.setVisible(false);
		
		lblAttributeName5 = new JLabel("Attribute 5 name");
		lblAttributeName5.setBounds(0, 10, 114, 16);
		panel_4.add(lblAttributeName5);
		
		comboAttributeName5 = new JComboBox(attributeNames.toArray());
		comboAttributeName5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName5.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName5.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue5;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName5.setSelectedIndex(-1);
		comboAttributeName5.setBounds(126, 6, 182, 27);
		panel_4.add(comboAttributeName5);
		
		lblAttributeValues5 = new JLabel("Attribute 5 value(s)");
		lblAttributeValues5.setBounds(0, 41, 130, 16);
		panel_4.add(lblAttributeValues5);
		
		txtAttributeValue5 = new JTextField();
		txtAttributeValue5.setColumns(10);
		txtAttributeValue5.setBounds(126, 36, 303, 26);
		panel_4.add(txtAttributeValue5);
		
		panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBounds(6, 309, 429, 60);
		panel.add(panel_5);
		panel_5.setVisible(false);
		
		lblAttributeName6 = new JLabel("Attribute 6 name");
		lblAttributeName6.setBounds(0, 10, 114, 16);
		panel_5.add(lblAttributeName6);
		
		comboAttributeName6 = new JComboBox(attributeNames.toArray());
		comboAttributeName6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName6.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName6.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue6;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName6.setSelectedIndex(-1);
		comboAttributeName6.setBounds(126, 6, 182, 27);
		panel_5.add(comboAttributeName6);
		
		lblAttributeValues6 = new JLabel("Attribute 6 value(s)");
		lblAttributeValues6.setBounds(0, 41, 130, 16);
		panel_5.add(lblAttributeValues6);
		
		txtAttributeValue6 = new JTextField();
		txtAttributeValue6.setColumns(10);
		txtAttributeValue6.setBounds(126, 36, 303, 26);
		panel_5.add(txtAttributeValue6);
		
		panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBounds(6, 368, 429, 60);
		panel.add(panel_6);
		panel_6.setVisible(false);
		
		lblAttributeName7 = new JLabel("Attribute 7 name");
		lblAttributeName7.setBounds(0, 10, 114, 16);
		panel_6.add(lblAttributeName7);
		
		comboAttributeName7 = new JComboBox(attributeNames.toArray());
		comboAttributeName7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName7.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName7.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue7;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName7.setSelectedIndex(-1);
		comboAttributeName7.setBounds(126, 6, 182, 27);
		panel_6.add(comboAttributeName7);
		
		lblAttributeValues7 = new JLabel("Attribute 7 value(s)");
		lblAttributeValues7.setBounds(0, 41, 130, 16);
		panel_6.add(lblAttributeValues7);
		
		txtAttributeValue7 = new JTextField();
		txtAttributeValue7.setColumns(10);
		txtAttributeValue7.setBounds(126, 36, 303, 26);
		panel_6.add(txtAttributeValue7);
		
		panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBounds(6, 427, 429, 60);
		panel.add(panel_7);
		panel_7.setVisible(false);
		
		lblAttributeName8 = new JLabel("Attribute 8 name");
		lblAttributeName8.setBounds(0, 10, 114, 16); 
		panel_7.add(lblAttributeName8);
		
		comboAttributeName8 = new JComboBox(attributeNames.toArray());
		comboAttributeName8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName8.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName8.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue8;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName8.setSelectedIndex(-1);
		comboAttributeName8.setBounds(126, 6, 182, 27);
		panel_7.add(comboAttributeName8);
		
		lblAttributeValues8 = new JLabel("Attribute 8 value(s)");
		lblAttributeValues8.setBounds(0, 41, 130, 16);
		panel_7.add(lblAttributeValues8);
		
		txtAttributeValue8 = new JTextField();
		txtAttributeValue8.setColumns(10);
		txtAttributeValue8.setBounds(126, 36, 303, 26);
		panel_7.add(txtAttributeValue8);
		
		panel_8 = new JPanel();
		panel_8.setLayout(null);
		panel_8.setBounds(6, 486, 429, 60);
		panel.add(panel_8);
		panel_8.setVisible(false);
		
		lblAttributeName9 = new JLabel("Attribute 9 name");
		lblAttributeName9.setBounds(0, 10, 114, 16);
		panel_8.add(lblAttributeName9);
		
		comboAttributeName9 = new JComboBox(attributeNames.toArray());
		comboAttributeName9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName9.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName9.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue9;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName9.setSelectedIndex(-1);
		comboAttributeName9.setBounds(126, 6, 182, 27);
		panel_8.add(comboAttributeName9);
		
		lblAttributeValues9 = new JLabel("Attribute 9 value(s)");
		lblAttributeValues9.setBounds(0, 41, 130, 16);
		panel_8.add(lblAttributeValues9);
		
		txtAttributeValue9 = new JTextField();
		txtAttributeValue9.setColumns(10);
		txtAttributeValue9.setBounds(126, 36, 303, 26);
		panel_8.add(txtAttributeValue9);
		
		panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBounds(6, 545, 429, 60);
		panel.add(panel_9);
		panel_9.setVisible(false);
		
		lblAttributeName10 = new JLabel("Attribute 10 name");
		lblAttributeName10.setBounds(0, 10, 114, 16);
		panel_9.add(lblAttributeName10);
		
		comboAttributeName10 = new JComboBox(attributeNames.toArray());
		comboAttributeName10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName10.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName10.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue10;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName10.setSelectedIndex(-1);
		comboAttributeName10.setBounds(126, 6, 182, 27);
		panel_9.add(comboAttributeName10);
		
		lblAttributeValues = new JLabel("Attribute 10 value(s)");
		lblAttributeValues.setBounds(0, 41, 130, 16);
		panel_9.add(lblAttributeValues);
		
		txtAttributeValue10 = new JTextField();
		txtAttributeValue10.setColumns(10);
		txtAttributeValue10.setBounds(126, 36, 303, 26);
		panel_9.add(txtAttributeValue10);
		
		panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBounds(6, 604, 429, 60);
		panel.add(panel_10);
		panel_10.setVisible(false);
		
		lblAttributeName11 = new JLabel("Attribute 11 name");
		lblAttributeName11.setBounds(0, 10, 114, 16);
		panel_10.add(lblAttributeName11);
		
		comboAttributeName11 = new JComboBox(attributeNames.toArray());
		comboAttributeName11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboAttributeName11.getSelectedIndex() >-1)
					AttributeList.setListData(productMgr.getAttributeValuesbyName(comboAttributeName11.getSelectedItem().toString()).toArray());
				selectedAttribute = txtAttributeValue11;
				if (selectedAttribute!=null) {
					selectedAttribute.setText("");
				}
			}
		});
		comboAttributeName11.setSelectedIndex(-1);
		comboAttributeName11.setBounds(126, 6, 182, 27);
		panel_10.add(comboAttributeName11);
		
		lblAttributeValues11 = new JLabel("Attribute 11 value(s)");
		lblAttributeValues11.setBounds(0, 41, 130, 16);
		panel_10.add(lblAttributeValues11);
		
		txtAttributeValue11 = new JTextField();
		txtAttributeValue11.setColumns(10);
		txtAttributeValue11.setBounds(126, 36, 303, 26);
		panel_10.add(txtAttributeValue11);

		selectedAttribute = txtAttributeValue1;
		
		// add all the attribute names and value controls object into one list starting from Attribute 2 as attribute 1 is a required field for Brand.
		attributeCtrls.add(new AttributeControlSet(panel_1,comboAttributeName2, txtAttributeValue2));
		attributeCtrls.add(new AttributeControlSet(panel_2,comboAttributeName3, txtAttributeValue3));
		attributeCtrls.add(new AttributeControlSet(panel_3,comboAttributeName4, txtAttributeValue4));
		attributeCtrls.add(new AttributeControlSet(panel_4,comboAttributeName5, txtAttributeValue5));
		attributeCtrls.add(new AttributeControlSet(panel_5,comboAttributeName6, txtAttributeValue6));
		attributeCtrls.add(new AttributeControlSet(panel_6,comboAttributeName7, txtAttributeValue7));
		attributeCtrls.add(new AttributeControlSet(panel_7,comboAttributeName8, txtAttributeValue8));
		attributeCtrls.add(new AttributeControlSet(panel_8,comboAttributeName9, txtAttributeValue9));
		attributeCtrls.add(new AttributeControlSet(panel_9,comboAttributeName10, txtAttributeValue10));
		attributeCtrls.add(new AttributeControlSet(panel_10,comboAttributeName11, txtAttributeValue11));
		
		AttributeList = new JList(productMgr.getAttributeValuesbyName("Brand").toArray());
		AttributeList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		            // Double-click detected
		            int index = AttributeList.locationToIndex(evt.getPoint());
		            String item = AttributeList.getSelectedValue().toString();
		            
		            if (selectedAttribute.getText().isEmpty()) {
		            	selectedAttribute.setText(item) ;
		            }		            	
		            else if(!ProductMgr.hasAttributeItem(selectedAttribute.getText(), ", ", item)) {
		            	selectedAttribute.setText(selectedAttribute.getText() + ", " + item);
					}
		        }
		    }
		});
		JScrollPane spanelAttributes = new JScrollPane(AttributeList);
		spanelAttributes.setBounds(446, 33, 199, 605);
		
		panel.add(spanelAttributes);
		
		lblChooseAttributeValues = new JLabel("Choose Attribute Values");
		lblChooseAttributeValues.setBounds(445, 15, 203, 16);
		panel.add(lblChooseAttributeValues);
		
		checkbxPublished = new JCheckBox("Published");
		checkbxPublished.setBounds(618, 203, 102, 23);
		frame.getContentPane().add(checkbxPublished);
		
		lblMsg = new JLabel("");
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setBounds(733, 24, 164, 53);
		frame.getContentPane().add(lblMsg);
		
		btnMetaCsv = new JButton("Save Meta CSV");
		btnMetaCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Product product = new Product();
				product.setSku(txtSku.getText());
				product.setName(txtName.getText());
				product.setCategories(txtCategories.getText());
				product.setImages(txtImages.getText());
				product.setTags(txtTags.getText());
				product.setShortDesciption(textAreaShortDescription.getText());
				product.setDescription(textAreaDescription.getText());
				product.setPublished(checkbxPublished.isSelected());
				product.setGtin(txtGtin.getText());
				if (!txtStock.getText().isEmpty())
					product.setStock(Integer.parseInt(txtStock.getText()));
				if (!txtRegularPrice.getText().trim().isEmpty())
					product.setRegularPrice(Double.parseDouble(txtRegularPrice.getText()));
				if (!txtSalePrice.getText().trim().isEmpty())
					product.setSalePrice(Double.parseDouble(txtSalePrice.getText()));
				if (!txtWeight.getText().trim().isEmpty())
					product.setWeight(Double.parseDouble(txtWeight.getText()));
				if (!txtLength.getText().trim().isEmpty())
					product.setLength(Double.parseDouble(txtLength.getText()));
				if (!txtWidth.getText().trim().isEmpty())
					product.setWidth(Double.parseDouble(txtWidth.getText()));
				if (!txtHeight.getText().trim().isEmpty())
					product.setHeight(Double.parseDouble(txtHeight.getText()));
				
				Hashtable<String, String> attributes = new Hashtable<String, String>();
				attributes.put(comboAttributeName1.getSelectedItem().toString(), txtAttributeValue1.getText());
				for (AttributeControlSet ac : attributeCtrls)
				{
					if(ac.getPanel().isVisible()&& ac.getAttributeNameBox().getSelectedIndex()>-1 && !ac.getAttributeValueField().getText().trim().isEmpty()) 
						attributes.put(ac.getAttributeNameBox().getSelectedItem().toString(), ac.getAttributeValueField().getText());
					else {
						break;
					}
				}
				product.setAttributes(attributes);
				
				productMgr.createMetaCsv(selectPath, product);
				lblMsg.setText("meta.csv Saved!");
				lblMsg.setForeground(new Color(7,130, 26));
				
			}
		});
		btnMetaCsv.setBounds(909, 18, 128, 62);
		frame.getContentPane().add(btnMetaCsv);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Apps");
		menuBar.add(mnNewMenu);
		
		mntmBatchProcess = new JMenuItem("Batch Process");
		mntmBatchProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						BatchProcessorGui batchProcessorGui = BatchProcessorGui.GetInstance();
						batchProcessorGui.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			}
		});
		mnNewMenu.add(mntmBatchProcess);
		
	}
}
