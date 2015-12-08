# GearService
[![Build Status](https://travis-ci.org/Nandtel/GearService.svg?branch=master)](https://travis-ci.org/Nandtel/GearService)

Demo CRM application. Gear Service is compact and easy-to-use solution to collect your sales. Presents opportunities to work with customers through interaction with checks: easy and dynamic sorting on client-side by angularJS, safe and quick storing data in reliable databases, analytics you need to find bottlenecks in your pipeline. You become faster and more responsible with Gear Service.

# Technologies
Server-side:
- Java 8 (Stream API, Lambdas, new Date and Time)
- Spring Boot 1.3.0 (Spring Framework, Spring Security, Spring Data, Spring Web)
- MySQL (main db), MongoDB (secondary db for blobs), PostgreSQL (db for heroku), H2 (db for tests)
- Gradle build tool
- JSoup html parser
- Apache POI (Java API for Microsoft Documents)
- Guava Cache
- another server-side dependencies you can see in build.gradle in root of project

Client-side:
- Angular JS 1.5.0-RC6
- Angular-Material (UI components)
- Moment JS
- Google ReCaptcha
- HTML5, CSS3
- Npm JS package manager
- Bower JS package manager
- Grunt JS task runner
- Jasmine framework for testing code
- Protractor E2E-test framework
- Karma unit-test runner
- another client-side dependencies you can see in bower.json for bower and package.json for npm in root of project  

# License
The MIT License (MIT)
