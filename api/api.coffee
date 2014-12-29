inj = require("./../schema/schema-injection.js")
tools = require("./../lib/tools.js")
fs = require("fs")
path = require("path")
imgPath = path.resolve(__dirname, "..", "./docs/img/fullsize/")

exports.addProd = (req, res) ->
  prod = new inj.Product(
    name: req.body.name
    code: req.body.code
    sizes: req.body.sizes
    desc: req.body.desc
  )
  prod.save (err, _prod) ->
    if err
      res.send err
    else
      res.send _prod
    return

  return

exports.updateProd = (req, res) ->
  inj.Product.findById req.body.id, (err, prod) ->
    i = 0

    while i < req.body.actions.length
      switch req.body.actions[i]
        when "name"
          prod.name = req.body.name
        when "code"
          prod.code = req.body.code
        when "sizes"
          prod.sizes = req.body.sizes
        when "desc"
          prod.desc = req.body.desc
        when "images"
          prod.images = req.body.images
      i++
    prod.save (err) ->
      if err
        res.send err
      else
        res.send [product: prod]
      return

    return

  return

exports.getProd = (req, res) ->
  inj.Product.findById req.params.id, (error, prod) ->
    res.json product: prod
    return

  return

exports.getProdList = (req, res) ->
  inj.Product.find (err, products) ->
    if err
      res.send err
    else
      res.send products
    return

  return

exports.deleteProd = (req, res) ->
  inj.Product.findById req.params.id, (err, prod) ->
    i = 0
    
    #for imgId, image of prod.images
    while i < prod.images.length
      fs.unlink imgPath + "/" + prod.images[i].fileName, (err) ->
        throw err  if err
        console.log "successfully deleted file" + prod.images[i].originalFileName
        return

      i++
    prod.remove (err) ->
      if err
        res.send err
      else
        res.send
          status: "ok"
          prodname: prod.name
      return

    return

  return
  
exports.deleteProdImage = (req, res) ->
  inj.Product.findById req.params.prodId, (err, prod) ->
    imgObj = tools.cloneJSON(prod.images)
    i = 0

    while i < imgObj.length
      if imgObj[i].id is req.params.id
        fn = imgObj[i].fileName
        imgObj.splice i, 1
        prod.images = imgObj
        prod.save (err) ->
          if err
            res.send err
            throw err
          fs.unlink imgPath + "/" + fn, (err) ->
            if err
              res.send err
              throw err
            res.send prod
            return

          return

      i++
    return

  return

exports.getFullSizeImage = (req, res) ->
  fp = imgPath + "/" + req.params.id
  stat = fs.statSync(fp)
  res.writeHead 200,
    "Content-Type": req.query.type
    "Content-Length": stat.size

  readStream = fs.createReadStream(fp)
  readStream.pipe res
  return