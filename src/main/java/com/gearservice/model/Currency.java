package com.gearservice.model;

import com.gearservice.model.repositories.CurrencyRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.time.LocalDate;

@Entity
public class Currency {

    @Id
    private Long id;

    private float usd;
    private float uah;
    private float eur;
    private float rub;

    public Currency() {
        this.id = LocalDate.now().toEpochDay();
    }

    public Currency forToday() {
        this.id = LocalDate.now().toEpochDay();
        return this;
    }

    public Currency withRUB() {
        this.rub = 1F;
        return this;
    }

    public Currency getFromServer(String link, String tag) throws IOException {
        String[] elements = parseWebSite(link, tag);

        for(int i = 0; i < elements.length;) {
            switch (elements[i++]) {
                case "USD":
                    this.setUsd((Float.parseFloat(elements[i++]) + Float.parseFloat(elements[++i])) / 2); break;
                case "UAH":
                    this.setUah((Float.parseFloat(elements[i++]) + Float.parseFloat(elements[++i])) / 2); break;
                case "EUR":
                    this.setEur((Float.parseFloat(elements[i++]) + Float.parseFloat(elements[++i])) / 2); break;
            }
            i++;
        }
        return this;
    }

    private static String[] parseWebSite(String link, String tag) throws IOException {
        return Jsoup.connect(link)
                .get()
                .select(tag)
                .text()
                .split(" ");
    }

    public String getId() {return LocalDate.ofEpochDay(id).toString();}
    public void setId(String id) {this.id = LocalDate.parse(id).toEpochDay();}
    public float getUsd() {return usd;}
    public void setUsd(float usd) {this.usd = usd;}
    public float getUah() {return uah;}
    public void setUah(float uah) {this.uah = uah;}
    public float getEur() {return eur;}
    public void setEur(float eur) {this.eur = eur;}
    public float getRub() {return rub;}
    public void setRub(float rub) {this.rub = rub;}
}
