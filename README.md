Company News (Hello World web app) deployed in Elastic Beanstalk 
===============

This is hypothetical exercise and application developed here does not serve any major purpose.
As foundation for this project simple Java webapp was used. Application stores visitors IPs in a file based database.
This application includes configuration necessary to be deployed and run on Elastic Beanstalk AWS service. Elastic Beanstalk is a managed service that abstracts underlying AWS implementation / configuration complexities 
and provides a quick start for your projects. This particular application profile appears to be suitable for Elastic Beanstalk - WAR packaged application running in web container is one of the standard Elastic Beanstalk deployment patterns.
Although Elastic Beanstalk deployment process is simple (abstracts complexities / uses default values), explicit configuration is possible. Deployment can be further customised to run in a custom built environment (e.g. make use of custom VPC, deployment policies, etc).

### Prerequisites

* AWS account. Free tier will default VPC work for this example. 
* Elastic Beanstalk CLI client installed in order to use Elastic Beanstalk service. Below are instructions outlining how to install the client: http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install-osx.html
* IAM user that has AWSElasticBeanstalkFullAccess managed policy assigned in order to execute eb cli commands. (as per http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/concepts-roles.html)
* Project uses ansible for orchestration

### Static web content

Designers / Frontend developers should be able to checkout project and deploy/commit static web content.

Static web content is located in

    cd src/webapp/static
        
    static
    - css
    - images


### To run application in local dev environment 


in console:

    export RESOURCES_URL=http://localhost:9090
    
    mvn clean package
    
    
and then

    mvn tomcat7:run

navigate to url: http://localhost:9090
    
### To deploy application to Elastic Beanstalk

It is recommended to create ~/.aws/credentials with a profile for your app:

    [my-profile]
    aws_access_key_id=YOUR_ACCESS_KEY_ID
    aws_secret_access_key=YOUR_SECRET_ACCESS_KEY

Run on CLI:

    export AWS_EB_PROFILE=my-profile
    
Update variables:
    
    cicd/ansible/vars.yml

To create environment (env = prod/develop/training) and deploy static and web application:

    ansible-playbook -e env_name=[env] cicd/create_all.yml

### To update application in environments

To re-deploy static website and web application:

    ansible-playbook -e env_name=[env] cicd/deploy_all.yml
    

### Customize env Elastic Beanstalk configuration

Elastic Beanstalk configuration for prod/develop/training environments is avaialble in:

    .elasticbeanstalk/saved_config/[env].cfg.yml

Development and Training environments are extremely simple consisting only of single EC2 instance with Elastic IP.

Production environment has a load balancer and defines an autoscaling group, most of other settings were left default (but as discussed earlier customisable via prod.cfg.yml EB config file).

### To check status and url for application
    
    eb status
    
Sample output

     Environment details for: prod
       Application name: company-news
       Region: ap-southeast-2
       Deployed Version: app-fe5f-170815afw432
       Environment ID: e-y48sdf55mjs
       Platform: arn:aws:elasticbeanstalk:ap-southeast-2::platform/Tomcat 8 with Java 8 running on 64bit Amazon Linux/2.6.3
       Tier: WebServer-Standard
       URL >>>    CNAME: prod.3avxasdfit8s.ap-southeast-2.elasticbeanstalk.com   <<<
       Updated: 2017-08-15 13:27:36.276000+00:00
       Status: Ready
       Health: Green   
       
    
### Limitations to consider

Application uses Prevayler persistence library to store data on local filesystem. This limits scalability as state stored locally on each EC2 instance in the LB autoscaling pool. State is partitioned across EC2 instances and not replicated - that could be a problem if correctness of data is important. 
To address this proper RDS storage as Dynamo or RDS as Postgress/MySQL can be used to store dynamic content.

### Following improvements / consideration

1. As mentioned before user proper RDS.

2. DONE. This application appears to be of mostly read / less write type (as name suggests it is a CMS type application). 
Thus it is good candidate for caching. 

    Following can be done to improve performance when traffic to application grows: 

    * Static file can be separated and hosted in S3 bucket.
    * Use CloudFront to cache static content and potentially some non-frequently changing dynamic pages via HTTP response headers.

3. Application could be re-designed to be deployed on AWS Lambda (serverless) backed by Dynamo storage. This will reduce manually managed infrastructure, configuration, simplify CD. At the same time testing lambda is still not easy - this may impede development and testing.
4. In case architecture evolves and Elastic Beanstalk becomes too prescribed it can be easily converted to Cloud Formation script to achieve most granular control over resources created.
