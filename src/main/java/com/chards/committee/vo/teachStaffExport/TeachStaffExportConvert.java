package com.chards.committee.vo.teachStaffExport;

import java.util.*;

/**
 * @author LiuSu
 * @create 2021/6/4 16:51
 */
public class TeachStaffExportConvert {

    public static List<List<Object>> getAwardsList(List<TeachStaffBasicExportVO> teachStaffBasicExportVOList){
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < teachStaffBasicExportVOList.size(); i++) {
            TeachStaffBasicExportVO teachStaffBasicExportVO = teachStaffBasicExportVOList.get(i);
            List<Object> data = new ArrayList<Object>();
            data.add(i+1);
            data.add(teachStaffBasicExportVO.getDepartment());
            data.add(teachStaffBasicExportVO.getName());
            List<String> awards= Arrays.asList(teachStaffBasicExportVO.getAwards().split("#"));
            for(String v : awards){
                String[] strings = v.split("%");
                for(String s: strings) {
                    if (s.equals("")){
                        s = "无";
                    }
                    data.add(s);
                }
            }
            list.add(data);
        }
        return list;
    }

    public static List<List<Object>> getQualificationCertificateList(List<TeachStaffBasicExportVO> teachStaffBasicExportVOList){
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < teachStaffBasicExportVOList.size(); i++) {
            TeachStaffBasicExportVO teachStaffBasicExportVO = teachStaffBasicExportVOList.get(i);
            List<Object> data = new ArrayList<Object>();
            data.add(i+1);
            data.add(teachStaffBasicExportVO.getDepartment());
            data.add(teachStaffBasicExportVO.getName());
            List<String> qualificationCertificate= Arrays.asList(teachStaffBasicExportVO.getAwards().split("#"));
            for(String v : qualificationCertificate){
                String[] strings = v.split("%");
                if (strings.length > 1){
                    String[] times = strings[0].split(",");
                    if (times.length == 1){
                        data.add(times[0]);
                        data.add("无");
                    }if (times.length > 1){
                        data.add(times[0]);
                        data.add(times[1]);
                    }else{
                        data.add("空");
                        data.add("空");
                    }
                    for(int j = 1; j < strings.length; j++) {
                        if (strings[j].equals("")){
                            strings[j] = "无";
                        }
                        data.add(strings[j]);
                    }
                }

            }
            list.add(data);
        }
        return list;
    }


}
