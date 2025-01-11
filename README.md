# Getting Started
Follow the instructions below to set up and run the project.

### Prerequisites
Ensure you have the following tools installed:
- Kafka
- IntelliJ IDEA
- Postman or Insomnia
- Keycloak
- Prometheus
- Browser (for testing)

### Application Start Instructions

#### Step 1: Start Kafka Servers
1. **Start Zookeeper Server**:
   ```
   cd C:\kafka  (provide the kafka folder file path)
   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
   ```

2. **Start Kafka Server**:
   ```
   cd C:\kafka (provide the kafka folder file path)
   .\bin\windows\kafka-server-start.bat .\config\server.properties
   ```

#### Step 2: Run the Application
1. Open IntelliJ IDEA.
2. Run the application along with all microservices.

#### Step 3: Verify Eureka Discovery Server
1. Open your browser.
2. Navigate to `http://localhost:8761/`.
3. Confirm which services are running.

#### Step 4: Set Up Keycloak
1. Start Keycloak in development mode:
   ```
   bin\kc.bat start-dev
   ```
2. Open the browser and navigate to `http://localhost:8080` for the Keycloak Administration Console.
3. Use Postman or Insomnia to create an OAuth2 authorization token:
   - Token Endpoint
   - Client ID
   - Client Secret

#### Step 5: Configure and Run Prometheus
1. Update `prometheus.yml` with the relevant targets (running port numbers).
2. Start Prometheus:
   ```
   ./prometheus --config.file=D:\Personal-Projects\Spring_Springboot_Apps\ABCCompany\prometheus\prometheus.yml
   ```

### Notes
- Ensure all ports are accessible and not blocked by a firewall.
- Check logs for any errors during server or service initialization.

### Additional Information
- The Eureka Discovery Server helps manage and monitor the microservices.
- Prometheus is used for monitoring and metrics collection.
- OAuth2 tokens are essential for secure API communication with Keycloak.

### Troubleshooting
- If a service doesn't start, check the respective log files for errors.
- Verify all dependencies and configurations are correctly set up before starting the application.
