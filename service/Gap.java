package gap.service;

import gap.model.Product;

import java.util.Collection;

public interface Gap {
    public boolean addProduct();

    public void searchProduct();

    public boolean addProductToCart(Collection<Product> cart, Product product);
}
