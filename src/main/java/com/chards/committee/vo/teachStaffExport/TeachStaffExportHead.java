package com.chards.committee.vo.teachStaffExport;

import java.util.*;

/**
 * @author LiuSu
 * @create 2021/6/4 11:34
 */
public class TeachStaffExportHead {

    public static List<List<String>> ExcelHead0() {
        List<List<String>> headList = new ArrayList();
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("序号");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("学院");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("姓名");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("工号");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("性别");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("民族");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("出生年月");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("政治面貌");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("入党时间");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("籍贯");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("工作时间");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("工作单位");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("财务编号");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("职级");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("职级首聘时间");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("职称");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("职称首聘时间");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("编制情况");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("办公电话");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("办公地点");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("手机");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("QQ");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("微信");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("邮箱");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("学历/学位");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("现任职务");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("婚姻状况");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("宿舍");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("家庭住址");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("特长");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（基本信息）");
            add("担任社会职务");
        }});
        return headList;
    }

    public static List<List<String>> ExcelHead1() {
        List<List<String>> headList = new ArrayList();

        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（奖惩情况）");
            add("序号");
            add("序号");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（奖惩情况）");
            add("学院");
            add("学院");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（奖惩情况）");
            add("姓名");
            add("姓名");
        }});
        for (int i = 1;i<31; i++) {
            for (int j = 0; j < 4; j++){
                int finalI = i;
                switch (j) {
                    case 0:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（奖惩情况）");
                            add("奖惩情况" + finalI);
                            add("获奖时间");
                        }});
                        break;
                    case 1:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（奖惩情况）");
                            add("奖惩情况" + finalI);
                            add("获奖级别");
                        }});
                        break;
                    case 2:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（奖惩情况）");
                            add("奖惩情况" + finalI);
                            add("获奖名称");
                        }});
                        break;
                    case 3:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（奖惩情况）");
                            add("奖惩情况" + finalI);
                            add("授予单位");
                        }});
                        break;
                }
            }
        }
        return headList;
    }

    public static List<List<String>> ExcelHead2() {
        List<List<String>> headList = new ArrayList();

        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（培训经历）");
            add("序号");
            add("序号");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（培训经历）");
            add("学院");
            add("学院");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（培训经历）");
            add("姓名");
            add("姓名");
        }});
        for (int i = 1;i<31; i++) {
            for (int j = 0; j < 6; j++){
                int finalI = i;
                switch (j) {
                    case 0:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（培训经历）");
                            add("培训经历" + finalI);
                            add("开始月份");
                        }});
                        break;
                    case 1:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（培训经历）");
                            add("培训经历" + finalI);
                            add("结束月份");
                        }});
                        break;
                    case 2:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（培训经历）");
                            add("培训经历" + finalI);
                            add("培训主题");
                        }});
                        break;
                    case 3:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（培训经历）");
                            add("培训经历" + finalI);
                            add("培训名称");
                        }});
                        break;
                    case 4:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（培训经历）");
                            add("培训经历" + finalI);
                            add("组织单位");
                        }});
                        break;
                    case 5:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（培训经历）");
                            add("培训经历" + finalI);
                            add("培训级别");
                        }});
                        break;
                }
            }
        }
        return headList;
    }

    public static List<List<String>> ExcelHead3() {
        List<List<String>> headList = new ArrayList();

        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（科研情况）");
            add("序号");
            add("序号");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（科研情况）");
            add("学院");
            add("学院");
        }});
        headList.add(new ArrayList() {{
            add("辅导员信息统计汇总表（科研情况）");
            add("姓名");
            add("姓名");
        }});
        for (int i = 1;i<21; i++) {
            for (int j = 0; j < 6; j++){
                int finalI = i;
                switch (j) {
                    case 0:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（科研情况）");
                            add("科研情况" + finalI);
                            add("时间");
                        }});
                        break;
                    case 1:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（科研情况）");
                            add("科研情况" + finalI);
                            add("成果类型");
                        }});
                        break;
                    case 2:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（科研情况）");
                            add("科研情况" + finalI);
                            add("成果名称");
                        }});
                        break;
                    case 3:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（科研情况）");
                            add("科研情况" + finalI);
                            add("成果级别");
                        }});
                        break;
                    case 4:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（科研情况）");
                            add("科研情况" + finalI);
                            add("作者位次");
                        }});
                        break;
                    case 5:
                        headList.add(new ArrayList() {{
                            add("辅导员信息统计汇总表（科研情况）");
                            add("科研情况" + finalI);
                            add("出版物名称/项目编号");
                        }});
                        break;
                }
            }
        }
        return headList;
    }

