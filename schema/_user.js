// Generated by CoffeeScript 1.8.0
(function() {
  var ObjectId, Schema, mongoos, userSchema;

  mongoos = require(mongoose);

  Schema = mongoose.Schema;

  ObjectId = Schema.ObjectId;

  userSchema = new Schema({
    username: String,
    secret: String,
    email: String,
    apikey: String,
    fname: String,
    lname: String
  });

  module.exports = userSchema;

}).call(this);