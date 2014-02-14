package core;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

//@ Project     : ProjectWaifu
//@ File Name   : Localization.java
//@ Date        : 2013.07.02.
//@ Author      : Giovanni Capuano <https://github.com/RoxasShadow>
//@ Copyright   : All rights reserved


public class Localization {
  private static final String         I18N_BUNDLE = "core.Resources";
  private static       ResourceBundle bundle      = null;
  public  static       Locale         locale      = Locale.getDefault();

  public static String getString(String key, Object... params) {
    try {
      if(bundle == null)
        bundle = ResourceBundle.getBundle(I18N_BUNDLE, locale);
      return getString(key, bundle, params);
    }
    catch(MissingResourceException e) {
      return "'" + I18N_BUNDLE + "': missing resource.";
    }
  }

  public static String getString(String key, Locale locale, Object... params) {
    try {
      if(bundle == null)
        bundle = ResourceBundle.getBundle(I18N_BUNDLE, locale);
      return getString(key, bundle, params);
    }
    catch(MissingResourceException e) {
      return "'" + I18N_BUNDLE + "': missing resource.";
    }
  }

  public static String getString(String key, ResourceBundle bundle, Object... params) {
    if(key == null || key.isEmpty())
      return "";
    
    try {
      return bundle.getString(key);
    }
    catch(MissingResourceException e) {
      return "'" + key + "' key not found.";
    }
  }
}