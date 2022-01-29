const usersRouter = require('express').Router();
let usersRepo = require("./usersRepo");
let auth = require('basic-auth')

usersRouter.get('/users', function (req, res, next) {
    usersRepo.getByName(auth(req).name, function (data) {
      if(data){
        res.status(200).json(data);
      }
      else {
        res.status(404).json({
          "code": "NOT_FOUND",
          "message": "The user '" + auth(req).name + "' could not be found"
        });
      }
    }, function (err) {
      next(err);
    });
  });
  
  
usersRouter.get('/users/:id', function (req, res, next) {
    usersRepo.getById(req.params.id, function (data) {
      if (data) {
        res.status(200).json(data);
      }
      else {
        res.status(404).json({
          "code": "NOT_FOUND",
          "message": "The user '" + req.params.id + "' could not be found"
          
        });
      }
    }, function(err) {
      next(err);
    });
  });
  

usersRouter.post('/users', function (req, res, next) {
    usersRepo.insert(req.body, function(data) {
      res.status(201).json({
        "code": "CREATED",
        "message": "New user added"
      });
    }, function(err) {
      next(err);
    });
  });
  
usersRouter.put('/users/:id', function(req, res, next){
    usersRepo.update(req.params.id, req.body, function(data){
      if(data){
        res.status(200).json({
          "code": "UPDATED",
          "message": "User " + req.params.id + " updated",
        });
      }
      else{
        res.status(404).json({
          "code": "NOT_FOUND",
          "message": "The user '" + req.params.id + "' could not be found"
        });
      }
    }, function(err){
      next(err)
    });
  });


module.exports = usersRouter;