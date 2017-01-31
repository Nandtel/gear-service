# GearService
[![Build Status](https://travis-ci.org/Nandtel/gear-service.svg?branch=master)](https://travis-ci.org/Nandtel/GearService)

This is my own "in production" application, which is works for a little service company and, to my surprise, they're even find it helpfull. This code may be interesting to you only as example of my brutal attempts to realize my vision of good application based on Spring Boot and AngularJS.

## Technologies
Server-side:
- Java 8 (Stream API, Lambdas, new Date and Time)
- Spring Boot 1.4.4 (Spring Framework, Spring Security, Spring Data, Spring Web)
- MySQL (main DB), MongoDB (secondary DB for blobs), PostgreSQL (DB for heroku), H2 (DB for tests)
- Gradle build tool
- JSoup html parser
- Apache POI (Java API for Microsoft Documents)
- Caffeine Cache
- other server-side dependencies you can see in [build.gradle](build.gradle)

Client-side:
- Angular JS 1.5.8
- Angular-Material (UI components)
- Moment JS
- Google ReCaptcha
- HTML5, CSS3
- Npm JS package managers
- Gulp JS task runner
- Jasmine framework for testing code
- Protractor E2E-test framework
- Karma unit-test runner
- other client-side dependencies you can see in bower.json for bower and [package.json](package.json)

## Demo
Сonvenient demo at Heroku: https://gearservice.herokuapp.com <br />
Wait a few seconds until the Heroku initializes app. <br />
The demo isn't updated from repository, so it's always outdated.

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
