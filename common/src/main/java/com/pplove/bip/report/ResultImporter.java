package com.pplove.bip.report;



public class ResultImporter {
    private String table;
    private Engine storePlace;
    private String name;
    private String inputDir;

    public ResultImporter(SchedulableReport report, int date) {
        this.name = String.format("%s-%d-importer", report.getName(), date);
        this.storePlace = report.getStorePlace();
        this.table = report.getResultTable();
        this.inputDir = report.getHdfsResultDir(date);
    }

    public String getName() {
        return name;
    }

    public String getTable() {
        return table;
    }

    public Engine getStorePlace() {
        return storePlace;
    }

    public String getInputDir() {
        return inputDir;
    }
}
