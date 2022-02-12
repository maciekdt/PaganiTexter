let fs = require("fs");
let dbService = require("../service/dbService")
const { resolve } = require("path");
const FILE_NAME = "./assets/users.json"

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

  getByName: function (name, resolve, reject) {
    fs.readFile(FILE_NAME, function (err, data) {
      if (err) {
        reject(err);
      }
      else {
        let user = JSON.parse(data).find(u => u.name == name);
        resolve(user);
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


  update: function(id, newData, resolve, reject){
    fs.readFile(FILE_NAME, function(err, data){
      if(err){
        reject(err)
      }
      else{
        let users = JSON.parse(data);
        let user = users.find(u => u.id == id);
        if(user){
          Object.assign(user, newData);
          fs.writeFile(FILE_NAME+"1", JSON.stringify(users), function (err){
            if(err){
              reject(err);
            }
            else{
              resolve(newData);
            }
          });
        }
        else{
          resolve(user);
        }
      }
    });
  },


  getUserByName: function(name, resolve, reject){
    dbService.getUser(name, 
      function(user){
        console.log(user);
        resolve(user);
      },
      function(err){
        reject(err);
    });
  }
};

module.exports = usersRepo;