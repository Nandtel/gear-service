# GearService
[![Build Status](https://travis-ci.org/Nandtel/GearService.svg?branch=master)](https://travis-ci.org/Nandtel/GearService)

Demo CRM application. Gear Service is compact and easy-to-use solution to collect sales. Presents opportunities to work with customers through interaction with checks: easy and dynamic sorting on client-side by angularJS, safe and quick storing data in reliable databases, analytics need to find bottlenecks in pipeline.

## Technologies
Server-side:
- Java 8 (Stream API, Lambdas, new Date and Time)
- Spring Boot 1.3.3 (Spring Framework, Spring Security, Spring Data, Spring Web)
- MySQL (main DB), MongoDB (secondary DB for blobs), PostgreSQL (DB for heroku), H2 (DB for tests)
- Gradle build tool
- JSoup html parser
- Apache POI (Java API for Microsoft Documents)
- Guava Cache
- other server-side dependencies you can see in [build.gradle](build.gradle)

Client-side:
- Angular JS 1.5.1
- Angular-Material (UI components)
- Moment JS
- Google ReCaptcha
- HTML5, CSS3
- Npm JS, Bower JS package managers
- Grunt JS task runner
- Jasmine framework for testing code
- Protractor E2E-test framework
- Karma unit-test runner
- other client-side dependencies you can see in bower.json for bower and [package.json](package.json)

## Demo
Сonvenient demo at Heroku: https://gearservice.herokuapp.com <br />
Wait a few seconds until the Heroku initializes app. 

For Heroku app was rewritten:
- Migrated from MySQL to PostgreSQL
- Disabled Mongo, all blobs store in PostgreSQL
- Disabled Google reCaptcha

Available following users for login:
- login: 'admin', pass: 'pass' with administrator role
- login: 'engin', pass: 'pass' with engineer restrictions
- login: 'secret', pass: 'pass' with secretary restrictions

## License
The MIT License (MIT)
