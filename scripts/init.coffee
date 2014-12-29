##! /usr/local/bin/node
console.log "**************\n* automation *\n**************\n"
_ref1 = require("./../lib/shell-helper")
_ref2 = require("./../lib/shell-helper")

_ref1.series ["coffee -c ./../"], (err) ->
  console.log err
  return

_ref1.series ["mongod"], (err) ->
  console.log err
  return

_ref2.series ["node ./../app/app"], (err) ->
  console.log err
  return

_ref2.series ["node ./../app/rest"], (err) ->
  console.log err
  return


#
#var cp=require('child_process');
#var mongod=cp.spawn('mongod',[]);
#
#mongod.stdout.on('data',function(data){
#	console.log('stdout: '+data);
#});
#mongod.stderr.on('data',function(data){
#	console.log('stderr: '+data);
#});
#mongod.on('close',function(code){
#	console.log('child process exited with code '+code);
#});
#
#