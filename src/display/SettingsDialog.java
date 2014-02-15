package display;

import javax.swing.JDialog;

import core.Localization;
import core.Serializer;
import core.Settings;
import core.Startup;
import core.WindowsStartup;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;


//  @ Project		: ProjectWaifu
//  @ File Name		: SettingsDialog.java
//  @ Date			: 2013.07.02.
//  @ Author		: csiki
//  @ Copyright		: All rights reserved


public class SettingsDialog extends JDialog {
	
	private static final long serialVersionUID = -6435994273787507937L;
	
	private Settings settings;
	private JFrame mainFrame;
	
	private final JPanel buttonPane = new JPanel();
	private final JButton cancelButton = new JButton(Localization.getString("cancel"));
	private final JButton okButton = new JButton(Localization.getString("ok"));
	
	//private int result = -1;
	private final Action okAction = new OkAction();
	private final Action cancelAction = new CancelAction();
	private final JPanel settingsPanel = new JPanel();
	private final JLabel lblResolution = new JLabel(Localization.getString("resolution"));
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final JComboBox<String> resCombo = new JComboBox(Settings.sizes.keySet().toArray());
	private final JLabel lblRunOnStartup = new JLabel(Localization.getString("runOnStartup"));
	private final JCheckBox runOnSCheckb = new JCheckBox("");
	private final JLabel lblPlaceOnTop = new JLabel(Localization.getString("placeOnTop"));
	private final JCheckBox placeOnTCheckb = new JCheckBox("");
	
	public SettingsDialog(Settings settings, JFrame mf) {
		super(mf, true);
		
		this.mainFrame = mf;
		this.settings = settings;
		getContentPane().setLayout(new BorderLayout(0, 0));
		init();
	}
    
	public void init() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle(Localization.getString("settings"));
		
		// clear
		this.okButton.setEnabled(true);
		this.cancelButton.setEnabled(true);
		this.setBounds(this.settings.getPosX() + this.settings.getCurrentSizing().offsetInputDialogBoxX - 100,
				this.settings.getPosY() + this.settings.getCurrentSizing().offsetInputDialogBoxY - 100,
				300, 150);
		buttonPane.setOpaque(false);
		buttonPane.setBackground(new Color (0, 0, 0, 0));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		GridBagConstraints gbc_buttonPane = new GridBagConstraints();
		gbc_buttonPane.anchor = GridBagConstraints.NORTH;
		gbc_buttonPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonPane.gridx = 0;
		gbc_buttonPane.gridy = 1;
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		okButton.setAction(okAction);
		
		okButton.setActionCommand(Localization.getString("ok"));
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		cancelButton.setAction(cancelAction);
		cancelButton.setActionCommand(Localization.getString("cancel"));
		buttonPane.add(cancelButton);
		
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_settingsPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_settingsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_settingsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		settingsPanel.setLayout(gbl_settingsPanel);
		
		GridBagConstraints gbc_lblResolution = new GridBagConstraints();
		gbc_lblResolution.anchor = GridBagConstraints.EAST;
		gbc_lblResolution.insets = new Insets(10, 0, 5, 5);
		gbc_lblResolution.gridx = 1;
		gbc_lblResolution.gridy = 1;
		settingsPanel.add(lblResolution, gbc_lblResolution);
		
		GridBagConstraints gbc_resCombo = new GridBagConstraints();
		gbc_resCombo.insets = new Insets(10, 0, 5, 0);
		gbc_resCombo.anchor = GridBagConstraints.SOUTHWEST;
		gbc_resCombo.gridx = 3;
		gbc_resCombo.gridy = 1;
		settingsPanel.add(resCombo, gbc_resCombo);
		
		GridBagConstraints gbc_lblRunOnStartup = new GridBagConstraints();
		gbc_lblRunOnStartup.anchor = GridBagConstraints.EAST;
		gbc_lblRunOnStartup.insets = new Insets(0, 30, 5, 5);
		gbc_lblRunOnStartup.gridx = 1;
		gbc_lblRunOnStartup.gridy = 2;
		settingsPanel.add(lblRunOnStartup, gbc_lblRunOnStartup);
		
