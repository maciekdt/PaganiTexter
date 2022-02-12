let dbService = require("../service/dbService");
const bcrypt = require('bcrypt');
const saltRounds = 10;


let usersRepo = {
  getUserByNameAndPass: function(name, pass, resolve, reject){
    dbService.getUser(name, 
      function(user){
        if(user){
          bcrypt.compare(pass, user.password, function(err, result) {
            if(result) resolve(user);
            else resolve(false);
        });
        }
        else resolve(false);
      },
      function(err){
        reject(err);
    });
  },

  insertUser: function(user, resolve, reject){
    bcrypt.hash(user.password, saltRounds, function(err, hash){
      user.password = hash;
      dbService.insertUser(user, 
        function(){
          resolve();
        },
        function(err){
          reject(err);
        });
    });
  }
};

module.exports = usersRepo;