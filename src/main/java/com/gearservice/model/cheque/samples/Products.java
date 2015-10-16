package com.gearservice.model.cheque.samples;

/**
 * Enum Products contains samples of products
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
public enum Products {
    product1("Монитор"),
    product2("GPS навигатор"),
    product3("Нетбук"),
    product4("Ноутбук"),
    product5("Планшетный ПК"),
    product6("Видеорегистратор"),
    product7("Сетевое оборудование"),
    product8("Смартфон"),
    product9("Телефон"),
    product10("Системный блок"),
    product11("Видеокарта"),
    product12("Материнская плата"),
    product13("Акустика"),
    product14("Электронная книга"),
    product15("HDD"),
    product16("Принтер"),
    product17("Сканер"),
    product18("Картридж"),
    product19("ИБП"),
    product20("Аккумулятор"),
    product21("Пульт ДУ"),
    product22("Клавиатура"),
    product23("Мышь");

    private final String product;
    Products(String s) {product = s;}
    public boolean equalsName(String otherProduct) {return (otherProduct != null) && product.equals(otherProduct);}
    public String toString() {return this.product;}
}
