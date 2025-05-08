class VGAtoHDMI implements VGA {
    private HDMI HDMI;

    public VGAtoHDMI(HDMI HDMI) {
        this.HDMI = HDMI;
    }

    @Override
    public void connectWithVGA() {
        System.out.println("Adapting VGA to HDMI...");
        HDMI.connectWithHDMI();
    }
}