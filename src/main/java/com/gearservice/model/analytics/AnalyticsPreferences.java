package com.gearservice.model.analytics;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AnalyticsPreferences {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private LocalDate findFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private LocalDate findTo;
    private String row;
    private String column;

    public LocalDate getFindFrom() {return findFrom;}
    public void setFindFrom(LocalDate findFrom) {this.findFrom = findFrom;}
    public LocalDate getFindTo() {return findTo;}
    public void setFindTo(LocalDate findTo) {this.findTo = findTo;}
    public String getRow() {return row;}
    public void setRow(String row) {this.row = row;}
    public String getColumn() {return column;}
    public void setColumn(String column) {this.column = column;}
}
