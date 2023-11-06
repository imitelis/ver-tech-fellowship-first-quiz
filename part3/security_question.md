## Security Question

### Uber for Solar Panels system's security concerns (in regard of OWASP Top 10 for 2021):

  *  **A01:2021 - Broken Access Control:** Safeguarding HTTP requests from the users using a logged control token (such as using JWT in the headers with an specific expiration time for revoking this access) and retrieving this authentication from the user (after successful login in the service) before he is allowed to send any request in the API that is available to users. Denying any access to private APIs if the user is not a superuser. And so, by having APIs as private by defaults to the client, we can avoid security breachs such as an unauthorized user making a POST request. Public resources (basic data about the app, public photos and text (details and common questions, from the landing page, for example)) can remain public. 

  - We can retrieve a JWT from a successful login response in the FastAPI server and store it in the Browser cache (for the Next.JS webapp) or in the user device (for the Android / iOs app).


  *  **A02:2021 - Cryptographic Failures:** When storing private information (such as passwords or any other sensitive data), enforcing the user to use strong passwords (long passwords with different characters and numbers) and hashing it with a encrypting algorithm (by using a library such as bycrypt, and implementing unique salts for each password) so that even if the someone is capable to break access into our DB or can hear the incoming HTTP requests in the server's port, they cannot retrieve the original passwords or use the hashes for authentication. Also, since we used salting, it is not going to be posible for a hacker to use precomputed tables (brute-forcing the compromised information) to reverse the retrieved hashes.

  -  We can install and implement bcrypt for our FastAPI server.


  *  **A03:2021 – Injection:** Validating and sanitizing all the incoming data from the clients HTTP requests. So that if an attacker tries to place an SQL query as a text field in his login username field and then makes a login request, we can reject this request and deny this input from causing a potential issue in our MySQL database. We could also use RegEx for rejecting inputs that contain unexpected characters in the frontend itself and then sanitize the validated data as an string. We could also use parametrized queries for preventing attackers from injecting malicious SQL code. For example, by hardcoding each of our services (creating a new user, buying a solar panel, scheduling an installation, etc) as SQL queries in the endpoints of our APIs.

  -  We can use pydantic (which comes by default with FastAPI) for validating our data and we can implement SQLAlchemy for the database sessions and connection.
  

  *  **A04:2021 – Insecure Design:** Aside from the previously explained security concerns (missing authentication, lack of input validation, public API endpoints and data exposure), we should follow good practices when writing the code for our FastAPI server (we should separate the files based on their functions and only importing and using the files that are required for each functionality, for example, by not opening the SQLAlchemy session if it is not required). We could write some unit and functional tests for testing the requests handling and exceptions of each for the most critical of our application services. 

  -  We can implement unit tests in Python (we can test any Python function and class, even without a framework) with the pytest library and we can also implement functional tests using pytest along httpx for the FastAPI API endpoints.


  *  **A05:2021 – Security Misconfiguration:** When deploying our application through a configured container image and orchestrating those images for multiple containers on a cloud service, we should be very careful of not shating the specific environment variables, API keys and SSH keys in any of our files, either Dockerfile, README.md or even our FastAPI code. We should use .env files for hardcoding them locally (we should ignore those files through .gitignore when updating the repository) and we can also set them globally for our deployed app in AWS through SSH. We should also avoid exposing unneccessary ports, and making publicly available the cloud data.
  
  -  We can use Docker to expose only one port of our app image. We can also limit privileges and implement network policies in Kubernetes, along with using Kubernetes Secrets for the sensitive data. In AWS, we can restrict access to S3 buckets using AIM.
  
  
  *  **A06:2021 – Vulnerable and Outdated Components:** We must use the only the most stable versions for the libraries that we use in our apps. We should check periodically news and forums related with the technologies that we use (particularly those of cybersecurity). And also check that there are not going to be any version conflicts. We should keep an eye on the development and deployed terminal of the server for scanning if there is any runtime error or any existing vulnerability.
  
  -  We can manage different versions of our server, where we can check the suitability of every package and library. We can check the logs in the console of our deployed applications with AWS CLI.
  
  
  *  **A07:2021 – Identification and Authentication Failures:** In extension of our concerns for A01, we can apply multi-factor authentication particularly when updating sensitive data from our users (for example, if an user wants to change his password, we can customize an endpoint valid with an expiration time and send a link to the email account linked with his account). We can also limit the rates of our API so that an IP address is only allowed to send a limited number of request during a given period of time. We should also use hashed session tokens and enforce the user to relog (and the client to reset the session token) if it is expired.
  
  -  In React.js and in React Native, we can use the useEffect hook (or even better, we can use a signal) to track any change in the session token and close session immediatly if it is expired.

  
  *  **A08:2021-Software and Data Integrity Failures:** We should secure our CI/CD pipeline to use access management and authentication so none apart from our organization can alter the content of our code. We should also check that the merging code in the main branch is completely functional and secure. So that the code is ready for delivery. We must also download the libraries and dependencies required for our app from trusted sources. 
  
  -  We can use GitHub to grant specific access to our repositories and revoke it if neccessary. We can use GitHub Actions to automate running some tests and scans as part of our pipeline.


  *  **A09:2021 – Security Logging and Monitoring Failures:** We can log any incoming HTTP request from the client using the proper middleware, which we can extend to also monitor the context of the user (for example, showing the IP address, username or even the user device). So we can create alerts and block an specific IP address or ban an user if it is trying to consume our API in any suspicious way. Howerever, we should also avoid logging sensitive data (such as credit card numbers and passwords) so even if our monitoring is compromised, our data is not.
  
  -  We can create our own custom middlewares in FastAPI from scratch but we can also import the logging library for building customized middlewares.
  
  
  *  **A10:2021 – Server-Side Request Forgery (SSRF):** The client (web and mobile app) should only fetch a remote resource once the user has been validated. In the server, we must ensure that there are not unexpected endpoints and enforce an URL schema so that the client is commanded to only use those specific URLs. In order to prevent the server to make unintended requests, we can also rescrict the allowed hosts (the deployed server domain) and the incoming requests (using CORS for the deployed client domain). We can also validate the URLs from the server side.
  
  -  In FastAPI we can implement CORSMiddleware for configuring the resource sharing and we can also use usllib to parse the endpoint URLs.
  

**System architecture:** With 12 engineers we should be capable to improve those our security in those concerns, since the most critical parts are those from the server and the cloud, I would suggest having at least 3 people working in the server and database, 3 people in CI/CD, devops and cloud, 3 people for the client side code of the web app, other 2 working on the mobile app and a last one implementing functional tests for our app.
