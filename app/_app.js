var _api_port=8999,_app_port=9000;
var express=require('express');

var path=require('path');

console.log('__dirname:'+__dirname);
var currentPath=path.resolve(__dirname,'..','..','./theta/trunk/app');
var currentAdminPath=path.resolve(__dirname,'..','..','./xi/trunk/app');

console.log('currentPath:'+currentPath);
var exp=express();
var httpProxy = require('http-proxy');

function apiProxy(url) {
  return function(req, res, next) {
    if(req.url.match(new RegExp('^\/api\/'))) {
	//console.log(httpProxy);
	var proxy = httpProxy.createProxyServer({});
	proxy.web(req, res, {target: url});
    } else {
      next();
    }
  }
};

function webAccess() {
    return function(req, res, next) {
	//Admin access
	if(req.url.match(new RegExp('^\/admin\/'))) {
	    //res.send(req.path.substring(6));
	    var p=currentAdminPath+req.path.substring(6);
	    if (path.existsSync(p)){
		console.log('loading.. ' + p);
		res.sendfile(p);
	    }
	    else {
		res.send('Thank you for your inquiry.');
	    }

	}
	//Public Access
	else {
	    var vpath=(req.path==='/'?'/index.html':path.resolve(req.path));
		
	    if (path.existsSync(currentPath+vpath)){
		console.log('loading.. ' +vpath);
		res.sendfile(currentPath+vpath);
	    }
	    else
		res.send('Thanks for your inquery.');
	}	
    }
};

exp.configure(function(){
    //exp.use(express.bodyParser());
    exp.use(apiProxy('http://localhost:'+ _api_port));
    exp.use(webAccess());
    //exp.use(exp.router);
});
 
exp.listen(_app_port);
console.log('The application\'s listening on the port# '+_app_port);
process.on('uncaughtException',function(err){
    console.log('Application exits. err: '+err);
});

process.on('SIGTERM',function(err){
    console.log('Application killed successfully. err: '+err);
});
