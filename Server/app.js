const db = require("./models")
const {User,Friend} = require("./models")

async function main() {
    await db.sequelize.sync({alter:true});
}

main();

// Rooro = User.create({
//     username: "ItsRooro",
//     email: "12w2",
//     password: "test",
// })

// Raziel = User.create({
//     username: "Raziel",
//     email: "!@12",
//     password: "test",
// })

// User.findByPk(1)
//     .then((one) => {
//         User.findByPk(2)
//             .then((two) => {
//                 one.setSender(two);
//                 one.firstName = "Hadi"
//                 one.save();
//             })
//     })


