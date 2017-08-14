Hello World! (WAR-style) Deployed in Elastic Beanstalk 
===============

This is the simplest possible Java webapp for testing servlet container deployments.  
It should work on any container and requires no other dependencies or configuration.

##To run application from CLI 

do:

    mvn package
    
and then

    java -jar target/dependency/jetty-runner.jar target/*.war
    
    
### To deploy application to Elastic Beanstalk

It is recommended to create new profile (e.g. admin)for aws-key and aws-secret in ~/.aws/credentials

When executing below command specify Tomcat type of deployment

    eb init --profile admin

    eb create company-news-dev -s
    
    eb create company-news-prod