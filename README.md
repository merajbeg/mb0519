# mb0519
Tool Renting Application
------------------------

Application Usage: It is given that the tool rental application will be used by customers to rent tools from a store. The customers will interact with the application to check tool availability, make rental requests, and receive rental agreements.

The current implementation is a working code that can be tested and deployed preferrably as a docker container over kubernetes. Dockerfile is included. All 6 test cases can be run at the start of the application by uncommenting the line, //application.run();, in the ToolRentalApplication.fetchToolRecrods() method or by making a API call to runTest endpoint as follows and the results are displayed on the console itself. I have also implemented a separate JUnit test classe that can be called and run from outside. I used Maven to manage dependencies and widely used SpringBoot  framework for my development.


In order to run required test, plese 
curl --location 'http://localhost:8080/v1/api/tools/runTest' \
--header 'Content-Type: application/json' \
--data '{
  "toolCode": "CHNS",
  "checkoutDate": "2022-05-19",
  "rentalDays": 5,
  "discount": 10
}'

In order to rent a tool, please run this command that wil make an api call to the application and return the final charge.
curl --location 'http://localhost:8080/v1/api/tools/rent' \
--header 'Content-Type: application/json' \
--data '{
  "toolCode": "JAKR",
  "checkoutDate": "2022-05-19",
  "rentalDays": 5,
  "discount": 10
}'


Note: Since authentication (AuthN) and authorization (AuthZ) was not explictly called for in teh requirement document, even though I understand in a real world application it is a must, I have skipped implementing this feature. If desired, I would have implemented OAuth 2.0 based AuthN/AuthZ using Spring Security, and may have integrated with a 3rd party AuthN provider like Auth0 or AWS Cognito.    


---------------------------------------------------------------------------------
With that said, here are some guiding principals to consider before building the tool rental application. Later, I have also included specific assumptions that I made in building the tool renting application.
---------------------------------------------------------------------------------

Scalability: The application should be designed to handle a potentially large number of concurrent users. It should be scalable to accommodate increasing user traffic and rental requests without compromising performance.

Resiliency: The application should be resilient and able to recover from failures or unexpected events. It should handle errors gracefully and provide appropriate error messages to users. Implementing retry mechanisms, caching, and load balancing can contribute to the resiliency of the application.

Peak and Off-Peak Traffic: The application should be designed to handle both peak and off-peak traffic. During peak hours, there might be a higher influx of rental requests, requiring the application to efficiently manage the load and prioritize requests.

Calls per Hour, Day, and Month: It is important to estimate the potential number of API calls the application might receive per hour, day, and month. This will help determine the necessary infrastructure requirements, such as server capacity, network bandwidth, and database capacity, to handle the expected load.

Authentication and Authorization: The application should enforce authentication and authorization mechanisms to ensure that only authenticated users can access the rental functionality. Implementing OAuth 2.0 can provide secure authentication and authorization capabilities.

Security: The application should prioritize security measures to protect user data and prevent unauthorized access. This includes secure communication over HTTPS, proper input validation, and protection against common security vulnerabilities, such as SQL injection and cross-site scripting (XSS).

Data Persistence: The application should store and retrieve tool rental data reliably. A robust database system should be chosen to handle data storage efficiently, with proper indexing and optimization to support frequent data retrieval and updates.

Logging and Monitoring: Implement comprehensive logging and monitoring mechanisms to capture application activities, track errors, and monitor performance. This helps in troubleshooting issues, analyzing usage patterns, and optimizing the application.

Service Level Agreement (SLA): Define the desired service level agreement for the application, including response time, uptime, and data consistency requirements. This will guide the development process and help set appropriate benchmarks for performance and reliability.

Disaster Recovery:

The application should have a well-defined disaster recovery plan in place to ensure business continuity in the event of a disaster or major outage. This includes having redundant systems, backup data, and a strategy to quickly recover and restore the application to a functional state.

RTO (Recovery Time Objective):

The RTO defines the acceptable downtime for the application in the event of a disaster or outage. It represents the maximum time allowed for the application to be offline before recovery is completed. The RTO should be determined based on business requirements and the impact of downtime on operations and customers.

RPO (Recovery Point Objective):

The RPO represents the acceptable data loss in case of a disaster or outage. It defines the maximum amount of data that can be lost during the recovery process. The RPO should be determined based on data criticality, frequency of data updates, and business requirements.

These guiding principals provide a foundation for designing and developing the tool rental application while considering its potential usage, scalability, resiliency, and other important factors. Based on the answers, correct deisgn patterns like CQRS, Event Driven, Eventual consistency vds. real time, event sourcing etc. can be put in place to meet the expectstions. Read heavy vs. write heavy applications and many other factors help us decide in picking either a SQL or NoSQL databases, and document vs. graph vs. column databases. It is essential to analyze the specific requirements and consult with stakeholders to make informed decisions and build a robust application.

----------------------------------------------------------------------------------------------------
Here are some assumptions I, as an Software Architect, considered before building the tool rental application.
-----------------------------------------------------------------------------------------------------

Expected Usage: I estimated that the application would receive an average of 5000 rental requests per day, resulting in approximately 150,000 requests per month.

Scalability: The application was designed to handle a surge in traffic during peak hours, with the assumption that the concurrent user load could reach up to 500 users per minute.

Database Capacity: Based on the expected usage, I estimated that the database should be able to handle up to 150,000 rental agreements per month, with appropriate indexes and query optimization to ensure efficient data retrieval and updates. However, I used json file based storage in my proof of concept implementation. 

High Availability: The application was designed with high availability in mind, aiming for an uptime of at least 99.9%. This required redundant infrastructure, load balancing, and failover mechanisms to minimize downtime and ensure uninterrupted service.

Disaster Recovery: I assumed a recovery time objective (RTO) of 4 hours, meaning that the application should be restored and operational within 4 hours in the event of a disaster. The recovery point objective (RPO) was set at 1 hour, indicating that the maximum acceptable data loss would be 1 hour worth of rental data.

Load Testing: We shall load test to validate the application's performance and scalability. The application should be tested under simulated peak load conditions, ensuring that it could handle the expected number of concurrent users and rental requests without performance degradation.

Security and Compliance: I wish to incorporate industry-standard security practices and compliance measures to protect customer data, including secure storage, encryption, and adherence to relevant regulations such as CCPA ( California Consumer Privacy Act) and GDPR (General Data Protection Regulation), going global.

I advise paclaging the application in a docker image and deploying it over kubernetes (K8s). This will help migrating the application to any public or private cloud and most importantly, making it elastic which can be scaled up and down to handle the peak and off peak load to provide the best customer experience.

These specific assumptions helped me guide the architectural decisions, infrastructure planning, and development process of the tool rental application, ensuring it was designed to meet the anticipated usage patterns and performance requirements.
