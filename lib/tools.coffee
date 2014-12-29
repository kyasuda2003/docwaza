mongoose = require("mongoose")
module.exports =
  generateGUID: ->
    mongoose.Types.ObjectId()

  isJSON: (str) ->
    if /^[\],:{}\s]*$/.test(text.replace(/\\["\\\/bfnrtu]/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))
      true
    else
      false

  cloneJSON: clone = (a) ->
    JSON.parse JSON.stringify(a)

  lookup: (obj, field) ->
    return null  unless obj
    chain = field.split("]").join("").split("[")
    i = 0
    len = chain.length

    while i < len
      prop = obj[chain[i]]
      return null  if typeof (prop) is "undefined"
      return prop  if typeof (prop) isnt "object"
      obj = prop
      i++
    null