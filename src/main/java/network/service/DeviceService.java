package network.service;

import network.model.DeviceType;
import network.model.Device;

import java.util.List;

public interface DeviceService {
    void registerDevice(DeviceType type, String macAddress, String uplinkMacAddress);

    List<Device> getAllDevicesSorted();

    Device getDeviceByMac(String macAddress);

    void printFullTopology();

    void printTopologyFrom(String macAddress);
}
