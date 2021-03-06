// Generated by CoffeeScript 1.8.0
(function() {
  var _ref1, _ref2;

  console.log("**************\n* automation *\n**************\n");

  _ref1 = require("./../lib/shell-helper");

  _ref2 = require("./../lib/shell-helper");

  _ref1.series(["coffee -c ./../"], function(err) {
    console.log(err);
  });

  _ref1.series(["mongod"], function(err) {
    console.log(err);
  });

  _ref2.series(["node ./../app/app"], function(err) {
    console.log(err);
  });

  _ref2.series(["node ./../app/rest"], function(err) {
    console.log(err);
  });

}).call(this);
