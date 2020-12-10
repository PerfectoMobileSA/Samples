const mvn = require('maven').create({
    cwd: './Java'
  });
  mvn.execute(['clean', 'install', 'test' ,'-Pios'], { 'skipTests': false });
  mvn.execute(['clean', 'install', 'test' ,'-Pandroid'], { 'skipTests': false });