package com.ufo.learnchinese.utils;

public class Native
{
  static
  {
    System.loadLibrary("test");
  }
  
  public native String getName();
}
