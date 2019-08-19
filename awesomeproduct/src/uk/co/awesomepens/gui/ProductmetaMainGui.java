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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.TitledBorder;


import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;

public class ProductmetaMainGui {
	
	private int attributeCount = 1;
	private ProductMgr productMgr;
	private List<ProductAttribute> attributes;
	private JTextField selectedAttribute;
	private String selectPath;
//	private List<AttributeControlSet> attributeCtrls;
	private List<AttributeControlSet> attributeControlSets;

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
	private JButton btnMetaCsv;
	private JCheckBox checkbxPublished;
	private JTextArea textAreaShortDescription;
	private JTextArea textAreaDescription;
	private JList AttributeList;
	private JLabel lblMsg;
	private JMenuItem mntmBatchProcess;
	private JLabel label;
	private JButton button;
	
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
//		this.attributeCtrls = new ArrayList<AttributeControlSet>();
		this.attributeControlSets = new ArrayList<AttributeControlSet>();
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1420, 949);
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
		imgPanel.setBounds(1185, 6, 220, 220);
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
					
					for(AttributeControlSet ac : attributeControlSets) {
						ac.getAttributeNameBox().setSelectedIndex(-1);
						ac.getAttributeValueField().setText("");
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
						Iterator<AttributeControlSet> cIt = attributeControlSets.iterator();
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
		
		AttributeList = new JList();
		
		for (int i=1; i<=Product.MAX_ATTRIBUTE_AMOUNT; i++) {
			
			AttributeControlSet aSet = new AttributeControlSet();
			
			JLabel AttributeName = new JLabel("Attribute " + i + " name");
			JLabel AttributeValues = new JLabel("Attribute "+ i +" value(s)");
			JComboBox AttributeNameBox = new JComboBox(attributeNames.toArray());
			JTextField AttributeValueField = new JTextField();
			
			
			aSet.setAttributeName(AttributeName);
			aSet.setAttributeValues(AttributeValues);
			aSet.setAttributeNameBox(AttributeNameBox);
			aSet.setAttributeValueField(AttributeValueField);
			
			JPanel panel = new JPanel();
			panel.setLayout(null);
			AttributeName.setBounds(0, 23, 114, 16);
			panel.add(AttributeName);
			AttributeValues.setBounds(0, 54, 130, 16);
			panel.add(AttributeValues);
			AttributeNameBox.setBounds(126, 19, 182, 27);
			AttributeNameBox.setSelectedIndex(-1);
			AttributeNameBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (AttributeNameBox.getSelectedIndex() >-1)
						AttributeList.setListData(productMgr.getAttributeValuesbyName(AttributeNameBox.getSelectedItem().toString()).toArray());
					selectedAttribute = AttributeValueField;
					if (selectedAttribute!=null) {
						selectedAttribute.setText("");
					}
				}
			});
			panel.add(AttributeNameBox);
			AttributeValueField.setBounds(126, 49, 303, 26);
			
			panel.add(AttributeValueField);
			
			if(i==1) {
				AttributeNameBox.setSelectedItem("Brand");
				
			}
			aSet.setPanel(panel);
			
			attributeControlSets.add(aSet);
		}
		
		JPanel attributePanel = new JPanel();
		attributePanel.setPreferredSize(new Dimension(450, 2610));
		attributePanel.setLayout(null);
		
		JScrollPane sPaneAttributes = new JScrollPane(attributePanel);
		sPaneAttributes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sPaneAttributes.setBounds(727, 227, 460, 672);
		frame.getContentPane().add(sPaneAttributes);
		
		int count = 0;
		int height = 65;
		for (AttributeControlSet aSet : attributeControlSets) {
			JPanel panel = aSet.getPanel();
			panel.setBounds(6, height*count, 429, 80);
			attributePanel.add(panel);
			count++;
		}
		
		
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
				
				LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
				//attributes.put(comboAttributeName1.getSelectedItem().toString(), txtAttributeValue1.getText());
				for (AttributeControlSet ac : attributeControlSets)
				{
					if(ac.getAttributeNameBox().getSelectedIndex()>-1 && !ac.getAttributeValueField().getText().trim().isEmpty()) 
						attributes.put(ac.getAttributeNameBox().getSelectedItem().toString(), ac.getAttributeValueField().getText());

				}
				product.setAttributes(attributes);
				
				productMgr.createMetaCsv(selectPath, product);
				lblMsg.setText("meta.csv Saved!");
				lblMsg.setForeground(new Color(7,130, 26));
				
			}
		});
		btnMetaCsv.setBounds(909, 18, 128, 62);
		frame.getContentPane().add(btnMetaCsv);
		
		label = new JLabel("Choose Attribute Values");
		label.setBounds(1191, 227, 203, 16);
		frame.getContentPane().add(label);
		
		
		
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
		JScrollPane sPaneAttributeList = new JScrollPane(AttributeList);
		sPaneAttributeList.setBounds(1191, 252, 214, 605);
		frame.getContentPane().add(sPaneAttributeList);
		
		AttributeList.setListData(productMgr.getAttributeValuesbyName("Brand").toArray());
		
		button = new JButton("Add to Attribute");
		button.addActionListener(new ActionListener() {
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
		button.setBounds(1201, 866, 182, 29);
		frame.getContentPane().add(button);
		
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
