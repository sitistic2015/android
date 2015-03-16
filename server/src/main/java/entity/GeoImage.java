package entity;

/**
 * Image with metadata
 * Created by mds on 10/03/15.
 * Class ${CLASS}
 */
public class GeoImage {
    private String imageName;
    private Position imagePosition;
    private byte[] imageByteCode;

    /**
     * Image byte code getter
     *
     * @return byte[]
     */
    public byte[] getImageByteCode() {
        return imageByteCode;
    }

    /**
     * Image setter
     *
     * @param imageByteCode byte []
     */
    public void setImageByteCode(byte[] imageByteCode) {
        this.imageByteCode = imageByteCode;
    }

    /**
     * Image coordinate getter
     *
     * @return Position
     */
    public Position getImagePosition() {
        return imagePosition;
    }

    /**
     * Image coordinate setter
     *
     * @param imagePosition Position
     */
    public void setImagePosition(Position imagePosition) {
        this.imagePosition = imagePosition;
    }

    /**
     * Image name getter
     *
     * @return String
     */
    public String getImageName() {

        return imageName;
    }

    /**
     * Image name setter
     *
     * @param imageName String
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
