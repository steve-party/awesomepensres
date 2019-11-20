package uk.co.awesomepens.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.ColorChooserComponentFactory;


import uk.co.awesomepens.beans.ProductMgr;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;

public class BatchProcessorGui extends JFrame {

	private JPanel contentPane;
	private static BatchProcessorGui batchProcessorGui = null;
	private JTextField textField;
	private JList productList;
	private JTextArea textAreaOutput;
	private List<File> productFolders;
	private ProductMgr productMgr;
	private JTextField textField_1;
	//private List<File> colouredItems;

	/**
	 * Create the frame.
	 */
	private BatchProcessorGui() {
		setResizable(false);
		productFolders = new ArrayList<File>();
		textAreaOutput = new JTextArea();
		productMgr = new ProductMgr(textAreaOutput);
		//colouredItems = new ArrayList<File>();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1149, 865);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		productList = new JList();
		
		JScrollPane scrollPane = new JScrollPane(productList);
		scrollPane.setBounds(5, 5, 523, 767);
		contentPane.add(scrollPane);
		
		JButton btnAddProduct = new JButton("+");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Preferences prefs = Preferences.userRoot().node(getClass().getName());
				JFileChooser fc = new JFileChooser(prefs.get("LAST_USED_FOLDER",
					    new File(".").getAbsolutePath()));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setMultiSelectionEnabled(true);
				int status = fc.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {

					File[] files = fc.getSelectedFiles();
					
					//productFolders.addAll(Arrays.asList(files));
					
					for(File file : files) {
						if(!productFolders.contains(file))
							productFolders.add(file);
					}
					
					productList.setListData(productFolders.toArray());

					renderList();
					//Remember the last use
					prefs.put("LAST_USED_FOLDER", fc.getSelectedFile().getParent());
				}
				else if (status == JFileChooser.CANCEL_OPTION){
					System.out.println("calceled");
				}
			}
		});
		btnAddProduct.setBounds(5, 784, 94, 29);
		contentPane.add(btnAddProduct);
		
		JButton btnRemoveProduct = new JButton("-");
		btnRemoveProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indices = productList.getSelectedIndices();
				for(int i : indices) {
					productFolders.remove(productList.getModel().getElementAt(i));
				}
				productList.setListData(productFolders.toArray());
				
				renderList();
			}
		});
		

		btnRemoveProduct.setBounds(111, 784, 87, 29);
		contentPane.add(btnRemoveProduct);
		
		JLabel lblOutputLocation = new JLabel("Output Location");
		lblOutputLocation.setBounds(540, 5, 171, 16);
		contentPane.add(lblOutputLocation);
		
		textField = new JTextField();
		textField.setBounds(540, 33, 478, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Preferences prefs = Preferences.userRoot().node(getClass().getName());
				JFileChooser fc = new JFileChooser(prefs.get("LAST_USED_FOLDER",
					    new File(".").getAbsolutePath()));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int status = fc.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					textField.setText(fc.getSelectedFile().toString());
					//Remember the last use
					prefs.put("LAST_USED_FOLDER", fc.getSelectedFile().getParent());
				}
				else if (status == JFileChooser.CANCEL_OPTION){
					System.out.println("calceled");
				}
			}
		});
		btnNewButton.setBounds(1041, 33, 94, 29);
		contentPane.add(btnNewButton);
		
		
		
		JScrollPane scrollPane_1 = new JScrollPane(textAreaOutput);
		scrollPane_1.setBounds(540, 457, 595, 315);
		contentPane.add(scrollPane_1);
		
		JTextArea txtAreaExtraImages = new JTextArea();
		txtAreaExtraImages.setLineWrap(true);
		JScrollPane scrollPane_2 = new JScrollPane(txtAreaExtraImages);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(540, 133, 595, 113);
		contentPane.add(scrollPane_2);
		
		JCheckBox chckbxAppendToImage = new JCheckBox("Append to Image");
		chckbxAppendToImage.setBounds(540, 249, 154, 23);
		contentPane.add(chckbxAppendToImage);
		
		JCheckBox chckbxCreateGroupProduct = new JCheckBox("Create Group Product");
		chckbxCreateGroupProduct.setBounds(706, 249, 179, 23);
		contentPane.add(chckbxCreateGroupProduct);
		
		JButton btnNewButton_1 = new JButton("Process");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(productList.getModel().getSize() == 0) {
					MessageBox("No product Folder has been selected!");
				}
				else if(textField.getText().isEmpty()) {
					MessageBox("Please choose an output folder.");
				}
				String extraInfo = "";
				if(chckbxAppendToImage.isSelected()){
					if (!(txtAreaExtraImages.getText().trim().isEmpty()))
						extraInfo = txtAreaExtraImages.getText();
				}

				productMgr.BatchProcessProducts(productFolders, textField.getText(), extraInfo, chckbxCreateGroupProduct.isSelected());	
			}
		});
		btnNewButton_1.setBounds(1000, 778, 135, 41);
		contentPane.add(btnNewButton_1);
		
		JButton btnRemoveAll = new JButton("Remove All");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(productList.getModel().getSize() != 0) {
					productFolders.clear();
					productList.setListData(productFolders.toArray());
				}
			}
		});
		btnRemoveAll.setBounds(210, 784, 117, 29);
		contentPane.add(btnRemoveAll);
		
		JLabel lblChooseTheRoot = new JLabel("Choose the root folder to scan ready to export products:");
		lblChooseTheRoot.setBounds(540, 71, 380, 16);
		contentPane.add(lblChooseTheRoot);
		
		textField_1 = new JTextField();
		textField_1.setBounds(540, 99, 364, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Browse");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Preferences prefs = Preferences.userRoot().node(getClass().getName());
				JFileChooser fc = new JFileChooser(prefs.get("LAST_USED_FOLDER",
					    new File(".").getAbsolutePath()));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int status = fc.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					textField_1.setText(fc.getSelectedFile().toString());
					//Remember the last use
					prefs.put("LAST_USED_FOLDER", fc.getSelectedFile().getParent());
				}
				else if (status == JFileChooser.CANCEL_OPTION){
					System.out.println("calceled");
				}
			}
		});
		btnNewButton_2.setBounds(916, 99, 104, 29);
		contentPane.add(btnNewButton_2);
		
		JButton btnScan = new JButton("Scan");
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField_1.getText().isEmpty())
					MessageBox("Please choose to folder to scan!");
				else {
					productFolders = productMgr.getUnexportedProductPaths(textField_1.getText());
					productList.setListData(productFolders.toArray());
					renderList();
				}
			}
		});
		btnScan.setBounds(1039, 99, 96, 29);
		contentPane.add(btnScan);
		
		JLabel lblNoMetacsv = new JLabel("No Meta.csv");
		lblNoMetacsv.setBounds(540, 789, 87, 16);
		lblNoMetacsv.setForeground(new Color(60, 179, 113));
		contentPane.add(lblNoMetacsv);
		
		JLabel lblNoQulifiedImages = new JLabel("No Qulified Images");
		lblNoQulifiedImages.setBounds(639, 789, 127, 16);
		lblNoQulifiedImages.setForeground(new Color(155, 195, 235));
		contentPane.add(lblNoQulifiedImages);
		
		JLabel lblExportedBefore = new JLabel("Exported Before");
		lblExportedBefore.setBounds(784, 789, 104, 16);
		lblExportedBefore.setForeground(new Color(201, 155, 235));
		contentPane.add(lblExportedBefore);
		
		JButton btnRemoveHighlighted = new JButton("Remove Highlighted");
		btnRemoveHighlighted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<File> colouredItems = new ArrayList<File>();
				List<Integer> noMetaList= productMgr.noMetaFoundIndices(productFolders);
				List<Integer> noImageList = productMgr.noImageFoundIndices(productFolders);
				List<Integer> hasExportedList = productMgr.hasExportedIndices(productFolders);
				
				for(int index : noMetaList)
					colouredItems.add(productFolders.get(index));
				for(int index : noImageList)
					colouredItems.add(productFolders.get(index));
				for(int index : hasExportedList)
					colouredItems.add(productFolders.get(index));
				
				for(File colorDir : colouredItems)
					productFolders.remove(colorDir);
				
				productList.setListData(productFolders.toArray());
				renderList();
				
			}
		});
		btnRemoveHighlighted.setBounds(339, 784, 179, 29);
		contentPane.add(btnRemoveHighlighted);
		

		

	}
	
	private void renderList()
	{

		
		List<Integer> noMetaList= productMgr.noMetaFoundIndices(productFolders);
		List<Integer> noImageList = productMgr.noImageFoundIndices(productFolders);
		List<Integer> hasExportedList = productMgr.hasExportedIndices(productFolders);
		productList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                      boolean isSelected, boolean cellHasFocus) {
                 Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                 if (noMetaList.contains(index)) {
                       setBackground(new Color(163, 217, 181));
                 } 
                 else if (noImageList.contains(index)){
               	  setBackground(new Color(155, 195, 235));
                 }
                 else if (hasExportedList.contains(index)) {
               	  setBackground(new Color(201, 155, 235));
                 }
                 
                 return c;
            }
            });
	}
	
	public static BatchProcessorGui GetInstance() {
		if (batchProcessorGui == null) 
			batchProcessorGui = new BatchProcessorGui();
		return batchProcessorGui;
		
	}
	
	private void MessageBox(String str) {
		JOptionPane.showMessageDialog(this, str);
	}
}
