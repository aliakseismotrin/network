package network.controller;

import network.model.DeviceType;
import network.service.impl.DeviceServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class NetworkController {

    private final DeviceServiceImpl deviceService = new DeviceServiceImpl();

    // Register device
    @PostMapping
    public String registerDevice(@RequestBody DeviceRequest req) {
        try {
            deviceService.registerDevice(
                    DeviceType.valueOf(req.deviceType.toUpperCase()),
                    req.macAddress,
                    req.uplinkMacAddress);
            return "Device registered successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Retrieve all devices (sorted)
    @GetMapping
    public String getAllDevices() {
        StringBuilder sb = new StringBuilder();
        deviceService.printDeviceListTo(sb);
        return sb.toString();
    }

    // Retrieve device by MAC
    @GetMapping("/{mac}")
    public String getDeviceByMac(@PathVariable String mac) {
        return deviceService.getDeviceByMac(mac).toString();
    }

    // Retrieve full topology
    @GetMapping("/topology")
    public String getFullTopology() {
        return captureTopology(null);
    }

    // Retrieve topology starting from a specific device
    @GetMapping("/topology/{mac}")
    public String getTopologyFrom(@PathVariable String mac) {
        return captureTopology(mac);
    }

    // helper for topology as text
    private String captureTopology(String rootMac) {
        StringBuilder sb = new StringBuilder();
        if (rootMac == null) {
            deviceService.printFullTopologyTo(sb);
        } else {
            deviceService.printTopologyFromTo(rootMac, sb);
        }
        return sb.toString();
    }

    // DTO for JSON input
    static class DeviceRequest {
        public String deviceType;
        public String macAddress;
        public String uplinkMacAddress;
    }
}
