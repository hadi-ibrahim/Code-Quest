const db = require("./models")
const express = require('express');

const app = express()

const authRoute = require("./routes/auth")

// Middleware
app.use(express.json());
app.use(express.urlencoded({
    extended: true
  }));

// Import routes
app.use('/api/user', authRoute);



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
