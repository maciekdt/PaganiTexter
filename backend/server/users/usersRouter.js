const usersRouter = require('express').Router();
let usersRepo = require("./usersRepo");
let auth = require('basic-auth')

usersRouter.get('/users', function (req, res, next) {
    usersRepo.getByName(auth(req).name, 
    function (data) {
      if(data) res.status(200).send(data);
      else res.status(404).send();
    }, 
    function (err) {
      next(err);
    });
  });
  
  
usersRouter.get('/users/:id', function (req, res, next) {
    usersRepo.getById(req.params.id, 
    function (data) {
      if (data) res.status(200).send(data);
      else res.status(404).send();   
    }, 
    function(err) {
      next(err);
    });
  });
  

usersRouter.post('/users', function (req, res, next) {
    usersRepo.insert(req.body, 
    function(data) {
      res.status(201).send();
    }, 
    function(err) {
      next(err);
    });
  });
  
usersRouter.put('/users/:id', function(req, res, next){
    usersRepo.update(req.params.id, req.body, 
    function(data){
      if(data) res.status(200).send();
      else res.status(404).send();
    }, 
    function(err){
      next(err)
    });
  });


module.exports = usersRouter;