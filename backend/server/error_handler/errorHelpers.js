const logRepo = require("./logRepo");

let errorHelpers = {

  logErrors: function (err, req, res, next) {
    let errorObject = errorHelpers.errorBuilder(err);
    errorObject.requestInfo = {
      "hostname": req.hostname,
      "path": req.path,
      "app": req.app,
    }
    logRepo.write(errorObject, function (data) {
      console.log(data);
    }, function (err) {
      console.error(err);
    });
    next(err)
  },


  errorHandler: function (err, req, res, next) {
    res.status(500).send();
  },
};

module.exports = errorHelpers;