package cn.xqhuang.dps.utils;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class IPUtil {

    private static final String UNKNOWN = "unknown";

    protected IPUtil() {
    }

    /**
     * 获取 IP地址
     * 使用 Nginx等反向代理软件， 则不能通过 request.getRemoteAddr()获取 IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * X-Forwarded-For中第一个非 unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    public static String getAddr(String ip) {
        String dbPath = "src/main/resources/ip2region/ip2region.xdb";
        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff;
        try {
            cBuff = Searcher.loadContentFromFile(dbPath);
        } catch (Exception e) {
            log.info("failed to load content from `%s`: %s\n", dbPath, e);
            return null;
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        Searcher searcher;
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.info("failed to create content cached searcher: %s\n", e);
            return null;
        }
        // 3、查询
        try {
            String region = searcher.searchByStr(ip);
            return region;
        } catch (Exception e) {
            log.info("failed to search(%s): %s\n", ip, e);
        }
        return null;
    }
}