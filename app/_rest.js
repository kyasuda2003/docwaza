var _port=8999;
var express=require('express');
var passport=require('passport');
var strategy=require('./../lib/local-strategy').Strategy;
//var mongoose = require('mongoose');

//var path=require('path');

//console.log('__dirname:'+__dirname);
//var currentPath=path.resolve(__dirname,'..','..','..','./theta/trunk/app');
//console.log('currentPath:'+currentPath);
var exp=module.exports=express();

exp.configure(function(){
    exp.use(express.bodyParser());
    exp.use(express.methodOverride());
    exp.use(exp.router);
});

var api = require('./../api/api.js');

exp.post('/api/docs/image/create',api.addImage);
exp.delete('/api/docs/image/delete/:prodId/:id',api.deleteProdImage);
exp.get('/api/docs/image/:id',api.getFullSizeImage);

exp.post('/api/product/create', api.addProd);
exp.post('/api/product/edit',api.updateProd);
exp.delete('/api/product/delete/:id', api.deleteProd);
exp.get('/api/product/item/:id', api.getProd);
exp.get('/api/product/items', api.getProdList);

exp.listen(_port);
console.log('The applistion\'s listening on port# ' +_port); 

process.on('uncaughtException',function(err){
    console.log('Application exits. err: '+err);
});

process.on('SIGTERM',function(err){
    console.log('Application killed successfully. err: '+err);
});
