import javafx.scene.image.ImageView;

public class Cell {
    private int number = 0;
    private int positionX;
    private int positionY;
    private int nodeNum;
    private ImageView imageView;

    public Cell(int positionX, int positionY, int nodeNum) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.nodeNum = nodeNum;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
