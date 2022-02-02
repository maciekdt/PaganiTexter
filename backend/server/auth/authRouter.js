const authRouter = require('express').Router();
let baseAuth = require('basic-auth');
let tokenHandler = require('./tokenHandler');
let usersRepo = require('../users/usersRepo');
let auth = require('./authorizator')


authRouter.get('/token', function (req, res, next) {
    usersRepo.authenticate(baseAuth(req),
        function(user){
            if(user){
                tokenHandler.getToken(user.id,
                    function(token){
                        res.status(200).send({
                            "token": token,
                            "userId": user.id
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