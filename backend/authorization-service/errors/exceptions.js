let codes = require('./exceptionCodes');

let exceptions = {
    userAlreadyUsedException: function(message){
        const error = new Error(message);
        error.code = codes.userNameAlreadyUsed;
        return error;
    },

    emailAlreadyUsedException: function(message){
        const error = new Error(message);
        error.code = codes.emailAlreadyUsed;
        return error;
    }
}

module.exports = exceptions;