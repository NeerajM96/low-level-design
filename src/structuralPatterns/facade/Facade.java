/*
* The client currently has to call multiple services in a specific sequence to book a movie ticket.
* However, the client shouldn't be responsible for managing these steps manually,
* as this can lead to errors or missed steps. For example, if the client wants to book a concert
* or a stand-up comedy show instead of a movie, it would require repeating the same steps and logic.
*
* So, instead of making the client interact with all these directly, the Facade pattern provides a single (unified)
* interface class like MovieBookingFacade, which internally coordinates all the services.
*
* Cons: Violates SRP
* MovieBookingFacade handles multiple responsibilities like payments, reservations, notifications, loyalty points,
* and ticketing—so it has multiple reasons to change, which clearly violates the Single Responsibility
* Principle (SRP).
*
* */

package structuralPatterns.facade;

/*
* ===============================================================================================================\
* Version 1
* ===============================================================================================================
* */
//class PaymentService{
//    public void makePayment(String accountId, double amount){
//        System.out.println("Payment of $"+amount + " sucessfull for account " + accountId);
//    }
//}
//
//class SeatReservationService{
//    public void reserveSeat(String movieId, String seatNumber){
//        System.out.println("Seat " + seatNumber + " reserved for movie " + movieId);
//    }
//}
//
//class NotificationService{
//    public void sendNotification(String userEmail){
//        System.out.println("Booking confirmation sent to " + userEmail);
//    }
//}
//
//class LoyaltyPointsService{
//    public void addPoints(String accountId, int points){
//        System.out.println(points + " loyalty points added for account " + accountId);
//    }
//}
//
//class TicketService{
//    public void generateTicket(String movieId, String seatNumber){
//        System.out.println("Tickets generated for movie " + movieId + " and Seat: " + seatNumber);
//}
//
//}
//
//public class Facade {
//    public  static void main(String[] args) {
////            Booking a movie ticket Manually
//        // Step 1
//        PaymentService paymentService = new PaymentService();
//        paymentService.makePayment("user1245",500);
//
//        // Step 2
//        SeatReservationService seatReservationService = new SeatReservationService();
//        seatReservationService.reserveSeat("movie465","A11");
//
//        // Step 3
//        NotificationService notificationService = new NotificationService();
//        notificationService.sendNotification("user1245@mail.com");
//
//        // Step 4
//        LoyaltyPointsService loyaltyPointsService = new LoyaltyPointsService();
//        loyaltyPointsService.addPoints("user1245",50);
//
//        // Step 5
//        TicketService ticketService = new TicketService();
//        ticketService.generateTicket("user1245","A11");
//
//
//    }
//}

/*
 * ===============================================================================================================\
 * Version 2
 * Inside MovieBookingFacade we can replace bookMovieTicket with Builder pattern, so that this MovieBookingFacade
 * can be expanded for concerts/standup-comedy as these only have seatNumber.
 * ===============================================================================================================
 * */

class PaymentService{
    public void makePayment(String accountId, double amount){
        System.out.println("Payment of $"+amount + " sucessfull for account " + accountId);
    }
}

class SeatReservationService{
    public void reserveSeat(String movieId, String seatNumber){
        System.out.println("Seat " + seatNumber + " reserved for movie " + movieId);
    }
}

class NotificationService{
    public void sendNotification(String userEmail){
        System.out.println("Booking confirmation sent to " + userEmail);
    }
}

class LoyaltyPointsService{
    public void addPoints(String accountId, int points){
        System.out.println(points + " loyalty points added for account " + accountId);
    }
}

class TicketService{
    public void generateTicket(String movieId, String seatNumber){
        System.out.println("Tickets generated for movie " + movieId + " and Seat: " + seatNumber);
    }

}

class MovieBookingFacade{
    private PaymentService paymentService;
    private SeatReservationService seatReservationService;
    private NotificationService notificationService;
    private LoyaltyPointsService loyaltyPointsService;
    private TicketService ticketService;

    public MovieBookingFacade(){
        this.paymentService = new PaymentService();
        this.seatReservationService = new SeatReservationService();
        this.notificationService = new NotificationService();
        this.loyaltyPointsService = new LoyaltyPointsService();
        this.ticketService = new TicketService();
    }

    public void bookMovieTicket(String accountId, String movieId, String seatNumber, String userEmail,
    double amount
    ){
      this.paymentService.makePayment(accountId, amount);
      this.seatReservationService.reserveSeat(movieId, seatNumber);
      this.notificationService.sendNotification(userEmail);
      this.loyaltyPointsService.addPoints(accountId,50);
      this.ticketService.generateTicket(movieId, seatNumber);

    }
}

public class Facade {
    public static void main(String[] args) {
       MovieBookingFacade movieBookingFacade = new MovieBookingFacade();
       movieBookingFacade.bookMovieTicket("user1243","movie465","A11",
               "user1243@mail.com",500);


    }
}