exports.exec = function(cmd, cb){
    var child_process = require('child_process');
    var parts = cmd.split(/\s+/g);
    var p = child_process.spawn(parts[0], parts.slice(1), {stdio: 'inherit'});
    p.on('exit', function(code){
	    var err = null;
	    if (code) {
		err = new Error('command "'+ cmd +'" exited with wrong status code "'+ code +'"');
		err.code = code;
		err.cmd = cmd;
	    }
	    cb&&cb(err);
	});
    //p.on('data',function(data){
    //if (data.indexOf('waiting for connections on port')>-1){
    //cb&&cb(null);
    //}
    //});
};

exports.series = function(cmds, cb){
    var execNext = function(){
        exports.exec(cmds.shift(), function(err){
		if (err) {
		    cb(err);
		} else {
		    if (cmds.length) execNext();
		    else cb(null);
		}
	    });
    };
    execNext();
};