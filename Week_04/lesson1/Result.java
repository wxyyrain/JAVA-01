package org.geek_time.java_01_project.concurrent;

public class Result {

    private String result;

    private volatile boolean ready;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
