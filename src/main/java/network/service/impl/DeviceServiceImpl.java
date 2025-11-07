package network.service.impl;

import network.model.DeviceType;
import network.model.Device;
import network.service.DeviceService;

import java.util.*;
import java.util.stream.Collectors;

public class DeviceServiceImpl implements DeviceService {
    private final Map<String, Device> devices = new HashMap<>();

    @Override
    public void registerDevice(DeviceType type, String macAddress, String uplinkMacAddress) {
        if (devices.containsKey(macAddress)) {
            throw new IllegalArgumentException("Device with MAC " + macAddress + " already exists.");
        }

        Device device = new Device(type, macAddress);
        devices.put(macAddress, device);

        if (uplinkMacAddress != null) {
            Device uplink = devices.get(uplinkMacAddress);
            if (uplink == null) {
                throw new IllegalArgumentException("Uplink device not found: " + uplinkMacAddress);
            }
            device.setUplink(uplink);
            uplink.addChild(device);
        }
    }

    @Override
    public List<Device> getAllDevicesSorted() {
        return devices.values().stream()
                .sorted(Comparator.comparing(Device::getType, Comparator.comparingInt(this::deviceOrder)))
                .collect(Collectors.toList());
    }

    private int deviceOrder(DeviceType type) {
        return switch (type) {
            case GATEWAY -> 1;
            case SWITCH -> 2;
            case ACCESS_POINT -> 3;
        };
    }

    @Override
    public Device getDeviceByMac(String macAddress) {
        return devices.get(macAddress);
    }

    @Override
    public void printFullTopology() {
        System.out.println("=== Full Network Topology ===");
        devices.values().stream()
                .filter(d -> d.getUplink() == null) // root devices (e.g., Gateways)
                .forEach(d -> printTopologyRecursive(d, 0));
    }

    @Override
    public void printTopologyFrom(String macAddress) {
        Device root = devices.get(macAddress);
        if (root == null) {
            System.out.println("Device with MAC " + macAddress + " not found.");
            return;
        }
        System.out.println("=== Topology starting from " + macAddress + " ===");
        printTopologyRecursive(root, 0);
    }

    private void printTopologyRecursive(Device device, int level) {
        System.out.println("  ".repeat(level) + "- " + device.getMacAddress());
        for (Device child : device.getChildren()) {
            printTopologyRecursive(child, level + 1);
        }
    }

    public void printDeviceListTo(StringBuilder sb) {
        sb.append("All Registered Devices (sorted):\n");
        var devices = getAllDevicesSorted();
        if (devices.isEmpty()) {
            sb.append("(no devices registered)");
            return;
        }
        devices.forEach(d -> sb.append(d.getType()).append(" - ").append(d.getMacAddress()).append("\n"));
    }

    public void printFullTopologyTo(StringBuilder sb) {
        devices.values().stream()
                .filter(d -> d.getUplink() == null)
                .forEach(d -> appendTopologyRecursive(d, 0, sb));
    }

    public void printTopologyFromTo(String macAddress, StringBuilder sb) {
        Device root = devices.get(macAddress);
        if (root == null) {
            sb.append("Device not found: ").append(macAddress);
            return;
        }
        appendTopologyRecursive(root, 0, sb);
    }

    private void appendTopologyRecursive(Device device, int level, StringBuilder sb) {
        sb.append("  ".repeat(level)).append("- ").append(device.getMacAddress()).append("\n");
        for (Device child : device.getChildren()) {
            appendTopologyRecursive(child, level + 1, sb);
        }
    }
}

