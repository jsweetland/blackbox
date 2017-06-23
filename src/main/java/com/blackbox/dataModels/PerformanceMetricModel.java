package com.blackbox.dataModels;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


public class PerformanceMetricModel {
    double pageLoadTime;
    double totalTime;
    double tableRowCount;
    double metricScore;

    // <editor-fold desc="Constructors">

    public PerformanceMetricModel() {}

    public PerformanceMetricModel(double newPageLoadTime, double newTotalTime, double newTableRowCount, double newMetricScore) {
        setPageLoadTime(newPageLoadTime);
        setTotalTime(newTotalTime);
        setTableRowCount(newTableRowCount);
        setMetricScore(newMetricScore);
    }

    public PerformanceMetricModel(double newTotalTime, double newMetricScore) {
        setTotalTime(newTotalTime);
        setMetricScore(newMetricScore);
    }

    public PerformanceMetricModel(PerformanceMetricModelBuilder builder) {
        setPageLoadTime(builder.nestedPageLoadTime);
        setTotalTime(builder.nestedTotalTime);
        setTableRowCount(builder.nestedTableRowCount);
        setMetricScore(builder.nestedMetricScore);
    }

    // </editor-fold>


    // <editor-fold desc="Getters and Setters">

    public void setPageLoadTime(double newPageLoadTime) {
        pageLoadTime = newPageLoadTime;
    }

    public double getPageLoadTime() {
        return pageLoadTime;
    }

    public void setTotalTime(double newTotalTime) {
        totalTime = newTotalTime;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTableRowCount(double newTableRowCount) {
        tableRowCount = newTableRowCount;
    }

    public double getTableRowCount() {
        return tableRowCount;
    }

    public void setMetricScore(double newMetricScore) {
        metricScore = newMetricScore;
    }

    public double getMetricScore() {
        return metricScore;
    }

    // </editor-fold>


    // <editor-fold desc="Object Conversion">

    public JsonObject asJsonObject() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("pageLoadTime", new JsonPrimitive(pageLoadTime));
        jsonObject.add("totalTime", new JsonPrimitive(totalTime));
        jsonObject.add("tableRowCount", new JsonPrimitive(tableRowCount));
        jsonObject.add("metricScore", new JsonPrimitive(metricScore));

        return jsonObject;
    }

    // </editor-fold>


    // <editor-fold desc="Builder">

    public static class PerformanceMetricModelBuilder {
        private double nestedPageLoadTime;
        private double nestedTotalTime;
        private double nestedTableRowCount;
        private double nestedMetricScore;

        public PerformanceMetricModelBuilder() {}

        public PerformanceMetricModelBuilder setPageLoadTime(double newPageLoadTime) {
            nestedPageLoadTime = newPageLoadTime;
            return this;
        }

        public PerformanceMetricModelBuilder setTotalTime(double newTotalTime) {
            nestedTotalTime = newTotalTime;
            return this;
        }

        public PerformanceMetricModelBuilder setTableRowCount(double newTableRowCount) {
            nestedTableRowCount = newTableRowCount;
            return this;
        }

        public PerformanceMetricModelBuilder setMetricScore(double newMetricScore) {
            nestedMetricScore = newMetricScore;
            return this;
        }

        public PerformanceMetricModel build() {
            return new PerformanceMetricModel(this);
        }
    }

    // </editor-fold>
}
