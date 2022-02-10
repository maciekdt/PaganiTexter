const { MongoClient } = require('mongodb');

const uri = "mongodb+srv://maciek:pass@paganitexterauthcluster.lj906.mongodb.net/users?retryWrites=true&w=majority";
const client = new MongoClient(uri);
async function run() {
  try {
    await client.connect();
    const database = client.db("users");
    const movies = database.collection("auth_users");
    // Query for a movie that has the title 'The Room'
    const query = { name: "maciekdt" };
    const options = {
      // sort matched documents in descending order by rating
      // Include only the `title` and `imdb` fields in the returned document
      projection: { _id: 0}
    };
    const movie = await movies.findOne(query);
    // since this method returns the matched document, not a cursor, print it directly
    console.log(movie);
  } finally {
    await client.close();
  }
}
run().catch(console.dir);