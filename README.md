# Distributed-URL-Shortener-Using-Snowflake-IDs

Task: Build a Distributed URL Shortener Using Snowflake IDs
Objective:


Create a URL shortening service using Snowflake IDs with base conversion (to produce 7-8 character identifiers). The system should have at least two web servers behind a load balancer to ensure scalability and high availability.


Key Requirements:


Snowflake ID Generation:


Generate unique Snowflake IDs and convert them to a higher base (e.g., Base62) for 7-8 character shortened URLs.


URL Shortening:


Users submit long URLs, which are shortened using the Snowflake ID. On accessing the short URL, the system retrieves and redirects to the original URL.


Load Balancer:


Set up a load balancer to distribute traffic across two or more web servers, ensuring the system continues functioning if one server goes down.


Web Servers:


Deploy at least two web servers that independently generate Snowflake IDs and handle URL shortening and redirection requests.


Database:


Store the mappings of shortened URLs to original URLs in a shared database (e.g., PostgreSQL), accessible by all web servers.

====================================================================================================
## Overview


* UML Sequence Diagram for URL Shortening (Key Elements):

       1) User -> Load Balancer : submitLongURL()

       2) Load Balancer -> Web Server : forwardRequestToServer()

Web Server -> Service Layer : processURLRequest()

Service Layer -> Service Layer : generateSnowflakeID()

Service Layer -> Service Layer : convertToBase62()

Service Layer -> Repository : saveURLMapping()

Repository -> Database : storeInDatabase()

Database -> Repository : acknowledgeSave()

Repository -> Service Layer : returnAcknowledge()

Service Layer -> Web Server : returnShortenedURL()

Web Server -> Load Balancer : returnShortenedURL()

Load Balancer -> User : returnShortenedURL()




* Sequence Diagram for Accessing a Shortened URL:

User -> Load Balancer : accessShortenedURL()


Load Balancer -> Web Server : forwardRequestToServer()


Web Server -> Service Layer : processRetrieveRequest()


Service Layer -> Repository : findOriginalURL()


Repository -> Database : lookupOriginalURL()


Database -> Repository : returnOriginalURL()


Repository -> Service Layer : returnOriginalURL()


Service Layer -> Web Server : returnOriginalURL()


Web Server -> Load Balancer : returnOriginalURL()


Load Balancer -> User : returnOriginalURL()


* The Snowflake ID generation approach is a widely-used method for generating unique IDs in a distributed system. It was originally developed by Twitter and is useful in systems where you need to generate unique identifiers quickly across multiple machines with minimal coordination.

* A Snowflake ID typically consists of several components encoded into a 64-bit integer, including a timestamp, machine ID, and a sequence number. Here’s a breakdown of how a Snowflake ID is structured and how to generate them.

* 
![Screenshot_20241008_225840](https://github.com/user-attachments/assets/83cf46de-7a46-4913-8a3a-e285d743d3f5)

* How the Process Works:
1) User Submits a Long URL:

   * When the user submits a long URL (e.g., https://example.com/some/long/url), the URLShortenerService:
            1) Generates a unique Snowflake ID.
            2) Converts the Snowflake ID to a Base62 string (the shortened URL identifier).
            3) Stores the mapping between the Snowflake ID and the original URL in the database.

2) User Accesses the Shortened URL:

     *  When the user accesses the shortened URL (e.g., https://short.ly/abc123), the system:
           1) Decodes the Base62 string back to the original Snowflake ID.
           2) Looks up the original URL from the database.
           3) Redirects the user to the original URL.



=================================================================================================================================

  * I have used in this project the same application.properties and pom.xml as Codeforces_mimic_project


## What done so far:

* Implemented the snowflake Id + base62 


