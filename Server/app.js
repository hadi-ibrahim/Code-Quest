const db = require("./models")
const {User,Friend} = require("./models")

async function main() {
    await db.sequelize.sync({alter:true});
}

main();

Rooro = User.create({
    username: "ItsRooro",
    password: "test",
})

Raziel = User.create({
    username: "Raziel",
    password: "test",
})

User.findByPk(4)
    .then((one) => {
        User.findByPk(2)
            .then((two) => {
                one.setSender(two);
            })
    })
