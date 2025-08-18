package structuralPatterns.decorator;

/*
* Decorator Pattern: allows behaviour to be added to individual objects, dynamically at runtime w/o affecting
* the behaviour of other objects from the same class.
*
* --> Every decorator needs to have a parent(concrete component) at the stack end such as BasePizza,
* MargheritaPizza etc. e.g. new Stuffed(new Cheese(new Olive(new MargheritaPizza)) here margherita
* which is a concrete component is at the stack end
*                       OR IN OTHER WORDS
* In the decorator pattern, every decorator requires a concrete component to wrap, such as
* BasePizza or MargheritaPizza
*
* Protected gives access to child classes (like ExtraCheese) while still restricting access from unrelated
* external classes.concrete decorators (like ExtraCheese, Olives, etc.) to have direct access to the pizza
* instance that they’re decorating.
*                   private is not ideal because it hides the field from child classes, public exposes it
* to everyone, while protected is ideal as it allows access in subclasses while keeping it hidden from unrelated
* external classes.
*
* super(pizza) in the concrete decorator constructor ensures the parent class (PizzaDecorator) has a reference to
* the base or previously decorated pizza.
*
* STEP: interface -> concrete class -> decorator abstract class -> concrete decorator
*
* */

// interface pizza
interface Pizza{
    String getDestrictption();
    double getPrice();
}

// concrete class
class MargheritaPizza implements Pizza{
    @Override
    public String getDestrictption() {
        return "Margherita Pizza";
    }

    @Override
    public double getPrice() {
        return 200.0;
    }
}

// concrete class
class PlainPizza implements Pizza{
    @Override
    public String getDestrictption() {
        return "Plain Pizza";
    }

    @Override
    public double getPrice() {
        return 100.0;
    }
}

// Decorator abstract class: creates base pizza
abstract class PizzaDecorator implements Pizza{
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
}

// concrete decorators
class ExtraCheese extends PizzaDecorator {
    public ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDestrictption() {
        return pizza.getDestrictption() + ", ExtraCheese";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 40.0;
    }
}

// concrete decorator
class Olives extends PizzaDecorator {
    public Olives(Pizza pizza) {
        super(pizza);
    }
    @Override
    public String getDestrictption() {
        return pizza.getDestrictption() + ", Olives";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 30.0;
    }
}

public class Decorator {
    public static void main(String[] args) {
        Pizza pizza = new ExtraCheese(new MargheritaPizza());
        Pizza pizza1 = new Olives(new PlainPizza());
        Pizza pizza2 = new ExtraCheese(pizza1);
        System.out.println(pizza.getDestrictption());
        System.out.println(pizza.getPrice());

        System.out.println(pizza2.getDestrictption());
        System.out.println(pizza2.getPrice());
    }
}
