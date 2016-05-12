package smash.qr_code_reader_gyorffyl;

/**
 * Created by Smash on 2016.05.11..
 */
public class Response {

    /**
     * id : 1
     * item_name : headset
     * item_price : 300
     * item_picture : xxx.jpg
     */

    private String id;
    private String item_name;
    private String item_price;
    private String item_picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_picture() {
        return item_picture;
    }

    public void setItem_picture(String item_picture) {
        this.item_picture = item_picture;
    }
}
