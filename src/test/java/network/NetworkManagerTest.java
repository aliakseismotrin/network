package network;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NetworkManagerTest {

    private NetworkManager network;

    @BeforeEach
    void setUp() {
        network = new NetworkManager();

        // base structure
        network.registerDevice(DeviceType.GATEWAY, "AA:BB:CC:DD:EE:01", null);
        network.registerDevice(DeviceType.SWITCH, "AA:BB:CC:DD:EE:02", "AA:BB:CC:DD:EE:01");
        network.registerDevice(DeviceType.ACCESS_POINT, "AA:BB:CC:DD:EE:03", "AA:BB:CC:DD:EE:02");
        network.registerDevice(DeviceType.ACCESS_POINT, "AA:BB:CC:DD:EE:04", "AA:BB:CC:DD:EE:02");
    }

    @Test
    void testRegisterAndRetrieveByMac() {
        Device d = network.getDeviceByMac("AA:BB:CC:DD:EE:03");
        assertNotNull(d);
        assertEquals(DeviceType.ACCESS_POINT, d.getType());
    }

    @Test
    void testRegisterDuplicateMacThrowsError() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> network.registerDevice(DeviceType.SWITCH, "AA:BB:CC:DD:EE:02", null));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void testRegisterWithMissingUplinkThrowsError() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> network.registerDevice(DeviceType.SWITCH, "AA:BB:CC:DD:EE:10", "NOT:EX:IS:T"));
        assertTrue(ex.getMessage().contains("Uplink device not found"));
    }

    @Test
    void testGetAllDevicesSorted() {
        List<Device> devices = network.getAllDevicesSorted();

        // There should be 4 devices total
        assertEquals(4, devices.size());

        // Order: Gateway → Switch → Access Point
        assertEquals(DeviceType.GATEWAY, devices.get(0).getType());
        assertEquals(DeviceType.SWITCH, devices.get(1).getType());
        assertEquals(DeviceType.ACCESS_POINT, devices.get(2).getType());
        assertEquals(DeviceType.ACCESS_POINT, devices.get(3).getType());
    }

    @Test
    void testTopologyStructure() {
        Device gateway = network.getDeviceByMac("AA:BB:CC:DD:EE:01");
        Device switch1 = network.getDeviceByMac("AA:BB:CC:DD:EE:02");

        assertEquals(gateway, switch1.getUplink());
        assertEquals(2, switch1.getChildren().size()); // two access points
        assertEquals("AA:BB:CC:DD:EE:03", switch1.getChildren().get(0).getMacAddress());
    }

    @Test
    void testPrintTopologyMethodsDoNotThrow() {
        assertDoesNotThrow(() -> network.printFullTopology());
        assertDoesNotThrow(() -> network.printTopologyFrom("AA:BB:CC:DD:EE:02"));
    }
}
