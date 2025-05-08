class Monitor {
    public void connects(VGA charger) {
        System.out.println("Monitor expecting VGA...");
        charger.connectWithVGA();
        System.out.println("Connected using VGA.");
    }
}