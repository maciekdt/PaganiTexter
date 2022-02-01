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
    }
}

module.exports = authorizator;