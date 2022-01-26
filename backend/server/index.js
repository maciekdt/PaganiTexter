let express = require('express');
let app = express();
let usersRepo = require("./repos/usersRepo");
let errorHelper = require('./helpers/errorHelpers');
let auth = require('basic-auth')
let router = express.Router();


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


router.get('/users', function (req, res, next) {
  usersRepo.get(function (data) {
    res.status(200).json({
      "statusText": "OK",
      "message": "All users retrieved",
      "data": data
    });
  }, function (err) {
    next(err);
  });
});


router.get('/users/:id', function (req, res, next) {
  usersRepo.getById(req.params.id, function (data) {
    if (data) {
      res.status(200).json({
        "statusText": "OK",
        "message": "Single user retrieved",
        "data": data
      });
    }
    else {
      res.status(404).send({
        "statusText": "Not Found",
        "message": "The user '" + req.params.id + "' could not be found",
        "error": {
          "code": "NOT_FOUND",
          "message": "The user '" + req.params.id + "' could not be found"
        }
      });
    }
  }, function(err) {
    next(err);
  });
});

router.post('/users', function (req, res, next) {
  usersRepo.insert(req.body, function(data) {
    res.status(201).json({
      "statusText": "Created",
      "message": "New user added",
      "data": data
    });
  }, function(err) {
    next(err);
  });
});

router.put('/users/:id', function(req, res, next){
  usersRepo.update(req.params.id, req.body, function(data){
    if(data){
      res.status(200).json({
        "statusText": "Updated",
        "message": "User " + req.params.id + " updated",
        "data": data
      });
    }
    else{
      res.status(404).send({
        "statusText": "Not Found",
        "message": "User " + req.params.id + " could not be found",
        "error": {
          "code": "NOT_FOUND",
          "message": "User '" + req.params.id + "' could not be found"
        }
      });
    }
  }, function(err){
    next(err)
  });
});



app.use('/api/', router);
// Configure exception logger
app.use(errorHelper.logErrors);
// Configure client error handler
app.use(errorHelper.clientErrorHandler);
// Configure catch-all exception middleware last
app.use(errorHelper.errorHandler);

var server = app.listen(5000, function () {
  console.log('Node server is running on http://localhost:5000..');
});