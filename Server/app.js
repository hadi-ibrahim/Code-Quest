const db = require("./models")
const express = require('express');
const dotenv = require('dotenv');
const fillData = require("./fillData");
const expressOasGenerator = require('express-oas-generator');


dotenv.config();
const app = express()

expressOasGenerator.init(app, {});

const userRoute = require("./routes/user");
const questsRoute = require('./routes/quests');
const questionsRoute = require('./routes/questions');
const puzzlesRoute = require('./routes/puzzles');
const categoryRoute = require('./routes/categories');



// Middleware
app.use(express.json());
app.use(express.urlencoded({
    extended: true
  }));

// Import routes
app.use('/api/user', userRoute);
app.use('/api/quests', questsRoute);
app.use('/api/questions', questionsRoute);
app.use('/api/puzzles', puzzlesRoute);
app.use('/api/categories', categoryRoute)

// static route for images
app.use('/src', express.static(__dirname + '/src'));



app.listen(3000, () => console.log("Server up and listening..."))

async function main() {
  try {
    await db.sequelize.sync({alter:true, force:true});
    await fillData();
    // await db.sequelize.sync();
  } catch (err) {
    console.log(err);
  }
}

main();

User = db.User;

// User.findByPk(1)
//     .then((one) => {
//         User.findByPk(2)
//             .then((two) => {
//                 one.setSender(two);
//                 one.firstName = "Hadi"
//                 one.save();
//             })
//     })
