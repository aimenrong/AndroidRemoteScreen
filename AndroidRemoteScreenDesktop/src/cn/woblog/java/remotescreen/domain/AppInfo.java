package cn.woblog.java.remotescreen.domain;

/**
 * Created by renpingqing on 11/11/16.
 */

public class AppInfo {
    private String sourceDir;
    private String mainClassName;

    public String getMainClassName() {
        return mainClassName;
    }

    public void setMainClassName(String mainClassName) {
        this.mainClassName = mainClassName;
    }

    public AppInfo(String sourceDir, String mainClassName) {
        this.sourceDir = sourceDir;
        this.mainClassName = mainClassName;
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
