package core;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;

//  @ Project		  : ProjectWaifu
//  @ File Name		: KeyReader.java
//  @ Date			  : 2013.07.02.
//  @ Author		  : csiki
//  @ Modified by : Giovanni Capuano <https://github.com/RoxasShadow>
//  @ Copyright		: All rights reserved


public class KeyReader extends Sensor {
	
	volatile private int keyTyped;
	private GlobalKeyListener gkl;
	private KeyAdapter adapter;
	
	public KeyReader(String name) {
		super(name);
		this.keyTyped = 0;

		adapter = new KeyAdapter() {
      @Override public void keyPressed(KeyEvent event) {}
      
      @Override public void keyReleased(KeyEvent event) {
        int keyCode = event.getVirtualKeyCode();

        if (turnedOn && !(keyCode == KeyEvent.VK_C && event.isCtrlPressed()) && keyCode != 162) {
          //System.out.println("[keyTyped]: " + keyCode);
          keyTyped = keyCode;
          notifyAllSubs();
        }
      }
    };
    this.gkl = new GlobalKeyListener();
    
		this.gkl.addKeyListener(adapter);
	}
	
    public int getKeyTyped() {
    	return this.keyTyped;
    }
    
    @Override
    public void turnOff() {
    	this.turnedOn = false;
    	this.gkl.removeKeyListener(adapter);
    	this.gkl = null;
    }
}
