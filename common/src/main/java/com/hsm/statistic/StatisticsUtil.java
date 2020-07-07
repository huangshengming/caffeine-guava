package com.hsm.statistic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;

import java.io.PrintWriter;

public class StatisticsUtil {

    static public void printIndent(int indent, PrintWriter writer) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < indent; i++)
        {
            sb.append("    ");
        }
        writer.print(sb);
    }

    static public void printCacheStatsEnd(PrintWriter writer) {
        writer.println("</table><p>");
    }
    static public void printCacheStatsBegin(String name, PrintWriter writer) {
        writer.println("<table width='100%' border='1' align='center' cellpadding='2' cellspacing='1'>");
        writer.println(" <caption>" + name + "</caption>");
        writer.println("<tr bgcolor='#D1DDAA'>");
        writer.println("<td>name</td>");
        writer.println("<td>size</td>");
        writer.println("<td>requestCount</td>");
        writer.println("<td>evictionCount</td>");
        writer.println("<td>hitCount</td>");
        writer.println("<td>hitRate</td>");
        writer.println("<td>missCount</td>");
        writer.println("<td>missRate</td>");
        writer.println("<td>loadCount</td>");
        writer.println("<td>averageLoadPenalty(ms)</td>");
        writer.println("<td>totalLoadTime(ms)</td>");
        writer.println("<td>loadExceptionCount</td>");
        writer.println("<td>loadExceptionRate</td>");
        writer.println("<td>loadSuccessCount</td>");
        writer.println("</tr>");
    }

    static public void printCacheStats(PrintWriter writer, String name, long size, CacheStats stats) {


        writer.println("<tr>");
        writer.println("<td>" + name + "</td>");
        writer.println("<td>" + size + "</td>");
        writer.println("<td>" + stats.requestCount() + "</td>");
        writer.println("<td>" + stats.evictionCount() + "</td>");
        writer.println("<td>" + stats.hitCount() + "</td>");
        writer.println("<td>" + stats.hitRate() + "</td>");
        writer.println("<td>" + stats.missCount() + "</td>");
        writer.println("<td>" + stats.missRate() + "</td>");
        writer.println("<td>" + stats.loadCount() + "</td>");
        writer.println("<td>" + stats.averageLoadPenalty() / 100000 + "</td>");
        writer.println("<td>" + stats.totalLoadTime() / 100000+ "</td>");
        writer.println("<td>" + stats.loadExceptionCount() + "</td>");
        writer.println("<td>" + stats.loadExceptionRate() + "</td>");
        writer.println("<td>" + stats.loadSuccessCount() + "</td>");
        writer.println("</tr>");

    }

    static public void printCacheStats(PrintWriter writer, String name, Cache<?, ?> cache) {
        printCacheStats(writer, name, cache.size(), cache.stats());
    }
}
