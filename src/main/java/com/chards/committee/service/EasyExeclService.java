package com.chards.committee.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author: chards
 * @Date: 12:03 2020/8/23
 */
@Service
@Slf4j
public class EasyExeclService {

    /**
     * 将execl数据写入到返回文件中
     *
     * @param response  请求的HttpServletResponse
     * @param filename  下载文件的filename （不要带中文）
     * @param data      写入的数据
     * @param dataClass 写入的xlsx head信息
     */
    public <T> void writeToResponse(HttpServletResponse response, String filename, List<T> data, Class<T> dataClass) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
            EasyExcel.write(outputStream, dataClass).sheet(filename).doWrite(data);
        } catch (Exception e) {
            log.error("写入到execl文件错误 : " + e.getMessage(), e);
        }
    }

    /**
     * 在同一个工作簿中写入多张表格
     */
    public <T> void writeMoreToResponse(HttpServletResponse response, HttpServletRequest request,String filename, List<T> data, Class<T> dataClass){
        String[] titles = {"基本信息","奖惩情况","培训经历","论文成果","课题研究","著作"};
        try {
            filename =  URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + filename + ".xlsx");
            request.getSession().setAttribute("exportIsEnd", "false");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 开始写入
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), dataClass).build();
            for (int i = 0; i < 6; i++) {
                // 下面可以指定head
                WriteSheet writeSheet = EasyExcel.writerSheet(i, titles[i]).build();
                excelWriter.write(data, writeSheet);
            }
            //不关闭会打不开excel
            excelWriter.finish();
            request.getSession().setAttribute("exportIsEnd", "true");
        } catch (Exception e) {
            log.error("写入到execl文件错误 : " + e.getMessage(), e);
        }
    }

}
