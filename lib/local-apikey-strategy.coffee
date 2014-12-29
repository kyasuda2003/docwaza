###*
Module dependencies.
###

#, BadRequestError = require('./errors/badrequesterror');

###*
`Strategy` constructor.

The local api key authentication strategy authenticates requests based on the
credentials submitted through an HTML-based login form.

Applications must supply a `verify` callback which accepts `username` and
`password` credentials, and then calls the `done` callback supplying a
`user`, which should be set to `false` if the credentials are not valid.
If an exception occured, `err` should be set.

Optionally, `options` can be used to change the fields in which the
credentials are found.

Options:
- `apiKeyField`  field name where the apikey is found, defaults to _apiKey_
- `apiKeyHeader`  header name where the apikey is found, defaults to _apiKey_
- `passReqToCallback`  when `true`, `req` is the first argument to the verify callback (default: `false`)

Examples:

passport.use(new LocalAPIKeyStrategy(
function(apikey, done) {
User.findOne({ apikey: apikey }, function (err, user) {
done(err, user);
});
}
));

@param {Object} options
@param {Function} verify
@api public
###
Strategy = (options, verify) ->
  if typeof options is "function"
    verify = options
    options = {}
  throw new Error("local authentication strategy requires a verify function")  unless verify
  @_apiKeyField = options.apiKeyField or "apikey"
  @_apiKeyHeader = options.apiKeyHeader or "apikey"
  passport.Strategy.call this
  @name = "localapikey"
  @_verify = verify
  @_passReqToCallback = options.passReqToCallback
  return
passport = require("passport")
util = require("util")

###*
Inherit from `passport.Strategy`.
###
util.inherits Strategy, passport.Strategy

###*
Authenticate request based on the contents of a form submission.

@param {Object} req
@api protected
###
Strategy::authenticate = (req, options) ->
  verified = (err, user, info) ->
    return self.error(err)  if err
    return self.fail(info)  unless user
    self.success user, info
    return
  lookup = (obj, field) ->
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
  options = options or {}
  apikey = lookup(req.body, @_apiKeyField) or lookup(req.query, @_apiKeyField) or lookup(req.headers, @_apiKeyHeader)
  return @fail(new BadRequestError(options.badRequestMessage or "Missing API Key"))  unless apikey
  self = this
  if self._passReqToCallback
    @_verify req, apikey, verified
  else
    @_verify apikey, verified
  return


###*
Expose `Strategy`.
###
module.exports = Strategy