let fs = require("fs");
const { resolve } = require("path");
const FILE_NAME = "./asssets/users.json"
let usersRepo = {
  get: function (resolve, reject) {
      fs.readFile(FILE_NAME, function (err, data) {
        if (err) {
          reject(err);
        }
        else {
          resolve(JSON.parse(data));
        }
      });
  },
  
  getById: function (id, resolve, reject) {
    fs.readFile(FILE_NAME, function (err, data) {
      if (err) {
        reject(err);
      }
      else {
        let user = JSON.parse(data).find(u => u.id == id);
        resolve(user);
      }
    });
  },


  insert: function (newData, resolve, reject) {
    fs.readFile(FILE_NAME, function (err, data) {
      if (err) {
        reject(err);
      }
      else {
        let users = JSON.parse(data);
        users.push(newData);
        fs.writeFile(FILE_NAME, JSON.stringify(users), function (err) {
          if (err) {
            reject(err);
          }
          else {
            resolve(newData);
          }
        });
      }
    });
  },


  update: function(id, newData, resolv, reject){
    fs.readFile(FILE_NAME, function(err, data){
      if(err){
        reject(err)
      }
      else{
        let users = JSON.parse(data);
        let user = JSON.parse(data).find(u => u.id == id);
        if(user){
          Object.assign(user, newData);
          fs.writeFile(FILE_NAME, JSON.stringify(users), function (err){
            if(err){
              reject(err);
            }
            else{
              resolv(newData);
            }
          });
        }
        //else{
          //resolve(undefined);
        //}
      }
    });
  }   
};


module.exports = usersRepo;