//    public static List<List<String>> ExcelHead4() {
//        List<List<String>> headList = new ArrayList();
//
//        headList.add(new ArrayList() {{
//            add("辅导员信息统计汇总表（课题研究）");
//            add("序号");
//            add("序号");
//        }});
//        headList.add(new ArrayList() {{
//            add("辅导员信息统计汇总表（课题研究）");
//            add("学院");
//            add("学院");
//        }});
//        headList.add(new ArrayList() {{
//            add("辅导员信息统计汇总表（课题研究）");
//            add("姓名");
//            add("姓名");
//        }});
//        for (int i = 1;i<31; i++) {
//            for (int j = 0; j < 6; j++){
//                int finalI = i;
//                switch (j) {
//                    case 0:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（课题研究）");
//                            add("课题研究" + finalI);
//                            add("立项时间");
//                        }});
//                        break;
//                    case 1:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（课题研究）");
//                            add("课题研究" + finalI);
//                            add("项目名称");
//                        }});
//                        break;
//                    case 2:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（课题研究）");
//                            add("课题研究" + finalI);
//                            add("作者位次");
//                        }});
//                        break;
//                    case 3:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（课题研究）");
//                            add("课题研究" + finalI);
//                            add("立项单位");
//                        }});
//                        break;
//                    case 4:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（课题研究）");
//                            add("课题研究" + finalI);
//                            add("项目编号");
//                        }});
//                        break;
//                    case 5:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（课题研究）");
//                            add("课题研究" + finalI);
//                            add("项目级别");
//                        }});
//                        break;
//                }
//            }
//        }
//        return headList;
//    }
//
//    public static List<List<String>> ExcelHead5() {
//        List<List<String>> headList = new ArrayList();
//
//        headList.add(new ArrayList() {{
//            add("辅导员信息统计汇总表（著作）");
//            add("序号");
//            add("序号");
//        }});
//        headList.add(new ArrayList() {{
//            add("辅导员信息统计汇总表（著作）");
//            add("学院");
//            add("学院");
//        }});
//        headList.add(new ArrayList() {{
//            add("辅导员信息统计汇总表（著作）");
//            add("姓名");
//            add("姓名");
//        }});
//        for (int i = 1;i < 11; i++) {
//            for (int j = 0; j < 6; j++){
//                int finalI = i;
//                switch (j) {
//                    case 0:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（著作）");
//                            add("著作" + finalI);
//                            add("出版时间");
//                        }});
//                        break;
//                    case 1:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（著作）");
//                            add("著作" + finalI);
//                            add("著作名称");
//                        }});
//                        break;
//                    case 2:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（著作）");
//                            add("著作" + finalI);
//                            add("作者位次");
//                        }});
//                        break;
//                    case 3:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（著作）");
//                            add("著作" + finalI);
//                            add("出版社名称");
//                        }});
//                        break;
//                    case 4:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（著作）");
//                            add("著作" + finalI);
//                            add("字数");
//                        }});
//                        break;
//                    case 5:
//                        headList.add(new ArrayList() {{
//                            add("辅导员信息统计汇总表（著作）");
//                            add("著作" + finalI);
//                            add("著作获奖情况");
//                        }});
//                        break;
//                }
//            }
//        }
//        return headList;
//    }
}
