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
    res.render("createPayment.pug", {keyPublishable}));
app.post("/createPayment", async (req, res) => {
    try {
        let {status} = await stripe.charges.create({
            amount: 2000,
            currency: "usd",
            description: "An example charge",
            source: req.body
        });

        res.json({status});
    } catch (err) {
        res.status(500).end();
    }
});

app.listen(80);