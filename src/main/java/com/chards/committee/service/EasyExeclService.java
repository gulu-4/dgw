package com.chards.committee.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.chards.committee.vo.teachStaffExport.TeachStaffBasicExportVO;
import com.chards.committee.vo.teachStaffExport.TeachStaffExportConvert;
import com.chards.committee.vo.teachStaffExport.TeachStaffExportHead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
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
        String[] titles = {"基本信息","奖惩情况","培训经历","科研情况"};
        List<List<List<String>>> exportHead = new ArrayList<List<List<String>>>();
        exportHead.add(TeachStaffExportHead.ExcelHead0());
        exportHead.add(TeachStaffExportHead.ExcelHead1());
        exportHead.add(TeachStaffExportHead.ExcelHead2());
        exportHead.add(TeachStaffExportHead.ExcelHead3());
//        exportHead.add(TeachStaffExportHead.ExcelHead4());
//        exportHead.add(TeachStaffExportHead.ExcelHead5());
        try {
            filename =  URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + filename + ".xlsx");
            request.getSession().setAttribute("exportIsEnd", "false");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 开始写入
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            for (int i = 0; i < 4; i++) {
                // 建立表格
                WriteSheet writeSheet = EasyExcel.writerSheet(i, titles[i]).head(exportHead.get(i)).build();
                if (i == 0) {
                    excelWriter.write(data, writeSheet);
                }else{
                    //解析数据
                    switch (i){
                        case 1:
                            excelWriter.write(TeachStaffExportConvert.getAwardsList((List<TeachStaffBasicExportVO>) data), writeSheet);
                            break;
                        case 2:
                            excelWriter.write(TeachStaffExportConvert.getQualificationCertificateList((List<TeachStaffBasicExportVO>) data), writeSheet);
                            break;
                        case 3:
                            excelWriter.write(TeachStaffExportConvert.getResearchesList((List<TeachStaffBasicExportVO>) data), writeSheet);
                            break;
                    }
                }
            }
            //不关闭会打不开excel
            excelWriter.finish();
            request.getSession().setAttribute("exportIsEnd", "true");
        } catch (Exception e) {
            log.error("写入到execl文件错误 : " + e.getMessage(), e);
        }
    }

}
