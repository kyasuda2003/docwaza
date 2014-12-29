###*
Module dependencies.
###

###*
`Strategy` constructor.

The local authentication strategy authenticates requests based on the
credentials submitted through an HTML-based login form.

Applications must supply a `verify` callback which accepts `username` and
`password` credentials, and then calls the `done` callback supplying a
`user`, which should be set to `false` if the credentials are not valid.
If an exception occured, `err` should be set.

Optionally, `options` can be used to change the fields in which the
credentials are found.

Options:
- `usernameField`  field name where the username is found, defaults to _username_
- `passwordField`  field name where the password is found, defaults to _password_
- `passReqToCallback`  when `true`, `req` is the first argument to the verify callback (default: `false`)

Examples:

passport.use(new LocalStrategy(
function(username, password, done) {
User.findOne({ username: username, password: password }, function (err, user) {
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
  throw new TypeError("LocalStrategy requires a verify callback")  unless verify
  @_usernameField = options.usernameField or "username"
  @_passwordField = options.passwordField or "password"
  passport.Strategy.call this
  @name = "local"
  @_verify = verify
  @_passReqToCallback = options.passReqToCallback
  return
passport = require("passport-strategy")
util = require("util")
lookup = require("./tools").lookup

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
  options = options or {}
  username = lookup(req.body, @_usernameField) or lookup(req.query, @_usernameField)
  password = lookup(req.body, @_passwordField) or lookup(req.query, @_passwordField)
  if not username or not password
    return @fail(
      message: options.badRequestMessage or "Missing credentials"
    , 400)
  self = this
  try
    if self._passReqToCallback
      @_verify req, username, password, verified
    else
      @_verify username, password, verified
  catch ex
    return self.error(ex)
  return


###*
Expose `Strategy`.
###
module.exports = Strategy