package e.usf.nutritionapp;

import java.util.List;

public class FoodItemContainer {
    private String q;
    private String sr;
    private String ds;
    private int start;
    private int end;
    private int total;
    private String group;
    private String sort;
    private List<FoodItem> item;

    public FoodItemContainer(String q, String sr, String ds, int start, int end, int total, String group, String sort, List<FoodItem> item) {
        this.q = q;
        this.sr = sr;
        this.ds = ds;
        this.start = start;
        this.end = end;
        this.total = total;
        this.group = group;
        this.sort = sort;
        this.item = item;
    }

    public String getQ() {
        return q;
    }

    public String getSr() {
        return sr;
    }

    public String getDs() {
        return ds;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getTotal() {
        return total;
    }

    public String getGroup() {
        return group;
    }

    public String getSort() {
        return sort;
    }

    public List<FoodItem> getItem() {
        return item;
    }
}
