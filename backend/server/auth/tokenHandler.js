let jwt = require('jsonwebtoken');
const privateKey = "TQaYFHRCLEE0vrOiVNKZ";
const expiresTime = "30s";


let tokenHandler = {

    getToken: function(userId, resolve, reject){
        jwt.sign({claimId: userId}, privateKey, {expiresIn: expiresTime}, function(err, token){
            if(err) reject(err);
            else resolve(token);
        });
    },

    verifyToken: function(token, resolve, reject){
        jwt.verify(token, privateKey, function(err, decoded){
            if(err) reject(err);
            else resolve(decoded.claimId)
        });
    }
};

module.exports = tokenHandler;