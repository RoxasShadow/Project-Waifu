package core;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;

//  @ Project		: ProjectWaifu
//  @ File Name		: HighlightTracker.java
//  @ Date			: 2013.07.02.
//  @ Author		: csiki
//  @ Copyright		: All rights reserved


public class HighlightTracker extends Sensor implements Runnable, ClipboardOwner {
	
	public interface CustomUser32 extends StdCallLibrary {
        CustomUser32 INSTANCE = (CustomUser32) Native.loadLibrary("user32", CustomUser32.class);
        HWND GetForegroundWindow();
        void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);
    }
	
	private volatile String highlighted;
	private String previous;
	
    public HighlightTracker(String name) {
		super(name);
		this.highlighted = null;
		this.previous = null;
	}
    
    @Override
	public void run() {
    	
    	try {
			this.previous = this.getSelectedText(User32.INSTANCE, CustomUser32.INSTANCE);
		} catch (Exception e) {
			this.previous = null;
		}
    	
		while (this.turnedOn) {
			
			String selectedText = null;
			
			if (this.isThereAnySubs()) {
				try {
					selectedText = this.getSelectedText(User32.INSTANCE, CustomUser32.INSTANCE);
				} catch (Exception e) {
					e.printStackTrace();
					selectedText = null;
				}
			}
			
			// set highlighted
			if (selectedText != null && !selectedText.equals(this.previous)) {
				this.highlighted = selectedText;
				this.previous = selectedText;
				this.notifyAllSubs();
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    
    @Override
	public void turnOff() {
		this.turnedOn = false;
	}
    
    public String getHighlightedText() {
    	return this.highlighted;
    }
    
    private void controlC(CustomUser32 customUser32) {
        customUser32.keybd_event((byte) 0x11 /* VK_CONTROL*/, (byte) 0, 0, 0);
        customUser32.keybd_event((byte) 0x43 /* 'C' */, (byte) 0, 0, 0);
        customUser32.keybd_event((byte) 0x43 /* 'C' */, (byte) 0, 2 /* KEYEVENTF_KEYUP */, 0);
        customUser32.keybd_event((byte) 0x11 /* VK_CONTROL*/, (byte) 0, 2 /* KEYEVENTF_KEYUP */, 0);// 'Left Control Up
    }

    private String getClipboardText() throws Exception {
      Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
      return (t.isDataFlavorSupported(DataFlavor.stringFlavor)) ? t.getTransferData(DataFlavor.stringFlavor).toString() : null;
    }

    private void setClipboardText(String data) throws Exception {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data), (ClipboardOwner) this);
    }
    
    private String getSelectedText(User32 user32, CustomUser32 customUser32) throws Exception {
        
    	// get highlighted text
    	HWND hwnd = customUser32.GetForegroundWindow();
        char[] windowText = new char[512];
        user32.GetWindowText(hwnd, windowText, 512);
        
        String before = null;
        try {
        	before = getClipboardText();
        } catch (Exception c) {
        	before = "";
        }
        
        // emulate Ctrl C
        controlC(customUser32);
        
        // give it some time
        Thread.sleep(100);
        String highlightedText = getClipboardText();
        
        // restore what was previously in the clipboard
        setClipboardText(before);
        
        // only return the text if it's not equal to the text that was on clipboard
        if (!before.equals(highlightedText)) {
        	return highlightedText;
        }
        
        return null;
    }

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// needed for implementing ClipboardOwner
	}
}
