package com.leonardo.demobase.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/23
 */

public class DBThreadPool extends ThreadPoolExecutor {

  private static final int COREPOOLSIZE = 3; //修饰符 static 应在final 前
  private static final int MAXIMUMPOOLSIZE = 10;
  private static final long KEEPALIVETIME = 0L;

  private DBThreadPool() {
    this(COREPOOLSIZE, MAXIMUMPOOLSIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>());
  }

  private DBThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
      BlockingQueue<Runnable> workQueue) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  private static ThreadPoolExecutor dbThreadPool;

  public static ThreadPoolExecutor getThreadPool() {
    if (null == dbThreadPool) {
      dbThreadPool = new DBThreadPool();
      return dbThreadPool;
    } else {
      return dbThreadPool;
    }
  }
}