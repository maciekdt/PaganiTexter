let jwt = require('jsonwebtoken');
let fs = require("fs");
let path = require("path");
const privateKey  = fs.readFileSync('./logs/private.txt');
const publicKey  = fs.readFileSync('./keys/public.txt', 'utf8');
const expiresTime = "30s";


let tokenHandler = {

    getToken: function(userId, resolve, reject){
        jwt.sign({claimId: userId}, privateKey, {expiresIn: expiresTime, algorithm: "RS256"}, function(err, token){
            if(err){
                console.log(err);
                reject(err);}
            else resolve(token);
        });
    },

    verifyToken: function(token, resolve, reject){
        jwt.verify(token, publicKey, {algorithm: ["RS256"]}, function(err, decoded){
            if(err) reject(err);
            else resolve(decoded.claimId)
        });
    }
};

module.exports = tokenHandler;