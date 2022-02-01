let express = require('express');
let app = express();
let errorHelper = require('./error_handler/errorHelpers');
let usersRouter = require("./users/usersRouter");
let authRouter = require('./auth/authRouter');
let auth = require('./auth/authorizator');

app.use(express.json());
app.use('/auth', authRouter);
app.use(auth.authorize);
app.use('/users', usersRouter);
app.use(errorHelper.logErrors);
app.use(errorHelper.errorHandler);

var server = app.listen(5000, function () {
  console.log('Node server is running on http://localhost:5000..');
  // lt --port 5000 --subdomain maciekdt 
});