let express = require('express');
let app = express();
let usersRepo = require("./repos/usersRepo")

let router = express.Router();
app.use(express.json());


router.get('/', function (req, res, next) {
  usersRepo.get(function (data) {
    res.status(200).json({
      "status": 200,
      "statusText": "OK",
      "message": "All users retrieved.",
      "data": data
    });
  }, function (err) {
    next(err);
  });
});


router.get('/:id', function (req, res, next) {
  usersRepo.getById(req.params.id, function (data) {
    if (data) {
      res.status(200).json({
        "status": 200,
        "statusText": "OK",
        "message": "Single user retrieved.",
        "data": data
      });
    }
    else {
      res.status(404).send({
        "status": 404,
        "statusText": "Not Found",
        "message": "The user '" + req.params.id + "' could not be found.",
        "error": {
          "code": "NOT_FOUND",
          "message": "The user '" + req.params.id + "' could not be found."
        }
      });
    }
  }, function(err) {
    next(err);
  });
});

router.post('/', function (req, res, next) {
  usersRepo.insert(req.body, function(data) {
    res.status(201).json({
      "status": 201,
      "statusText": "Created",
      "message": "New user added.",
      "data": data
    });
  }, function(err) {
    next(err);
  });
});



app.use('/api/', router);

var server = app.listen(5000, function () {
  console.log('Node server is running on http://localhost:5000..');
});