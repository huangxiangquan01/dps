package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.handler.MergeStrategy;
import cn.xqhuang.dps.utils.EasyExcelUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @author huangxq
 * @description: TODO
 * @date 2023/3/2010:41
 */
public class TableExportService {

    private static Integer ROW_SIZE = 10000;
    private static Integer ROW_PAGE = 10;

    /*public String exportTable(ExportTable exportTable) throws Exception {
        StringBuffer path = new StringBuffer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StringBuffer sign = new StringBuffer();
        //redis key
        sign.append(exportTable.getId());
        try {
            // 用来记录需要为 行 列设置样式
            Map<String, Map<Integer, List<Map<Integer, ExportTable.ExportColumn.Font>>>> map = new HashMap<>();
            sign.append("#").append(String.join(",", fields.stream().map(e -> e.isShow() ? "true" : "false").collect(Collectors.toList())));
            setFontStyle(0, 0, exportTable.getFields(), map);
            // 获取表头长度
            int headRow = head.stream().max(Comparator.comparingInt(List::size)).get().size();

            // 数据量超过十万 则不带样式
            // 只处理表头：表头合并 表头隐藏 表头冻结
            if (rowCount * fields.size() > ROW_SIZE * 6.4) {
                map.put("cellStyle", null);
            }
            sign.append("#").append(exportTable.getStyle());
            // 数据量超过百万或者数据为空，只返回有表头得单元格
            if (rowCount == 0 || rowCount * fields.size() >= ROW_SIZE * 1500) {
                EasyExcel.write(outputStream)
                        // 这里放入动态头
                        .head(head).sheet("数据")
                        // 传入表头样式
                        .registerWriteHandler(EasyExcelUtils.getStyleStrategy())
                        // 当然这里数据也可以用 List<List<String>> 去传入
                        .doWrite(new LinkedList<>());
                byte[] bytes = outputStream.toByteArray();
                // 上传文件到FastDFS  返回上传路径
                return fastWrapper.uploadFile(bytes, bytes.length, "xlsx") + "?filename=" + fileName + ".xlsx";
            }
            sign.append("#").append(rowCount);
            String fieldSign = fields.stream().sorted(Comparator.comparing(ExportTable.ExportColumn::getId))
                    .map(e -> e.getId()).collect(Collectors.joining(","));
            sign.append("#").append(fieldSign);
            *//**
             * 相同的下载文件请求 直接返回
             * the redis combines with datasetId - filter - size of data - fields
             *//*
            // if (redisClientImpl.hasKey(sign.toString())) {
               // return redisClientImpl.get(sign.toString()).toString();
            // }
            *//**
             * 分sheet页
             * divide into sheets with 10M data per sheet
             *//*
            int sheetCount = (rowCount / (ROW_SIZE * ROW_PAGE)) + 1;
            String[] paths = new String[sheetCount];
            ByteArrayInputStream[] ins = new ByteArrayInputStream[sheetCount];

            CountDownLatch threadSignal = new CountDownLatch(sheetCount);
            for (int i = 0; i < sheetCount; i++) {
                int finalI = i;
                String finalTable = table;
                Datasource finalDs = ds;
                String finalOrder = order;
                int finalRowCount = rowCount;
                threadExecutor.submit(() -> {
                    // excel文件流
                    ByteArrayOutputStream singleOutputStream = new ByteArrayOutputStream();
                    ExcelWriter excelWriter = EasyExcel.write(singleOutputStream).build();
                    // 单sheet页写入数
                    int sheetThreadCount = finalI == (sheetCount - 1) ? (finalRowCount - finalI * (ROW_SIZE * ROW_PAGE)) / ROW_SIZE + 1 : ROW_PAGE;
                    CountDownLatch sheetThreadSignal = new CountDownLatch(sheetThreadCount);
                    for (int j = 0; j < sheetThreadCount; j++) {
                        int page = finalI * ROW_PAGE + j + 1;
                        // 最后一页数据
                        int pageSize = j == (sheetThreadCount - 1) && finalI == (sheetCount - 1) ? finalRowCount % ROW_SIZE : ROW_SIZE;
                        threadExecutor.submit(() -> {
                            try {
                                writeExcel(dataSetTableRequest, datasetTable, finalTable, qp,
                                        datasetTableFields, exportTable, page, pageSize, finalDs, datasourceProvider,
                                        fieldArray, fields, head, map, headRow, excelWriter, mergeIndex, finalOrder);
                                sheetThreadSignal.countDown();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    try {
                        sheetThreadSignal.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 关闭写入流
                    excelWriter.finish();
                    paths[finalI] = (finalI + 1) + "-" + fileName + ".xlsx";
                    // 单文件
                    if (sheetCount == 1) {
                        // xlsx
                        // 将sign存入redis并设置过期时间
                    }
                    threadSignal.countDown();
                });
            }
            threadSignal.await();

            if (sheetCount != 1) {
                ZipUtil.zip(outputStream, paths, ins);
                byte[] bytes = outputStream.toByteArray();
                // 上传文件到FastDFS  返回上传路径
                path.append(fastWrapper.uploadFile(bytes, bytes.length, "zip"))
                        .append("?filename=").append(fileName).append(".zip");
                // 将sign存入redis并设置过期时间
                redisClientImpl.set(sign.toString(), path.toString(), SYS_REDIS_EXPIRE_TIME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path.toString();
    }*/

    private void writeExcel(ExcelWriter excelWriter) {
        //数据查询
        // todo
        synchronized (excelWriter) {
            /*WriteSheet writeSheet = EasyExcel.writerSheet(0, "第" + 1 + "页数据")
                    // 这里放入动态头
                    .head(head)
                    //传入样式
                    .registerWriteHandler(EasyExcelUtils.getStyleStrategy())
                    .registerWriteHandler(new CellColorSheetWriteHandler(map, headRow))
                    .registerWriteHandler(new MergeStrategy(lists.size(), mergeIndex))
                    // 当然这里数据也可以用 List<List<String>> 去传入
                    .build();*/
            //excelWriter.write(lists, writeSheet);
        }
    }
}