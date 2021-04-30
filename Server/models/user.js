'use strict';
const { Model, DATE } = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {

      const Quest = models.Quest;

      // ------ pending friend requests
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

      // ------ friends
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

      // -------- completed quests
      User.belongsToMany(Quest, {
        through: "completed",
        as: "completedQuest"
      })

    }
  };

  User.init({
    email: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
      validate: {
        isEmail: true,
        min: 8
      }
    },
    password: {
      type: DataTypes.STRING,
      allowNull: false,
      validate: {
        min: 8
      }
    },
    username: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
      validate: {
        min: 3,
        max: 32
      }
    },
    fullName: {
      type: DataTypes.STRING,
      max: 64
    },
    birthday: {
      type: DataTypes.DATE,
      isDate: true
    },
    imgPath: {
      type: DataTypes.STRING,
      max: 256
    }

  }, {
    sequelize,
    modelName: 'User',
  });
  return User;
};