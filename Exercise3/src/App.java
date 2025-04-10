public class App {
    public static void main(String[] args) throws Exception {
        Hybrid_Sedan Hybrid_Sedan = new Hybrid_Sedan("Hybrid Sedan");
        

        System.out.println("I am driving a " + Hybrid_Sedan.getCarname());
        Hybrid_Sedan.charge_batt();
        Hybrid_Sedan.fillgas();

            Hybrid_Pickup Hybrid_Pickup = new Hybrid_Pickup("Hybrid Pickup");
            
    
            System.out.println("I am driving a " + Hybrid_Pickup.getCarname());
            Hybrid_Pickup.charge_batt();
            Hybrid_Pickup.fillgas();


        Carwash washwash = new Carwash();

        washwash.wash(Hybrid_Sedan);
        washwash.wash(Hybrid_Pickup);
    }
}