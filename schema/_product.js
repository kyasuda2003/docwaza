var mongoose = require('mongoose')
   ,Schema = mongoose.Schema
   ,ObjectId = Schema.ObjectId;

var prodSchema = new Schema({
    //id: ObjectId,
    name: String,
    code: String,
    sizes: [String],
    desc: String,
    images: Schema.Types.Mixed,
    date: {type: Date, default: Date.now},
    /*
    author: {type: String, default: 'Anon'},
    */
});

module.exports=prodSchema;
