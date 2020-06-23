package test;

import java.util.List;

public class LineStrategy {

    private Header header;
    private Integer defaultLine;
    private List<Strategy> strategy;
    public void setHeader(Header header) {
        this.header = header;
    }
    public Header getHeader() {
        return header;
    }

    public void setDefaultLine(Integer defaultLine) {
        this.defaultLine = defaultLine;
    }
    public Integer getDefaultLine() {
        return defaultLine;
    }

    public void setStrategy(List<Strategy> strategy) {
        this.strategy = strategy;
    }

    public List<Strategy> getDefaultStrategy(List<Strategy> strategy) {
        return strategy;
    }
    public List<Strategy> getStrategy() {
        return strategy;
    }


}
