package com.gearservice.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Class AnalyticsPreferences is model Entity, that not store in database
 * and consists request of frontend to make an analytical report.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

public class AnalyticsPreferences {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private LocalDate findFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private LocalDate findTo;
    private String row;
    private String column;
    private String fund;

    public LocalDate getFindFrom() {return findFrom;}
    public void setFindFrom(LocalDate findFrom) {this.findFrom = findFrom;}
    public LocalDate getFindTo() {return findTo;}
    public void setFindTo(LocalDate findTo) {this.findTo = findTo;}
    public String getRow() {return row;}
    public void setRow(String row) {this.row = row;}
    public String getColumn() {return column;}
    public void setColumn(String column) {this.column = column;}
    public String getFund() {return fund;}
    public void setFund(String fund) {this.fund = fund;}
}
