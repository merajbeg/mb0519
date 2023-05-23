# mb0519

<a name="readme-top"></a>
# Tool Renting Application


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#Not-in-scope">Not in scope</a></li>
      </ul>
      <ul>
        <li><a href="#Assumptions">Assumptions</a></li>
      </ul>
      <ul>
        <li><a href="#General-Principles-Guildelines">Architecture Principles & Guildelines</a></li>
      </ul>            
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>
<br/>

<hr/>

# About The Project:
<p>
It is given that the tool rental application will be used by customers to rent tools from a store. The customers will interact with the application to check tool availability, make rental requests, and receive rental agreements.</p>

<p>
The current implementation is a working code that can be tested and deployed preferrably as a docker container over kubernetes. Dockerfile is included. API versioning is implemented as well.
All 6 test cases can be run in 3 different ways: <br/>
<ol>
    <ul>
    <li>By running TooRenterApplicationTests test class e.g. mvn test</li>
    </ul>
    <ul>
    <li>By making an API call to runTest endppoint. http://localhost:8080/v1/api/tools/runTest</li>
    </ul>
    <ul>
    <li>Least preferred way and not recommended: At the startup of the application, all 6 requested tests will run, if desired, and print the renter agreements on console per the requirements</li>
    </ul>
</ol>

</p>
<br/><br/>
<hr/>

# Not in scope:
<p>
    <ol>
        <ul>
        <li>User/customer management Module and Storage, Purchase history Module, Payment Module, Auditing Module, Caching solution, Product Search Module.</li>
        </ul>
        <ul>
        <li>Database usage: Tool information is stored in a Json file storage instead of a database, later to be refactored to use a relational/NoSQL database.</li>
        </ul>
        <ul>
        <li>Data Encryption in transit and at rest: TLS or json file encyption was not built. </li>
        </ul>        
        <ul>
        <li>Authentication & Authorization: Since authentication (AuthN) and authorization (AuthZ) was not explictly called for in teh requirement document, even though I understand in a real world application it is a must, I have skipped implementing this feature. If desired, I would have implemented OAuth 2.0 based AuthN/AuthZ using Spring Security, and may have integrated with a 3rd party AuthN provider like Auth0 or AWS Cognito.</li>
        </ul>
    </ol>
</p>
<br/>
<hr/>

# Assumptions (as as a Software Architect):

## Expected Usage: 
I estimated that the application would receive an average of 5000 rental requests per day, resulting in approximately 150,000 requests per month.
<br/>

## Scalability: 
The application was designed to handle a surge in traffic during peak hours, with the assumption that the concurrent user load could reach up to 200 users per minute.
<br/>

## Database Capacity: 
Based on the expected usage, I estimated that most relational and NoSQL databases should be able to handle up to 150,000 rental agreements per month, with appropriate indexes and query optimization to ensure efficient data retrieval and updates. However, I used json file based storage in my proof of concept implementation to cut down on extra dependencies, keep it simple at POC level, and time limitation. 
<br/>

## High Availability: 
The application was designed with high availability in mind, aiming for an uptime of at least 99.9%. This required redundant infrastructure, load balancing, and failover mechanisms to minimize downtime and ensure uninterrupted service.
<br/>

## Disaster Recovery: 
I assumed a recovery time objective (RTO) of 4 hours, meaning that the application should be restored and operational within 4 hours in the event of a disaster. The recovery point objective (RPO) was set at 1 hour, indicating that the maximum acceptable data loss would be 1 hour worth of rental data.
<br/>

## Load Testing: 
We shall load test to validate the application's performance and scalability. The application should be tested under simulated peak load conditions, ensuring that it could handle the expected number of concurrent users and rental requests without performance degradation.
<br/>

## Security and Compliance: 
I wish to incorporate industry-standard security practices and compliance measures to protect customer data, including secure storage, encryption, and adherence to relevant regulations such as CCPA ( California Consumer Privacy Act) and GDPR (General Data Protection Regulation), if renting globally.
<br/>
<br/>
<p>
Deploying the application in docker containers over kubernetes (K8s) should further help migrating the application to any public or private cloud and most importantly, making it elastic which can help the application to scale up and down to handle the peak and off peak load to provide the best customer experience.

These  assumptions helped me guide the architectural decisions, infrastructure planning, and in development process of the tool rental application, ensuring it was designed to meet the anticipated usage patterns and performance requirements.
</p>
<br/><br/>
<hr/>


# Architecture Principles & Guildelines:
<p>
Here are guiding principals to consider before building any application including, the tool rental application.
</p>
<br/>

## Scalability: 
The application should be designed to handle a potentially large number of concurrent users. It should be scalable to accommodate increasing user traffic and rental requests without compromising performance.
<br/>

## Resiliency: 
The application should be resilient and able to recover from failures or unexpected events. It should handle errors gracefully and provide appropriate error messages to users. Implementing retry mechanisms, caching, and load balancing can contribute to the resiliency of the application.
<br/>

## Peak and Off-Peak Traffic: 
The application should be designed to handle both peak and off-peak traffic. During peak hours, there might be a higher influx of rental requests, requiring the application to efficiently manage the load and prioritize requests.
<br/>

