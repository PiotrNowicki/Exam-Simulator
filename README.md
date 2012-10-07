Exam Simulator
===============

The aim of this project is to create a basic platform for conducting mock exams 
(at this point it's only *Oracle Certified Expert Web Component Developer* one.) 

It is a replacement for old Wordpress based questions available [on my site](http://piotrnowicki.com/2011/03/jee-6-scwcd-mock-questions/).

### Technologies

This project is aimed at Java EE 6, so it uses the following technologies: 

* EJB - application entry-point and transactions,
* CDI - business logic, POJO injection, produces methods, observers,
* JSF - front-end,
* JPA - persistence layer,
* Bean Validation (JSR 303) - for cross-layer validation of entities and inputs.

### Deployment

This project is deployed on OpenShift's JBoss AS 7.1 cartridge so its structure 
consists of several OpenShift's elements (`.openshift`, `deployments/`). 
It works with the OpenShift's PostgreSQL cartridge and is built with Jenkins.

### Tests

Project consists of unit tests and integration tests. Unit tests are executed by default during the
Maven's `test` phase. The integration tests are executed only when `integrationTests` profile is activated,
so during: `mvn -PintegrationTests test`. The latter one requires the JBoss AS 7 running somewhere - 
it will be used by the Arquillian in the remote mode. 