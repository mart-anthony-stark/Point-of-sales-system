import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
;

public class Main extends JFrame implements KeyListener{

	private JPanel contentPane;
	static Connection cn;
	static Statement st;
	static ResultSet rss;
	static PreparedStatement pst;
	DefaultTableModel model;
	private JTextField productID;
	private JTextField productName;
	private JTextField productPrice;
	private JTextField productAmount;
	private JTable table;
	private JTextField totalF;
	private JTextField payF;
	private JTextField balanceF;
	private JTextArea reciept;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sales","root","");
			System.out.println("Connected!");
			st = cn.createStatement();
			JOptionPane.showMessageDialog(null, "Successfully Connected!");
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Not Connected!\nMake sure that you are connected to the database");
			JOptionPane.showMessageDialog(null, "Not Connected!\nMake sure that you are connected to the database", "Point of Sales System", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		Connection cn;
		Statement st;
		ResultSet rss;
		PreparedStatement pst;
		
		setFocusable(true);
		
		setType(Type.NORMAL);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 819, 521);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Point of Sales System", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBackground(Color.BLUE);
		panel.setBounds(10, 33, 535, 108);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Product ID");
		lblNewLabel.setBounds(10, 33, 70, 19);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblNewLabel);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setBounds(116, 33, 93, 19);
		lblProductName.setForeground(Color.WHITE);
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblProductName);
		
		JLabel lblQty = new JLabel("Qty.");
		lblQty.setBounds(348, 33, 29, 19);
		lblQty.setForeground(Color.WHITE);
		lblQty.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblQty);
		
		JSpinner quantity = new JSpinner();
		quantity.setBounds(348, 69, 29, 20);
		quantity.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int qty = Integer.parseInt(quantity.getValue().toString());
				int amount = Integer.parseInt(productPrice.getText())*qty;
				String amt = Integer.toString(amount);
				
				
				productAmount.setText(amt);
			}
		});
		panel.add(quantity);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(246, 33, 30, 19);
		lblPrice.setForeground(Color.WHITE);
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblPrice);
		
		JLabel amount = new JLabel("Amount");
		amount.setBounds(420, 33, 79, 19);
		amount.setForeground(Color.WHITE);
		amount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(amount);
		
		productID = new JTextField();
		productID.setForeground(Color.WHITE);
		productID.setBackground(Color.BLACK);
		productID.setBounds(10, 69, 86, 20);
		productID.requestFocus();
		productID.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						String pID = productID.getText();
						
						try {
							Class.forName("com.mysql.jdbc.Driver");
							Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sales", "root","");
							System.out.println("Yes?!");
							PreparedStatement pst = con.prepareStatement("SELECT * FROM products WHERE id=?");
							pst.setString(1, pID);
							System.out.println(pst.toString());
							ResultSet rss = pst.executeQuery();
							
							if(rss.next()==false) {
								JOptionPane.showMessageDialog(null, "Invalid product code", "Database", JOptionPane.WARNING_MESSAGE);
							}else {
								System.out.println("Product name: " + rss.getString("product"));
								System.out.println("Product price: " + rss.getString("price"));
								
								String pname = rss.getString("product");
								String pprice = rss.getString("price");
								
								productName.setText(pname);
								productPrice.setText(pprice);
								
							}
							
							
							
						}catch(Exception o) {
							o.printStackTrace();
						}
					}
			}
		});
		panel.add(productID);
		productID.setColumns(10);
		
		productName = new JTextField();
		productName.setColumns(10);
		productName.setBounds(116, 69, 86, 20);
		panel.add(productName);
		
		productPrice = new JTextField();
		productPrice.setColumns(10);
		productPrice.setBounds(226, 69, 86, 20);
		panel.add(productPrice);
		
		productAmount = new JTextField();
		productAmount.setColumns(10);
		productAmount.setBounds(420, 69, 86, 20);
		panel.add(productAmount);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 182, 535, 212);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Price", "Qty", "Amount"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
	
		
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBounds(456, 152, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model2 = (DefaultTableModel)table.getModel();
				Object[] row = {
						productID.getText(),
						productName.getText(),
						productPrice.getText(),
						quantity.getValue().toString(),
						productAmount.getText()
				};
				model2.addRow(row);
				
				
				//GETTING THE SUM OF AMOUNT IN EVERY ROW OF TABLE
				int sum = 0;
				for(int i=0;i<table.getRowCount();i++) {
					sum+=Integer.parseInt(table.getValueAt(i, 4).toString());
				}
				totalF.setText(Integer.toString(sum));
				
				quantity.setValue(0);
				productAmount.setText("");
				productName.setText("");
				productID.setText("");
				productPrice.setText("");
				productID.requestFocus();
				
			}
		});
		
		
		
		contentPane.add(btnNewButton);
		
		
		
		
		
		JLabel lblNewLabel_1 = new JLabel("TOTAL");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(636, 11, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		totalF = new JTextField();
		totalF.setBounds(616, 36, 86, 20);
		contentPane.add(totalF);
		totalF.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("PAY");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setBounds(636, 67, 46, 14);
		contentPane.add(lblNewLabel_1_1);
		
		payF = new JTextField();
		payF.setColumns(10);
		payF.setBounds(616, 92, 86, 20);
		
		payF.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					int total = Integer.parseInt(totalF.getText());
					float payment = Float.parseFloat(payF.getText());
					float change = payment-total;
					
					balanceF.setText(Float.toString(change));
					
				}
			}
		});
		
		contentPane.add(payF);
		
		JLabel lblNewLabel_1_2 = new JLabel("BALANCE");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setBounds(636, 124, 66, 14);
		contentPane.add(lblNewLabel_1_2);
		
		balanceF = new JTextField();
		balanceF.setColumns(10);
		balanceF.setBounds(616, 149, 86, 20);		
		contentPane.add(balanceF);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(556, 180, 226, 268);
		contentPane.add(scrollPane_1);
		
		reciept = new JTextArea();
		scrollPane_1.setViewportView(reciept);
		
		JButton btnNewButton_1 = new JButton("RECEIPT");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(626, 459, 89, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String total = totalF.getText();
				String bal = balanceF.getText();
				String pay = payF.getText();
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				reciept.setText(reciept.getText()+"*****************************************************\n");
				reciept.setText(reciept.getText()+"                      R E C E I P T          \n");
				reciept.setText(reciept.getText()+"*****************************************************\n");
				reciept.setText(reciept.getText()+"\tProduct\t" + "Price\t" + "Amount\t\n");
				
				for(int i=0;i<model.getRowCount();i++) {
					String product = (String)table.getValueAt(i, 1);
					String price = (String)table.getValueAt(i, 2);
					String amt = (String)table.getValueAt(i, 4);
					reciept.setText(reciept.getText()+ product +"\t"+price+"\t"+amt+"\n");
				}
				reciept.setText(reciept.getText()+"\t\tTotal  :" + total + "\n");
				reciept.setText(reciept.getText()+"\t\tPayment:" + pay + "\n");
				reciept.setText(reciept.getText()+"\t\tChange :" + bal + "\n");
			}
		});
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("CLEAR");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirm = JOptionPane.showConfirmDialog(null, "Do you want to clear the system?");
				if(confirm==0) {
					reciept.setText("");
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					model.setRowCount(0);
					
					quantity.setValue(0);
					productAmount.setText("");
					productName.setText("");
					productID.setText("");
					productPrice.setText("");
					totalF.setText("");
					payF.setText("");
					balanceF.setText("");
				}
			
			}
		});
		btnNewButton_2.setBounds(424, 425, 89, 23);
		contentPane.add(btnNewButton_2);
		model = new DefaultTableModel();
		Object column[] = {"id","name","price"};
		model.setColumnIdentifiers(column);
		
		
		
//		JButton addBtn = new JButton("Add");
//		addBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				row[0]=productID.getText();
//				row[1]=productName.getText();
//				row[2]=productPrice.getText();
//				model.addRow(row);
//			}
//		});
//		addBtn.setBounds(166, 398, 89, 23);
//		contentPane.add(addBtn);
//		printBtn.addActionListener(new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("Total: "+total.getText());
//		}
//		});
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					System.exit(0);
					System.out.println("Exit");
				}
			
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
