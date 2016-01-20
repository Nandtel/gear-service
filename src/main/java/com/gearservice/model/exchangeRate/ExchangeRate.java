package com.gearservice.model.exchangeRate;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class ExchangeRate {

    @Id
    private String addDate;

    private BigDecimal usd;
    private BigDecimal uah;
    private BigDecimal eur;
    private BigDecimal rub;

    public ExchangeRate() {
        this.addDate = LocalDate.now().toString();
        this.rub = new BigDecimal("1");
    }

    public ExchangeRate getFromServer(String link, String tag) {
        String[] elements = parseWebSite(link, tag);

        for(int i = 0; i < elements.length;) {
            switch (elements[i++]) {
                case "USD":
                    this.setUsd(average(new BigDecimal(elements[i++]), new BigDecimal(elements[++i]))); break;
                case "UAH":
                    this.setUah(average(new BigDecimal(elements[i++]), new BigDecimal(elements[++i]))); break;
                case "EUR":
                    this.setEur(average(new BigDecimal(elements[i++]), new BigDecimal(elements[++i]))); break;
            }
            i++;
        }
        return this;
    }

    private static String[] parseWebSite(String link, String tag) {
        try {
            return Jsoup.connect(link)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36")
                    .cookie("realauth", "SvBD85dINu3")
                    .get()
                    .select(tag)
                    .text()
                    .split(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BigDecimal average(BigDecimal first, BigDecimal second) {
        BigDecimal two = new BigDecimal("2");
        return first.add(second).divide(two, 2, BigDecimal.ROUND_HALF_UP);
    }

    public String getAddDate() {return addDate;}
    public void setAddDate(String addDate) {this.addDate = addDate;}
    public BigDecimal getUsd() {return usd;}
    public void setUsd(BigDecimal usd) {this.usd = usd;}
    public BigDecimal getUah() {return uah;}
    public void setUah(BigDecimal uah) {this.uah = uah;}
    public BigDecimal getEur() {return eur;}
    public void setEur(BigDecimal eur) {this.eur = eur;}
    public BigDecimal getRub() {return rub;}
    public void setRub(BigDecimal rub) {this.rub = rub;}
}
