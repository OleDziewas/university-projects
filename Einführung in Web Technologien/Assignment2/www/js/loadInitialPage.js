export async function loadFlights() {
  const loadbar = document.querySelector("#loading-bar");
  loadbar.style.display = "block";

  // Download data and turn it to JSON
  const fetched_results = await fetch(
    "http://217.160.241.48:3000/goto-mars/launches"
  ).catch((x) => printFail(x));
  const results = await fetched_results.json();
  loadbar.style.display = "none";

  // Select div for adding content later
  const flightDiv = document.querySelector("#flight-list");
  const data = results.data;
  //Iterate over data and add divs for flights to flight-list
  for (let index in data) {
    let div = createInfoElement(data[index]);
    flightDiv.appendChild(div);
  }

  const select = document.querySelector("#location-filter");
  // Add filter options
  const locations_with_duplicates = data.map((obj) => obj["launchSite"]);
  const locations = [...new Set(locations_with_duplicates)];

  for (let index in locations) {
    const option = document.createElement("option");
    option.innerHTML = locations[index];
    option.value = locations[index];
    select.append(option);
  }

  // Add sort options
  const sort = document.querySelector("#sorter");
  const ca = document.createElement("option");
  ca.innerHTML = "Cost (Asc)";
  ca.value = "ca";
  const cd = document.createElement("option");
  cd.innerHTML = "Cost (Desc)";
  cd.value = "cd";
  const da = document.createElement("option");
  da.innerHTML = "Date (Asc)";
  da.value = "da";
  const dd = document.createElement("option");
  dd.innerHTML = "Date (Desc)";
  dd.value = "dd";
  sort.append(ca, cd, da, dd);
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
