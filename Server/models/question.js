'use strict'
const { Model, DATE } = require('sequelize');


module.exports = (sequelize, dataTypes) => {
    class Question extends Model {
        static associate(models){

            Question.belongsTo(models.Quest);
        

        }
    };
    Question.init({
        prompt: {
            type: dataTypes.STRING,
            notNull:true,
            max:512
        },
        solution: {
            type: dataTypes.STRING,
            notNull: true,
            max:128
        },
        hint: {
            type: dataTypes.STRING,
            max:128
        }

    }, {
        sequelize,
        modelName: "Question"
    })
    return Question;
    
}