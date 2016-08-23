package org.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.ManagementFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;

public class Logger {
  private static Log log;
  private static Log recoveryLog;
  private static Log accessLog;
  private static String pid;
  
  public static void initialize() {
    PropertyConfigurator.configure(Configration.getSystemProperty());
    log = LogFactory.getLog("root");
    if (BaseConfiguration.isRecoveryRequired()) {
      recoveryLog = LogFactory.getLog("recovery");
    }
    accessLog = LogFactory.getLog("access");

    pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void uncaughtException(Thread thread, Throwable t) {
        Logger.get().error("Unexpected exception occurred", t);
      }
    });
  }

  public static Log get() {
    MDC.put("PID", pid);
    return log;
  }
  
  public static Log getAccessLog() {
	return accessLog;
  }
  
  public static Log getRecoveryLog() {
    return recoveryLog;
  }
}