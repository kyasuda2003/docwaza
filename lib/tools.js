// Generated by CoffeeScript 1.8.0
(function() {
  var clone, mongoose;

  mongoose = require("mongoose");

  module.exports = {
    generateGUID: function() {
      return mongoose.Types.ObjectId();
    },
    isJSON: function(str) {
      if (/^[\],:{}\s]*$/.test(text.replace(/\\["\\\/bfnrtu]/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) {
        return true;
      } else {
        return false;
      }
    },
    cloneJSON: clone = function(a) {
      return JSON.parse(JSON.stringify(a));
    },
    lookup: function(obj, field) {
      var chain, i, len, prop;
      if (!obj) {
        return null;
      }
      chain = field.split("]").join("").split("[");
      i = 0;
      len = chain.length;
      while (i < len) {
        prop = obj[chain[i]];
        if (typeof prop === "undefined") {
          return null;
        }
        if (typeof prop !== "object") {
          return prop;
        }
        obj = prop;
        i++;
      }
      return null;
    }
  };

}).call(this);