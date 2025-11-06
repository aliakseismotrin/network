package network;

import java.util.List;

public interface DeviceAPI {
    void registerDevice(DeviceType type, String macAddress, String uplinkMacAddress);

    List<Device> getAllDevicesSorted();

    Device getDeviceByMac(String macAddress);

    void printFullTopology();

    void printTopologyFrom(String macAddress);
}
