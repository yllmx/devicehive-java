package com.devicehive.websocket.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceResponse extends Action {
    @SerializedName("device")
    private DeviceVO device;

    @Override
    public String toString() {
        return "{\n\"DeviceListResponse\":{\n"
                + "\"status\":\"" + status + "\""
                + ",\n \"devices\":" + device
                + ",\n \"action\":\"" + action + "\""
                + ",\n \"requestId\":\"" + requestId + "\""
                + "}\n}";
    }
}
