package com.gearservice.model.cheque.samples;

/**
 * Enum Kits contains samples of kits
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
public enum Kits {
    kit1("АБ"),
    kit2("АБ 2 шт."),
    kit3("ГТ продавца"),
    kit4("ГТ производителя"),
    kit5("Документация"),
    kit6("Упаковка"),
    kit7("Кабель USB"),
    kit8("Кабель USB 2 шт."),
    kit9("Переходник USB"),
    kit10("Переходник USB 2 шт."),
    kit11("Вилка"),
    kit12("Вилка 2 шт."),
    kit13("Чехол"),
    kit14("Крышка"),
    kit15("SD-карта"),
    kit16("CD/DVD диск"),
    kit17("Драйвера"),
    kit18("З/У"),
    kit19("Кабель сетевой"),
    kit20("Сумка"),
    kit21("Чек"),
    kit22("Пакет"),
    kit23("Копия ГТ"),
    kit24("Копия чека"),
    kit25("Картридж"),
    kit26("Картридж 2 шт."),
    kit27("Кабель VGA"),
    kit28("Кабель DVI"),
    kit29("Подставка"),
    kit30("Наушники"),
    kit31("Уплотнитель"),
    kit32("Автомобильное З/У"),
    kit33("Крепеж"),
    kit34("Стилус"),
    kit35("Батарейка"),
    kit36("Батарейка 2 шт."),
    kit37("Кабель ethernet"),
    kit38("Пенопласт"),
    kit39("Пульт ДУ"),
    kit40("Клавиатура"),
    kit41("Мышь");

    private final String kit;
    Kits(String s) {kit = s;}
    public boolean equalsName(String otherKit) {return (otherKit != null) && kit.equals(otherKit);}
    public String toString() {return this.kit;}
}
