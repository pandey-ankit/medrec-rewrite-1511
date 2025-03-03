package com.oracle.medrec.service.batch;

import com.oracle.medrec.model.Record;
import com.oracle.medrec.service.RecordService;

import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.batch.runtime.BatchRuntime;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Reads qualified {@link com.oracle.medrec.model.Record}s from {@link com.oracle.medrec.service.RecordService} for
 * {@link com.oracle.medrec.service.batch.RecordItemProcessor}.
 *
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.<br>
 *         Created by xiaojwu on 14-2-13.
 */
@Named("RecordItemReader")
public class RecordItemReader extends AbstractItemReader {

  private static final Logger LOGGER = Logger.getLogger(RecordItemReader.class.getName());

  Iterator<Record> records;

  @Inject
  private JobContext jobContext;

  @Inject
  private RecordService service;

  @Override
  public void open(Serializable e) throws Exception {
    LOGGER.info("Init record item reader.");
    Properties jobParameters = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
    records = service.getRecordsByDateInterval(
        Date.valueOf(jobParameters.getProperty(BatchConstants.PARAM_START_DATE)),
        Date.valueOf(jobParameters.getProperty(BatchConstants.PARAM_END_DATE))).iterator();
    LOGGER.info("Init record item reader successfully");
  }

  @Override
  public Object readItem() throws Exception {
    return records.hasNext() ? records.next() : null;
  }
}
