package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.Car;
import Model.Database;
import Model.JButton;
import Model.JLabel;
import Model.JTextField;
import Model.Operation;
import Model.User;

public class UpdateCar implements Operation {
	
	private JTextField brand, model, color, year, price;
	private Database database;
	private JFrame frame;

	@Override
	public void operation(Database database, JFrame f, User user) {
		
		this.database = database;
		
		frame = new JFrame("Update Car");
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(f);
		frame.getContentPane().setBackground(new Color(250, 206, 27));
		frame.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Update Car", 35);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		frame.add(title, BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new GridLayout(7, 2, 15, 15));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		panel.add(new JLabel("ID:", 22));
		
		String[] ids = new String[] {" "};
		ArrayList<Integer> idsArray = new ArrayList<>();
		try {
			ResultSet rs0 = database.getStatement()
					.executeQuery("SELECT `ID`, `Available` FROM `cars`;");
			while (rs0.next()) {
				if (rs0.getInt("Available")<2) idsArray.add(rs0.getInt("ID"));
			}
		} catch (Exception e0) {
			JOptionPane.showMessageDialog(frame, e0.getMessage());
			frame.dispose();
		}
		
		ids = new String[idsArray.size()+1];
		ids[0] = " ";
		for (int i=1;i<=idsArray.size();i++) {
			ids[i] = String.valueOf(idsArray.get(i-1));
		}
		
		Model.JComboBox id = new Model.JComboBox(ids, 22);
		id.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateData(id.getSelectedItem().toString());
			}
		});
		panel.add(id);
		
		panel.add(new JLabel("Brand:", 22));
		
		brand = new JTextField(22);
		panel.add(brand);
		
		panel.add(new JLabel("Model:", 22));
		
		model = new JTextField(22);
		panel.add(model);
		
		panel.add(new JLabel("Color:", 22));
		
		color = new JTextField(22);
		panel.add(color);
		
		panel.add(new JLabel("Year:", 22));
		
		year = new JTextField(22);
		panel.add(year);
		
		panel.add(new JLabel("Price per Hour:", 22));
		
		price = new JTextField(22);
		panel.add(price);
		
		JButton cancel = new JButton("Cancel", 22);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(cancel);
		
		JButton confirm = new JButton("Confirm", 22);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (id.getSelectedItem().toString().equals(" ")) {
					JOptionPane.showMessageDialog(frame, "ID cannot be empty");
					return;
				}
				if (brand.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Brand cannot be empty");
					return;
				}
				if (model.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Model cannot be empty");
					return;
				}
				if (color.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Color cannot be empty");
					return;
				}
				if (year.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Year cannot be empty");
					return;
				}
				if (price.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Price cannot be empty");
					return;
				}
				int yearInt;
				double priceDoub;
				try {
					yearInt = Integer.parseInt(year.getText());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Year must be int");
					return;
				}
				try {
					priceDoub = Double.parseDouble(price.getText());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Price must be double");
					return;
				}
				
				try {
				
					String update = "UPDATE `cars` SET `Brand`='"+brand.getText()+"',`Model`='"+model.getText()+"',"
							+ "`Color`='"+color.getText()+"',`Year`='"+yearInt+"',`Price`='"+priceDoub+"' "
									+ "WHERE `ID` = '"+id.getSelectedItem().toString()+"';";
					
					database.getStatement().execute(update);
					JOptionPane.showMessageDialog(frame, "Car updated successfully");
					frame.dispose();
					} catch (SQLException e2) {
						JOptionPane.showMessageDialog(frame, e2.getMessage());
					}
				
			}
		});
		panel.add(confirm);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.requestFocus();
	}
	
	private void updateData(String ID) {
		if (ID.equals(" ")) {
			brand.setText("");
			model.setText("");
			color.setText("");
			year.setText("");
			price.setText("");
		} else {
			try {
				ResultSet rs1 = database.getStatement()
						.executeQuery("SELECT * FROM `cars` WHERE `ID` = '"+ID+"';");
				rs1.next();
				Car car = new Car();
				car.setID(rs1.getInt("ID"));
				brand.setText(rs1.getString("Brand"));
				model.setText(rs1.getString("Model"));
				color.setText(rs1.getString("Color"));
				year.setText(String.valueOf(rs1.getInt("Year")));
				price.setText(String.valueOf(rs1.getDouble("Price")));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(frame, e1.getMessage());
				frame.dispose();
			}
		}
	}

}
