module.exports = function(grunt) {
    grunt.initConfig({
        nggettext_extract: {
            pot: {
                options: {
                    extensions: {
                        htm: 'html',
                        html: 'html',
                        php: 'html',
                        phtml: 'html',
                        tml: 'html',
                        aspx: 'html',
                        js: 'js'
                    }
                },
                files: {
                    'i18n/template.pot': [
                        'app/server/views/*.html'
                    ]
                }
            }
        },
        nggettext_compile: {
            all: {
                files: {
                    'app/client/translations/translations.js': ['i18n/*.po']
                }
            }
        },
        useminPrepare: {
            html: 'src/main/resources/conf.usemin.html',
            options: {
                dest: 'src/'
            }
        },
        clean: ['.tmp/templates/'],
        usemin: {
            html: 'resources/static/templates/index.html'
        },
        ngtemplates: {
            app: {
                options: {
                    module: "templates",
                    standalone: true,
                    usemin: '/main/resources/static/javascript/application.min.js',
                    htmlmin:  {
                        collapseBooleanAttributes:      true,
                        collapseWhitespace:             true,
                        removeAttributeQuotes:          true,
                        removeComments:                 true, // Only if you don't use comment directives!
                        removeEmptyAttributes:          true,
                        removeRedundantAttributes:      true,
                        removeScriptTypeAttributes:     true,
                        removeStyleLinkTypeAttributes:  true
                    },
                    url: function(url) { return url.replace('src/main/webapp/', ''); }
                },
                src: "src/main/webapp/**/*.html",
                dest: ".tmp/templates/templates.js"
            }
        },
        karma: {
            unit: {
                configFile: 'karma.conf.js'
            }
        },
        protractor: {
            options: {
                keepAlive: true, // If false, the grunt process stops when the test fails.
                noColor: false, // If true, protractor will not use colors in its output.
                args: {}
            },
            local: {
                options: {
                    configFile: "protractor.conf.js",
                    args: {}
                }
            }
        }

    });

    require('time-grunt')(grunt);
    require('load-grunt-tasks')(grunt);

    grunt.registerTask('default', [
        'newer:ngtemplates',
        'newer:useminPrepare',
        'newer:concat',
        'newer:cssmin',
        'newer:uglify',
        'newer:usemin']);
    grunt.registerTask('extract', ['nggettext_extract']);
    grunt.registerTask('compile', ['nggettext_compile']);
    grunt.registerTask('pr', ['protractor']);
    grunt.registerTask('ka', ['karma']);
};
