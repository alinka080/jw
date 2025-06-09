package org.example.jewelryshop.util;

import org.example.jewelryshop.models.products; // исправил имя класса
import java.util.HashMap;

public class Basket {
    // Карта: товар -> объект Item (с количеством и стоимостью)
    protected HashMap<products, Item> basket = new HashMap<>();

    /**
     * Получить корзину (словарь товаров и их Item)
     */
    public HashMap<products, Item> getBasket() {
        return basket;
    }

    /**
     * Очистить корзину
     */
    public void clearBasket() {
        basket.clear();
    }

    /**
     * Добавить товар в корзину
     * Если товар уже есть, увеличить количество на 1
     */
    public void addProductInBasket(products product, String ringSize) {
        if (basket.containsKey(product)) {
            Item item = basket.get(product);
            int newCount = item.getCount() + 1;
            double newTotal = product.getCost() * newCount;
            basket.put(product, new Item(product, newCount, newTotal, ringSize));
        } else {
            double price = product.getCost();
            basket.put(product, new Item(product, 1, price, ringSize));
        }
    }

    /**
     * Установить количество товара в корзине
     * Если количество <= 0, товар удаляется из корзины
     */
    public void setCount(products product, int count, String ringSize) {
        if (basket.containsKey(product)) {
            if (count <= 0) {
                basket.remove(product);
            } else {
                double total = product.getCost() * count;
                basket.put(product, new Item(product, count, total, ringSize));
            }
        }
    }

    /**
     * Удалить товар из корзины
     */
    public void deleteProductFromBasket(products product) {
        basket.remove(product);
    }

    /**
     * Получить общую стоимость корзины
     */
    public double getTotalCost() {
        double sum = 0;
        for (Item item : basket.values()) {
            sum += item.getTotal();
        }
        return sum;
    }

    /**
     * Получить количество разных товаров в корзине
     */
    public int getCount() {
        return basket.size();
    }

    /**
     * Проверить, что на складе у каждого товара не меньше 3 единиц
     */
    public boolean isOnStock() {
        for (products product : basket.keySet()) {
            if (product.getQuantityOfProducts() < 3) {
                return false;
            }
        }
        return true;
    }
}
