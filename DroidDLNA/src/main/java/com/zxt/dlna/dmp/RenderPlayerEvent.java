package com.zxt.dlna.dmp;

public class RenderPlayerEvent {

    private String name;
    private String uri;

    public RenderPlayerEvent(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }
}
