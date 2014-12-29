module.exports={
    generateGUID:function(){return mongoose.Types.ObjectId();},
    isJSON:function(str) {
        if (/^[\],:{}\s]*$/.test(text.replace(/\\["\\\/bfnrtu]/g, '@')
               .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
	       .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
				     return true;
				 }
				 else{
				     return false;
				 }
    },
    cloneJSON:function clone(a) {
	return JSON.parse(JSON.stringify(a));
    },
    lookup:function(obj, field) {
	if (!obj) { return null; }
	var chain = field.split(']').join('').split('[');
	for (var i = 0, len = chain.length; i < len; i++) {
	    var prop = obj[chain[i]];
	    if (typeof(prop) === 'undefined') { return null; }
	    if (typeof(prop) !== 'object') { return prop; }
	    obj = prop;
	}
	return null;
     }
}