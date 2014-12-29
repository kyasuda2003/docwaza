exports.exec = (cmd, cb) ->
  child_process = require("child_process")
  parts = cmd.split(/\s+/g)
  p = child_process.spawn(parts[0], parts.slice(1),
    stdio: "inherit"
  )
  p.on "exit", (code) ->
    err = null
    if code
      err = new Error("command \"" + cmd + "\" exited with wrong status code \"" + code + "\"")
      err.code = code
      err.cmd = cmd
    cb and cb(err)
    return
  

  return


#p.on('data',function(data){
#if (data.indexOf('waiting for connections on port')>-1){
#cb&&cb(null);
#}
#});
exports.series = (cmds, cb) ->
  execNext = ->
    exports.exec cmds.shift(), (err) ->
      if err
        cb err
      else
        if cmds.length
          execNext()
        else
          cb null
      return

    return

  execNext()
  return