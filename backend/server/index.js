let express = require('express');
let app = express();
let usersRepo = require("./users/usersRepo");
let errorHelper = require('./error_handler/errorHelpers');
let auth = require('basic-auth')
let usersRouter = require("./users/usersRouter")


app.use(function(req, res, next){
  usersRepo.authorize(auth(req), function(result){
    if(result){
      next();
    }
    else{
      res.status(401).json({
        "statusText": "Not Authorized",
        "message": "User not authorized, wrong password or login"
      })
    }
  }, function(err){
    next(err);
  })
});


app.use(express.json());
app.use('/api/', usersRouter);
app.use(errorHelper.logErrors);
app.use(errorHelper.clientErrorHandler);
app.use(errorHelper.errorHandler);

var server = app.listen(5000, function () {
  console.log('Node server is running on http://localhost:5000..');
  // lt --port 3000 --subdomain hello 
});