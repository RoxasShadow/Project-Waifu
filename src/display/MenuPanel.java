package display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import core.Localization;
import core.Settings;
import core.Sizing;
import main.WaifuBuilder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//  @ Project		: ProjectWaifu
//  @ File Name		: MenuPanel.java
//  @ Date			: 2013.07.02.
//  @ Author		: csiki
//  @ Copyright		: All rights reserved



public class MenuPanel extends JPanel {

	private static final long serialVersionUID = -951173760082267556L;
	
	private BufferedImage settingsIcon;
    private BufferedImage exitIcon;
    private WaifuBuilder exitInterface;
    private JPopupMenu popupMenu;
    private Settings settings;
    private float opacity;
    
    private CustomMenuItem skinOpMI;
    private CustomMenuItem settingsMI;
    private CustomMenuItem aiOpMI;
    
    SkinOptionsDialog skinOptionsDialog;
    AIOptionsDialog aiOptionsDialog;
    SettingsDialog settingsDialog;
    
    public MenuPanel(Settings settings, BufferedImage settingsIcon, BufferedImage exitIcon, WaifuBuilder exitInterface,
    		final SkinOptionsDialog skinOptionsDialog, final AIOptionsDialog aiOptionsDialog, final SettingsDialog settingsDialog) {
    	this.settings = settings;
    	this.settingsIcon = settingsIcon;
    	this.exitIcon = exitIcon;
    	this.exitInterface = exitInterface;
    	this.skinOptionsDialog = skinOptionsDialog;
		this.aiOptionsDialog = aiOptionsDialog;
		this.settingsDialog = settingsDialog;
		this.opacity = 0.2f;
    	
		Sizing currentSizing = this.settings.getCurrentSizing();
    	this.setPreferredSize(new Dimension(currentSizing.menuPanelWidth, currentSizing.menuPanelHeight));
    	
    	// popup menu
    	this.popupMenu = new JPopupMenu();
    	this.skinOpMI = new CustomMenuItem(currentSizing, Localization.getString("skinOptions"));
    	this.settingsMI = new CustomMenuItem(currentSizing, Localization.getString("settings"));
    	this.aiOpMI = new CustomMenuItem(currentSizing, Localization.getString("AIOptions"));
    	
    	this.popupMenu.add(this.skinOpMI);
    	this.popupMenu.add(this.settingsMI);
    	this.popupMenu.add(this.aiOpMI);
    	
    	// popup style
    	this.popupMenu.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    	
    	// popupmenu elements mouse listeners
    	this.skinOpMI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				skinOptionsDialog.showD();
			}
		});
    	
    	this.settingsMI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				settingsDialog.showD();
			}
		});
    	
    	this.aiOpMI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				aiOptionsDialog.showD();
			}
		});
    	
    	// mouse listener
    	addMouseListener(new MenuMouseListener(this, settingsIcon, exitIcon));
    }
    
    public void exitClicked() {
    	this.exitInterface.exit();
    }
    
    public void wrenchClicked() {
    	this.popupMenu.show(this, 0, this.settingsIcon.getHeight());
    	this.popupMenu.repaint();
    }
    
    public void increaseOpacity() {
    	this.opacity = 1f;
    	this.repaint();
    }
    
    public void decreaseOpacity() {
    	this.opacity = 0.2f;
    	this.repaint();
    }
    
    @Override
	public void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
    	this.setOpaque(false);
    	
    	// set opacity
    	int rule = AlphaComposite.SRC_OVER;
    	Composite comp = AlphaComposite.getInstance(rule, this.opacity);
    	g2.setComposite(comp);
    	
    	// draw icons
    	g2.drawImage(settingsIcon,
    			0, 0, settingsIcon.getWidth(), settingsIcon.getHeight(),
    			0, 0, settingsIcon.getWidth(), settingsIcon.getHeight(), null);
    	g2.drawImage(exitIcon,
    			settingsIcon.getWidth() + 5, 0, exitIcon.getWidth() + settingsIcon.getWidth() + 5, exitIcon.getHeight(),
    			0, 0, exitIcon.getWidth(), exitIcon.getHeight(), null);
    	
    	this.setBackground(new Color(0, 0, 0, this.opacity));
    }
}
