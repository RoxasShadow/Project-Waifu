package core;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Win32Exception;

//@ Project     : ProjectWaifu
//@ File Name   : WindowsStartup.java
//@ Date        : 2013.02.15.
//@ Author      : Giovanni Capuano <https://github.com/RoxasShadow>
//@ Copyright   : All rights reserved


public class WindowsStartup implements Startup {
  private String path;
  
  public WindowsStartup(String path) {
    this.path = path;
  }
  
  public boolean add()    throws Win32Exception {
    Advapi32Util.registrySetStringValue(HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", "Waifu", path);
    return exists();
  }
  
  public boolean remove() throws Win32Exception {
    Advapi32Util.registryDeleteKey(HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", "Waifu");
    return !exists();
  }
  
  public boolean exists() throws Win32Exception {
    String value = Advapi32Util.registryGetStringValue(HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", "Waifu");
    return value.equals(path);
  }
}
