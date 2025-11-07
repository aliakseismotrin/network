package network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DeviceFileLoader {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<DeviceInput> loadFromJson(String path) throws IOException {
        return mapper.readValue(new File(path), new TypeReference<>() {});
    }

    // Inner DTO class for JSON structure
    public static class DeviceInput {
        public String deviceType;
        public String macAddress;
        public String uplinkMacAddress;
    }
}
