const { MongoClient } = require('mongodb');
const uri = "mongodb+srv://maciek:pass@paganitexterauthcluster.lj906.mongodb.net/retryWrites=true&w=majority";
let client;

let dbService = {
  getUser: async function(userName, resolve, reject){
    try {
      if(!client) client = new MongoClient(uri) ;
      await client.connect();
      let database = client.db("users");
      let users = database.collection("auth_users");
      let query = {name: userName};
      let user = await users.findOne(query);

      await client.close();
      console.log(user);
      await resolve(user)
    }
    catch(err){
      reject(err);
    }
    finally {
      if(client) await client.close();
    }
  }
}

module.exports = dbService;