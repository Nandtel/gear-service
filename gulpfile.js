const gulp = require('gulp');
const concat = require('gulp-concat');
const newer = require('gulp-newer');
const uglify = require('gulp-uglify');
const templateCache = require('gulp-angular-templatecache');
const htmlmin = require('gulp-htmlmin');
const cleanCSS = require('gulp-clean-css');
const ngAnnotate = require('gulp-ng-annotate');

const staticDir = 'src/main/resources/static/';
const webAppDir = 'src/main/javascript/';

const jslib = [
    'node_modules/lodash/lodash.min.js',
    'node_modules/ng-file-upload/dist/ng-file-upload-shim.min.js',
    'node_modules/angular/angular.min.js',
    'node_modules/angular-animate/angular-animate.min.js',
    'node_modules/angular-aria/angular-aria.min.js',
    'node_modules/angular-gettext/dist/angular-gettext.min.js',
    'node_modules/angular-ui-router/release/angular-ui-router.min.js',
    'node_modules/moment/min/moment.min.js',
    'node_modules/moment/locale/ru.js',
    'node_modules/angular-moment/angular-moment.min.js',
    'node_modules/angular-material/angular-material.min.js',
    'node_modules/angular-material-data-table/dist/md-data-table.min.js',
    'node_modules/angular-loading-bar/build/loading-bar.min.js',
    'node_modules/angular-cache/dist/angular-cache.min.js',
    'node_modules/angular-scroll/angular-scroll.min.js',
    'node_modules/ng-file-upload/dist/ng-file-upload.min.js',
    'node_modules/angular-recaptcha/release/angular-recaptcha.min.js',
    'node_modules/angular-file-saver/dist/angular-file-saver.bundle.min.js'
];

const csslib = [
    'node_modules/angular-material/angular-material.min.css',
    'node_modules/angular-material-data-table/dist/md-data-table.min.css',
    'node_modules/angular-loading-bar/build/loading-bar.min.css'
];

gulp.task('source-concat', function() {
    return gulp.src(jslib)
        .pipe(newer(staticDir + 'javascript/source.min.js'))
        .pipe(concat('source.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest(staticDir + 'javascript/'))
});

gulp.task('design-concat', function() {
    return gulp.src(csslib)
        .pipe(newer(staticDir + 'stylesheets/design.min.css'))
        .pipe(concat('design.min.css'))
        .pipe(cleanCSS())
        .pipe(gulp.dest(staticDir + 'stylesheets/'))
});

gulp.task('app-concat', function () {
    return gulp.src([
        webAppDir + 'app.js',
        webAppDir + 'app/**/*.js',
        webAppDir + 'directives/**/*.js',
        webAppDir + 'services/**/*.js',
        webAppDir + 'translations/**/*.js'
    ])
        .pipe(newer(staticDir + 'javascript/application.min.js'))
        .pipe(concat('application.min.js'))
        .pipe(ngAnnotate())
        .pipe(uglify())
        .pipe(gulp.dest(staticDir + 'javascript/'))
});

gulp.task('css-concat', function () {
    return gulp.src([webAppDir + '**/*.css'])
        .pipe(newer(staticDir + 'stylesheets/main.min.css'))
        .pipe(concat('main.min.css'))
        .pipe(cleanCSS())
        .pipe(gulp.dest(staticDir + 'stylesheets/'))
});

gulp.task('template-concat', function () {
    return gulp.src([
        webAppDir + '/**/*.html'
    ])
        .pipe(htmlmin({collapseWhitespace: true}))
        .pipe(templateCache({
            module:'templates',
            standalone: true,
            filename: "templates.min.js"
        }))
        .pipe(gulp.dest(staticDir + 'javascript/'));
});

gulp.task('default', ['source-concat', 'design-concat', 'app-concat', 'css-concat', 'template-concat']);

gulp.task('watch', function() {
    gulp.watch(webAppDir + '**/*.js', ['app-concat']);
    gulp.watch(webAppDir + '**/*.html', ['template-concat']);
    gulp.watch(webAppDir + '**/*.css', ['css-concat']);
});