		GridBagConstraints gbc_runOnSCheckb = new GridBagConstraints();
		gbc_runOnSCheckb.insets = new Insets(0, 0, 5, 0);
		gbc_runOnSCheckb.anchor = GridBagConstraints.WEST;
		gbc_runOnSCheckb.gridx = 3;
		gbc_runOnSCheckb.gridy = 2;
		settingsPanel.add(runOnSCheckb, gbc_runOnSCheckb);
		
		GridBagConstraints gbc_lblPlaceOnTop = new GridBagConstraints();
		gbc_lblPlaceOnTop.anchor = GridBagConstraints.EAST;
		gbc_lblPlaceOnTop.insets = new Insets(0, 30, 0, 5);
		gbc_lblPlaceOnTop.gridx = 1;
		gbc_lblPlaceOnTop.gridy = 3;
		settingsPanel.add(lblPlaceOnTop, gbc_lblPlaceOnTop);
		
		GridBagConstraints gbc_placeOnTCheckb = new GridBagConstraints();
		gbc_placeOnTCheckb.anchor = GridBagConstraints.WEST;
		gbc_placeOnTCheckb.gridx = 3;
		gbc_placeOnTCheckb.gridy = 3;
		settingsPanel.add(placeOnTCheckb, gbc_placeOnTCheckb);
		
		this.setResizable(false);
	}
	
	public void showD() {
		// combo box select set
		this.resCombo.setSelectedItem(this.settings.getCurrentSizingName());
				
		// checkboxes set set
		this.placeOnTCheckb.setSelected(this.settings.isPlaceOnTop());
		this.runOnSCheckb.setSelected(this.settings.isRunOnStartUp());
		
		this.okAction.setEnabled(true);
		this.cancelAction.setEnabled(true);
		this.setVisible(true);
	}
	
	private class OkAction extends AbstractAction {

		private static final long serialVersionUID = 4623275957298708592L;

		public OkAction() {
			putValue(NAME, Localization.getString("ok"));
			putValue(SHORT_DESCRIPTION, Localization.getString("okDescription"));
		}
		
		public void actionPerformed(ActionEvent e) {
			this.setEnabled(false);
			//result = JOptionPane.YES_OPTION;
			
			// settings
			settings.setPlaceOnTop(placeOnTCheckb.isSelected());
			settings.setRunOnStartUp(runOnSCheckb.isSelected());
			settings.setCurrentSizing( (String) resCombo.getSelectedItem());
			
			// serialize settings
			Serializer.serialize(null, settings, "settings.waifu");
			
			// manipulate mainframe
			mainFrame.setAlwaysOnTop(placeOnTCheckb.isSelected());
			
			// run on startup
			if(System.getProperty("os.name").toLowerCase().indexOf("win") < 0)
			  JOptionPane.showMessageDialog(null, Localization.getString("thisFeatureIsSupportedOnlyOn").replace("{{os}}", "Windows"));
			else {
			  try {
			    Startup startup = new WindowsStartup();
  			  if(runOnSCheckb.isSelected()) {
			      if(!startup.add())
			        JOptionPane.showMessageDialog(null, Localization.getString("errorStartingOnBoot").replace("{{os}}", "Windows"));
  			  }
    			else {
    			  if(!startup.remove())
    			    JOptionPane.showMessageDialog(null, Localization.getString("errorRemovingFromBoot").replace("{{os}}", "Windows"));
    			}
			  }
        catch(Exception ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage());
        }
			}
			
			dispose();
		}
	}
	
	private class CancelAction extends AbstractAction {
		
		private static final long serialVersionUID = -6470808910237258184L;

		public CancelAction() {
			putValue(NAME, Localization.getString("cancel"));
      putValue(SHORT_DESCRIPTION, Localization.getString("cancelDescription"));
		}
		
		public void actionPerformed(ActionEvent e) {
			this.setEnabled(false);
			//result = JOptionPane.CANCEL_OPTION;
			dispose();
		}
	}
}
