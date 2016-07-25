package de.sda.einkaufsliste.service;

import java.util.ArrayList;
import java.util.List;

import de.sda.einkaufsliste.model.DBOpenHelper;
import de.sda.einkaufsliste.model.Product;

/**
 * Created by Dmitry Sokolyuk on 25.07.2016.
 */
public class FileServiceLogic {

    public static void exportProductsToSDCard(List<Product> products, DBOpenHelper db) {
//!!!
System.out.println("exportProductsToSDCard");
    }

    public static List<Product> importProductsFromSDCard(DBOpenHelper db) {
        List<Product> res = new ArrayList<>();
//!!!
System.out.println("importProductsFromSDCard");
        return res;
    }

}
