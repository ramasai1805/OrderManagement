package gap.service;

import gap.constants.Category;
import gap.constants.Color;
import gap.constants.Gender;
import gap.model.Product;

import java.util.Collection;
import java.util.Map;

public interface ProductFilter {


//    public List<T> getListFromMap(Map<K, V> products);

    public Map<Integer, Category> getCategoriesByGender(Gender gender);

    public Map<Integer, Color> getColorsByGenderAndCategory(Gender gender,Category category);

    public Map<Integer, Product> getProductsByGenderAndCategory(Gender gender, Category category);

    public Map<Integer, Product> getProductsByGenderAndColor(Collection<Product> productList, Color color);


//    public Map<K, V> getProductsByCategory(List<T> products, Category category);
//
//    public Map<K, V> getProductsByColor(List<T> products, Color color);
//
//    public Map<K, V> getProductsBySize(List<T> products, Size size);

}
