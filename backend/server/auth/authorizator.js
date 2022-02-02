let tokenHandler = require('./tokenHandler');

let authorizator = {
    authorize: function(req, res, next){
        tokenHandler.verifyToken(req.header("authorization"), 
            function(id){
                next();
            },
            function(err){
                res.status(401).send("Not valid token");
        });
    }, 

    checkToken: function(req, res, next){
        tokenHandler.verifyToken(req.header("authorization"), 
            function(id){
                res.status(200).send("Valid token")
            },
            function(err){
                res.status(401).send("Not valid token");
        });
    }
}

module.exports = authorizator;