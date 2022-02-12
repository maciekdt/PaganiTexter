const authRouter = require('express').Router();
let baseAuth = require('basic-auth');
let tokenHandler = require('./tokenHandler');
let usersRepo = require('../users/usersRepo');
let fs = require("fs");
let path = require("path");


authRouter.get('/token', function (req, res, next) {
    usersRepo.getUserByNameAndPass(baseAuth(req).name, baseAuth(req).pass,
        function(user){
            if(user){
                tokenHandler.getToken(user._id,
                    function(token){
                        res.status(200).send({
                            "token": token,
                            "userId": user._id
                        });
                    },
                    function(err){
                        next(err);
                });
            }
            else res.status(401).send("Wrong login or password"); 
        },
        function(err){
            next(err);
        }
    );
});

authRouter.get('/checkToken', function(req, res, next){
    tokenHandler.verifyToken(req.header("authorization"), 
        function(id){
            res.status(200).send("Valid token");
        },
        function(err){
            res.status(401).send("Not valid token");
    });
})

authRouter.get('/publicKey', function(req, res, next){
    res.status(200).send({
        "algorithm": tokenHandler.getAlgorithmName(),
        "key": tokenHandler.getPublicKey()
        });
})

authRouter.post('/register', function(req, res, next){
    usersRepo.insertUser(req.body, 
        function(){
            res.status(201).send();
        },
        function(err){
            next(err);
    });
})


module.exports = authRouter;