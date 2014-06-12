package com.cfranc.irc.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.activation.MailcapCommandMap;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class ConnectionPanel extends JPanel {

    private JTextField serverPortField;
    private JTextField serverField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JTextField pseudoField;
    
    

	public JTextField getServerPortField() {
		return serverPortField;
	}



	public JTextField getServerField() {
		return serverField;
	}



	/**
	 * Create the panel.
	 */
	public ConnectionPanel() {
	
	
	    JPanel connectionPanel = new JPanel(false);
		connectionPanel.setLayout(new BoxLayout(connectionPanel,
							BoxLayout.X_AXIS));
	
		JPanel namePanel = new JPanel(false);
		namePanel.setLayout(new GridLayout(0, 1));
	
		JPanel fieldPanel = new JPanel(false);
		fieldPanel.setLayout(new GridLayout(0, 1));
	    JLabel userNameLabel = new JLabel("User name: ", JLabel.RIGHT);
	    userNameField = new JTextField("guest");
	    JLabel passwordLabel = new JLabel("Password: ", JLabel.RIGHT);
	    JLabel serverLabel = new JLabel("Server name:", JLabel.RIGHT);
	    JLabel serverPortLabel = new JLabel("Port: ", JLabel.RIGHT);
	    passwordField = new JPasswordField("trustworthy");
	    serverField = new JTextField("localhost");
	    serverPortField = new JTextField("4567");
	    
	    JLabel lblNewLabel = new JLabel("pseudo");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    
	    pseudoField = new JTextField();
	    pseudoField.setColumns(10);
	    GroupLayout groupLayout = new GroupLayout(this);
	    groupLayout.setHorizontalGroup(
	    	groupLayout.createParallelGroup(Alignment.LEADING)
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addContainerGap(102, Short.MAX_VALUE)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	    				.addComponent(userNameLabel)
	    				.addGroup(groupLayout.createSequentialGroup()
	    					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	    						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
	    						.addComponent(passwordLabel)
	    						.addComponent(serverLabel)
	    						.addComponent(serverPortLabel))
	    					.addPreferredGap(ComponentPlacement.RELATED)))
	    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
	    				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
	    					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
	    						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
	    							.addComponent(namePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    							.addComponent(userNameField, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
	    						.addGap(26)
	    						.addComponent(fieldPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	    					.addComponent(pseudoField, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
	    				.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(serverField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(serverPortField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	    			.addGap(40))
	    );
	    groupLayout.setVerticalGroup(
	    	groupLayout.createParallelGroup(Alignment.LEADING)
	    		.addGroup(groupLayout.createSequentialGroup()
	    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
	    				.addGroup(groupLayout.createSequentialGroup()
	    					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    						.addComponent(userNameLabel)
	    						.addComponent(userNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	    					.addGap(3)
	    					.addComponent(namePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    					.addPreferredGap(ComponentPlacement.RELATED)
	    					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    						.addComponent(pseudoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    						.addComponent(lblNewLabel)))
	    				.addGroup(groupLayout.createSequentialGroup()
	    					.addGap(5)
	    					.addComponent(fieldPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
	    			.addPreferredGap(ComponentPlacement.UNRELATED)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(passwordLabel))
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(serverField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(serverLabel))
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(serverPortField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(serverPortLabel))
	    			.addContainerGap(68, Short.MAX_VALUE))
	    );
	    setLayout(groupLayout);	 
	    setPreferredSize(new Dimension(300,200));
	}

	public JTextField getUserNameField() {
		return userNameField;
	}
	public JPasswordField getPasswordField() {
		return passwordField;
	}
	public JTextField getPseudoField() {
		return pseudoField;
	}

}
