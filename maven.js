const mvn = require('maven').create({
  cwd: './Java'
});

mvn.execute(['clean', 'install', 'test', '-Pios'], { 'skipTests': false }).then(function () {
}, function (err) {
  // this handler is only needed if you need the console.log(err) here
  console.log(err);
  throw err;
}).then(function () {
  mvn.execute(['clean', 'install', 'test', '-Pandroid'], { 'skipTests': false }).then(function () {
  }, function (err) {
    // this handler is only needed if you need the console.log(err) here
    console.log(err);
    throw err;
  });
});
process.on('unhandledRejection', (reason) => {
  console.log('REJECTION', reason);
  process.exit(1);
});
