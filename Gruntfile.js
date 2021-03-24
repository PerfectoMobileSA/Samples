module.exports = function (grunt) {

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    uglify: {
      options: {
        banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n'
      },
      build: {
        src: 'src/<%= pkg.name %>.js',
        dest: 'build/<%= pkg.name %>.min.js'
      }
    },
    replace: {
      creds: {
        src: ['**/**/Java/**/*.java', "**/**/Ruby/**/*.rb", "**/**/Python/**/*.py", "Appium/**/Javascript/wdio/ios/*.js", "Appium/**/Javascript/wdio/android/*.js", "Appium/**/CSharp/**/**/*.cs", "Selenium/Javascript/protractor/*.js", 'Selenium/Java/**/*.java', "Selenium/Ruby/**/*.rb", "Selenium/Python/**/*.py","Selenium/CSharp/**/**/*.cs"],
        overwrite: true,
        replacements: [{
          from: "<<cloud name>>",
          to: process.env.cloudName
        }, {
          from: "<<security token>>",
          to: process.env.securityToken
        }]
      }
    }
  });

  // Load the plugin that provides the "uglify" task.
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-text-replace');
  // Default task(s).
  grunt.registerTask('default', ['uglify']);

};
