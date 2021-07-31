package com.chards.committee.config;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.chards.committee.domain.ComprehensiveAssessment;
import com.chards.committee.service.ComprehensiveAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * 有个很重要的点DemoDataListener不能被Spring管理，要每次读取excel都要new，然后里面用到Spring可以构造方法传进去
 */
public class ExcelDataListener extends AnalysisEventListener<ComprehensiveAssessment> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelDataListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    private String year = null;

    List<ComprehensiveAssessment> dataList = new ArrayList<>();

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    //@Autowired
    //@Qualifier(value = "ComprehensiveAssessmentService")
    private ComprehensiveAssessmentService comprehensiveAssessmentService;

    public ExcelDataListener(){
    }

    public ExcelDataListener(ComprehensiveAssessmentService comprehensiveAssessmentService, String year){
        this.comprehensiveAssessmentService = comprehensiveAssessmentService;
        this.year = year;
    }

    /**
     * 这个每一条数据解析都会来调用
     * @param data
     * @param context
     */
    @Override
    public void invoke(ComprehensiveAssessment data, AnalysisContext context) {
        LOGGER.info("解析到一条数据：{}",data.toString());
        dataList.add(data);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (dataList.size() >= BATCH_COUNT){
            saveData();
            //存储完成，清理list
            dataList.clear();
        }
    }

    /**
     * 所有数据解析完成了都会来调用
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        dataList.clear();
        //LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        //LOGGER.info("{}条数据，开始存储数据库！", dataList.size());
        //System.out.println(dataList);
        int size = dataList.size();
        for (int i = 0; i < size; i++){
            dataList.get(i).setCreateTime(year);
        }
        comprehensiveAssessmentService.importComprehensiveAssessmentInfo(dataList);
        //LOGGER.info("存储数据库成功！");
    }
}
