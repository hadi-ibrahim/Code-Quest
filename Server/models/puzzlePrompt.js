'use strict'
const { Model, DATE } = require('sequelize');


module.exports = (sequelize, dataTypes) => {
    class PuzzleOption extends Model {
        static associate(models){

         PuzzleOption.belongsTo(models.Puzzle);
        

        }
    };
 PuzzleOption.init({
        option: {
            type: dataTypes.STRING,
            notNull:true,
        },
        isCorrect: {
            type: dataTypes.BOOLEAN,
            notNull: true,
        }
    }, {
        sequelize,
        modelName: "PuzzleOption"
    })
    return PuzzleOption;
    
}