import http from "http";
import express from "express";
import data from "./data.json" assert { type: "json" };
import cors from "cors";

//app und server aufsetzen 
const app = express();
app.server = http.createServer(app);
//cross site origin erlauben
app.use(cors());

// Adding a default route at /
app.get("/", (req, res) => {
  res.send("Server is up and running!");
});

//alle stats anzeigen lassen
app.get("/goto-mars/stats", (req, res) => {
  res.send(data);
});

//per index auf die einzelnen stats zugreifen
app.get("/goto-mars/stats/:index", (req, res) => {
  try{
  const dataArray = data["data"];
  const index = req.params.index;
  res.send(dataArray[index]
    );} 
  catch (error) {
    console.error("An error ocurred: " + error);
  }
})

//Server auf dem Port 3000 horchen lassen
app.server.listen(3000, () => {
  console.log(`Started on port ${app.server.address().port}`);
});
