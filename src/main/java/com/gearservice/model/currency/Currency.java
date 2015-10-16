package com.gearservice.model.currency;

import org.jsoup.Jsoup;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Currency {

    @Id
    private Long id;

    private BigDecimal usd;
    private BigDecimal uah;
    private BigDecimal eur;
    private BigDecimal rub;

    public Currency() {
        this.id = LocalDate.now().toEpochDay();
    }

    public Currency forToday() {
        this.id = LocalDate.now().toEpochDay();
        return this;
    }

    public Currency withRUB() {
        this.rub = new BigDecimal("1");
        return this;
    }

    public Currency getFromServer(String link, String tag) {
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

    public String getId() {return LocalDate.ofEpochDay(id).toString();}
    public void setId(String id) {this.id = LocalDate.parse(id).toEpochDay();}
    public BigDecimal getUsd() {return usd;}
    public void setUsd(BigDecimal usd) {this.usd = usd;}
    public BigDecimal getUah() {return uah;}
    public void setUah(BigDecimal uah) {this.uah = uah;}
    public BigDecimal getEur() {return eur;}
    public void setEur(BigDecimal eur) {this.eur = eur;}
    public BigDecimal getRub() {return rub;}
    public void setRub(BigDecimal rub) {this.rub = rub;}
}
