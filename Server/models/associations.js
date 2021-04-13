function applyAssociations(db){

    const User = db.User;
    const Quest = db.Quest;
    User.belongsToMany(User, {
    through: "pending_req",
    as: "Sender",
    foreignKey: "senderID"
    })

    User.belongsToMany(User, {
    through: "pending_req",
    as: "Receiver",
    foreignKey: "ReceiverID"
    })

    User.belongsToMany(User, {
        through: "friend",
        as: "user1",
        foreignKey: "userID_1"
    })
    
    User.belongsToMany(User, {
        through: "friend",
        as: "user2",
        foreignKey: "userID_2"
    })

    User.hasOne(Quest);
    Quest.belongsTo(User)

    return db;
}
module.exports ={applyAssociations}
