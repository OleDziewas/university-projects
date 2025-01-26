async function applyFilter() {
  const loadbar = document.querySelector("#loading-bar");
  loadbar.style.display = "block";
  // Remove all flight elements
  const flightDiv = document.querySelector("#flight-list");
  while (flightDiv.firstChild) {
    flightDiv.firstChild.remove();
  }
  // Get filter and sort options
  const filter = document.querySelector("#location-filter");
  const sort = document.querySelector("#sorter");
  const fv = filter.value;
  const sv = sort.value;
  console.log(sv);
  // Download data and turn it to JSON
  const fetched_results = await fetch(
    "http://217.160.241.48:3000/goto-mars/launches"
  ).catch((x) => printFail(x));
  const results = await fetched_results.json();
  loadbar.style.display = "none";
  let data = results.data;
  // Apply filter and sorts
  if (fv !== "") {
    data = data.filter((obj) => obj["launchSite"] === fv);
  }

  if (sv === "cd") {
    data.sort((obj1, obj2) => obj2["costs"] - obj1["costs"]);
  } else if (sv === "ca") {
    data.sort((obj1, obj2) => obj1["costs"] - obj2["costs"]);
  } else if (sv === "dd") {
    data.sort(
      (obj1, obj2) =>
        new Date(obj2["launchTime"]) - new Date(obj1["launchTime"])
    );
  } else if (sv === "da") {
    data.sort(
      (obj1, obj2) =>
        new Date(obj1["launchTime"]) - new Date(obj2["launchTime"])
    );
  }

  // Add sorted and filtered elements
  for (let index in data) {
    let div = createInfoElement(data[index]);
    flightDiv.appendChild(div);
  }
  console.log(data);
}

function createInfoElement(flight) {
  // Create Flight Element (div)
  let div = document.createElement("div");
  div.className = "flight";
  let textinfo = document.createElement("div");
  // Add launchsite
  let launchSite = document.createElement("h2");
  launchSite.innerHTML = flight["launchSite"];
  // Add launchtime
  let launchTime = document.createElement("p");
  launchTime.innerHTML = "Time: " + convertToDate(flight["launchTime"]);
  // Add location
  let location = document.createElement("p");
  location.innerHTML = "Location: " + flight["location"];
  // Add costs
  let cost = document.createElement("p");
  cost.innerHTML = "Costs: " + flight["costs"].toLocaleString() + " €";
  // Add available seats
  let availableSeats = document.createElement("p");
  availableSeats.innerHTML = "Available Seats: " + flight["availableSeats"];
  // Compose div
  let image = document.createElement("img");
  image.src = "http://217.160.241.48:3000/" + flight["image"];
  textinfo.append(launchSite, launchTime, location, cost, availableSeats);
  div.append(textinfo, image);
  return div;
}

function convertToDate(d) {
  // Convert date to german time format
  const date = new Date(d);
  const formattedDate = date.toLocaleDateString("de-DE", {
    day: "numeric",
    month: "long",
    year: "numeric",
  });
  return formattedDate; // 22. Juni 2023
}

async function printFail(x) {
  const loadbar = document.querySelector("#loading-bar");
  loadbar.style.display = "none";
  // When fetching fails: Create error message
  const flightlist = document.querySelector("#flight-list");
  const error = document.createElement("p");
  error.innerHTML = "Could not load data.";
  flightlist.append(error);
}