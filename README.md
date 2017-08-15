Hello World! (WAR-style) Deployed in Elastic Beanstalk 
===============

As foundation for this project very simple Java webapp for testing servlet container deployments was used.
This application uses Elastic Beanstalk service. Elastic Beanstalk is a managed service that abstracts many complexities 
and provides a quick start for your dev and prod environments. 
When application use patterns are identified deployment configuration can be customised to run in a custom built environment. 

### Prerequisites

* AWS account. Free tier will default VPC work for this example. 
* Elastic Beanstalk CLI client installed in order to use Elastic Beanstalk service. Below are instructions outlining how to install the client: http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install-osx.html
* IAM user that has AWSElasticBeanstalkFullAccess managed policy assigned in order to execute eb cli commands. (as per http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/concepts-roles.html)


### To run application in local dev environment 

in console:

    mvn clean package
    
and then

    java -jar target/dependency/jetty-runner.jar target/*.war
    
    
### To deploy application to Elastic Beanstalk

It is recommended to create ~/.aws/credentials with a profile for your app:

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
Production environment has a load balancer and defines an autoscaling group. Most of the settings are default but can be customized to
suit production environment requirements. Further customisations are possible such as: custom VPC, modify number of instances to support HA configuration, application update policy, sns topic to receive alarms, custom monitoring, etc.
Customizations can be achieved by providing extra configuration in .ebextensions (http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/customize-containers.html) folder 
and/or by modifying .elasticbeanstalk/config.global.yml files
    
    eb create prod

### To update application in environments

To update application in respective env:

    git checkout [env-branch]
    eb deploy
    
### Limitations to consider

Application uses Prevayler to store data on filesystem. This limits scalability (as state stored locally on EC2 instance filesystem). 
Proper RDS storage as Dynamo or RDS as Postgress/MySQL can be used to store dynamic content.


### Following work

This application appears to be of mostly read / less write type (as name suggests it is a CMS type application). 
Thus it is good candidate for caching. 

Following can be done to improve performance when traffic to application grows: 

* Static file can be separated and hosted in S3 bucket.
* Use CloudFront to cache static content and potentially some non-frequently changing dynamic pages via HTTP response headers.

