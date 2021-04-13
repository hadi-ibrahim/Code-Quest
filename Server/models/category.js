'use strict'
const { Model, DATE } = require('sequelize');


module.exports = (sequelize, dataTypes) => {
    class Category extends Model {
        static associate(models){

        }
    };
    Category.init({
        title: {
            type: dataTypes.STRING
        }
    }, {
        sequelize,
        modelName: "Category"
    })
    return Category;
    
}