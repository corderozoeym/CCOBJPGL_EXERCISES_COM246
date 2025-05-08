public class App {
    public static void main(String[] args) {
        HDMI socket = new HDMI();
        VGA oldAdaptor = new VGAtoHDMI(socket);
       
 
        Monitor monitor = new Monitor();
        monitor.connects(oldAdaptor);

    }
}