## Calls per Hour, Day, and Month: 
It is important to estimate the potential number of API calls the application might receive per hour, day, and month. This will help determine the necessary infrastructure requirements, such as server capacity, network bandwidth, and database capacity, to handle the expected load.
<br/>

## Authentication and Authorization: 
The application should enforce authentication and authorization mechanisms to ensure that only authenticated users can access the rental functionality. Implementing OAuth 2.0 can provide secure authentication and authorization capabilities.
<br/>

## Security: 
The application should prioritize security measures to protect user data and prevent unauthorized access. This includes secure communication over HTTPS, proper input validation, and protection against common security vulnerabilities, such as SQL injection and cross-site scripting (XSS).
<br/>

## Data Persistence: 
The application should store and retrieve tool rental data reliably. A robust database system should be chosen to handle data storage efficiently, with proper indexing and optimization to support frequent data retrieval and updates.
<br/>

## Logging and Monitoring: 
Implement comprehensive logging and monitoring mechanisms to capture application activities, track errors, and monitor performance. This helps in troubleshooting issues, analyzing usage patterns, and optimizing the application.
<br/>

## Service Level Agreement (SLA): 
Define the desired service level agreement for the application, including response time, uptime, and data consistency requirements. This will guide the development process and help set appropriate benchmarks for performance and reliability.
<br/>

## Disaster Recovery:

The application should have a well-defined disaster recovery plan in place to ensure business continuity in the event of a disaster or major outage. This includes having redundant systems, backup data, and a strategy to quickly recover and restore the application to a functional state.

### RTO (Recovery Time Objective):

The RTO defines the acceptable downtime for the application in the event of a disaster or outage. It represents the maximum time allowed for the application to be offline before recovery is completed. The RTO should be determined based on business requirements and the impact of downtime on operations and customers.

### RPO (Recovery Point Objective):

The RPO represents the acceptable data loss in case of a disaster or outage. It defines the maximum amount of data that can be lost during the recovery process. The RPO should be determined based on data criticality, frequency of data updates, and business requirements.
<br/>
<p>
These guiding principals provide a foundation for designing and developing the tool rental application while considering its potential usage, scalability, resiliency, and other important factors. Based on the answers, correct deisgn patterns like CQRS, Event Driven, Eventual consistency vds. real time, event sourcing etc. can be put in place to meet the expectstions. Read heavy vs. write heavy applications and many other factors help us decide in picking either a SQL or NoSQL databases, and document vs. graph vs. column databases. It is essential to analyze the specific requirements and consult with stakeholders to make informed decisions and build a robust application.
</p>
<br/>
<hr/>


# Built With:
This section lists any major frameworks/libraries used to bootstrap the project.

* [Spring Boot]
* [Maven]
* [Spring Initializr] [https://start.spring.io/]


<p align="right">(<a href="#readme-top">back to top</a>)</p>
<br/>
<hr/>

<!-- GETTING STARTED -->
## Getting Started

instructions to set up your project locally.
To get a local copy up and running follow these simple steps.

### Prerequisites

* Java 1.8+ is installed
* Maven is intalled
* git is installed
* optionally, docker is installed if you want to run it as a docker container

Please follow the standard procedures to install above main dependencies before proceeding further.

### Installation
<p>
Below is an example of how you can install, set up and your app as a Springboot Application.

1. Clone the repo
   ```sh
   git clone https://github.com/merajbeg/mb0519.git
   ```
2. Build the project with Maven
   ```sh
   mvn clean install
   ```
3. Run the application
   ```sh
   java -jar target/tool_renter-1.0-SNAPSHOT.jar 
   ```
</p>
<br/>
<p>
Below is an example of how you can install, set up and your app as a docker container.

1. Clone the repo
   ```sh
   git clone https://github.com/merajbeg/mb0519.git
   ```
2. Create a docker image
   ```sh
   docker build -t my-tool-app .
   ```
3. Run as a docker container
   ```sh
   docker run -p 8080:8080 my-tool-app 
   ```
</p>
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

* ### In order to run required test, plese 
curl --location 'http://localhost:8080/v1/api/tools/runTest' \
--header 'Content-Type: application/json' \
--data '{
  "toolCode": "CHNS",
  "checkoutDate": "2022-05-19",
  "rentalDays": 5,
  "discount": 10
}'

* ### In order to rent a tool, please run this command that wil make an api call to the application and return the final charge.
curl --location 'http://localhost:8080/v1/api/tools/rent' \
--header 'Content-Type: application/json' \
--data '{
  "toolCode": "JAKR",
  "checkoutDate": "2022-05-19",
  "rentalDays": 5,
  "discount": 10
}'


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Add Authentication
- [ ] Add Database
- [ ] Add Customer management
- [ ] Add Payment Module
- [ ] Multi-language Support
    - [ ] Spanish

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Meraj Beg - hmmtmm2@gmail.com

Project Link: [https://github.com/merajbeg/mb0519](https://github.com/merajbeg/mb0519)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

