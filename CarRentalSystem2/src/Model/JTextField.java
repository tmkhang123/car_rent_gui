package Model;

import java.awt.Font;import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JTextField extends javax.swing.JTextField {
	
	public JTextField(int textSize) {
		super();
		setFont(new Font("SansSerif", Font.BOLD, textSize));
		setHorizontalAlignment(JLabel.CENTER);
		setBorder(null);
	}

}
