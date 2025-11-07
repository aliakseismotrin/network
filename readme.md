Description
-----------------
Network Project  
A Java application for managing a network deployment consisting of devices such as Gateways, Switches, and Access Points.  
It supports both command-line interaction and a REST API for device registration, querying, and topology visualization.

Project Overview
-----------------
Simulates a small-scale computer network where each device is identified by a MAC address and can have an uplink connection.

Features
-----------------
CLI:
- Register a new device interactively
- List all devices (sorted)
- Retrieve a device by MAC address
- View full network topology
- View topology starting from a device
- Load devices from JSON file

REST API:
- Perform the same operations via HTTP (Postman or curl)
- JSON-based input/output

Technologies
-----------------
- Java 17
- Maven
- Spring Boot
- Jackson (JSON)
- JUnit 5

Project Structure
-----------------
network/
├── pom.xml  
├── devices.json  
├── NetworkApplication.postman_collection.json  
├── src/main/java/network/...  
└── src/test/java/network/...

Build & Run
------------
1. Clone the repo:
2. Build:
   mvn clean package
3. Run CLI:
   mvn exec:java
4. Run REST API:
   mvn spring-boot:run

CLI Example
---------------------------
Menu options appear for registering, listing, and viewing devices.
Example JSON for loading devices (devices.json):
[
{ "deviceType": "GATEWAY", "macAddress": "AA:BB:CC:DD:EE:01", "uplinkMacAddress": null },
{ "deviceType": "SWITCH", "macAddress": "AA:BB:CC:DD:EE:02", "uplinkMacAddress": "AA:BB:CC:DD:EE:01" },
{ "deviceType": "ACCESS_POINT", "macAddress": "AA:BB:CC:DD:EE:03", "uplinkMacAddress": "AA:BB:CC:DD:EE:02" }
]

NOTE:  
There is an example "devices.json" file in the root of the project.

REST API
---------------------------
Base URL: http://localhost:8080/api/devices

Endpoints:
- POST /api/devices → Register device
- GET /api/devices → Get all devices
- GET /api/devices/{mac} → Get device by MAC
- GET /api/devices/topology → Full topology
- GET /api/devices/topology/{mac} → Topology from MAC

Example curl:
curl -X POST http://localhost:8080/api/devices \
-H "Content-Type: application/json" \
-d '{"deviceType":"GATEWAY","macAddress":"AA:BB:CC:DD:EE:01","uplinkMacAddress":null}'

NOTE:  
The file "NetworkApplication.postman_collection.json" in the root of the project contains a Postman collection of REST requests for testing the API.

