package gio;

import com.growingio.growingapi.GrowingDownloadApi;

public class Demo {
    public static void main(String[] args) {
        GrowingDownloadApi api = new GrowingDownloadApi();
        api.download("2016071221");
    }
}
