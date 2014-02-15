package core;

//@ Project     : ProjectWaifu
//@ File Name   : Startup.java
//@ Date        : 2013.02.15.
//@ Author      : Giovanni Capuano <https://github.com/RoxasShadow>
//@ Copyright   : All rights reserved


public interface Startup {
  public boolean add()    throws Exception;
  public boolean remove() throws Exception;
  public boolean exists() throws Exception;
}
