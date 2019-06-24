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

app.get("/createSub", (req, res) =>
    res.render("createSubscription.pug", {keyPublishable}));

app.get("/deleteSub", (req, res) =>
    res.render("deleteSubscription.pug", {keyPublishable}));

app.post("/createSubscription", (req, res) => {

    stripe.subscriptions.create ({
        customer: req.body.customerID,
        items: [
            {
                plan: "PLAN_ID",
            },
        ]
    })
        .then(res.render("createSubscriptionSuccess.pug"));
});
app.post("/deleteSubscription", (req, res) => {
    stripe.subscriptions.del(req.body.subID)
        .then(res.render("deleteSubscriptionSuccess.pug"));
});

app.listen(80);