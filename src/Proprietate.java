public abstract class Proprietate {
    private String tip;
    private int gabarit, cost;

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getGabarit() {
        return gabarit;
    }

    public void setGabarit(int gabarit) {
        this.gabarit = gabarit;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    Proprietate(int gabarit, int cost, String tip){
        this.gabarit = gabarit;
        this.cost = cost;
        this.tip = tip;
    }
}
