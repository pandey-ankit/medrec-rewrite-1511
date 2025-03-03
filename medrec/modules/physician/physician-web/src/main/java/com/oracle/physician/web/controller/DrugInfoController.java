package com.oracle.physician.web.controller;

import com.oracle.medrec.facade.model.DrugInfo;
import com.oracle.physician.service.DrugService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @anthor Copyright (c) 2012, 2019, Oracle and/or its
 * affiliates. All rights reserved.
 */
@ApplicationScoped
public class DrugInfoController {

  @Named("drugList")
  @Produces
  private List<SelectItem> selectItems = new ArrayList<SelectItem>();

  @Inject
  private DrugService service;

  @PostConstruct
  public void initDrugInfoList() {
    List<DrugInfo> drugInfoList = service.getAllDrugsInfo();
    for (DrugInfo drugInfo : drugInfoList) {
      selectItems.add(new SelectItem(drugInfo.getId() + "-" + drugInfo.getName()));
    }
  }

}
