apiProxy = (url) ->
  (req, res, next) ->
    if req.url.match(new RegExp("^/api/"))
      
      #console.log(httpProxy);
      proxy = httpProxy.createProxyServer({})
      proxy.web req, res,
        target: url

    else
      next()
    return

webAccess = ->
  (req, res, next) ->
    
    #Admin access
    if req.url.match(new RegExp("^/admin/"))
      
      #res.send(req.path.substring(6));
      p = currentAdminPath + req.path.substring(6)
      if path.existsSync(p)
        console.log "loading.. " + p
        res.sendfile p
      else
        res.send "Thank you for your inquiry."
    
    #Public Access
    else
      vpath = ((if req.path is "/" then "/index.html" else path.resolve(req.path)))
      if path.existsSync(currentPath + vpath)
        console.log "loading.. " + vpath
        res.sendfile currentPath + vpath
      else
        res.send "Thanks for your inquery."
    return

_api_port = 8999
_app_port = 9000
express = require("express")
path = require("path")
console.log "__dirname:" + __dirname
currentPath = path.resolve(__dirname, "..", "..", "./theta/trunk/app")
currentAdminPath = path.resolve(__dirname, "..", "..", "./xi/trunk/app")
console.log "currentPath:" + currentPath
exp = express()
httpProxy = require("http-proxy")

exp.configure ->
  #exp.use(express.bodyParser());
  exp.use apiProxy("http://localhost:" + _api_port)
  exp.use webAccess()
  return

#exp.use(exp.router);
exp.listen _app_port
console.log "The application's listening on the port# " + _app_port
process.on "uncaughtException", (err) ->
  console.log "Application exits. err: " + err
  return

process.on "SIGTERM", (err) ->
  console.log "Application killed successfully. err: " + err
  return
