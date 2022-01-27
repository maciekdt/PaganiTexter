let fs = require('fs');

const FILE_NAME = './error_handler/log.txt';

let logRepo = {
  write: function (data, resolve, reject) {
    let date = new Date();
    let toWrite = "Time/Date: " + date.toLocaleTimeString() + " / " + date.toLocaleDateString() + "\r\n";
    toWrite += "Exception Info: " + JSON.stringify(data) + "\r\n\r\n";
    fs.appendFile(FILE_NAME, toWrite, function (err) {
      if (err) {
        reject(err);
      }
      else {
        resolve(true);
      }
    });
  }
};

module.exports = logRepo;