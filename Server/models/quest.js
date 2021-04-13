'use strict'
const { Model, DATE } = require('sequelize');


module.exports = (sequelize, dataTypes) => {
    class Quest extends Model {
        static associate(models){
            // Users that completed the quest
            Quest.belongsToMany(models.User,{
                through: "completed"
            })

            Quest.belongsTo(models.Category)
        }
    };
    Quest.init({
        description: {
            type: dataTypes.STRING
        }
    }, {
        sequelize,
        modelName: "Quest"
    })
    return Quest;
    
}