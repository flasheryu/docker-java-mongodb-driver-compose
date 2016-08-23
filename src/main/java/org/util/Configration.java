package org.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

public class Configration {

  private static Properties systemProperties;

  public static final String ENCRYPTED = "encrypted";

  private static Properties decodeProperties(Properties properties) {
    Properties result = new Properties();
    Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
    while (it.hasNext()) {
      Entry<Object, Object> entry = it.next();
      result.put(entry.getKey(),
          EncryptUtils.decode(entry.getValue().toString()));
    }
    return result;
  }

  public static void initialize() {
   initialize("startup.properties");
  }

  public static void initialize(InputStream stream)
  {
    systemProperties = new Properties();
    try {
        systemProperties.load(stream);
        if(systemProperties.containsKey(ENCRYPTED) &&
            getAsInt(ENCRYPTED) != 0) {
          systemProperties = decodeProperties(systemProperties);
        }
        systemProperties.remove(ENCRYPTED);
        stream.close();
      } catch (IOException e) {
        throw new RuntimeException(e);    // here, log utility is not ready yet.
      }
  }
  public static boolean initialize(String path) {
    try {
		InputStream stream = new FileInputStream(path);
		initialize(stream);
		return true;
	} catch (FileNotFoundException e) {
		//Logger.get().error(e);
	    System.out.println(e);
		return false;
	}

  }

  public static Properties getSystemProperty() {
    return systemProperties;
  }

  public static String get(String key) {

    return systemProperties.getProperty(key);
  }

  public static List<String> getAsList(String key){
      String config = Configration.get(key);
      if(config == null || config.length() ==0){
          return null;
      }

      String[] items = config.split(",");
      List<String> result = new ArrayList<String>();
      for(String item: items){
          if(item ==null || item.length() ==0){
              continue;
          }
          result.add(item.trim());
      }
      return result;
  }

  public static String get(String key, String defaultValue) {
    return StringUtils.getNonEmptyString(get(key), defaultValue);
  }

  public static int getAsInt(String key) {
    return Integer.parseInt(get(key));
  }

  public static int getAsInt(String key, int defaultValue) {
    try {
      return getAsInt(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static long getAsLong(String key) {
	  return Long.parseLong(get(key));
  }

  public static long getAsLong(String key, long defaultValue) {
    try {
      return getAsLong(key);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public static boolean getAsBoolean(String key) {
	  return Boolean.parseBoolean(get(key));
  }
}