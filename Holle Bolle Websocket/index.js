"use strict";
const { Server } = require("socket.io");

const namesDataset = [
  "Alice",
  "Bob",
  "Charlie",
  "Ellie",
  "Max",
  "Weshley",
  "Mark",
];
const sentencesDataset = [
  "Papier hier, {naam}.",
  "Dankuwel {naam}, Dankuwel.",
  "Goedemorgen, {naam}.",
  "Goededag, {naam}.",
  "Hey {naam}, papier hier.",
  "{naam}, heb jij wat papier voor mij?",
  "Oh {naam}, wat heb ik honger.",
  "Hey {naam}, laten we samen het park schoon houden.",
  "Hoi {naam}, heb jij een cadeautje voor mij?",
  "Ik zit nog lang niet vol, {naam}.",
];

const specialNames = ["Ellie", "Max", "Weshley", "Mark"]; // Names with a special audio file
const io = new Server(8080, {
  cors: {
    origin: "*",
  },
});

let lastProcessedTime = 0; // Track the last processed time

console.log("Socket.IO server running on port 8080");

io.on("connection", (socket) => {
  console.log("Client connected with id: " + socket.id);

  socket.on("message", (name) => {
    const currentTime = Date.now();
    if (currentTime - lastProcessedTime < 10000) {
      // 10 seconds have not yet passed
      console.log(
        `Name received too soon after the last one: '${name}'. Ignoring.`,
      );
      return; // Exit the function and do not process this name
    }
    lastProcessedTime = currentTime; // Update lastProcessedTime to current time
    console.log(`Received name from app: '${name}'`);
    processName(name, socket);
  });
});

function processName(name, socket) {
  console.log(name);
  io.emit("name", name); // Broadcast the name to all clients

  if (namesDataset.includes(name)) {
    const randomIndex = Math.floor(Math.random() * sentencesDataset.length);
    const personalizedSentence = sentencesDataset[randomIndex].replace(
      "{naam}",
      name,
    );
    console.log(`Emitting personalized sentence: '${personalizedSentence}'`);
    io.emit("personalizedSentence", personalizedSentence);

    if (specialNames.includes(name)) {
      // If the name has a special audio file associated with it
      console.log(`Emitting event to play audio for name: '${name}'`);
      io.emit("playAudio", { audioFile: `${name}Papier.wav` }); // Inform clients to play the audio
    }
  } else {
    console.log("Name not in dataset");
  }
}
