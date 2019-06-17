package e.usf.nutritionapp;

public class TestItem {
    private int image;
    private String text;
    private String text2;

    public TestItem(int image, String text, String text2){
        this.image = image;
        this.text = text;
        this.text2 = text2;
    }

    public int getImage(){
        return image;
    }

    public String getText() {
        return text;
    }

    public String getText2() {
        return text2;
    }
}
