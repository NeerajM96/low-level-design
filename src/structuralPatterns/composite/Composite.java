package structuralPatterns.composite;
/*
* Composite Pattern: Allows us to compose objects into tree structures to represent part-whole hierarchies. We
* can treat a group of objects just like we would treat a single object.
* e.g. client should not be checking which is product and which is product bundle, rather they should treat both
* of them equally.
*
* When to use: i) For hierarchical structure like folders
* ii) You want to treat individual and groups in the same way eg file and folder as same
* iii) You want to avoid client side logic to differentiate leaf and composite e.g. show only name & size of
* file & folder.
*
* Cons: Violates SRP on scale
* e.g. cart on scale end up doing a lot of things which violates SRP.
*
* */

/*
 * ===============================================================================================================\
 * Version 2: composite pattern
 * ===============================================================================================================
 *
 * */

import java.util.ArrayList;
import java.util.List;

// 1) interface
interface CartItem{
    double getPrice();
    void display(String indent);
}

class Product implements CartItem{
    private String name;
    private double price;

    public Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Product: " + name + " - $" + price);
    }
}

class ProductBundle implements CartItem{
    private String bundleName;
    private List<CartItem> items = new ArrayList<>();

    public ProductBundle(String bundleName){
        this.bundleName = bundleName;
    }
    @Override
    public double getPrice() {
        double total = 0;
        for(CartItem item: items){
            total += item.getPrice();
        }
        return total;
    }

    public void addItem(CartItem product){
        items.add(product);
    }

    @Override
    public void display(String indent){
        System.out.println(indent + "Bundle: " + this.bundleName);
        for(CartItem item: items){
            item.display(indent + " ");
        }
    }
}

public class Composite{
    public static void main(String[] args){
        // individual items
        CartItem book = new Product("Book", 500.00);
        CartItem headphones = new Product("Headphones", 1500.00);
        CartItem pencil = new Product("Pencil", 15.00);
        CartItem charger = new Product("Charger", 2000.00);
        CartItem notebook = new Product("Notebook", 50.00);

        // Bundle iphone combo
        ProductBundle iphoneCombo = new ProductBundle("iPhone Combo");
        iphoneCombo.addItem(headphones);
        iphoneCombo.addItem(charger);

        // Bundle: School kit
        ProductBundle schoolKit = new ProductBundle("School Kit");
        schoolKit.addItem(notebook);
        schoolKit.addItem(book);
        schoolKit.addItem(pencil);

        // add to cart
        List<CartItem> cart = new ArrayList<>();
        cart.add(book);
        cart.add(iphoneCombo);
        cart.add(schoolKit);

        // Display cart
        double total = 0;
        System.out.println("Cart Details:\n");
        for(CartItem item: cart){
            total += item.getPrice();
            item.display(" ");
        }
        System.out.println("Total Price: " + total);

    }
}

/*
* ===============================================================================================================\
* Version 1: w/o composite pattern
* ===============================================================================================================
*
* This code doesn't treat both Product & ProductBundle uniformly and the logic always has to check which type
* we are working with.
*
* 1) instanceof is used repeatedly breaking polymorphism
* 2) Cart uses List<Object>, which(using Object, as it has no defined structure) is unsafe and violates abstraction
* 3) ProductBundle cannot contain another ProductBundle
* 4) Display and price logic are duplicated instead of unified.
*
* KIM: i) Even though you checked item instanceof Product, item is still declared as Object, so Java does not
* auto-convert it to Product
* ii) instanceof checks runtime type, but Java is statically typed — it needs to know the type at compile time.
*
* e.g. String str = (String) obj; obj is of type Object. So it won’t let you do obj.length(), even though at
* runtime it might be a String.
*
* */
/**
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Product{
    private String name;
    private double price;

    public Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void display(String indent){
        System.out.println(indent + "Product: " + this.name + " - $" + this.price);
    }
}

class ProductBundle{
    private String bundleName;
    private List<Product> products = new ArrayList<>();

    public ProductBundle(String bundleName){
        this.bundleName = bundleName;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public double getPrice(){
        double total = 0;
        for(Product product: products){
            total += product.getPrice();
        }
        return total;
    }

    public void display(String indent){
        System.out.println(indent + "Bundle: " + this.bundleName);
        for(Product product: products){
            product.display(indent + " ");
        }
    }
}

public class Composite {
    public static void main(String[] args) {
        // individual items
        Product book = new Product("Book", 500.00);
        Product headphones = new Product("Headphones", 1500.00);
        Product pencil = new Product("Pencil", 15.00);
        Product charger = new Product("Charger", 2000.00);
        Product notebook = new Product("Notebook", 50.00);

        // Bundle iphone combo
        ProductBundle iphoneCombo = new ProductBundle("iPhone Combo");
        iphoneCombo.addProduct(headphones);
        iphoneCombo.addProduct(charger);

        // Bundle: School kit
        ProductBundle schoolKit = new ProductBundle("School Kit");
        schoolKit.addProduct(notebook);
        schoolKit.addProduct(book);
        schoolKit.addProduct(pencil);

        // Add to cart logic
        List<Object> cart = new ArrayList<>();
        cart.add(book);
        cart.add(iphoneCombo);
        cart.add(schoolKit);

        // Display cart
        double total = 0;
        System.out.println("Cart Details:\n");

        for(Object item: cart){
            if(item instanceof Product){
                Product product = (Product) item;
                product.display(" ");
                total += product.getPrice();
            }
            else if(item instanceof ProductBundle){
                ProductBundle productBundle = (ProductBundle) item;
                productBundle.display(" ");
                total += productBundle.getPrice();
            }
        }

        System.out.println("\nTotal Price: $" + total);
    }
}
*/

