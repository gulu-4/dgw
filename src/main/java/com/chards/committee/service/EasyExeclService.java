package com.chards.committee.service;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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

}
