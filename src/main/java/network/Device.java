package network;

import java.util.ArrayList;
import java.util.List;

public class Device {
    private final DeviceType type;
    private final String macAddress;
    private Device uplink;  // reference to parent device
    private final List<Device> children = new ArrayList<>();

    public Device(DeviceType type, String macAddress) {
        this.type = type;
        this.macAddress = macAddress;
    }

    public DeviceType getType() {
        return type;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Device getUplink() {
        return uplink;
    }

    public void setUplink(Device uplink) {
        this.uplink = uplink;
    }

    public List<Device> getChildren() {
        return children;
    }

    public void addChild(Device child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", type, macAddress);
    }
}
