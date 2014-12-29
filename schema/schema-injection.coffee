mongoose = require("mongoose")
Schema = mongoose.Schema
ObjectId = Schema.ObjectId
prodSchema = require("./product.js")
uristring = "mongodb://localhost/poe"
mongoose.connect uristring, (err, res) ->
  if err
    console.log "ERROR connecting to: " + uristring + ". " + err
  else
    console.log "Succeeded connected to: " + uristring
  return


#
#module.exports={
#    generateGUID:function(){return mongoose.Types.ObjectId();},
#    isJSON:function(str) {
#	if (/^[\],:{}\s]*$/.test(text.replace(/\\["\\\/bfnrtu]/g, '@').
#				 replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').
#				 replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
#	    return true;
#	}
#	else{
#	    return false;
#	}
#    },
#    cloneJSON:function clone(a) {
#	return JSON.parse(JSON.stringify(a));
#    }
#}
#
module.exports.Product = mongoose.model("Product", prodSchema)