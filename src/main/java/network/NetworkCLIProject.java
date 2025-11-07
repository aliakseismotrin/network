package network;

import java.util.Scanner;

public class NetworkCLIProject {

    private static final Scanner scanner = new Scanner(System.in);
    private static final NetworkManager networkManager = new NetworkManager();
    private static final DeviceFileLoader fileLoader = new DeviceFileLoader();

    public static void main(String[] args) {
        System.out.println("=== Network Deployment CLI ===");

        while (true) {
            printMenu();
            System.out.print("Choose option (1-7): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> registerDevice();
                case "2" -> listDevices();
                case "3" -> retrieveDeviceByMac();
                case "4" -> printFullTopology();
                case "5" -> printTopologyFromMac();
                case "6" -> loadFromFile();
                case "7" -> {
                    System.out.println("Exiting... Goodbye");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Register a device");
        System.out.println("2. Retrieve all devices (sorted)");
        System.out.println("3. Retrieve device by MAC address");
        System.out.println("4. Print full network topology");
        System.out.println("5. Print topology starting from a device");
        System.out.println("6. Load devices from JSON file (devices.json)");
        System.out.println("7. Exit");
    }

    private static void registerDevice() {
        System.out.println("\nRegister new device:");
        System.out.println("Device types: GATEWAY, SWITCH, ACCESS_POINT");
        System.out.print("Enter device type: ");
        String typeInput = scanner.nextLine().trim().toUpperCase();

        DeviceType type;
        try {
            type = DeviceType.valueOf(typeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid device type.");
            return;
        }

        System.out.print("Enter MAC address: ");
        String mac = scanner.nextLine().trim();

        System.out.print("Enter uplink MAC address (or press Enter if none): ");
        String uplink = scanner.nextLine().trim();
        if (uplink.isEmpty()) uplink = null;

        try {
            networkManager.registerDevice(type, mac, uplink);
            System.out.println("Device registered successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listDevices() {
        System.out.println("\nAll Registered Devices (sorted):");
        var devices = networkManager.getAllDevicesSorted();
        if (devices.isEmpty()) {
            System.out.println("(no devices registered)");
            return;
        }
        devices.forEach(d -> System.out.println(d.getType() + " - " + d.getMacAddress()));
    }

    private static void retrieveDeviceByMac() {
        System.out.print("\nEnter MAC address: ");
        String mac = scanner.nextLine().trim();
        Device d = networkManager.getDeviceByMac(mac);
        if (d == null) {
            System.out.println("Device not found.");
        } else {
            System.out.println("Found: " + d);
            if (d.getUplink() != null)
                System.out.println("Uplink: " + d.getUplink().getMacAddress());
        }
    }

    private static void printFullTopology() {
        System.out.println();
        networkManager.printFullTopology();
    }

    private static void printTopologyFromMac() {
        System.out.print("\nEnter MAC address to start from: ");
        String mac = scanner.nextLine().trim();
        networkManager.printTopologyFrom(mac);
    }
    private static void loadFromFile() {
        String path = "devices.json";
        try {
            var deviceInputs = fileLoader.loadFromJson(path);
            for (var input : deviceInputs) {
                DeviceType type = DeviceType.valueOf(input.deviceType.toUpperCase());
                networkManager.registerDevice(type, input.macAddress,
                        input.uplinkMacAddress == null || input.uplinkMacAddress.isEmpty()
                                ? null
                                : input.uplinkMacAddress);
            }
            System.out.println("Devices loaded successfully from " + path);
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
