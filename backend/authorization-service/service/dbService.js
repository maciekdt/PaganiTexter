const { MongoClient } = require('mongodb');
let exceptions = require('../errors/exceptions')
const uri = "mongodb+srv://maciek:pass@paganitexterauthcluster.lj906.mongodb.net/retryWrites=true&w=majority";
let client;

let dbService = {
  getUser: async function(query, resolve, reject){
    try {
      if(!client) client = new MongoClient(uri) ;
      await client.connect();
      let database = client.db("users");
      let users = database.collection("auth_users");
      let user = await users.findOne(query);

      await client.close();
      await resolve(user)
    }
    catch(err){
      reject(err);
    }
    finally {
      if(client) await client.close();
    }
  },

  insertUser: async function(user, resolve, reject){
    try {
      if(!client) client = new MongoClient(uri) ;
      await client.connect();
      let database = client.db("users");
      let users = database.collection("auth_users");

      let userWithSameName = await users.findOne({name: user.name});
      let userWithSameEmail = await users.findOne({email: user.email});

      if(userWithSameName) reject(exceptions.userAlreadyUsedException())
      else if(userWithSameEmail) reject(exceptions.emailAlreadyUsedException())
      else{
        let result = await users.insertOne(user);
        console.log(`A document was inserted with the _id: ${result.insertedId}`);
        await resolve();
      }
      await client.close();
    }
    catch(err){
      reject(err);
    }
    finally {
      await client.close();
    }
  }
}


module.exports = dbService;