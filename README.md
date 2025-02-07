### Workaround for Creating Child Objects Using Parent Instances

I found it very handy to use a parent instance for creating a child instance, automatically copying fields from the parent without the need for manual work. By leveraging Lombok's `@SuperBuilder` annotation, I benefit from an auto-generated constructor that accepts a builder. This allows me to convert the parent object to its builder and then pass it to the `DetailedApiResponse` constructor using `super`.

Once that's done, I can continue to use the builder to populate any remaining fields specific to the child class. I’m particularly proud of this solution because, for a long time, I couldn’t find a simple way to achieve this, and now I use it regularly in various scenarios. This approach not only reduces redundancy but also improves maintainability by allowing child objects to be created in a clean and consistent way, minimizing the chance of errors in manual copying of fields.

---

### Factory Pattern with Spring Dependency Injection

In traditional factory patterns, a factory class is responsible for creating and managing instances of different implementations. However, with Spring’s Dependency Injection (DI), this process is handled automatically, reducing complexity.

Instead of manually maintaining a `Map<String, ApiService>` to associate service IDs with their corresponding implementations, I rely on Spring to inject the right instance. Each service class implements `ApiService` and defines its own unique `getId()` method. When I need to retrieve a service by its ID, I simply filter the injected list of `ApiService` instances rather than manually managing a factory.

This approach makes the system highly scalable, as new implementations can be introduced without modifying existing logic. It also provides flexibility, allowing services to be replaced dynamically through Spring configuration. By eliminating the need to manually register services or manage a separate factory class, the code remains clean, maintainable, and easy to extend. Compared to traditional approaches, this reduces boilerplate code and the risk of errors in manually configuring services.

---

### Efficient Multi-Threading with ExecutorService

Parallel execution can be a useful optimization when multiple independent tasks need to run simultaneously. By setting time limits, it becomes possible to prevent long-running tasks from delaying the entire process. I applied this approach when integrating with insurance companies’ APIs, where making requests sequentially would have resulted in long wait times for clients.

To achieve this, I used `ThreadPoolExecutorService` along with `invokeAll()`, ensuring that requests were executed in parallel while enforcing a maximum execution time. This prevented any single slow response from blocking the entire operation. At the time, I used `RestTemplate`, which is a blocking HTTP client. Now, I am exploring `WebClient`, which supports a non-blocking approach, potentially simplifying the logic and improving efficiency even further. This shift to parallel processing not only optimizes performance but also enhances user experience by reducing wait times, demonstrating a clear improvement in execution speed and reliability.

### Build & Run the Docker Image

To build and run your Docker container, follow these steps:

**Build the Docker Image**:
```bash
docker build -t asics-partala-demo .
```
**Run the Docker Container**:

```bash
docker run -p 8080:8080 asics-partala-demo
```

### Testing the API with cURL

After running the container, you can test the endpoint using cURL:

```bash
curl --location 'http://localhost:8080/asics/service/demo/services/execute-all'
```

### API Response Example

When calling the endpoint, the response includes the results of services that completed within the execution time limit of **2000ms**. 
Below is an example where **Service 1** and **Service 3** returned data successfully, while **Service 2** always canceled.

#### **Example Response:**
```json
[
    {
        "serviceId": "1",
        "data": "2a5524c2-22c5-4309-96ee-e569e0298cc0",
        "executionTime": 52
    },
    {
        "serviceId": "3",
        "data": "064dc110-b5b1-4f74-8d97-dceb754b3168",
        "executionTime": 1669
    }
]
```

- **Service 1** completed in **52ms**, well within the limit.
- **Service 3** completed in **1669ms**, also within the limit.
- **Service 2** was not included in the response, meaning it was **canceled due to exceeding the 2000ms timeout**.

### Possible Enhancements (Not Included to Keep the Code Simpler)

The current implementation simulates the execution of multiple services in parallel within a time limit. 
However, instead of simulating work, real processing could be performed using REST API calls, external libraries, or other integrations. 
Additional improvements could include:

- **Real Processing Instead of Simulation** – Services could perform actual computations, API requests, or use libraries for complex tasks.
- **Swagger Documentation** – To provide API documentation and make it easier to test endpoints.
- **Security Configuration** – Implement authentication and authorization to restrict access.
- **Actuator Health Endpoints** – Expose health checks for monitoring and debugging.
- **Database Integration** – I could use a database for storing entities so the application could interact with them.
- **Error Handling in Response** – Include detailed error messages for failed or canceled services.
- **Configuration Management** – Externalize properties to make the solution more flexible.

These enhancements were left out to keep the focus on demonstrating my skills and the core coding techniques.

Throughout the process of refining my code, ideas, and documentation, I leveraged AI tools. 
These tools helped me structure my thoughts more clearly and improve the overall clarity and effectiveness of the content, ensuring a well-rounded presentation of my work.