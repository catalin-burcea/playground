package ro.cburcea.playground.designpatterns.proxy.virtualproxy;

public class VirtualProxyImage implements Image {

    private RealImage realImage;
    private String fileName;

    public VirtualProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}