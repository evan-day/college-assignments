const keyPublishable = "STRIPE_PUBLISHABLE_KEY";
const keySecret = "STRIPE_SECRET_KEY";

var createError = require('http-errors');

const app = require('express')();
const stripe = require("stripe")(keySecret);

var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');


// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');


app.use(require("body-parser").urlencoded({extended: false}));

app.get("/", (req, res) =>
    res.render("index.pug", {keyPublishable}));

// app.get("/createCustomer", (req, res) =>
//     res.render("createCustomer.pug", {keyPublishable}));
app.listen(80);