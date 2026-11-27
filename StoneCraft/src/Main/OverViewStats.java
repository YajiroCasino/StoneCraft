package Main;

public class OverViewStats {
    private int totalProduct;
    private int totalStock;
    private int totalPending;
    private int totalInProgress;

    public OverViewStats(int totalProduct, int totalStock, int totalPending, int totalInProgress){
        this.totalProduct = totalProduct;
        this.totalStock = totalStock;
        this.totalPending  = totalPending;
        this.totalInProgress = totalInProgress;
    }

    public int getTotalProduct(){
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct){
        this.totalProduct = totalProduct;
    }

    public int getTotalStock(){
        return totalStock;
    }

    public void setTotalStock(int totalStock){
        this.totalStock = totalStock;
    }

    public int getTotalPending(){
        return totalPending;
    }

    public void setTotalPending(int totalPending){
        this.totalPending = totalPending;
    }

    public int getTotalInProgress(){
        return totalInProgress;
    }

    public void setTotalInProgress(int totalInProgress){
        this.totalInProgress = totalInProgress;
    }
}
