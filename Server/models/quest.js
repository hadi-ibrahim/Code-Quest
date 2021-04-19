'use strict'
const { Model, DATE, QueryTypes } = require('sequelize');


module.exports = (sequelize, dataTypes) => {
    class Quest extends Model {
        static associate(models){
            // Users that completed the quest
            Quest.belongsToMany(models.User,{
                through: "completed",
            })

            Quest.belongsTo(models.Category)

            Quest.hasMany(models.Question);

        }
    };
    Quest.init({
        title: {
            type: dataTypes.STRING,
            max:128,
            notNull: true,
        },
        experience: {
            type: dataTypes.INTEGER,
            isNumeric: true,
            notNull: true
        },
        imgPath: {
            type: dataTypes.STRING,
            max:256
        },
        trophy: {
            type: dataTypes.STRING,
            max:64
        }
    }, {
        sequelize,
        modelName: "Quest"
    })
    return Quest;
    
}