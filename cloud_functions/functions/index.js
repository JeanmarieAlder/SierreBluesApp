'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

exports.sendNotification = functions.database.ref("/acts/{idAct}").onWrite((event) => {
    const data = event.data;
    console.log('Act added triggered');
 
    const payload = {
        notification: {
            title: 'Sierre Blues App',
            body: 'A new act has been added',
            //badge: '1',
            sound: "default"
        }
    };

    return admin.messaging().sendToTopic("Act_Added", payload);
   
});