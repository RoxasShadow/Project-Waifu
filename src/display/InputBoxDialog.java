package display;

//@ Project			: ProjectWaifu
//@ File Name		: InputBoxDialog.java
//@ Date			: 2013.07.02.
//@ Author			: csiki
//@ Copyright		: All rights reserved

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import core.Localization;
import core.Settings;

public class InputBoxDialog extends JDialog {

	private static final long serialVersionUID = 27985878973149436L;
	private final JPanel buttonPane = new JPanel();
	private JFrame parentFrame;
	
	private final JButton cancelButton = new JButton(Localization.getString("cancel"));
	private final JButton okButton = new JButton(Localization.getString("ok"));
	private final JTextField textField = new JTextField();
	
	private int result = -1;
	private String input;
	private Settings settings;
	private final Action okAction = new OkAction();
	private final Action cancelAction = new CancelAction();
	
	public int getResult() {
		return this.result;
	}
	
	public String getInput() {
		return this.input;
	}

	/**
	 * Create the dialog.
	 */
	public InputBoxDialog(Settings settings, JFrame parentFrame) {
		super(parentFrame, true);
		this.parentFrame = parentFrame;
		this.settings = settings;
	}
	
	@Override
	public void paintComponents(Graphics g) {
		// position
		int offsetX = (this.settings.getCurrentSizing().cloudWidth - this.getSize().width) / 2;
		int offsetY = (this.settings.getCurrentSizing().cloudHeight - this.getSize().height) / 2 + 50;
						
		setBounds(parentFrame.getLocation().x + this.settings.getCurrentSizing().offsetInputDialogBoxX + offsetX,
				parentFrame.getLocation().y + this.settings.getCurrentSizing().offsetInputDialogBoxY + offsetY,
				this.getSize().width, this.getSize().height);
	}
	
	public void init() {
		textField.setColumns(10);
		this.setPreferredSize(new Dimension(150, 150));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().setOpaque(false);
		this.setUndecorated(true);
		this.getContentPane().setBackground(new Color (0, 0, 0, 0));
		this.setBackground(new Color (0, 0, 0, 0));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0};
		gridBagLayout.rowHeights = new int[]{10, 33, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		getContentPane().add(textField, gbc_textField);
		
		// clear
		this.okButton.setEnabled(true);
		this.cancelButton.setEnabled(true);
		this.textField.setText("");
		
		buttonPane.setOpaque(false);
		buttonPane.setBackground(new Color (0, 0, 0, 0));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		GridBagConstraints gbc_buttonPane = new GridBagConstraints();
		gbc_buttonPane.anchor = GridBagConstraints.NORTH;
		gbc_buttonPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonPane.gridx = 0;
		gbc_buttonPane.gridy = 1;
		getContentPane().add(buttonPane, gbc_buttonPane);
		okButton.setAction(okAction);
		
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		cancelButton.setAction(cancelAction);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		this.pack();
		
		// position
		int offsetX = (this.settings.getCurrentSizing().cloudWidth - this.getSize().width) / 2;
		int offsetY = (this.settings.getCurrentSizing().cloudHeight - this.getSize().height) / 2 + 50;
				
		setBounds(parentFrame.getLocation().x + this.settings.getCurrentSizing().offsetInputDialogBoxX + offsetX,
				parentFrame.getLocation().y + this.settings.getCurrentSizing().offsetInputDialogBoxY + offsetY,
				this.getSize().width, this.getSize().height);
		
		setVisible(true);
	}

	private class OkAction extends AbstractAction {

		private static final long serialVersionUID = 2718147188036426104L;

		public OkAction() {
			putValue(NAME, Localization.getString("ok"));
			putValue(SHORT_DESCRIPTION, Localization.getString("okDescription"));
		}
		
		public void actionPerformed(ActionEvent e) {
			this.setEnabled(false);
			result = JOptionPane.YES_OPTION;
			input = textField.getText();
			dispose();
		}
	}
	
	private class CancelAction extends AbstractAction {

		private static final long serialVersionUID = 5132265699678512529L;
		
		public CancelAction() {
			putValue(NAME, Localization.getString("cancel"));
      putValue(SHORT_DESCRIPTION, Localization.getString("cancelDescription"));
		}
		
		public void actionPerformed(ActionEvent e) {
			this.setEnabled(false);
			result = JOptionPane.CANCEL_OPTION;
			input = null;
			dispose();
		}
	}
}
