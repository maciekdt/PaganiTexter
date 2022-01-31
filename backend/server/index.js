let express = require('express');
let app = express();
let usersRepo = require("./users/usersRepo");
let errorHelper = require('./error_handler/errorHelpers');
let auth = require('basic-auth')
let usersRouter = require("./users/usersRouter")


app.use(function(req, res, next){
  usersRepo.authorize(auth(req), 
  function(result){
    if(result) next();
    else res.status(401).send();
  }, 
  function(err){
    next(err);
  });
});


app.use(express.json());
app.use('/api/', usersRouter);
app.use(errorHelper.logErrors);
app.use(errorHelper.errorHandler);

var server = app.listen(5000, function () {
  console.log('Node server is running on http://localhost:5000..');
  // lt --port 5000 --subdomain maciekdt 
});