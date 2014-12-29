//#! /usr/local/bin/node

console.log('**************\n\
             * automation *\n\
             **************\n');

var _ref1=require('./../lib/shell-helper');
var _ref2=require('./../lib/shell-helper');

_ref1.series(['mongod'],function(err){
	console.log(err);
});

_ref2.series(['node ./../app/app'],function(err){console.log(err);});
_ref2.series(['node ./../app/rest'],function(err){console.log(err);});


/*
var cp=require('child_process');
var mongod=cp.spawn('mongod',[]);

mongod.stdout.on('data',function(data){
	console.log('stdout: '+data);
});
mongod.stderr.on('data',function(data){
	console.log('stderr: '+data);
});
mongod.on('close',function(code){
	console.log('child process exited with code '+code);
});

*/