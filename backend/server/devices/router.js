const router = require('express').Router();
let usersRepo = require("../repos/usersRepo");

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


module.exports = router;