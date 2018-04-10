package runOnt;

import java.awt.Component;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.GridBagLayout;

import javax.swing.JTextField;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.io.PrintStream;

import javax.swing.JTextArea;



public class UIFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5768705352632498667L;
	private JPanel contentPane;
	private JTextField textField_Ont;
	private JTextField textField_CSV;
	private JLabel lblOntology;
	private JLabel lblCsvFile;
	private JButton btnExecuteReasoner;
	private String AsciiString;
	


	public UIFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 783, 613);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{20, 0, 80, 0, 0, 0, 0, 0, 80, 0, 15, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 15, 0, 15, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblOntology = new JLabel("Ontology:");
		lblOntology.setForeground(Color.DARK_GRAY);
		lblOntology.setFont(new Font("Arial", Font.BOLD, 12));
		GridBagConstraints gbc_lblOntology = new GridBagConstraints();
		gbc_lblOntology.insets = new Insets(0, 0, 5, 5);
		gbc_lblOntology.gridx = 2;
		gbc_lblOntology.gridy = 1;
		contentPane.add(lblOntology, gbc_lblOntology);
		
		

		
		
		textField_Ont = new JTextField();
		textField_Ont.setEditable(false);
		GridBagConstraints gbc_textField_Ont = new GridBagConstraints();
		gbc_textField_Ont.gridwidth = 3;
		gbc_textField_Ont.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Ont.fill = GridBagConstraints.BOTH;
		gbc_textField_Ont.gridx = 4;
		gbc_textField_Ont.gridy = 1;
		contentPane.add(textField_Ont, gbc_textField_Ont);
		textField_Ont.setColumns(10);
		
		lblCsvFile = new JLabel("CSV File:");
		lblCsvFile.setForeground(Color.DARK_GRAY);
		lblCsvFile.setFont(new Font("Arial", Font.BOLD, 12));
		GridBagConstraints gbc_lblCsvFile = new GridBagConstraints();
		gbc_lblCsvFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblCsvFile.gridx = 2;
		gbc_lblCsvFile.gridy = 2;
		contentPane.add(lblCsvFile, gbc_lblCsvFile);
		

		
		textField_CSV = new JTextField();
		textField_CSV.setEditable(false);
		GridBagConstraints gbc_textField_CSV = new GridBagConstraints();
		gbc_textField_CSV.gridwidth = 3;
		gbc_textField_CSV.insets = new Insets(0, 0, 5, 5);
		gbc_textField_CSV.fill = GridBagConstraints.BOTH;
		gbc_textField_CSV.gridx = 4;
		gbc_textField_CSV.gridy = 2;
		contentPane.add(textField_CSV, gbc_textField_CSV);
		textField_CSV.setColumns(10);
		
		JButton btnBrowse_Ont = new JButton("Browse");
		btnBrowse_Ont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("OWL Files", "owl");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setFileFilter(filter);
				jfc.setDialogTitle("Choose a directory to save your file: ");
				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (jfc.getSelectedFile().isFile()) {
						textField_Ont.setText(jfc.getSelectedFile().getPath());;
					}
				}
			}
		});
		GridBagConstraints gbc_btnBrowse_Ont = new GridBagConstraints();
		gbc_btnBrowse_Ont.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowse_Ont.gridx = 8;
		gbc_btnBrowse_Ont.gridy = 1;
		contentPane.add(btnBrowse_Ont, gbc_btnBrowse_Ont);
		
		JButton btnBrowse_CSV = new JButton("Browse");
		btnBrowse_CSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setFileFilter(filter);
				jfc.setDialogTitle("Choose a directory to save your file: ");
				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (jfc.getSelectedFile().isFile()) {
						textField_CSV.setText(jfc.getSelectedFile().getPath());;
					}
				}
			}
		});
		GridBagConstraints gbc_btnBrowse_CSV = new GridBagConstraints();
		gbc_btnBrowse_CSV.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowse_CSV.gridx = 8;
		gbc_btnBrowse_CSV.gridy = 2;
		contentPane.add(btnBrowse_CSV, gbc_btnBrowse_CSV);
		
		
		JTextArea txtConsole = new JTextArea();
		txtConsole.setFont(new Font("Monospaced", Font.PLAIN, 12));
		GridBagConstraints gbc_txtConsole = new GridBagConstraints();
		gbc_txtConsole.gridwidth = 9;
		gbc_txtConsole.insets = new Insets(0, 0, 5, 5);
		gbc_txtConsole.fill = GridBagConstraints.BOTH;
		gbc_txtConsole.gridx = 1;
		gbc_txtConsole.gridy = 5;
		
		txtConsole.setEditable(false);
	    JScrollPane scroll = new JScrollPane(txtConsole);

	    contentPane.add(scroll, gbc_txtConsole);
	    
		btnExecuteReasoner = new JButton("Execute Reasoner");
		btnExecuteReasoner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component frame = null;
				if(textField_Ont.getText().isEmpty() && textField_Ont.getText() != null){
					JOptionPane.showMessageDialog(frame, "Please select an Ontology File");
				}else if(textField_CSV.getText().isEmpty() && textField_CSV.getText() != null){
					JOptionPane.showMessageDialog(frame, "Please select a CSV file");
				}else{
					txtConsole.setText(Main.doReason(textField_Ont.getText(), textField_CSV.getText()));
					System.out.println(Main.doReason(textField_Ont.getText(), textField_CSV.getText()));
					
				}
					
			}
		});
		btnExecuteReasoner.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnExecuteReasoner.setForeground(new Color(60, 179, 113));
		GridBagConstraints gbc_btnExecuteReasoner = new GridBagConstraints();
		gbc_btnExecuteReasoner.insets = new Insets(0, 0, 5, 5);
		gbc_btnExecuteReasoner.gridx = 5;
		gbc_btnExecuteReasoner.gridy = 3;
		contentPane.add(btnExecuteReasoner, gbc_btnExecuteReasoner);
		
		//PrintStream out = new PrintStream( new TextAreaOutputStream( txtConsole ) );
		// redirect standard output stream to the TextAreaOutputStream
		//System.setOut( out );
		// redirect standard error stream to the TextAreaOutputStream
		//System.setErr( out );
		// now test the mechanism
		//
	}

}
