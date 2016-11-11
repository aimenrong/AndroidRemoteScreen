package cn.woblog.android.remotescreen.domain;

/**
 * Created by renpingqing on 11/11/16.
 */

public class AppInfo {
    private String sourceDir;

    public AppInfo(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public AppInfo() {
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }
}
