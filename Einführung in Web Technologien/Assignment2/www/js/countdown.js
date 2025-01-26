export async function startCountdown() {
  // Download data and turn it to JSON
  const fetched_results = await fetch(
    "http://217.160.241.48:3000/goto-mars/launches"
  ).catch((x) => printFail(x));
  const results = await fetched_results.json();
  // Collect all dates from data
  const data = results.data;
  let dates = [];
  for (let index in data) {
    dates.push(data[index]["launchTime"]);
  }
  // Turn dates into js-Dates()
  dates = dates.map((date) => new Date(date));

  // Get current date as well as closest date
  let currentDate = new Date();
  let shortestDifference = Infinity;
  let closestDate;
  for (let i = 0; i < dates.length; i++) {
    let difference = Math.abs(dates[i] - currentDate);
    if (difference < shortestDifference) {
      shortestDifference = difference;
      closestDate = dates[i];
    }
  }

  // Update countdown and force rendering of DOMTree
  while (true) {
    updateCountdown(closestDate);
    await new Promise((resolve) => requestAnimationFrame(resolve));
  }
}

export function updateCountdown(closestDate) {
  let timeDifferenceSeconds = Math.abs(new Date() - closestDate) / 1000;
  // Calculate days, hours, minutes and seconds
  let days = Math.floor(timeDifferenceSeconds / (24 * 60 * 60));
  let hours = Math.floor((timeDifferenceSeconds % (24 * 60 * 60)) / (60 * 60));
  let minutes = Math.floor((timeDifferenceSeconds % (60 * 60)) / 60);
  let seconds = Math.floor(timeDifferenceSeconds % 60);
  // Update html to fit time difference
  document.querySelector("#days").innerHTML = days;
  document.querySelector("#hours").innerHTML = hours;
  document.querySelector("#minutes").innerHTML = minutes;
  document.querySelector("#seconds").innerHTML = seconds;
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