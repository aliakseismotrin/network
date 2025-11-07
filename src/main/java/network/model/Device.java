package network.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Device {
    private final DeviceType type;
    private final String macAddress;
    private Device uplink;  // reference to parent device
    private final List<Device> children = new ArrayList<>();

    public Device(DeviceType type, String macAddress) {
        this.type = type;
        this.macAddress = macAddress;
    }

    public void addChild(Device child) {
        children.add(child);
    }
}
