// Generated by CoffeeScript 1.8.0
(function() {
  var fs, imgPath, inj, path, tools;

  inj = require("./../schema/schema-injection.js");

  tools = require("./../lib/tools.js");

  fs = require("fs");

  path = require("path");

  imgPath = path.resolve(__dirname, "..", "./docs/img/fullsize/");

  exports.addProd = function(req, res) {
    var prod;
    prod = new inj.Product({
      name: req.body.name,
      code: req.body.code,
      sizes: req.body.sizes,
      desc: req.body.desc
    });
    prod.save(function(err, _prod) {
      if (err) {
        res.send(err);
      } else {
        res.send(_prod);
      }
    });
  };

  exports.updateProd = function(req, res) {
    inj.Product.findById(req.body.id, function(err, prod) {
      var i;
      i = 0;
      while (i < req.body.actions.length) {
        switch (req.body.actions[i]) {
          case "name":
            prod.name = req.body.name;
            break;
          case "code":
            prod.code = req.body.code;
            break;
          case "sizes":
            prod.sizes = req.body.sizes;
            break;
          case "desc":
            prod.desc = req.body.desc;
            break;
          case "images":
            prod.images = req.body.images;
        }
        i++;
      }
      prod.save(function(err) {
        if (err) {
          res.send(err);
        } else {
          res.send([
            {
              product: prod
            }
          ]);
        }
      });
    });
  };

  exports.getProd = function(req, res) {
    inj.Product.findById(req.params.id, function(error, prod) {
      res.json({
        product: prod
      });
    });
  };

  exports.getProdList = function(req, res) {
    inj.Product.find(function(err, products) {
      if (err) {
        res.send(err);
      } else {
        res.send(products);
      }
    });
  };

  exports.deleteProd = function(req, res) {
    inj.Product.findById(req.params.id, function(err, prod) {
      var i;
      i = 0;
      while (i < prod.images.length) {
        fs.unlink(imgPath + "/" + prod.images[i].fileName, function(err) {
          if (err) {
            throw err;
          }
          console.log("successfully deleted file" + prod.images[i].originalFileName);
        });
        i++;
      }
      prod.remove(function(err) {
        if (err) {
          res.send(err);
        } else {
          res.send({
            status: "ok",
            prodname: prod.name
          });
        }
      });
    });
  };

  exports.addImage = function(req, res) {
    console.log(req.files);
    fs.readFile(req.files.image.path, function(err, data) {
      var imgId, newPath, op;
      if (!req.files.image.name) {
        console.log("There was an error");
        res.redirect("/");
        res.end();
      } else {
        imgId = tools.generateGUID();
        op = imgId;
        newPath = imgPath + "/" + op;
        fs.writeFile(newPath, data, function(err) {
          if (err) {
            res.send(err);
          } else {
            res.send({
              status: "ok",
              id: imgId,
              originalFileName: req.files.image.name,
              type: req.files.image.type
            });
          }
        });
      }
    });
  };

  exports.deleteProdImage = function(req, res) {
    inj.Product.findById(req.params.prodId, function(err, prod) {
      var fn, i, imgObj;
      imgObj = tools.cloneJSON(prod.images);
      i = 0;
      while (i < imgObj.length) {
        if (imgObj[i].id === req.params.id) {
          fn = imgObj[i].fileName;
          imgObj.splice(i, 1);
          prod.images = imgObj;
          prod.save(function(err) {
            if (err) {
              res.send(err);
              throw err;
            }
            fs.unlink(imgPath + "/" + fn, function(err) {
              if (err) {
                res.send(err);
                throw err;
              }
              res.send(prod);
            });
          });
        }
        i++;
      }
    });
  };

  exports.getFullSizeImage = function(req, res) {
    var fp, readStream, stat;
    fp = imgPath + "/" + req.params.id;
    stat = fs.statSync(fp);
    res.writeHead(200, {
      "Content-Type": req.query.type,
      "Content-Length": stat.size
    });
    readStream = fs.createReadStream(fp);
    readStream.pipe(res);
  };

}).call(this);
