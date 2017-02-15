package org.kuali.ole.report;

import org.kuali.ole.model.jpa.ReportEntity;
import org.kuali.ole.repo.jpa.ReportDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Date;
import java.util.List;

/**
 * Created by sheiks on 15/02/17.
 */
@Component
public class ReportGenerator {

    Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

    @Autowired
    ReportDetailRepository reportDetailRepository;

    @Autowired
    CSVSolrExceptionReportGenerator csvSolrExceptionReportGenerator;


    public String generateReport(String fileName, String reportType, Date from, Date to) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<ReportEntity> reportEntityList;
        reportEntityList = reportDetailRepository.findByFileAndTypeAndDateRange(fileName, reportType, from, to);
        stopWatch.stop();
        logger.info("Total Time taken to fetch Report Entities From DB : " + stopWatch.getTotalTimeSeconds());
        logger.info("Total Num of Report Entities Fetched From DB : " + reportEntityList.size());
        String generatedFileName = csvSolrExceptionReportGenerator.generateReport(fileName, reportEntityList);
        logger.info("The Generated File Name is : " + generatedFileName);
        return generatedFileName;
    }
}
