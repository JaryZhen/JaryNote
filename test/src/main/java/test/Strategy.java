package test;

import lombok.Data;

import java.util.HashSet;

@Data
public class Strategy {
    private Integer productId;
    private Integer line;
    private HashSet<String> roomIds;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public HashSet<String> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(HashSet<String> roomIds) {
        this.roomIds = roomIds;
    }
}