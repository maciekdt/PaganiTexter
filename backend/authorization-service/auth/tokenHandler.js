let jwt = require('jsonwebtoken');
let fs = require("fs");
let path = require("path");
const privateKey  = fs.readFileSync('./assets/keys/private.txt', 'utf8');
const publicKey  = fs.readFileSync('./assets/keys/public.txt', 'utf8');
const algorithmName = "RS256";
const expiresTime = "40s";


let tokenHandler = {

    getToken: function(userId, resolve, reject){
        jwt.sign({claimId: userId}, privateKey, {expiresIn: expiresTime, algorithm: algorithmName}, function(err, token){
            if(err){
                console.log(err);
                reject(err);}
            else resolve(token);
        });
    },

    verifyToken: function(token, resolve, reject){
        jwt.verify(token, publicKey, {algorithm: [algorithmName]}, function(err, decoded){
            if(err) reject(err);
            else resolve(decoded.claimId)
        });
    },

    getPublicKey: function(){
        return publicKey;
    },

    getAlgorithmName: function(){
        return algorithmName;
    }
};

module.exports = tokenHandler;