package core;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Win32Exception;

//@ Project     : ProjectWaifu
//@ File Name   : WindowsStartup.java
//@ Date        : 2013.02.15.
//@ Author      : Giovanni Capuano <https://github.com/RoxasShadow>
//@ Copyright   : All rights reserved


public class WindowsStartup implements Startup {
  private String path, jar, bat;
  
  public WindowsStartup(String path) throws FileNotFoundException {
    this.path = path;
    jar  = new File(System.getProperty("java.class.path")).getAbsoluteFile().toString();
    bat  = path + File.separator + "waifu.bat";
  }
  
  public WindowsStartup()  throws FileNotFoundException {
    path = new File(System.getProperty("java.class.path")).getAbsoluteFile().getParent().toString();
    jar  = new File(System.getProperty("java.class.path")).getAbsoluteFile().toString();
    bat  = path + File.separator + "waifu.bat";
  }
  
  private void createBat() throws FileNotFoundException, IOException {
    File f = new File(bat);
    if(!f.exists()) {
      PrintWriter writer = new PrintWriter(bat);
      writer.println("cd "          + path);
      writer.println(path.charAt(0) + ":");
      writer.println("start \"\" \"javaw\" -jar "  + jar);
      writer.close();
    }
  }
  
  private void removeBat() {
    File f = new File(bat);
    if(f.exists())
      f.delete();
  }
  
  public boolean add()    throws Win32Exception, FileNotFoundException, IOException {
    createBat();
    Advapi32Util.registrySetStringValue(HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", "Waifu", bat);
    return exists();
  }
  
  public boolean remove() throws Win32Exception {
    removeBat();
    Advapi32Util.registryDeleteKey(HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", "Waifu");
    return !exists();
  }
  
  public boolean exists()  throws Win32Exception {
    String value = Advapi32Util.registryGetStringValue(HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", "Waifu");
    return value.equals(bat);
  }
}
