const mvn = require('maven').create({
  cwd: './Native/Java'
});

mvn.execute(['clean', 'install', 'test', '-Pios']).then(function () {
}, function (err) {
  console.log(err);
  throw err;
}).then(function () {
  mvn.execute(['clean', 'install', 'test', '-Pandroid']).then(function () {
  }, function (err) {
    console.log(err);
    throw err;
  });
});
process.on('unhandledRejection', (reason) => {
  console.log('REJECTION', reason);
  process.exit(1);
});
