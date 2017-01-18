// Reference Configuration File
//
// This file shows all of the configuration options that may be passed
// to Protractor.
//
// Because this file shows all of the options, if used in practice some
// will be overridden or ignored. If you're looking for a seed configuration
// file, see example/conf.js

exports.config = {
    // Spec patterns are relative to the location of this config.
    // specs: ['src/test/todo-spec.js'],
    specs: ['src/test/javascript/e2e/**/*.js'],
    seleniumAddress: 'http://localhost:4444/wd/hub',

    // Patterns to exclude.
    //exclude: [],

    // Alternatively, suites may be used. When run without a command line
    // parameter, all suites will run. If run with --suite=smoke or
    // --suite=smoke,full only the patterns matched by the specified suites will
    // run.
    // suites: {
    //     filter: 'test/client/e2e/filters/*.js',
    //     header: 'test/client/e2e/header/*.js',
    //     create: 'test/client/e2e/create/*.js',
    //     edit: 'test/client/e2e/edit/edit.js',
    //     datetimepicker: 'test/client/e2e/datetimepicker/*.js',
    //     full: 'test/client/e2e/**/*.js'
    // },

    // ---------------------------------------------------------------------------
    // ----- How to set up browsers ----------------------------------------------
    // ---------------------------------------------------------------------------
    //
    // Protractor can launch your tests on one or more browsers. If you are
    // testing on a single browser, use the capabilities option. If you are
    // testing on multiple browsers, use the multiCapabilities array.

    // For a list of available capabilities, see
    // https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
    //
    // In addition, you may specify count, shardTestFiles, and maxInstances.
    capabilities: {
        browserName: 'chrome'
    },

    // If you would like to run more than one instance of WebDriver on the same
    // tests, use multiCapabilities, which takes an array of capabilities.
    // If this is specified, capabilities will be ignored.
    //multiCapabilities: [{
    //    browserName: 'chrome'
    //}, {
    //    browserName: 'firefox'
    //}],

    // If you need to resolve multiCapabilities asynchronously (i.e. wait for
    // server/proxy, set firefox profile, etc), you can specify a function here
    // which will return either `multiCapabilities` or a promise to
    // `multiCapabilities`.
    // If this returns a promise, it is resolved immediately after
    // `beforeLaunch` is run, and before any driver is set up.
    // If this is specified, both capabilities and multiCapabilities will be
    // ignored.
    //getMultiCapabilities: null,

    // Maximum number of total browser sessions to run. Tests are queued in
    // sequence if number of browser sessions is limited by this parameter.
    // Use a number less than 1 to denote unlimited. Default is unlimited.
    maxSessions: 1,

    // ---------------------------------------------------------------------------
    // ----- Global test information ---------------------------------------------
    // ---------------------------------------------------------------------------
    //
    // A base URL for your application under test. Calls to protractor.get()
    // with relative paths will be prepended with this.
    baseUrl: 'https://localhost',

    // CSS Selector for the element housing the angular app - this defaults to
    // body, but is necessary if ng-app is on a descendant of <body>.
    //rootElement: 'body',

    // The timeout in milliseconds for each script run on the browser. This should
    // be longer than the maximum time your application needs to stabilize between
    // tasks.
    //allScriptsTimeout: 11000,

    // How long to wait for a page to load.
    //getPageTimeout: 10000,

    // A callback function called once protractor is ready and available, and
    // before the specs are executed.
    // If multiple capabilities are being run, this will run once per
    // capability.
    // You can specify a file containing code to run by setting onPrepare to
    // the filename string.
    onPrepare: function() {
        browser.get('/login');
        browser.executeScript("arguments[0].setAttribute('style', 'zoom:100%')", element(by.css('body')));
        browser.driver.manage().window().maximize();
        browser.waitForAngular();
        element(by.id('username')).sendKeys('admin');
        element(by.id('password')).sendKeys('b' + protractor.Key.ENTER);
        browser.waitForAngular();

    },

    // ---------------------------------------------------------------------------
    // ----- The test framework --------------------------------------------------
    // ---------------------------------------------------------------------------

    // Test framework to use. This may be one of:
    //  jasmine, jasmine2, cucumber, mocha or custom.
    //
    // When the framework is set to "custom" you'll need to additionally
    // set frameworkPath with the path relative to the config file or absolute
    //  framework: 'custom',
    //  frameworkPath: './frameworks/my_custom_jasmine.js',
    // See github.com/angular/protractor/blob/master/lib/frameworks/README.md
    // to comply with the interface details of your custom implementation.
    //
    // Jasmine and Jasmine2 are fully supported as test and assertion frameworks.
    // Mocha and Cucumber have limited support. You will need to include your
    // own assertion framework (such as Chai) if working with Mocha.
    framework: 'jasmine2',

    // Options to be passed to jasmine2.
    //
    // See https://github.com/jasmine/jasmine-npm/blob/master/lib/jasmine.js
    // for the exact options available.
    jasmineNodeOpts: {
        // If true, print colors to the terminal.
        showColors: true,
        // Default time to wait in ms before a test fails.
        defaultTimeoutInterval: 30000,
        // Function called to print jasmine results.
        //print: function() {},
        // If set, only execute specs whose names match the pattern, which is
        // internally compiled to a RegExp.
        //grep: 'pattern',
        // Inverts 'grep' matches
        //invertGrep: false
    }
};