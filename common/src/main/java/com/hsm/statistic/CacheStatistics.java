package com.hsm.statistic;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * 缓存信息统计模块
 * add to hsm 2018/08/08
 */
public enum CacheStatistics {
    INSTANCE;
    private List<IStatisticsPrinter> statPrinters = Collections.synchronizedList(new ArrayList<IStatisticsPrinter>());

    public void addPrinter(IStatisticsPrinter printer)
    {
        statPrinters.add(printer);
    }

    public void printAllStatistics(PrintWriter writer) {
        StatisticsUtil.printCacheStatsBegin("guava", writer);
        for (IStatisticsPrinter iStatisticsPrinter : statPrinters) {
            iStatisticsPrinter.printStatistics(writer);
        }

        StatisticsUtil.printCacheStatsEnd(writer);
    }
}
