const authRouter = require('express').Router();
let baseAuth = require('basic-auth');
let tokenHandler = require('./tokenHandler');
let usersRepo = require('../users/usersRepo');
let auth = require('./authorizator')


authRouter.get('/token', function (req, res, next) {
    usersRepo.getUserByName(baseAuth(req).name,
        function(user){
            if(user && user.password == baseAuth(req).pass){
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
    auth.checkToken(req, res, next)
})


module.exports = authRouter;