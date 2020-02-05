
var http     = require('http'),
	bodyParser = require('body-parser');

var express = require('express');

var app = express();

app.use(bodyParser.json());  // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encoded bodies
app.use(express.static('public'));



app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*"); // update to match the domain you will make the request from
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.header("Content-Security-Policy", "default-src 'self' * localhost");
  res.header("X-Content-Security-Policy", "default-src 'self' * localhost");
  next();
});

var users = {
   liss: {errores: 1, aciertos: 4},
   stefy: {errores: 1, aciertos: 10}
};

app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.get('/users/:name', function(req, res) {
  const name = req.params.name;

  if(users[name]){
      res.send(JSON.stringify(users[name]));
  } else {
      res.send("User not found: " + name);
  }

});

app.get('/login/:name/:pass', function(req, res) {
  const name = req.params.name;

  if(users[name]){
      if(users[name].pass == req.params.pass){
         res.send("Login success: " + JSON.stringify(users[name]));
      } else {
         res.send("Login failed: " + name);
      }
  } else {
      res.send("User not exists: " + name);
  }

});

app.get('/users', function (req, res) {
  res.send(users);
});

app.post('/users', function (req, res) {
//console.log(req);
  const name = req.body.name;
  if(users[name]){
      users[name].errores = req.body.errores; 
      users[name].aciertos = req.body.aciertos;
     
  } else {
      users[name] = {errores: req.body.errores, aciertos: req.body.aciertos};
  }
   
  res.send('User saved successfully: '+ name);
});


app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});


