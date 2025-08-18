/*
* Structural Pattern: How classes and objects are structured to form larger components. In doing so
* we make sure that the small pieces are decoupled(i.e. small pieces can exist independently, and they
* can be maintained and scaled independently)
*
* Adapter Pattern:
*
* Problem it solves: Suppose we were using PayU payment gateway(PayU is implementing PaymentGateway
* interface that we defined), and now we want to shift to RazorPay payment gateway and this gives
* us a class RazorPayAPI(method: MakePayment) but it is not using PaymentGateway interface and has
* a separate method for making payment(In PaymentGateway interface method was pay). Due to above
* reasons inside Checkout service we cannot pass RazorPay payment gateway because it is a third
* party API.
*           To solves this issue we would have to make changes at many places, but we shouldn't be
* doing these many changes, specially at client side or on the checkout service. ADAPTER pattern
* solves this and allows us to integrate RazorPay easily without touching any other portion of the
* code we only need to add an adapter class RazorPayAdapter that implements PaymentGateway and this
* adapter will internally call the makePayment method of RazorPay API.
*
* -->According to OOPS, We need to write code such that if tomorrow we need to change payment gateway
* from PayU to some other payment gateway then we can easily do it, so for this we create a Checkout
* service
*
* Q) When do we need Adapter Pattern?
* Ans) i) When we have a class but, it's interface does not match with what we need.
* ii) We want to use legacy code w/o modifying it.
* iii) When we are integrating third party APIs or external services.
*
* Pros & Cons from takeuforward website theory.
* */

package structuralPatterns.adapter;

interface PaymentGateway{
    void pay(String orderId, double amount);
}

class PayUPaymentGateway implements PaymentGateway{
    @Override
    public void pay(String orderId, double amount) {
        System.out.println("Paying " + orderId + " with amount " + amount + " by PayUPaymentGateway");
    }
}

class RazorpayAPI{
    public void makePayment(String orderId, double amount) {
        System.out.println("Making " + orderId + " with amount " + amount + " by RazorPaymentGateway");
    }
}

class RazorPayAdapter implements PaymentGateway{
    private RazorpayAPI razorpayAPI;

    public RazorPayAdapter() {
        this.razorpayAPI = new RazorpayAPI();
    }

    @Override
    public void pay(String orderId, double amount) {
        razorpayAPI.makePayment(orderId, amount);
    }
}

// Client
class CheckoutService{
    private PaymentGateway paymentGateway;
    public CheckoutService(PaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }

    public void checkout(String orderId, double amount){
        paymentGateway.pay(orderId, amount);
    }
}

public class Adapter {
    public static void main(String[] args) {
        CheckoutService checkoutService = new CheckoutService(new PayUPaymentGateway());
        CheckoutService checkoutService1 = new CheckoutService(new RazorPayAdapter());
        checkoutService.checkout("12165", 0.5);
    }
}
