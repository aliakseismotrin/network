package network;

public class NetworkProject {
    public static void main(String[] args) {
        NetworkManager network = new NetworkManager();

        // Register devices
        network.registerDevice(DeviceType.GATEWAY, "AA:BB:CC:DD:EE:01", null);
        network.registerDevice(DeviceType.SWITCH, "AA:BB:CC:DD:EE:02", "AA:BB:CC:DD:EE:01");
        network.registerDevice(DeviceType.ACCESS_POINT, "AA:BB:CC:DD:EE:03", "AA:BB:CC:DD:EE:02");
        network.registerDevice(DeviceType.ACCESS_POINT, "AA:BB:CC:DD:EE:04", "AA:BB:CC:DD:EE:02");

        // Print all devices sorted
        System.out.println("\nAll registered devices (sorted):");
        network.getAllDevicesSorted().forEach(System.out::println);

        // Retrieve by MAC
        System.out.println("\nRetrieve by MAC (AA:BB:CC:DD:EE:03):");
        System.out.println(network.getDeviceByMac("AA:BB:CC:DD:EE:03"));

        // Print full topology
        System.out.println();
        network.printFullTopology();

        // Print topology from a specific device
        System.out.println();
        network.printTopologyFrom("AA:BB:CC:DD:EE:02");
    }
}
