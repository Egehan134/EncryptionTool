package encryptionProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import java.io.File;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainUI {

	private JFrame frmEncryptionTool;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldAESDecrypt;
	private JPasswordField passwordFieldAESFileEncrypt;
	private JPasswordField passwordFieldAESFileDecrypt;
	public static final Color koyu = new Color (46, 59, 86);
	public static final Color acik = new Color (248, 243, 239);
	File inputFile;
	File outputFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frmEncryptionTool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEncryptionTool = new JFrame();
		frmEncryptionTool.setTitle("Encryption Tool");
		frmEncryptionTool.getContentPane().setBackground(new Color(35, 45, 65));
		frmEncryptionTool.setResizable(false);
		frmEncryptionTool.setBounds(100, 100, 625, 500);
		frmEncryptionTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEncryptionTool.getContentPane().setLayout(null);
		
		JPanel panelTepe = new JPanel();
		panelTepe.setBackground(new Color(248, 243, 239));
		panelTepe.setBounds(10, 10, 590, 70);
		frmEncryptionTool.getContentPane().add(panelTepe);
		panelTepe.setLayout(null);
		
		JButton btnAES = new JButton("AES");
		btnAES.setForeground(new Color(248, 243, 239));
		btnAES.setBackground(new Color(46, 59, 85));
		btnAES.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAES.setBounds(20, 26, 130, 45);
		panelTepe.add(btnAES);
		
		JButton btnAESFile = new JButton("AES File");
		btnAESFile.setForeground(new Color(46, 59, 85));
		btnAESFile.setBackground(new Color(248, 243, 239));
		btnAESFile.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAESFile.setBounds(160, 26, 130, 45);
		panelTepe.add(btnAESFile);
		
		JPanel panelMain = new JPanel();
		panelMain.setBounds(10, 80, 590, 373);
		frmEncryptionTool.getContentPane().add(panelMain);
		panelMain.setLayout(new CardLayout(0, 0));
		
		JPanel panelAESMain = new JPanel();
		panelAESMain.setBackground(new Color(46, 59, 85));
		panelMain.add(panelAESMain, "panelAESMain");
		panelAESMain.setLayout(null);
		
		JPanel panelAESInner = new JPanel();
		panelAESInner.setBounds(100, 10, 480, 353);
		panelAESMain.add(panelAESInner);
		panelAESInner.setLayout(new CardLayout(0, 0));
		
		JPanel panelAESEncrypt = new JPanel();
		panelAESEncrypt.setBackground(new Color(248, 243, 239));
		panelAESInner.add(panelAESEncrypt, "panelAESEncrypt");
		panelAESEncrypt.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Encryption Key:");
		lblNewLabel.setForeground(new Color(46, 59, 86));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 17));
		lblNewLabel.setBounds(20, 10, 130, 30);
		panelAESEncrypt.add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("DialogInput", Font.PLAIN, 17));
		passwordField.setBounds(160, 10, 200, 30);
		panelAESEncrypt.add(passwordField);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(new Color(248, 243, 239));
		comboBox.setBackground(new Color(46, 59, 86));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"AES-128", "AES-192", "AES-256"}));
		comboBox.setFont(new Font("Dialog", Font.BOLD, 17));
		comboBox.setBounds(370, 10, 100, 30);
		panelAESEncrypt.add(comboBox);
		
		JCheckBox chckbxShowKey = new JCheckBox("Show Key");
		chckbxShowKey.setBackground(new Color(248, 243, 239));
		chckbxShowKey.setForeground(new Color(46, 59, 86));
		chckbxShowKey.setBounds(160, 46, 93, 21);
		panelAESEncrypt.add(chckbxShowKey);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(160, 73, 310, 80);
		panelAESEncrypt.add(scrollPane);
		
		JTextArea textEncryptInput = new JTextArea();
		scrollPane.setViewportView(textEncryptInput);

		
		JLabel lblYourText = new JLabel("Your Text:");
		lblYourText.setForeground(new Color(46, 59, 86));
		lblYourText.setFont(new Font("Dialog", Font.BOLD, 17));
		lblYourText.setBounds(20, 73, 130, 30);
		panelAESEncrypt.add(lblYourText);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(160, 203, 310, 120);
		panelAESEncrypt.add(scrollPane_1);
		
		JTextArea textEncryptOutput = new JTextArea();
		textEncryptOutput.setLineWrap(true);
		scrollPane_1.setViewportView(textEncryptOutput);
		
		
		

		JLabel lblEncryptedText = new JLabel("Encrypted Text:");
		lblEncryptedText.setForeground(new Color(46, 59, 86));
		lblEncryptedText.setFont(new Font("Dialog", Font.BOLD, 17));
		lblEncryptedText.setBounds(20, 203, 130, 30);
		panelAESEncrypt.add(lblEncryptedText);

		JButton btnNewButton = new JButton("Random");
		btnNewButton.setForeground(new Color(248, 243, 239));
		btnNewButton.setBackground(new Color(46, 59, 86));
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 11));
		btnNewButton.setBounds(280, 45, 80, 21);
		panelAESEncrypt.add(btnNewButton);
		
		JButton btnNewButton_3 = new JButton("Encrypt");
		

		btnNewButton_3.setForeground(new Color(248, 243, 239));
		btnNewButton_3.setBackground(new Color(46, 59, 86));
		btnNewButton_3.setFont(new Font("Dialog", Font.BOLD, 17));
		btnNewButton_3.setBounds(370, 163, 100, 30);
		panelAESEncrypt.add(btnNewButton_3);
		
		
		JPanel panelAESDecrypt = new JPanel();
		panelAESDecrypt.setBackground(new Color(248, 243, 239));
		panelAESInner.add(panelAESDecrypt, "panelAESDecrypt");
		panelAESDecrypt.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Decryption Key:");
		lblNewLabel_1.setForeground(new Color(46, 59, 86));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 17));
		lblNewLabel_1.setBounds(20, 10, 130, 30);
		panelAESDecrypt.add(lblNewLabel_1);
		
		passwordFieldAESDecrypt = new JPasswordField();
		passwordFieldAESDecrypt.setFont(new Font("DialogInput", Font.PLAIN, 17));
		passwordFieldAESDecrypt.setBounds(160, 10, 310, 30);
		panelAESDecrypt.add(passwordFieldAESDecrypt);
		
		JLabel lblYourText_1 = new JLabel("Your Text:");
		lblYourText_1.setForeground(new Color(46, 59, 86));
		lblYourText_1.setFont(new Font("Dialog", Font.BOLD, 17));
		lblYourText_1.setBounds(20, 73, 130, 30);
		panelAESDecrypt.add(lblYourText_1);
		
		JCheckBox chckbxShowKeyAESDecrypt = new JCheckBox("Show Key");
		chckbxShowKeyAESDecrypt.setBackground(new Color(248, 243, 239));
		chckbxShowKeyAESDecrypt.setForeground(new Color(46, 59, 86));
		chckbxShowKeyAESDecrypt.setBounds(160, 46, 93, 21);
		panelAESDecrypt.add(chckbxShowKeyAESDecrypt);
		
		JButton btnAESDecrypter = new JButton("Decrypt");
		btnAESDecrypter.setBackground(new Color(46, 59, 86));
		btnAESDecrypter.setForeground(new Color(248, 243, 239));
		btnAESDecrypter.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAESDecrypter.setBounds(370, 163, 100, 30);
		panelAESDecrypt.add(btnAESDecrypter);
		
		JLabel lblDecryptedText = new JLabel("Decrypted Text:");
		lblDecryptedText.setForeground(new Color(46, 59, 86));
		lblDecryptedText.setFont(new Font("Dialog", Font.BOLD, 17));
		lblDecryptedText.setBounds(20, 203, 130, 30);
		panelAESDecrypt.add(lblDecryptedText);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(160, 73, 310, 80);
		panelAESDecrypt.add(scrollPane_2);
		
		JTextArea textAreaDecryptInput = new JTextArea();
		scrollPane_2.setViewportView(textAreaDecryptInput);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(160, 203, 310, 120);
		panelAESDecrypt.add(scrollPane_3);
		
		JTextArea textAreaDecryptOutput = new JTextArea();
		scrollPane_3.setViewportView(textAreaDecryptOutput);
		
		JPanel panelAESFileMain = new JPanel();
		panelAESFileMain.setBackground(new Color(46, 59, 86));
		panelAESFileMain.setForeground(new Color(248, 243, 239));
		panelMain.add(panelAESFileMain, "panelAESFileMain");
		panelAESFileMain.setLayout(null);
		
		JPanel panelAESFileInner = new JPanel();
		panelAESFileInner.setBounds(100, 10, 480, 353);
		panelAESFileMain.add(panelAESFileInner);
		panelAESFileInner.setLayout(new CardLayout(0, 0));
		
		JPanel panelAESFileEncrypt = new JPanel();
		panelAESFileEncrypt.setLayout(null);
		panelAESFileEncrypt.setBackground(new Color(248, 243, 239));
		panelAESFileInner.add(panelAESFileEncrypt, "panelAESFileEncrypt");
		
		JLabel lblNewLabel_2 = new JLabel("Encryption Key:");
		lblNewLabel_2.setForeground(new Color(46, 59, 86));
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 17));
		lblNewLabel_2.setBounds(20, 10, 130, 30);
		panelAESFileEncrypt.add(lblNewLabel_2);
		
		passwordFieldAESFileEncrypt = new JPasswordField();
		passwordFieldAESFileEncrypt.setFont(new Font("DialogInput", Font.PLAIN, 17));
		passwordFieldAESFileEncrypt.setBounds(160, 10, 200, 30);
		panelAESFileEncrypt.add(passwordFieldAESFileEncrypt);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"AES-128", "AES-192", "AES-256"}));
		comboBox_1.setForeground(new Color(248, 243, 239));
		comboBox_1.setFont(new Font("Dialog", Font.BOLD, 17));
		comboBox_1.setBackground(new Color(46, 59, 86));
		comboBox_1.setBounds(370, 10, 100, 30);
		panelAESFileEncrypt.add(comboBox_1);
		
		JCheckBox chckbxShowKeyAESFileEncrypt = new JCheckBox("Show Key");
		chckbxShowKeyAESFileEncrypt.setBackground(new Color(248, 243, 239));
		chckbxShowKeyAESFileEncrypt.setForeground(new Color(46, 59, 86));
		chckbxShowKeyAESFileEncrypt.setBounds(160, 46, 93, 21);
		panelAESFileEncrypt.add(chckbxShowKeyAESFileEncrypt);
		
		JButton btnAESFileEncryptImport = new JButton("Import File");
		btnAESFileEncryptImport.setForeground(new Color(248, 243, 239));
		btnAESFileEncryptImport.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAESFileEncryptImport.setBackground(new Color(46, 59, 86));
		btnAESFileEncryptImport.setBounds(20, 100, 130, 30);
		panelAESFileEncrypt.add(btnAESFileEncryptImport);
		
		JButton btnNewButton_1 = new JButton("Random");
		btnNewButton_1.setForeground(new Color(248, 243, 239));
		btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 11));
		btnNewButton_1.setBackground(new Color(46, 59, 86));
		btnNewButton_1.setBounds(280, 45, 80, 21);
		panelAESFileEncrypt.add(btnNewButton_1);
		
		JButton btnAESFileEncryptFile = new JButton("Encrypt File");
		btnAESFileEncryptFile.setForeground(new Color(248, 243, 239));
		btnAESFileEncryptFile.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAESFileEncryptFile.setBackground(new Color(46, 59, 86));
		btnAESFileEncryptFile.setBounds(20, 140, 130, 30);
		panelAESFileEncrypt.add(btnAESFileEncryptFile);
		
		JButton btnAESFileEncryptExport = new JButton("Export File");
		btnAESFileEncryptExport.setForeground(new Color(248, 243, 239));
		btnAESFileEncryptExport.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAESFileEncryptExport.setBackground(new Color(46, 59, 86));
		btnAESFileEncryptExport.setBounds(20, 180, 130, 30);
		panelAESFileEncrypt.add(btnAESFileEncryptExport);
		
		JPanel panelAESFileDecrypt = new JPanel();
		panelAESFileDecrypt.setLayout(null);
		panelAESFileDecrypt.setBackground(new Color(248, 243, 239));
		panelAESFileInner.add(panelAESFileDecrypt, "panelAESFileDecrypt");
		
		JLabel lblNewLabel_1_1 = new JLabel("Decryption Key:");
		lblNewLabel_1_1.setForeground(new Color(46, 59, 86));
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 17));
		lblNewLabel_1_1.setBounds(20, 10, 130, 30);
		panelAESFileDecrypt.add(lblNewLabel_1_1);
		
		passwordFieldAESFileDecrypt = new JPasswordField();
		passwordFieldAESFileDecrypt.setFont(new Font("DialogInput", Font.PLAIN, 17));
		passwordFieldAESFileDecrypt.setBounds(160, 10, 310, 30);
		panelAESFileDecrypt.add(passwordFieldAESFileDecrypt);
		
		JCheckBox chckbxShowKeyAESFileDecrypt = new JCheckBox("Show Key");
		chckbxShowKeyAESFileDecrypt.setBackground(new Color(248, 243, 239));
		chckbxShowKeyAESFileDecrypt.setForeground(new Color(46, 59, 86));
		chckbxShowKeyAESFileDecrypt.setBounds(160, 46, 93, 21);
		panelAESFileDecrypt.add(chckbxShowKeyAESFileDecrypt);
		
		JButton btnAESFileDecryptExport = new JButton("Export File");
		btnAESFileDecryptExport.setForeground(new Color(248, 243, 239));
		btnAESFileDecryptExport.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAESFileDecryptExport.setBackground(new Color(46, 59, 86));
		btnAESFileDecryptExport.setBounds(20, 180, 130, 30);
		panelAESFileDecrypt.add(btnAESFileDecryptExport);
		
		JButton btnAESFileDecryptFile = new JButton("Decrypt File");
		btnAESFileDecryptFile.setForeground(new Color(248, 243, 239));
		btnAESFileDecryptFile.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAESFileDecryptFile.setBackground(new Color(46, 59, 86));
		btnAESFileDecryptFile.setBounds(20, 140, 130, 30);
		panelAESFileDecrypt.add(btnAESFileDecryptFile);
		
		JButton btnAESFileDecryptImport = new JButton("Import File");
		btnAESFileDecryptImport.setForeground(new Color(248, 243, 239));
		btnAESFileDecryptImport.setFont(new Font("Dialog", Font.BOLD, 17));
		btnAESFileDecryptImport.setBackground(new Color(46, 59, 86));
		btnAESFileDecryptImport.setBounds(20, 100, 130, 30);
		panelAESFileDecrypt.add(btnAESFileDecryptImport);
		
		JButton btnAESFileEncrypt = new JButton("Encrypt");
		btnAESFileEncrypt.setForeground(new Color(46, 59, 85));
		btnAESFileEncrypt.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAESFileEncrypt.setBackground(new Color(248, 243, 239));
		btnAESFileEncrypt.setBounds(10, 20, 90, 30);
		panelAESFileMain.add(btnAESFileEncrypt);
		
		JButton btnAESFileDecrypt = new JButton("Decrypt");
		btnAESFileDecrypt.setForeground(new Color(248, 243, 239));
		btnAESFileDecrypt.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAESFileDecrypt.setBackground(new Color(46, 59, 85));
		btnAESFileDecrypt.setBounds(10, 60, 90, 30);
		panelAESFileMain.add(btnAESFileDecrypt);

		
		JButton btnAESEncrypt = new JButton("Encrypt");
		btnAESEncrypt.setForeground(new Color(46, 59, 85));
		btnAESEncrypt.setBackground(new Color(248, 243, 239));

		
		btnAESEncrypt.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAESEncrypt.setBounds(10, 20, 90, 30);
		panelAESMain.add(btnAESEncrypt);
		
		
		JButton btnAESDecrypt = new JButton("Decrypt");
		btnAESDecrypt.setBackground(new Color(46, 59, 85));
		btnAESDecrypt.setForeground(new Color(248, 243, 239));
		btnAESDecrypt.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAESDecrypt.setBounds(10, 60, 90, 30);
		panelAESMain.add(btnAESDecrypt);
		
        JButton btnAESCopy = new JButton("Copy");
        btnAESCopy.setForeground(new Color(248, 243, 239));
        btnAESCopy.setFont(new Font("Dialog", Font.BOLD, 11));
        btnAESCopy.setBackground(new Color(46, 59, 86));
        btnAESCopy.setBounds(370, 45, 65, 21);
        panelAESEncrypt.add(btnAESCopy);
        
    	JButton btnAESFileCopy = new JButton("Copy");
    	btnAESFileCopy.setForeground(new Color(248, 243, 239));
    	btnAESFileCopy.setFont(new Font("Dialog", Font.BOLD, 11));
    	btnAESFileCopy.setBackground(new Color(46, 59, 86));
    	btnAESFileCopy.setBounds(370, 45, 65, 21);
    	panelAESFileEncrypt.add(btnAESFileCopy);
		
		//Minor adjustments
		List<JButton> allButtons = Arrays.asList(btnAES, btnAESCopy, btnAESFileCopy, btnAESFile, btnAESEncrypt, btnAESDecrypt, btnAESDecrypter, btnNewButton_3, btnNewButton, btnAESFileEncryptImport, btnNewButton_1, btnAESFileEncryptFile, btnAESFileEncryptExport, btnAESFileDecryptImport, btnAESFileDecryptFile, btnAESFileDecryptExport, btnAESFileEncrypt, btnAESFileDecrypt);

		for (JButton btn : allButtons) {
		    btn.setFocusable(false);
		    btn.setRolloverEnabled(false);
		    
		}
		List<JButton> sideButtons = Arrays.asList(btnAESEncrypt, btnAESDecrypt, btnAESFileEncrypt, btnAESFileDecrypt, btnAESCopy, btnAESFileCopy);
		for(JButton btn : sideButtons) {
			btn.setBorder(null);
		}

		//GUI Navigation and Button color changes
		btnAES.addActionListener(e -> {
		switchPanelForMain(panelMain, "panelAESMain");
		btnAES.setBackground(koyu); btnAES.setForeground(acik);
		btnAESFile.setBackground(acik); btnAESFile.setForeground(koyu);
		});
		
		btnAESFile.addActionListener(e -> {
		switchPanelForMain(panelMain, "panelAESFileMain");
		btnAESFile.setBackground(koyu); btnAESFile.setForeground(acik);
		btnAES.setBackground(acik); btnAES.setForeground(koyu);
		});
		
		btnAESEncrypt.addActionListener(e -> {
		switchPanelForAES(panelAESInner, "panelAESEncrypt");
		btnAESEncrypt.setBackground(acik); btnAESEncrypt.setForeground(koyu);
		btnAESDecrypt.setBackground(koyu); btnAESDecrypt.setForeground(acik);
		});

		btnAESDecrypt.addActionListener(e -> {
		switchPanelForAES(panelAESInner, "panelAESDecrypt");
		btnAESDecrypt.setBackground(acik); btnAESDecrypt.setForeground(koyu);
		btnAESEncrypt.setBackground(koyu); btnAESEncrypt.setForeground(acik);
		});
		btnAESFileEncrypt.addActionListener(e -> {
		switchPanelForAESFile(panelAESFileInner, "panelAESFileEncrypt");
		btnAESFileEncrypt.setBackground(acik); btnAESFileEncrypt.setForeground(koyu);
		btnAESFileDecrypt.setBackground(koyu); btnAESFileDecrypt.setForeground(acik);
		});
		btnAESFileDecrypt.addActionListener(e -> {
		switchPanelForAESFile(panelAESFileInner, "panelAESFileDecrypt");
		btnAESFileDecrypt.setBackground(acik); btnAESFileDecrypt.setForeground(koyu);
		btnAESFileEncrypt.setBackground(koyu); btnAESFileEncrypt.setForeground(acik);
		});
		
		
		//Random key generator for AES and AESFile Random button
		btnNewButton.addActionListener(e -> {
			try {
                int keyLength = parseKeyLength((String) comboBox.getSelectedItem());
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(keyLength);
                SecretKey secretKey = keyGen.generateKey();
                String generatedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
                passwordField.setText(generatedKey);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frmEncryptionTool, "Error generating key: " + ex.getMessage());
            }
        });
		
		btnNewButton_1.addActionListener(e -> {
			try {
                int keyLength = parseKeyLength((String) comboBox_1.getSelectedItem());
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(keyLength);
                SecretKey secretKey = keyGen.generateKey();
                String generatedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
                passwordFieldAESFileEncrypt.setText(generatedKey);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frmEncryptionTool, "Error generating key: " + ex.getMessage());
            }
        });
		
		// Encrypting for AES
		btnNewButton_3.addActionListener(e -> {		//AES Encrypt Button
		    try {
		        String text = textEncryptInput.getText(); 
		        String key = new String(passwordField.getPassword()); 
		        int keyLength = parseKeyLength((String) comboBox.getSelectedItem()); 
		        
		        byte[] decodedKey;
		        try {
		            decodedKey = Base64.getDecoder().decode(key);
		        } catch (IllegalArgumentException ex) {
		            JOptionPane.showMessageDialog(frmEncryptionTool, "Invalid key format. Please provide a Base64 encoded key.");
		            return;
		        }

		        if (decodedKey.length * 8 != keyLength) { 
		            JOptionPane.showMessageDialog(frmEncryptionTool, "Key length must be " + keyLength + " bits.");
		            return;
		        }

		        SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");
		        Cipher cipher = Cipher.getInstance("AES");
		        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
		        textEncryptOutput.setText(Base64.getEncoder().encodeToString(encryptedBytes));

		    } catch (Exception ex) {
		        ex.printStackTrace();
		        textEncryptOutput.setText("Error: " + ex.getMessage());
		    }
		});
		
		// Decrypting for AES
		btnAESDecrypter.addActionListener(e -> {
		    try {
		        String encryptedText = textAreaDecryptInput.getText(); 
		        String key = new String(passwordFieldAESDecrypt.getPassword());
		        int keyLength = parseKeyLength((String) comboBox.getSelectedItem()); 

		        byte[] decodedKey;
		        try {
		            decodedKey = Base64.getDecoder().decode(key);
		        } catch (IllegalArgumentException ex) {
		            JOptionPane.showMessageDialog(frmEncryptionTool, "Invalid key format. Please provide a Base64 encoded key.");
		            return;
		        }

		        if (decodedKey.length * 8 != keyLength) { 
		            JOptionPane.showMessageDialog(frmEncryptionTool, "Key length must be " + keyLength + " bits.");
		            return;
		        }

		        SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");
		        Cipher cipher = Cipher.getInstance("AES");
		        cipher.init(Cipher.DECRYPT_MODE, secretKey);
		        
		        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		        textAreaDecryptOutput.setText(new String(decryptedBytes));

		    } catch (Exception ex) {
		        ex.printStackTrace();
		        textAreaDecryptOutput.setText("Error: " + ex.getMessage());
		    }
		});
		

		
		//Encrypt File Import
		btnAESFileEncryptImport.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    int returnValue = fileChooser.showOpenDialog(frmEncryptionTool);
		    if (returnValue == JFileChooser.APPROVE_OPTION) {
		        inputFile = fileChooser.getSelectedFile();
		        JOptionPane.showMessageDialog(frmEncryptionTool, "File imported successfully: " + inputFile.getName());
		    }
		});
		//Decrypt File Import
		btnAESFileDecryptImport.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    int returnValue = fileChooser.showOpenDialog(frmEncryptionTool);
		    if (returnValue == JFileChooser.APPROVE_OPTION) {
		        inputFile = fileChooser.getSelectedFile();
		        JOptionPane.showMessageDialog(frmEncryptionTool, "File imported successfully: " + inputFile.getName());
		    }
		});
		//File encrypt button
		btnAESFileEncryptFile.addActionListener(e -> {
		    try {
		        if (inputFile == null) {
		            throw new Exception("No file selected for encryption!");
		        }
		        String key = new String(passwordFieldAESFileEncrypt.getPassword());
		        outputFile = new File(inputFile.getPath() + ".enc"); //Encrypted file name
		        encryptFile(key, inputFile, outputFile);

		        JOptionPane.showMessageDialog(frmEncryptionTool, "File encrypted successfully: " + outputFile.getName());
		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(frmEncryptionTool, "Error encrypting file: " + ex.getMessage());
		    }
		});
		//Encrypted file export
		btnAESFileEncryptExport.addActionListener(e -> {
		    if (outputFile == null) {
		        JOptionPane.showMessageDialog(frmEncryptionTool, "No encrypted file to export!");
		        return;
		    }
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Save Encrypted File");

		    int userSelection = fileChooser.showSaveDialog(frmEncryptionTool);
		    if (userSelection == JFileChooser.APPROVE_OPTION) {
		        File fileToSave = fileChooser.getSelectedFile();
		        try {
		            Files.copy(outputFile.toPath(), fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING); 
		            JOptionPane.showMessageDialog(frmEncryptionTool, "Encrypted file saved successfully: " + fileToSave.getName());
		        } catch (IOException ex) {
		            JOptionPane.showMessageDialog(frmEncryptionTool, "Error saving file: " + ex.getMessage());
		        }
		    }
		});
		//Decrypted file export
		btnAESFileDecryptExport.addActionListener(e -> {
		    if (outputFile == null) {
		        JOptionPane.showMessageDialog(frmEncryptionTool, "No encrypted file to export!");
		        return;
		    }
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Save Encrypted File");

		    int userSelection = fileChooser.showSaveDialog(frmEncryptionTool);
		    if (userSelection == JFileChooser.APPROVE_OPTION) {
		        File fileToSave = fileChooser.getSelectedFile();
		        try {
		            Files.copy(outputFile.toPath(), fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING); 
		            JOptionPane.showMessageDialog(frmEncryptionTool, "Encrypted file saved successfully: " + fileToSave.getName());
		        } catch (IOException ex) {
		            JOptionPane.showMessageDialog(frmEncryptionTool, "Error saving file: " + ex.getMessage());
		        }
		    }
		});


		btnAESFileDecryptFile.addActionListener(e -> {
		    try {
		        if (inputFile == null) {
		            throw new Exception("No file selected for decryption!");
		        }
		        String key = new String(passwordFieldAESFileDecrypt.getPassword());
		        File decryptedFile = new File(inputFile.getPath().replace(".enc", ".dec")); // Decrypted file name 
		        decryptFile(key, inputFile, decryptedFile);

		        JOptionPane.showMessageDialog(frmEncryptionTool, "File decrypted successfully: " + decryptedFile.getName());
		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(frmEncryptionTool, "Error decrypting file: " + ex.getMessage());
		    }
		});






				
		//Action listeners for Key showing
		chckbxShowKey.addActionListener(e -> {
            if (chckbxShowKey.isSelected()) {
                passwordField.setEchoChar((char) 0); 
            } else {
                passwordField.setEchoChar('*');
            }
        });
		chckbxShowKeyAESDecrypt.addActionListener(e -> {
            if (chckbxShowKeyAESDecrypt.isSelected()) {
                passwordFieldAESDecrypt.setEchoChar((char) 0); 
            } else {
            	passwordFieldAESDecrypt.setEchoChar('*'); 
            }
        });
		chckbxShowKeyAESFileEncrypt.addActionListener(e -> {
            if (chckbxShowKeyAESFileEncrypt.isSelected()) {
            	passwordFieldAESFileEncrypt.setEchoChar((char) 0); 
            } else {
            	passwordFieldAESFileEncrypt.setEchoChar('*'); 
            }
        });
		chckbxShowKeyAESFileDecrypt.addActionListener(e -> {
            if (chckbxShowKeyAESFileDecrypt.isSelected()) {
            	passwordFieldAESFileDecrypt.setEchoChar((char) 0); 
            } else {
            	passwordFieldAESFileDecrypt.setEchoChar('*'); 
            }
        });
		
		//Action listeners for copy buttons
		btnAESCopy.addActionListener(e -> {
            char[] password = passwordField.getPassword();
            String passwordText = new String(password);

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(passwordText);
            clipboard.setContents(selection, null);

            JOptionPane.showMessageDialog(frmEncryptionTool, "Password copied to clipboard!");
        });
		btnAESFileCopy.addActionListener(e -> {
            char[] password = passwordFieldAESFileEncrypt.getPassword();
            String passwordText = new String(password);

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(passwordText);
            clipboard.setContents(selection, null);

            JOptionPane.showMessageDialog(frmEncryptionTool, "Password copied to clipboard!");
        });
	}
		//Methods for GUI Navigation
	 private void switchPanelForMain(JPanel panelMain, String panelName) {
	        CardLayout cardLayout = (CardLayout) panelMain.getLayout();
	        cardLayout.show(panelMain, panelName);
	    }
	 private void switchPanelForAES(JPanel panelAESInner, String panelName) {
		 CardLayout cardLayoutAESInner = (CardLayout) panelAESInner.getLayout();
		 cardLayoutAESInner.show(panelAESInner, panelName);
	 }
	 private void switchPanelForAESFile(JPanel panelAESFileInner, String panelName) {
		 CardLayout cardLayoutAESFileInner = (CardLayout) panelAESFileInner.getLayout();
		 cardLayoutAESFileInner.show(panelAESFileInner, panelName);
	 }
	 	//Method for comboBox selection
	 private int parseKeyLength(String selectedItem) {
	        return Integer.parseInt(selectedItem.split("-")[1]);
	    }
	 
	 public void encryptFile(String key, File inputFile, File outputFile) throws Exception {
		    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
		    Cipher cipher = Cipher.getInstance("AES");
		    cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		    try (FileInputStream fis = new FileInputStream(inputFile);
		         FileOutputStream fos = new FileOutputStream(outputFile);
		         CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {
		        byte[] buffer = new byte[1024];
		        int bytesRead;
		        while ((bytesRead = fis.read(buffer)) != -1) {
		            cos.write(buffer, 0, bytesRead);
		        }
		    }
		}

	 public void decryptFile(String key, File inputFile, File outputFile) throws Exception {
		    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
		    Cipher cipher = Cipher.getInstance("AES");
		    cipher.init(Cipher.DECRYPT_MODE, secretKey);

		    try (FileInputStream fis = new FileInputStream(inputFile);
		         FileOutputStream fos = new FileOutputStream(outputFile);
		         CipherInputStream cis = new CipherInputStream(fis, cipher)) {
		        byte[] buffer = new byte[1024];
		        int bytesRead;
		        while ((bytesRead = cis.read(buffer)) != -1) {
		            fos.write(buffer, 0, bytesRead);
		        }
		    }
		}

}

