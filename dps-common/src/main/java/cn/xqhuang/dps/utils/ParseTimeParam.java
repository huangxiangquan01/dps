package cn.xqhuang.dps.utils;

import com.aliyun.oss.ServiceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ParseTimeParam {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CorpConsRecordsQueryParam {
        private String corpId;
        private String queryDate;
        private List<String> queryDateList;
        private List<String> orderIds;
        //查询范围文案, 用于日志打印 (如: 2020-10-01 00:00:00 ~ 2020-10-05 23:59:59 或者 D02002, D20393, D39303)
        private String queryRangeStmt;
    }

    public static CorpConsRecordsQueryParam parseParam(String arg) {
        if (Objects.equals("syncAll", arg)) {
            return null;
        }
        final CorpConsRecordsQueryParam queryParam = new CorpConsRecordsQueryParam();
        if (StringUtils.isNotBlank(arg) && StringUtils.contains(arg, "|")) {
            final String[] args = StringUtils.split(arg, "|");
            if (args.length > 1) {
                queryParam.setCorpId(args[1]);
            }
            arg = args[0];
        }

        if (StringUtils.isBlank(arg)) {
            queryParam.setQueryDate(DateUtil.format(DateUtil.addDays(new Date(System.currentTimeMillis()), -1),
                    DateUtil.DATE_PATTERN_YYYY_MM_DD));
        } else if (StringUtils.contains(arg, "/")) {
            final String[] argEntries = StringUtils.split(arg, "/");
            if (1 == argEntries.length) {
                queryParam.setQueryDate(argEntries[0]);
            } else {
                final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern(DateUtil.DATE_PATTERN_YYYY_MM_DD);
                final LocalDate startDate;
                try {
                    startDate = LocalDate.parse(argEntries[0], YYYY_MM_DD);
                } catch (DateTimeParseException e) {
                    throw new ServiceException(String.format("不合法的日期格式: %s", argEntries[0]));
                }
                final LocalDate endDate;
                try {
                    endDate = LocalDate.parse(argEntries[1], YYYY_MM_DD);
                } catch (DateTimeParseException e) {
                    throw new ServiceException(String.format("不合法的日期格式: %s", argEntries[1]));
                }
                if (startDate.isBefore(endDate)) {
                    queryParam.setQueryDateList(getQueryDateList(startDate, endDate));
                } else {
                    queryParam.setQueryDateList(getQueryDateList(endDate, startDate));
                }
            }
        } else if (StringUtils.contains(arg, ",")) {
            queryParam.setOrderIds(Arrays.asList(StringUtils.split(arg, ",")));
        } else {
            queryParam.setQueryDate(arg);
        }

        if (!org.springframework.util.CollectionUtils.isEmpty(queryParam.orderIds)) {
            queryParam.setQueryRangeStmt(StringUtils.join(queryParam.orderIds, ", "));
        } else if (!org.springframework.util.CollectionUtils.isEmpty(queryParam.queryDateList)) {
            final String transStartTimeStr = DateUtil.format(DateUtil.startTimeOfGivenDate(DateUtil.parseDate(queryParam.queryDateList.get(0), DateUtil.DATE_PATTERN_YYYY_MM_DD)), DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
            final String transEndTimeStr = DateUtil.format(DateUtil.endTimeOfGivenDate(DateUtil.parseDate(queryParam.queryDateList.get(queryParam.queryDateList.size() - 1), DateUtil.DATE_PATTERN_YYYY_MM_DD)), DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
            queryParam.setQueryRangeStmt(String.format("%s ~ %s", transStartTimeStr, transEndTimeStr));
        } else if (Objects.nonNull(queryParam.queryDate)) {
            final Date transStartTime = DateUtil.startTimeOfGivenDate(DateUtil.parseDate(queryParam.queryDate, DateUtil.DATE_PATTERN_YYYY_MM_DD));
            final Date transEndTime = DateUtil.endTimeOfGivenDate(transStartTime, 0);
            queryParam.setQueryRangeStmt(String.format("%s ~ %s", DateUtil.format(transStartTime, DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS),
                    DateUtil.format(transEndTime, DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
        }
        return queryParam;
    }

    private static List<String> getQueryDateList(final LocalDate startDate, final LocalDate endDate) {
        final long daysGap = DateUtil.daysGap(startDate, endDate);
        final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern(DateUtil.DATE_PATTERN_YYYY_MM_DD);
        final List<String> queryDateList = new ArrayList<>();
        for (int i = 0; i <= daysGap; i++) {
            queryDateList.add(startDate.plusDays(i).format(YYYY_MM_DD));
        }
        return queryDateList;
    }
}
