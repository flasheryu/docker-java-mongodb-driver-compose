package org.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaseConfiguration {

  private static boolean isRecoveryRequired = false;
  private static boolean inRecoveryMode = false;
  private static BufferedReader recoveryReader;

  public static void setIsRecoveryRequired(boolean isRecoveryRequired) {
    BaseConfiguration.isRecoveryRequired = isRecoveryRequired;
  }

  public static boolean isRecoveryRequired() {
    return isRecoveryRequired;
  }

  public static void setInRecoveryMode(String fileName) {
    inRecoveryMode = true;
    try {
      recoveryReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setInNormalMode() {
    inRecoveryMode = false;
    try {
      recoveryReader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static boolean isInRecoveryMode() {
    return inRecoveryMode;
  }

  public static BufferedReader getRecoveryReader() {
    return recoveryReader;
  }
}