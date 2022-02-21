let dbService = require("../service/dbService");
const bcrypt = require('bcrypt');
const saltRounds = 10;


let usersRepo = {
  authenticateUser: function(loginData, resolve, reject){
    dbService.getUser({name: loginData.name}, 
      function(user){
        if(user){
          bcrypt.compare(loginData.pass, user.password, function(err, result) {
            if(err) reject(err);
            else if(result) resolve(user._id);
            else resolve(null);
          });
        }
        else resolve(null);
      },
      function(err){
        reject(err);
    });
  },

  
  insertUser: function(user, resolve, reject){
    bcrypt.hash(user.password, saltRounds, function(err, hash){
      if(err) reject(err);
      else{
        user.password = hash;
        dbService.insertUser(user, 
          function(){
            resolve();
          },
          function(err){
            reject(err);
        });
      }
    });
  }
};

module.exports = usersRepo;