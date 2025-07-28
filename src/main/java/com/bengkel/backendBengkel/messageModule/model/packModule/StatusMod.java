package com.bengkel.backendBengkel.messageModule.model.packModule;

import java.time.Instant;

import lombok.Data;

@Data
public class StatusMod {

    private Boolean read;
    private Instant readAt;
    private Instant deliveredAt;

    public StatusMod() {
    }

    public StatusMod(Boolean read, Instant readAt, Instant deliveredAt) {
        this.read = read;
        this.readAt = readAt;
        this.deliveredAt = deliveredAt;
    }

}
