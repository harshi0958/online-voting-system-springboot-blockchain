# 🗳️ Online Voting System using Spring Boot & Blockchain

A secure and modern **Online Voting System** developed using **Java Spring Boot, MySQL, and Blockchain technology**.

The main objective of this project is to provide a secure, transparent, and reliable digital voting platform where registered voters can authenticate themselves, view candidates, cast their vote, and verify voting information while maintaining vote integrity using blockchain.

---

## 📌 Project Overview

Traditional voting systems can involve problems such as:

- Manual verification
- Long queues
- Paper-based processes
- Vote manipulation concerns
- Difficulty in maintaining transparency
- Delayed result processing

This project provides a digital voting solution that combines:

**Spring Boot + MySQL + OTP Authentication + Blockchain**

The application allows voters to securely register, authenticate using OTP, participate in elections, and cast their vote. Voting records are additionally maintained through a blockchain structure to provide tamper-evident storage.

---

## 🚀 Key Features

### 👤 Voter Features

- Voter registration
- Voter login
- OTP-based authentication
- Mobile number verification
- Voter profile management
- View available candidates
- Cast vote
- Prevent duplicate voting
- View election results
- Voting status tracking

### 🛡️ Admin Features

- Admin authentication
- Admin dashboard
- Manage candidates
- Manage voters
- Control voting sessions
- Monitor election activity
- View voting results
- Verify blockchain records

### ⛓️ Blockchain Features

- Block-based vote storage
- Hash generation
- Previous block hash linking
- Blockchain validation
- Tamper detection
- Vote integrity verification

### 🔐 Security Features

- OTP authentication
- Password protection
- Session-based authentication
- Duplicate vote prevention
- Database validation
- Blockchain-based vote verification
- Sensitive credentials kept outside GitHub repository

---

## 🏗️ Technology Stack

| Technology | Purpose |
|---|---|
| ☕ Java | Core Programming Language |
| 🌱 Spring Boot | Backend Framework |
| 🗄️ MySQL | Database |
| ⛓️ Blockchain | Vote Integrity & Verification |
| 🌐 HTML | Frontend Structure |
| 🎨 CSS | Frontend Styling |
| 💻 JavaScript | Frontend Interactivity |
| 📱 Twilio | OTP/SMS Service |
| 📧 JavaMail | Email Services |
| 📦 Maven | Dependency Management |
| 🔧 Git & GitHub | Version Control |

---

## 🏛️ System Architecture

