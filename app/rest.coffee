_port = 8999
express = require("express")
#passport = require("passport")
#strategy = require("./../lib/local-strategy").Strategy

exp = express()
exp.configure ->
  exp.use express.bodyParser()
  exp.use express.methodOverride()
  exp.use exp.router
  return

api = require("./../api/api.js")
exp.post "/api/docs/image/create", api.addImage
exp["delete"] "/api/docs/image/delete/:prodId/:id", api.deleteProdImage
exp.get "/api/docs/image/:id", api.getFullSizeImage
exp.post "/api/product/create", api.addProd
exp.post "/api/product/edit", api.updateProd
exp["delete"] "/api/product/delete/:id", api.deleteProd
exp.get "/api/product/item/:id", api.getProd
exp.get "/api/product/items", api.getProdList
exp.listen _port

console.log "The applistion's listening on port# " + _port
process.on "uncaughtException", (err) ->
  console.log "Application exits. err: " + err
  return

process.on "SIGTERM", (err) ->
  console.log "Application killed successfully. err: " + err
  return

module.exports = exp