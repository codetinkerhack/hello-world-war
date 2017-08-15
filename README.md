Hello World! (WAR-style) Deployed in Elastic Beanstalk 
===============

This is the simplest possible Java webapp for testing servlet container deployments.  
It should work on any container and requires no other dependencies or configuration.

### Prerequisites

You will require an AWS account. Free tier will default VPC work for this example. 
However if you have proper VPC configured - this example can be further customised to make use of it.
Right now this deployment will make use of default VPC.

This deployment uses Elastic Beanstalk managed service to deploy the application.

You will need Elstic Beanstalk CLI client installed in order to use Elastic Beanstalk service.

Below are instructions outlining how to install the client:

http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install-osx.html


### To run application from CLI 

do:

    mvn package
    
and then

    java -jar target/dependency/jetty-runner.jar target/*.war
    
    
### To deploy application to Elastic Beanstalk

First, create ~/.aws/credentials with a profile for your app:

    [my-profile]
    aws_access_key_id=YOUR_ACCESS_KEY_ID
    aws_secret_access_key=YOUR_SECRET_ACCESS_KEY

Run on CLI:

    export AWS_EB_PROFILE=my-profile


Below command will deploy application to a development (named develop) environment
Development environment is extremely simple consisting only of single EC2 instance with Elastic IP.

    git checkout develop
    eb create develop -s

Similarly training environment can be created (same deployment configuration as develop)

    git checkout training
    eb create training -s

Below command will deploy application to a Prod env
Production environment has a load balancer and autoscaling group. Most of the settings are default but can be changed to
specify number of instances to support HA configuration, application update policy, sns topic to receive alarms, etc.
This can be achieved by providing extra configuraiotn in .ebextensions (http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/customize-containers.html) folder 
and/or by modifying .elasticbeanstalk/config.global.yml files
    
    eb create prod

### To update application in environments

To update application in respective env:

    git checkout [env-branch]
    eb deploy