```text
                    ┌──────────────────────┐
                    │       Voter          │
                    │   Web Application    │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │   Authentication     │
                    │   OTP Verification   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Spring Boot       │
                    │      Backend         │
                    └──────────┬───────────┘
                               │
             ┌─────────────────┼─────────────────┐
             │                 │                 │
             ▼                 ▼                 ▼
      ┌─────────────┐   ┌──────────────┐  ┌──────────────┐
      │   MySQL     │   │   Voting     │  │ Blockchain   │
      │  Database   │   │   Service    │  │   Service    │
      └─────────────┘   └──────────────┘  └───────┬──────┘
                                                  │
                                                  ▼
                                         ┌────────────────┐
                                         │ Vote Integrity │
                                         │ Verification   │
                                         └────────────────┘

🔄 Voting Workflow
User Registration
       ↓
Mobile / OTP Verification
       ↓
Voter Login
       ↓
View Election
       ↓
View Candidates
       ↓
Cast Vote
       ↓
Validate Voter
       ↓
Check Duplicate Vote
       ↓
Store Voting Record
       ↓
Create Blockchain Record
       ↓
Confirm Vote
       ↓
Election Results


⛓️ Blockchain Implementation

The project contains a custom blockchain implementation for maintaining the integrity of voting records.

Each block contains information such as:

Block index
Timestamp
Vote data
Previous block hash
Current block hash

Conceptually:

┌─────────────────────┐
│      Block 1        │
│                     │
│ Vote Data           │
│ Previous Hash       │
│ Current Hash        │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│      Block 2        │
│                     │
│ Vote Data           │
│ Previous Hash       │
│ Current Hash        │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│      Block 3        │
│                     │
│ Vote Data           │
│ Previous Hash       │
│ Current Hash        │
└─────────────────────┘

If a previous block is modified, its hash relationship becomes invalid, allowing the system to detect tampering.


📂 Project Structure
voting/
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/voting/
│   │   │       ├── blockchain/
│   │   │       │   ├── Block.java
│   │   │       │   ├── Blockchain.java
│   │   │       │   └── StringUtil.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── AdminController.java
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── CandidateController.java
│   │   │       │   ├── HomeController.java
│   │   │       │   ├── OtpController.java
│   │   │       │   ├── VoterController.java
│   │   │       │   └── VotingController.java
│   │   │       │
│   │   │       ├── entity/
│   │   │       │   ├── Admin.java
│   │   │       │   ├── Candidate.java
│   │   │       │   ├── Vote.java
│   │   │       │   └── Voter.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   ├── AdminRepository.java
│   │   │       │   ├── CandidateRepository.java
│   │   │       │   ├── VoteRepository.java
│   │   │       │   └── VoterRepository.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── AdminService.java
│   │   │       │   ├── EmailService.java
│   │   │       │   ├── OtpService.java
│   │   │       │   ├── SmsService.java
│   │   │       │   ├── VotingService.java
│   │   │       │   └── VotingSessionService.java
│   │   │       │
│   │   │       └── VotingApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── admin-dashboard.html
│   │       │   ├── admin.html
│   │       │   ├── blockchain.html
│   │       │   ├── face-verify.html
│   │       │   ├── login.html
│   │       │   ├── profile.html
│   │       │   ├── register.html
│   │       │   ├── results.html
│   │       │   ├── vote.html
│   │       │   └── style.css
│   │       │
│   │       ├── application-example.properties
│   │       └── data.sql
│   │
│   └── test/
│
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md


🧩 Backend Architecture

The backend follows a layered Spring Boot architecture.

Controller
    ↓
Service
    ↓
Repository
    ↓
Database
Controller Layer

Handles HTTP requests and application endpoints.

Examples:

AuthController
AdminController
CandidateController
VoterController
VotingController
OtpController
Service Layer

Contains the main application/business logic.

Examples:

VotingService
VotingSessionService
OtpService
SmsService
EmailService
AdminService
Repository Layer

Responsible for database operations using Spring Data JPA.

Examples:

VoterRepository
CandidateRepository
VoteRepository
AdminRepository
Entity Layer

Represents database entities.

Voter
Candidate
Vote
Admin


🗄️ Database

The project uses MySQL as the relational database.

The database stores application information such as:

Voter information
Candidate information
Vote records
Admin information
Election-related information

Example database configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/myvoting
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

⚠️ Never commit real database passwords or API credentials to GitHub.


📱 OTP Authentication

The system supports OTP-based authentication for voter verification.

The general flow is:

Enter Mobile Number
        ↓
Generate OTP
        ↓
Send OTP
        ↓
User Enters OTP
        ↓
Verify OTP
        ↓
Authentication Successful

SMS functionality is integrated through the Twilio service.

For security, Twilio credentials are loaded through configuration properties instead of being hardcoded in Java source code.


🛡️ Duplicate Vote Prevention

The system ensures that a voter cannot repeatedly vote in the same election.

Before accepting a vote, the system validates:

Is voter registered?
        ↓
Is voter authenticated?
        ↓
Is election active?
        ↓
Has voter already voted?
        ↓
        No
        ↓
Accept Vote

This helps maintain the integrity of the election process.


👨‍💼 Admin Dashboard

The admin module provides functionality for managing the election system.

Admin can:

Manage candidates
Manage voters
Control voting sessions
Monitor voting activity
View results
Verify blockchain information


📊 Election Results

After voting, the system can calculate and display election results.

The result section provides information about:

Candidates
Vote counts
Election status
Voting results

The blockchain component can additionally be used to verify the integrity of stored voting records.


🔐 Configuration & Security

Sensitive configuration files are intentionally excluded from GitHub.

The following type of files/configuration should remain local:

application.properties
Database passwords
Twilio credentials
Email passwords
API keys
Other private credentials

A safe configuration template is provided:

src/main/resources/application-example.properties

Before running the application, create your local:

application.properties

and add your own credentials.


⚙️ Installation & Setup
1. Clone the Repository
git clone https://github.com/harshi0958/online-voting-system-springboot-blockchain.git
2. Navigate to Project
cd online-voting-system-springboot-blockchain
3. Configure MySQL

Create the database:

CREATE DATABASE myvoting;

Update your local application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/myvoting
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
4. Configure OTP / Email Services

Add your own credentials to the local application.properties.

Do not upload real credentials to GitHub.

5. Build the Project

Using Maven:

mvn clean install

or using Maven Wrapper:

./mvnw clean install

On Windows:

mvnw.cmd clean install
6. Run the Application
mvn spring-boot:run

or:

mvnw.cmd spring-boot:run

The application will normally be available at:

http://localhost:8080


🧪 Testing

The project contains Spring Boot test configuration under:

src/test/

Run tests using:

mvn test


🖥️ Application Modules

The application contains several user-facing pages:

Page	Purpose
Register	Voter registration
Login	User authentication
OTP Verification	Mobile verification
Profile	Voter profile
Vote	Cast vote
Results	View election results
Admin Login	Admin authentication
Admin Dashboard	Manage election
Blockchain	Verify blockchain records


🔒 Security Considerations

This project is developed as an academic and demonstration project.

For production deployment, additional security mechanisms should be implemented, including:

HTTPS/TLS
Secure password hashing
Strong session management
CSRF protection
Rate limiting
Secure OTP expiration
Audit logging
Database encryption
Proper blockchain consensus mechanism
Role-based access control
Production-grade secret management
Security testing and penetration testing


🚀 Future Enhancements

The project can be extended with:

🪪 Aadhaar-based identity verification
📷 Advanced face verification
🔐 Stronger multi-factor authentication
⛓️ Decentralized blockchain network
🦾 Smart contract integration
📱 Mobile application
📊 Advanced election analytics
🔎 Real-time blockchain verification
🛡️ Advanced fraud detection
☁️ Cloud deployment
👥 Multiple election support
📈 Election analytics dashboard
🎯 Project Objectives

The major objectives of this project are:

Build a secure online voting platform.
Provide reliable voter authentication.
Prevent duplicate voting.
Maintain transparent election records.
Use blockchain technology for vote integrity.
Provide an admin-controlled election management system.
Reduce dependency on traditional paper-based voting.
Provide faster and more accessible election management.


📚 Learning Outcomes

Through this project, the following technologies and concepts are demonstrated:

Java Spring Boot development
REST/API-based backend architecture
Spring Data JPA
MySQL database integration
MVC architecture
Authentication and authorization
OTP-based verification
SMS integration
Email integration
Blockchain fundamentals
Hashing and block chaining
Git and GitHub
Maven project management
Full-stack web application development


👨‍💻 Developer

Harshit Jariwala

Master's / Computer Science Student
Interested in:

Java & Spring Boot
Full Stack Development
Blockchain Technology
Artificial Intelligence
Software Engineering


⭐ Project Status

Status: 🚧 Academic Project / Development

The project is being developed and improved with additional security, authentication, blockchain, and election management features.


📄 License

This project is created for educational and academic purposes.

You are free to study and modify the project for learning purposes.


⭐ Support

If you find this project useful or interesting, consider giving the repository a ⭐ on GitHub.


🔗 Repository

Online Voting System using Java Spring Boot & Blockchain

https://github.com/harshi0958/online-voting-system-springboot-blockchain
