const db = require("./models")
const express = require('express');
const dotenv = require('dotenv');

dotenv.config();
const app = express()

const authRoute = require("./routes/auth");
const postsRoute = require("./routes/posts");
const questsRoute = require('./routes/quests');
const questionsRoute = require('./routes/questions');
const puzzlesRoute = require('./routes/puzzles');


const { dirname } = require("path");

// Middleware
app.use(express.json());
app.use(express.urlencoded({
    extended: true
  }));

// Import routes
app.use('/api/user', authRoute);
app.use('/api/posts', postsRoute);
app.use('/api/quests', questsRoute);
app.use('/api/questions', questionsRoute);
app.use('/api/puzzles', puzzlesRoute);


app.use('/src', express.static(__dirname + '/src'));



app.listen(3000, () => console.log("Server up and listening..."))

async function main() {
    await db.sequelize.sync({alter:true});
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
