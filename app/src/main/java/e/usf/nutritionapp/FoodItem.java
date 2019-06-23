package e.usf.nutritionapp;

public class FoodItem {
    private int offset;
    private String group;
    private String name;
    private String ndbno;
    private String ds;
    private String manu;

    public FoodItem(int offset, String group, String name, String ndbno, String ds, String manu) {
        this.offset = offset;
        this.group = group;
        this.name = name;
        this.ndbno = ndbno;
        this.ds = ds;
        this.manu = manu;
    }

    public int getOffset() {
        return offset;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getNdbno() {
        return ndbno;
    }

    public String getDs() {
        return ds;
    }

    public String getManu() {
        return manu;
    }
}
