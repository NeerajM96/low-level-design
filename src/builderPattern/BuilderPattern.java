// // version 1
//package builderPattern;
//
//import java.util.List;
//
//class BurgerMeal{
//    // Required
//    private String bunType;
//    private String patty;
//
//
//    // Optional
//    private List<String> toppings;
//    private String drink;
//
//    public BurgerMeal(String bunType, String patty, List<String> toppings, String drink) {
//        this.bunType = bunType;
//        this.patty = patty;
//        this.toppings = toppings;
//        this.drink = drink;
//    }
//}
//
//public class BuilderPattern {
//    public static void main(String[] args) {
//        // we are passing optional fields as null manually.
//        BurgerMeal burgerMeal = new BurgerMeal("wheat"," veg",null,null);
//        System.out.println(burgerMeal);
//    }
//}

// // =============================================================================================================================
// version 2
// // =============================================================================================================================
/*
* Builder Pattern: creational pattern used to construct complex objects step by step. It separates the construction of an
* object from its representation, allowing the same construction process to create different representations.
* Till now we were doing everything at constructor level, but now we will follow builder pattern and we will replace
* multiple parameters with single a single class i.e. use static inner class BurgerMealBuilder which will be inside
* the BurgerMeal class. We will define all the parameters of the BurgerMeal class inside BurgerMealBuilder class as well.
* and we will pass only the required parameters to the constructor and for the optional fields we can have separate setters.
*
* KIM: 1) We can have BurgerMealBuilder class outside of BurgerMeal class but it will be an anti-pattern because since we are
* building a builder for BurgerMeal, so its better if we have BurgerMealBuilder as an inner class so that the responsibility
* is for BurgerMeal and its not exposed outside but if other classes have to use BurgerMealBuilder then we can put it outside
* but preferred method is to put it inside the class which is going to use it.
*
* 2)Telescoping Constructor Anti-Pattern: Instead of using BuilderPattern, we could create multiple constructors with all the
* possible combinations of fields that we need to add into burger meal and this will help us to avoid passing null
* in constructor to optional fields but it  will increase an overhead of creating multiple constructors with a lot of
* combination of fields that user needs to pass for a meal and it Doesn't scale eg (bunType, patty,hasCheese), (bunType, patty),
* (bunType, patty,side), (bunType, patty,drink,hasCheese) etc.
*
* 3)In Python we can pass default parameters in constructor for optional fields but java doesn't have it. So, in python
* we can just create a BurgerMeal class without using BurgerMealBuilder class. BurgerMealBuilder is redundant unless you're
* doing something unusually complex
*
* 4)In java for production level code there are many internal libraries and annotations to generate builder pattern, so we don't
* have to write BuilderPattern code by ourself.
*
* Q) When to use builder pattern?
* Ans) i) Object has multiple fields which can be optional
* ii)Immutability is preferred i.e. can't change fields once an object is created e.g we can't update order(food) once it is prepared.
* iii) For readable, maintainable object creation.
*
* Q) When to avoid builder pattern?
* Ans) i) Class has only 1,2 or 3 fields.
* ii) We don't need object customization or immutability i.e. if we have 3 fields and we know we need to pass all these 3 fields,
* then there is no need to use builder pattern
*
* Pros: i) avoids constructor telescoping
*       ii) ensures immutability
*       iii) clean, readable object creation
*       iv) great for complex object creation(multiple optional fields)
*
* Cons: i) Slightly tough to set up
*       ii) overkill for small cases(for less no. of fields)
*       iii) Separate builder class needed.
*
* Real world example: Lombok, Cart( e.g. amazon/ flipkart cart as cart could be anything productId (required), name (required),
* quantity (optional, default = 1), price (optional), discountCode (optional), giftWrap (optional), deliveryDate (optional).)
* */
package builderPattern;

import java.util.List;

class BurgerMeal{
    // Required
    private final String bunType;
    private final String patty;


    // Optional
    //    hasCheese,toppings,side,drink, withCheese, withToppings, withSide, build: create BurgerMeal
    private final List<String> toppings;
    private final String drink;
    private final String side;
    private final boolean hasCheese;

    public BurgerMeal(BurgerMealBuilder builder) {
        this.bunType = builder.bunType;
        this.patty = builder.patty;
        this.toppings = builder.toppings;
        this.drink = builder.drink;
        this.side = builder.side;
        this.hasCheese = builder.hasCheese;
    }

    static class BurgerMealBuilder{
        private final String bunType;
        private final String patty;

        // Optional
        //    hasCheese,toppings,side,drink, withCheese, withToppings, withSide, build: create BurgerMeal
        private List<String> toppings;
        private String drink;
        private String side;
        private boolean hasCheese;

        public BurgerMealBuilder(String bunType, String patty) {
            this.bunType = bunType;
            this.patty = patty;
        }

        public BurgerMealBuilder withCheese(boolean hasCheese) {
            this.hasCheese = hasCheese;
            return this;
        }

        public BurgerMealBuilder withToppings(List<String> toppings) {
            this.toppings = toppings;
            return this;
        }

        public BurgerMealBuilder withDrink(String drink) {
            this.drink = drink;
            return this;
        }

        public BurgerMealBuilder withSide(String side) {
            this.side = side;
            return this;
        }

        public BurgerMeal build(){
            return new BurgerMeal(this);
        }

    }


}

public class BuilderPattern {
    public static void main(String[] args) {
        BurgerMeal burgerMeal = new BurgerMeal.BurgerMealBuilder("wheat","veg").build();
        BurgerMeal burgerMealWithCheese = new BurgerMeal.BurgerMealBuilder("whole wheat","soyabeen").withCheese(true).build();
        BurgerMeal burgerMealWithCheeseAndFries = new BurgerMeal.BurgerMealBuilder("whole wheat","soyabeen")
                .withSide("fries").withCheese(true).build();
        System.out.println(burgerMeal);
    }
}
